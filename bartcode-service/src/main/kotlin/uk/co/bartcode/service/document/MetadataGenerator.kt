package uk.co.bartcode.service.document

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

@Component
class MetadataGenerator @Autowired constructor(
        @Value("\${application.base-url}") private val baseUrl: String
) {

    fun generate(properties: Map<String, String>, relativeUrl: String): Metadata {
        val canonicalUrl = properties.getOrDefault("canonicalUrl", generateCanonicalUrl(relativeUrl))
        val title = properties.getOrDefault("title", generateTitle(relativeUrl))
        return Metadata(canonicalUrl, title, properties["description"].orEmpty())
    }

    private fun generateCanonicalUrl(relativeUrl: String): String {
        return baseUrl + relativeUrl
    }

    private fun generateTitle(relativeUrl: String): String {
        return StringUtils.capitalize(relativeUrl
                .substring(relativeUrl.lastIndexOf("/") + 1)
                .replace("-", " ")
                .replaceFirst("/$".toRegex(), ""))
    }

}
