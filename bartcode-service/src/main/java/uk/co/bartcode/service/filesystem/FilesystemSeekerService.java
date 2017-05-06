package uk.co.bartcode.service.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
class FilesystemSeekerService {

    private static final Logger logger = LoggerFactory.getLogger(FilesystemSeekerService.class);

    void seekFilesByExtension(String extension, String baseDir, Consumer<FilesystemChangeEvent> consumer) {
        logger.debug("Searching for files, extension={}, baseDir={}", extension, baseDir);
        Finder finder = new Finder(extension);
        try {
            Files.walkFileTree(Paths.get(baseDir), finder);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't search for files, directory=" + baseDir, e);
        }
        finder.filesFound
                .forEach(file -> consumer.accept(
                        new FilesystemChangeEvent(this, file, EventType.CREATE)));
    }

    private static final class Finder implements FileVisitor<Path> {
        private final String extension;
        private final List<String> filesFound = new ArrayList<>();

        private Finder(final String extension) {
            this.extension = extension;
        }

        @Override
        public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            if (file.toString().endsWith(extension)) {
                logger.trace("Found file {}", file.toString());
                filesFound.add(file.toString());
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

}
