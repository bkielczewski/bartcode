package uk.co.bartcode.service.post

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import uk.co.bartcode.service.document.MarkdownParser
import uk.co.bartcode.service.document.MetadataGenerator
import uk.co.bartcode.service.document.PropertyExtractor
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

@Component
internal class PostFactory @Autowired constructor(
        private val markdownParser: MarkdownParser,
        private val propertyExtractor: PropertyExtractor,
        private val metadataGenerator: MetadataGenerator
) {

    fun create(path: String): Post {
        logger.debug("Creating post, path={}", path)
        val body = markdownParser.parse(path)
        val excerpt = generateExcerpt(body)
        val properties = propertyExtractor.getProperties(body)
        val published = generatePublished(path, properties.get("date"))
        val relativeUrl = generateRelativeUrl(path, published)
        val metadata = metadataGenerator.generate(properties, relativeUrl)
        val tags = generateTags(properties.get("tags"))
        val stats = PostStats(0, 0)
        return Post(null, relativeUrl, path, metadata, body, excerpt, published, tags, stats)
    }

    private fun generatePublished(file: String, customDate: String?): Date {
        if (customDate == null) {
            return Date(Files.getLastModifiedTime(Paths.get(file)).toMillis())
        } else {
            return SimpleDateFormat("dd/MM/yyyy").parse(customDate)
        }
    }

    private fun generateRelativeUrl(file: String, published: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = published
        return String.format("/%s/%s/%s",
                calendar.get(Calendar.YEAR),
                String.format("%02d", calendar.get(Calendar.MONTH) + 1),
                StringUtils.stripFilenameExtension(StringUtils.getFilename(file)!!))
    }


    private fun generateExcerpt(src: String): String {
        val matcher = PATTERN_EXCERPT_END.matcher(src)
        return if (matcher.find()) src.substring(0, matcher.start()) else src
    }

    private fun generateTags(tags: String?): List<String> {
        return if (tags == null) emptyList() else tags
                .split(',')
                .filter { tag -> tag.isNotEmpty() }
                .map { tag -> tag.trim() }
    }


    companion object {
        private val logger = LoggerFactory.getLogger(PostFactory::class.java)
        private val PATTERN_EXCERPT_END = Pattern.compile("<!--[\\s]*more[\\s]*-->", Pattern.CASE_INSENSITIVE)
    }

}