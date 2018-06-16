package uk.co.bartcode.service.image

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLEncoder


@Component
internal class ImageFactory(
        @Value("\${application.data-path}") private val dataPath: String,
        @Value("#{'\${application.images-preview-sizes}'.split(',')}") private val previewSizes: Set<String>,
        private val imagePreviewService: ImagePreviewService
) {

    fun create(path: String): Image {
        logger.debug("Creating image, path={}", path)
        val relativeUrl = generateRelativeUrl(path)
        return Image(null, relativeUrl, path, generatePreviews(path))
    }

    private fun generateRelativeUrl(path: String): String {
        return path.replace(dataPath, "")
                .split("/")
                .joinToString("/") { segment -> URLEncoder.encode(segment, "UTF-8") }
    }

    private fun generatePreviews(path: String): Map<String, Image.Preview> {
        try {
            val previews = mutableMapOf<String, Image.Preview>()
            val previewDir = path.substring(0, path.lastIndexOf('/') + 1) + PREVIEW_DIR
            val baseFilename = StringUtils.stripFilenameExtension(StringUtils.getFilename(path)!!)

            createDirIfNotExists(previewDir)

            previewSizes.forEach {
                val previewPath = "$previewDir/${baseFilename}_${it}.$PREVIEW_FORMAT"
                previews.put(it, createPreview(it, path, previewPath))
            }

            return previews
        } catch (e: IOException) {
            logger.error("Error while generating previews, path={}", path, e)
            return emptyMap()
        }
    }

    private fun createDirIfNotExists(path: String) {
        with(File(path)) {
            if (!exists()) {
                logger.trace("Creating preview directory, path={}", path)
                mkdir()
            }
        }
    }

    private fun createPreview(size: String, originalPath: String, previewPath: String): Image.Preview {
        val previewFile = File(previewPath)
        if (!previewFile.exists()) {
            logger.trace("Generating preview, size={}, path={}", size, previewPath)
            val (width, height) = size.split("x").map { it.toInt() }
            if (width > 0 && height > 0) {
                FileInputStream(originalPath).use { input ->
                    FileOutputStream(previewFile).use { output ->
                        imagePreviewService.writePreview(width, height, PREVIEW_FORMAT, input, output)
                    }
                }
            }
        }
        return Image.Preview(null, generateRelativeUrl(previewPath), previewPath)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ImageFactory::class.java)
        private val PREVIEW_DIR = "preview"
        private val PREVIEW_FORMAT = "png"
    }

}