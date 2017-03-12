package uk.co.bartcode.service.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.BiConsumer;

import static java.nio.file.StandardWatchEventKinds.*;

@Service
class FilesystemWatcherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilesystemWatcherService.class);

    @Async
    @SuppressWarnings("SameParameterValue")
    void startWatchThread(String baseDir, BiConsumer<String, WatchEvent.Kind> consumer) {
        LOGGER.debug("Watching for changes, baseDir={}", baseDir);
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
                    if (!isIgnoredPath(dir.toString())) {
                        dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (NoSuchFileException e) {
            LOGGER.debug("Path does not exist anymore, ignoring", e);
        }
    }

    private boolean isIgnoredPath(String dir) {
        return dir.startsWith(".");
    }

    private void processWatchEvents(Path parent, List<WatchEvent<?>> events,
                                    WatchService watchService,
                                    BiConsumer<String, WatchEvent.Kind> consumer) throws IOException {
        for (WatchEvent<?> event : events) {
            Path child = parent.resolve((Path) event.context());
            if (event.kind() == ENTRY_CREATE && Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                LOGGER.trace("Detected creation of the new directory={}, will add it to watch", child);
                registerRecursively(child, watchService);
            }
            consumer.accept(child.toString(), event.kind());
        }
    }

}
