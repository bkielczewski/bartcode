package uk.co.bartcode.service.document

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

@Component
class MetadataGenerator(@Value("\${application.base-url}") private val baseUrl: String) {

    fun generate(properties: Map<String, String>, relativeUrl: String): DocumentMetadata {
        val canonicalUrl = properties.getOrDefault("canonicalurl", generateCanonicalUrl(relativeUrl))
        val title = properties.getOrDefault("title", generateTitle(relativeUrl))
        return DocumentMetadata(canonicalUrl, title, properties["description"].orEmpty())
    }

    private fun generateCanonicalUrl(relativeUrl: String): String = baseUrl + relativeUrl

    private fun generateTitle(relativeUrl: String): String {
        return StringUtils.capitalize(relativeUrl
                .substring(relativeUrl.lastIndexOf("/") + 1)
                .replace("-", " ")
                .replaceFirst("/$".toRegex(), ""))
    }

}
