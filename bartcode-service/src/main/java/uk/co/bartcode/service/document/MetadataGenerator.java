package uk.co.bartcode.service.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
public class MetadataGenerator {

    private final String baseUrl;

    @Autowired
    public MetadataGenerator(@Value("${application.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public DocumentMetadata generate(Map<String, String> properties, String relativeUrl) {
        String canonicalUrl = properties.getOrDefault("canonicalUrl", generateCanonicalUrl(relativeUrl));
        String title = properties.getOrDefault("title", generateTitle(relativeUrl));
        return new DocumentMetadata(canonicalUrl, title, properties.get("description"));
    }

    private String generateCanonicalUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }

    private String generateTitle(String relativeUrl) {
        return StringUtils.capitalize(relativeUrl
                .substring(relativeUrl.lastIndexOf("/") + 1)
                .replace("-", " ")
                .replaceFirst("/$", ""));
    }

}
