package uk.co.bartcode.service.filesystem

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

@Service
internal class FilesystemSeekerService {

    fun seekFilesByExtension(extension: String, baseDir: String): List<String> {
        logger.debug("Searching for files, extension={}, baseDir={}", extension, baseDir)
        val finder = Finder(extension)
        try {
            Files.walkFileTree(Paths.get(baseDir), finder)
        } catch (e: IOException) {
            throw RuntimeException("Couldn't search for files, directory=" + baseDir, e)
        }
        return finder.filesFound.toList()
    }

    private class Finder (private val extension: String) : FileVisitor<Path> {
        val filesFound = mutableListOf<String>()

        override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
            return FileVisitResult.CONTINUE
        }

        override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
            if (file.toString().endsWith(extension)) {
                logger.trace("Found file {}", file)
                filesFound.add(file.toString())
            }
            return FileVisitResult.CONTINUE
        }

        override fun visitFileFailed(file: Path, exc: IOException?): FileVisitResult {
            return FileVisitResult.CONTINUE
        }

        override fun postVisitDirectory(dir: Path, exc: IOException?): FileVisitResult {
            return FileVisitResult.CONTINUE
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FilesystemSeekerService::class.java)
    }

}