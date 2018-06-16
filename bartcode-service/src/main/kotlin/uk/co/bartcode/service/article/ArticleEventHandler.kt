package uk.co.bartcode.service.article

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import uk.co.bartcode.service.filesystem.*
import javax.transaction.Transactional

@Component
internal class ArticleEventHandler(
        private val factory: ArticleFactory,
        private val repository: ArticleRepository,
        @Value("\${application.data-path}\${application.articles-path}") private val path: String
) : FilesystemEventHandler {

    @Transactional
    override fun handleFileDeletedEvent(event: FileDeletedEvent) {
        if (supportsFile(event.path)) {
            deleteExistingIfExists(event.path)
        }
    }

    private fun supportsFile(path: String): Boolean {
        logger.debug("Checking if file supported, path={}", path)
        return supportsDirectory(path) && StringUtils.endsWithIgnoreCase(path, EXTENSION)
    }

    private fun supportsDirectory(path: String): Boolean {
        logger.debug("Checking if directory supported, path={}", path)
        return StringUtils.startsWithIgnoreCase(path, this.path)
    }

    private fun deleteExistingIfExists(file: String) {
        repository.findByFile(file).ifPresent {
            repository.delete(it)
        }
    }

    @Transactional
    override fun handleFileModifiedEvent(event: FileModifiedEvent) {
        if (supportsFile(event.path)) {
            logger.debug("Updating, path={}", event.path)
            deleteExistingIfExists(event.path)
            repository.save(factory.create(event.path))
        }
    }

    @Transactional
    override fun handleFileCreatedEvent(event: FileCreatedEvent) {
        if (supportsFile(event.path)) {
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
        private const val EXTENSION = ".md"
        private val logger = LoggerFactory.getLogger(ArticleEventHandler::class.java)
    }

}