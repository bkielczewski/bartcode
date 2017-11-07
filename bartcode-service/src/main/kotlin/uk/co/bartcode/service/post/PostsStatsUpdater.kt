package uk.co.bartcode.service.post

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import uk.co.bartcode.service.facebook.FacebookService

@Component
internal class PostsStatsUpdater @Autowired constructor(
    private val postRepository: PostRepository,
    private val facebookService: FacebookService
) {

    @Scheduled(initialDelay = 3600000, fixedRate = 3600000)
    private fun updateAll() {
        logger.debug("Updating post stats")
        postRepository.findAll()
                .forEach { this.updateAndSave(it) }
    }

    private fun updateAndSave(post: Post) {
        val ogObject = facebookService.getStats(post.metadata.canonicalUrl)
        if (ogObject !== null) {
            post.stats.comments = ogObject.share?.commentCount ?: 0
            post.stats.shares = ogObject.share?.shareCount ?: 0
            postRepository.save(post)
        }
    }

    companion object {
        private var logger = LoggerFactory.getLogger(PostsStatsUpdater::class.java)
    }

}