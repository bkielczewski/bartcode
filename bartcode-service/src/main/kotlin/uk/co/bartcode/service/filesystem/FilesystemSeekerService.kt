package uk.co.bartcode.service.filesystem

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

@Service
internal class FilesystemSeekerService {

    fun seekFilesByExtensions(extensions: Set<String>, baseDir: String): List<String> {
        val filesFound = mutableListOf<String>()

        class Finder : FileVisitor<Path> {
            override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
                return FileVisitResult.CONTINUE
            }

            override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
                val extension = StringUtils.getFilenameExtension(file.toString())
                if (extension != null && extensions.contains(extension)) {
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

        logger.debug("Searching for files, extensions={}, baseDir={}", extensions, baseDir)
        val finder = Finder()
        try {
            Files.walkFileTree(Paths.get(baseDir), finder)
        } catch (e: IOException) {
            throw RuntimeException("Couldn't search for files, directory=" + baseDir, e)
        }
        return filesFound.toList()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FilesystemSeekerService::class.java)
    }

}
