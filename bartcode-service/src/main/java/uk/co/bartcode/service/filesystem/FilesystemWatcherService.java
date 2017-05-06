package uk.co.bartcode.service.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.*;

@Service
class FilesystemWatcherService {

    private static final Logger logger = LoggerFactory.getLogger(FilesystemWatcherService.class);

    @Async
    void startWatchThread(String baseDir, Consumer<FilesystemChangeEvent> consumer) {
        logger.debug("Watching for changes, baseDir={}", baseDir);
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            registerRecursively(Paths.get(baseDir), watchService);
            WatchKey watchKey;

            //noinspection InfiniteLoopStatement
            for (; ; ) {
                watchKey = watchService.take();
                Path parent = (Path) watchKey.watchable();
                processWatchEvents(parent, watchKey.pollEvents(), watchService, consumer);
                watchKey.reset();
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException("Watching interrupted, baseDir=" + baseDir, e);
        }
    }

    private void registerRecursively(Path start, final WatchService watchService) throws IOException {
        try {
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(final Path dir,
                                                         final BasicFileAttributes attrs) throws IOException {
                    dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (NoSuchFileException e) {
            logger.debug("Path does not exist anymore, ignoring", e);
        }
    }

    private void processWatchEvents(Path parent, List<WatchEvent<?>> events,
                                    WatchService watchService,
                                    Consumer<FilesystemChangeEvent> consumer) throws IOException {
        for (WatchEvent<?> event : events) {
            Path child = parent.resolve((Path) event.context());
            boolean isDirectory = Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS);
            if (isDirectory && event.kind() == ENTRY_CREATE) {
                logger.trace("Detected creation of the new directory={}, will add it to watch", child);
                registerRecursively(child, watchService);
            }
            consumer.accept(new FilesystemChangeEvent(this, child.toString(), EventType.valueOf(event.kind())));
        }
    }

}
