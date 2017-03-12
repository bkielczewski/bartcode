package uk.co.bartcode.service.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.bartcode.service.filesystem.FilesystemChangeEvent;

import static uk.co.bartcode.service.filesystem.EventType.*;

@Service
class PostSourceFileChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(PostSourceFileChangeListener.class);
    private final PostFileReader postFileReader;
    private final PostRepository postRepository;

    @Autowired
    PostSourceFileChangeListener(PostFileReader postFileReader, PostRepository postRepository) {
        this.postFileReader = postFileReader;
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
            postRepository.save(postFileReader.readFromFile(event.getPath()));
        } else {
            logger.debug("Ignoring, event={}", event);
        }
    }

}
