package uk.co.bartcode.service.post

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
internal class PostEventHandler @Autowired constructor(
        private val postFactory: PostFactory,
        private val postRepository: PostRepository,
        @Value("\${application.posts-path}") private val postsPath: String
) : DocumentEventHandler {

    private val EXTENSION = ".md"

    private fun supports(path: String): Boolean {
        logger.trace("Checking if supported, path={}", path)
        return StringUtils.startsWithIgnoreCase(path, postsPath)
                && StringUtils.endsWithIgnoreCase(path, EXTENSION)
    }

    @Transactional
    override fun handleFileDeletedEvent(event: FileDeletedEvent) {
        if (supports(event.path)) {
            postRepository.deleteByPathStartingWith(event.path)
        }
    }

    @Transactional
    override fun handleFileModifiedEvent(event: FileModifiedEvent) {
        if (supports(event.path)) {
            postRepository.findByPath(event.path)
                    .ifPresent { postRepository.delete(it) }
            postRepository.save(postFactory.create(event.path))
        }
    }

    @Transactional
    override fun handleFileCreatedEvent(event: FileCreatedEvent) {
        if (supports(event.path)) {
            postRepository.save(postFactory.create(event.path))
        }
    }

    @Transactional
    override fun handleDirectoryDeletedEvent(event: DirectoryDeletedEvent) {
        if (supports(event.path)) {
            postRepository.deleteByPathStartingWith(event.path)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(PostEventHandler::class.java)
    }

}