package uk.co.bartcode.service.filesystem

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

import java.nio.file.StandardWatchEventKinds.*

@Service
internal class FilesystemWatcherService {

    @Async
    fun startWatchThread(path: String, consumer: (kind: WatchEvent.Kind<*>, path: String) -> Unit) {
        logger.debug("Watching for changes, path={}", path)
        try {
            val watchService = FileSystems.getDefault().newWatchService()
            registerRecursively(Paths.get(path), watchService)
            doWatchLoop(watchService, consumer)
        } catch (e: InterruptedException) {
            throw RuntimeException("Watching interrupted, path=" + path, e)
        } catch (e: IOException) {
            throw RuntimeException("Watching failed, path=" + path, e)
        }
    }

    private fun registerRecursively(start: Path, watchService: WatchService) {
        try {
            Files.walkFileTree(start, object : SimpleFileVisitor<Path>() {
                @Throws(IOException::class)
                override fun preVisitDirectory(dir: Path,
                                               attrs: BasicFileAttributes): FileVisitResult {
                    dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)
                    return FileVisitResult.CONTINUE
                }
            })
        } catch (e: NoSuchFileException) {
            logger.debug("Path does not exist anymore, ignoring", e)
        }
    }

    private fun doWatchLoop(watchService: WatchService, consumer: (kind: WatchEvent.Kind<*>, path: String) -> Unit) {
        var watchKey: WatchKey
        while (true) {
            watchKey = watchService.take()
            val parent = watchKey.watchable() as Path
            processWatchEvents(parent, watchKey.pollEvents(), watchService)
                    .forEach { (kind, path ) -> consumer(kind, path) }
            watchKey.reset()
        }
    }

    private fun processWatchEvents(parent: Path, events: List<WatchEvent<*>>,
                                   watchService: WatchService): List<Pair<WatchEvent.Kind<*>, String>> {
        val processedEvents: MutableList<Pair<WatchEvent.Kind<*>, String>> = mutableListOf()

        for (event in events) {
            val child = parent.resolve(event.context() as Path)
            val isDirectory = Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)
            if (isDirectory && event.kind() === ENTRY_CREATE) {
                logger.trace("Detected creation of the new directory={} and will watch it", child)
                registerRecursively(child, watchService)
            }
            processedEvents.add(Pair(event.kind(), child.toString()))
        }
        return processedEvents
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FilesystemWatcherService::class.java)
    }

}
