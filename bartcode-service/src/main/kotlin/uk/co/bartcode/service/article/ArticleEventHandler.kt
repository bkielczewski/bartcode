package uk.co.bartcode.service.article

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import uk.co.bartcode.service.document.DocumentEventHandler
import uk.co.bartcode.service.filesystem.DirectoryDeletedEvent
import uk.co.bartcode.service.filesystem.FileCreatedEvent
import uk.co.bartcode.service.filesystem.FileDeletedEvent
import uk.co.bartcode.service.filesystem.FileModifiedEvent
import javax.transaction.Transactional

@Component
internal class ArticleEventHandler @Autowired constructor(
        private val articleFactory: ArticleFactory,
        private val articleRepository: ArticleRepository,
        @Value("\${application.articles-path}") private val articlesPath: String
) : DocumentEventHandler {

    private val EXTENSION = ".md"

    private fun supportsFile(path: String): Boolean {
        return supportsDirectory(path) && StringUtils.endsWithIgnoreCase(path, EXTENSION)
    }

    private fun supportsDirectory(path: String): Boolean {
        return StringUtils.startsWithIgnoreCase(path, articlesPath)
    }

    @Transactional
    override fun handleFileDeletedEvent(event: FileDeletedEvent) {
        if (supportsFile(event.path)) {
            deleteExistingIfExists(event.path)
        }
    }

    private fun deleteExistingIfExists(file: String) {
        articleRepository.findByFile(file).ifPresent {
            logger.debug("Deleting, file={}", file)
            articleRepository.delete(it)
        }
    }

    @Transactional
    override fun handleFileModifiedEvent(event: FileModifiedEvent) {
        if (supportsFile(event.path)) {
            deleteExistingIfExists(event.path)
            articleRepository.save(articleFactory.create(event.path))
        }
    }

    @Transactional
    override fun handleFileCreatedEvent(event: FileCreatedEvent) {
        if (supportsFile(event.path)) {
            deleteExistingIfExists(event.path)
            articleRepository.save(articleFactory.create(event.path))
        }
    }

    @Transactional
    override fun handleDirectoryDeletedEvent(event: DirectoryDeletedEvent) {
        if (supportsDirectory(event.path)) {
            logger.debug("Deleting, prefix={}", event.path)
            articleRepository.deleteByFileStartingWith(event.path)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ArticleEventHandler::class.java)
    }

}