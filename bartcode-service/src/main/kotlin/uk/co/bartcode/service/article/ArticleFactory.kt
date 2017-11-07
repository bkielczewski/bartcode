package uk.co.bartcode.service.article

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import uk.co.bartcode.service.document.MarkdownParser
import uk.co.bartcode.service.document.MetadataGenerator
import uk.co.bartcode.service.document.PropertyExtractor

@Component
internal class ArticleFactory @Autowired constructor(
        private val markdownParser: MarkdownParser,
        private val propertyExtractor: PropertyExtractor,
        private val metadataGenerator: MetadataGenerator
){

    fun create(path: String): Article {
        logger.debug("Creating article, path={}", path)
        val relativeUrl = generateRelativeUrl(path)
        val html = markdownParser.parse(path)
        val metadata = metadataGenerator.generate(propertyExtractor.getProperties(html), relativeUrl)
        return Article(null, relativeUrl, path, metadata, html)
    }

    private fun generateRelativeUrl(path: String): String {
        return String.format("/%s", StringUtils.stripFilenameExtension(StringUtils.getFilename(path)!!))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ArticleFactory::class.java)
    }

}