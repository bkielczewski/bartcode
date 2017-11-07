package uk.co.bartcode.service.filesystem

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.*

@Component
internal class FilesystemEventPublisher @Autowired constructor(
        private val seekerService: FilesystemSeekerService,
        private val watcherService: FilesystemWatcherService,
        private val eventPublisher: ApplicationEventPublisher,
        @Value("\${application.data-path}") private val dataPath: String
) {

    private val EXTENSION = "md"

    @EventListener(ApplicationReadyEvent::class)
    private fun init() {
        notifyAboutExistingFiles(dataPath)
        notifyAboutFutureChanges(dataPath)
    }

    private fun notifyAboutExistingFiles(path: String) {
        seekerService.seekFilesByExtension(EXTENSION, path)
                .forEach {
                    val event = FileCreatedEvent(this, it)
                    logger.trace("Publishing event={}", event)
                    eventPublisher.publishEvent(event)
                }
    }

    private fun notifyAboutFutureChanges(path: String) {
        watcherService.startWatchThread(path) { kind, eventPath -> processWatchEvent(kind, eventPath) }
    }

    private fun processWatchEvent(kind: WatchEvent.Kind<*>, path: String) {
        val directory = isDirectory(path)
        if (kind == ENTRY_CREATE && directory) {
            notifyAboutExistingFiles(path)
        }
        publishEvent(directory, kind, path)
    }

    private fun isDirectory(path : String): Boolean {
        return Files.isDirectory(Paths.get(path), LinkOption.NOFOLLOW_LINKS)
    }

    private fun publishEvent(directory: Boolean, kind: WatchEvent.Kind<*>, path: String) {
        val event = if (directory) {
            getEventForDirectory(kind, path)
        } else {
            getEventForFile(kind, path)
        }
        if (event !== null) {
            logger.trace("Publishing event={}", event)
            eventPublisher.publishEvent(event)
        }
    }

    private fun getEventForFile(kind: WatchEvent.Kind<*>, path: String): FilesystemEvent? {
        when (kind) {
            StandardWatchEventKinds.ENTRY_CREATE -> return FileCreatedEvent(this, path)
            StandardWatchEventKinds.ENTRY_MODIFY -> return FileModifiedEvent(this, path)
            StandardWatchEventKinds.ENTRY_DELETE -> return FileDeletedEvent(this, path)
            else -> return null
        }
    }

    private fun getEventForDirectory(kind: WatchEvent.Kind<*>, path: String): FilesystemEvent? {
        when (kind) {
            StandardWatchEventKinds.ENTRY_DELETE -> return DirectoryDeletedEvent(this, path)
            else -> return null
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FilesystemEventPublisher::class.java)
    }

}

