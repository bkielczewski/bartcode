package uk.co.bartcode.service.image

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import uk.co.bartcode.service.filesystem.*
import java.util.regex.Pattern
import javax.transaction.Transactional

@Component
internal class ImageEventHandler(
        private val factory: ImageFactory,
        private val repository: ImageRepository,
        @Value("\${application.data-path}\${application.images-path}") private val path: String
) : FilesystemEventHandler {


    @Transactional
    override fun handleFileDeletedEvent(event: FileDeletedEvent) {
        if (supportsDirectory(event.path)) {
            logger.debug("Deleting, path={}", event.path)
            deleteExistingIfExists(event.path)
        }
    }

    private fun supportsFile(path: String): Boolean {
        return supportsDirectory(path) &&
                EXTENSIONS.contains(StringUtils.getFilenameExtension(path).orEmpty().toLowerCase())
    }

    private fun supportsDirectory(path: String): Boolean {
        logger.debug("Checking if directory supported, path={}", path)
        return StringUtils.startsWithIgnoreCase(path, this.path) && !previewRegexp.matcher(path).matches()
    }

    private fun deleteExistingIfExists(file: String) {
        repository.findByFile(file).ifPresent {
            repository.delete(it)
        }
    }

    @Transactional
    override fun handleFileModifiedEvent(event: FileModifiedEvent) {
        if (supportsDirectory(event.path)) {
            logger.debug("Updating, path={}", event.path)
            deleteExistingIfExists(event.path)
            repository.save(factory.create(event.path))
        }
    }

    @Transactional
    override fun handleFileCreatedEvent(event: FileCreatedEvent) {
        if (supportsDirectory(event.path)) {
            logger.debug("Creating, path={}", event.path)
            deleteExistingIfExists(event.path)
            repository.save(factory.create(event.path))
        }
    }

    @Transactional
    override fun handleDirectoryDeletedEvent(event: DirectoryDeletedEvent) {
        if (supportsDirectory(event.path)) {
            logger.debug("Deleting, path={}", event.path)
            repository.deleteByFileStartingWith(event.path)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ImageEventHandler::class.java)
        private val previewRegexp = Pattern.compile(".*/preview/[^/]*$")
        private val EXTENSIONS = setOf("png", "jpg", "jpeg")
    }

}