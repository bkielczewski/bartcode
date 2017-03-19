package uk.co.bartcode.service.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uk.co.bartcode.service.facebook.FacebookStatsService;

import java.util.List;

@Service
class PostStatsService {

    private static final Logger logger = LoggerFactory.getLogger(PostStatsService.class);
    private final PostRepository postRepository;
    private final FacebookStatsService facebookStatsService;

    @Autowired
    public PostStatsService(PostRepository postRepository, FacebookStatsService facebookStatsService) {
        this.postRepository = postRepository;
        this.facebookStatsService = facebookStatsService;
    }

    @Scheduled(initialDelay = 3600000, fixedRate = 3600000)
    public void updateAll() {
        logger.debug("Updating post stats");
        List<Post> documents = postRepository.findAll();
        documents.forEach(this::updateAndSave);
    }

    private void updateAndSave(Post post) {
        PostStats current = post.getStats();
        PostStats updated = getUpdatedStats(post.getCanonicalUrl());
        current.setComments(updated.getComments());
        current.setShares(updated.getShares());
        postRepository.save(post);
    }

    public PostStats getUpdatedStats(String url) {
        return facebookStatsService.getStats(url);
    }

    public int calculatePopularity(PostStats stats) {
        return (2 * stats.getShares()) * stats.getComments();
    }

}
