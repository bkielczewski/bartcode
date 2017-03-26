package uk.co.bartcode.service.document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String relativeUrl;

    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private String file;

    @Embedded
    private DocumentMetadata metadata;

    @Column(nullable = false, updatable = false, length = 65536)
    @Lob
    private String text;

    protected Document() {
    }

    protected Document(Builder builder) {
        relativeUrl = builder.relativeUrl;
        file = builder.file;
        metadata = builder.metadata;
        text = builder.text;
    }

    public Long getId() {
        return id;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public String getFile() {
        return file;
    }

    public DocumentMetadata getMetadata() {
        return metadata;
    }

    public String getText() {
        return text;
    }

    public interface IBuilder<T> {
        T withRelativeUrl(String relativeUrl);

        T withFile(String file);

        T withMetadata(DocumentMetadata metadata);

        T withText(String text);
    }

    public static class Builder implements IBuilder<Builder> {
        private String relativeUrl;
        private String file;
        private DocumentMetadata metadata;
        private String text;

        @Override
        public Builder withRelativeUrl(String relativeUrl) {
            this.relativeUrl = relativeUrl;
            return this;
        }

        @Override
        public Builder withFile(String file) {
            this.file = file;
            return this;
        }

        @Override
        public Builder withMetadata(DocumentMetadata metadata) {
            this.metadata = metadata;
            return this;
        }

        @Override
        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public Document build() {
            return new Document(this);
        }
    }

}
