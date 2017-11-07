package uk.co.bartcode.service.document

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.HashMap
import java.util.regex.Pattern

@Component
class PropertyExtractor {

    fun getProperties(html: String): Map<String, String> {
        val m = PATTERN_PROPERTY_BLOCK.matcher(html)
        val props = HashMap<String, String>()
        while (m.find()) {
            val commentBlock = m.group(1)
            props.putAll(extract(commentBlock))
        }
        logger.debug("Extracted: {}", props)
        return props
    }

    private fun extract(src: String): Map<String, String> {
        val m = PATTERN_PROPERTY.matcher(src)
        val props = HashMap<String, String>()
        while (m.find()) {
            props.put(m.group(1).toLowerCase(), m.group(2))
        }
        return props
    }

    companion object {
        private val PATTERN_PROPERTY_BLOCK = Pattern.compile("[^\\s]*<!--(.*?)-->", Pattern.DOTALL)
        private val PATTERN_PROPERTY = Pattern.compile("\\s*([a-zA-Z0-9_]+)\\s*[=|:]\\s*(.+)\\s*")
        private val logger = LoggerFactory.getLogger(PropertyExtractor::class.java)
    }

}