package uk.co.bartcode.service.document;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocumentMetadata {

    @Column(nullable = false, updatable = false)
    private String canonicalUrl;

    @Column(nullable = false, updatable = false)
    private String title;

    @Column(updatable = false)
    private String description;

    private DocumentMetadata() {
    }

    public DocumentMetadata(String canonicalUrl, String title, String description) {
        this.canonicalUrl = canonicalUrl;
        this.title = title;
        this.description = description;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
