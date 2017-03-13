package uk.co.bartcode.service.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.bartcode.service.filesystem.FilesystemChangeEvent;

import static uk.co.bartcode.service.filesystem.EventType.*;

@Service
class PostImporterService {

    private static final Logger logger = LoggerFactory.getLogger(PostImporterService.class);
    private final PostFactoryService postFactory;
    private final PostStatsService statsService;
    private final PostRepository postRepository;

    @Autowired
    PostImporterService(PostFactoryService postFactory,
                        PostStatsService statsService,
                        PostRepository postRepository) {
        this.postFactory = postFactory;
        this.statsService = statsService;
        this.postRepository = postRepository;
    }

    @EventListener(FilesystemChangeEvent.class)
    void onFilesystemChangeEvent(FilesystemChangeEvent event) {
        logger.debug("Handling, event={}", event);
        if (event.isDirectory() && event.getType().equals(DELETE)) {
            logger.trace("Deleting documents in directory={}", event.getPath());
            postRepository.deleteByFileStartingWith(event.getPath());
        } else if (event.getType().equals(CREATE) || event.getType().equals(MODIFY)) {
            postRepository.getByFile(event.getPath()).ifPresent((post) -> {
                logger.trace("Deleting post, id={}", post.getId());
                postRepository.delete(post);
            });
            createAndUpdateStats(event.getPath());
        } else {
            logger.debug("Ignoring, event={}", event);
        }
    }

    private void createAndUpdateStats(String file) {
        Post post = postFactory.create(file);
        post.setStats(statsService.getUpdatedStats(post.getCanonicalUrl()));
        post.setPopularity(statsService.calculatePopularity(post.getStats()));
        postRepository.save(post);
    }

}
