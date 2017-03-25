package uk.co.bartcode.service.post;

import uk.co.bartcode.service.document.Document;
import uk.co.bartcode.service.document.DocumentMetadata;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
public final class Post extends Document {

    @Column(nullable = false, updatable = false, length = 1024)
    @Lob
    private String excerpt;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime published;

    @ElementCollection
    @CollectionTable
    @Column(updatable = false)
    private List<String> tags;

    @Embedded
    private PostStats stats;

    private Post() {
        super();
    }

    private Post(Builder builder) {
        super(builder.baseBuilder);
        excerpt = builder.excerpt;
        published = builder.published;
        tags = builder.tags;
        stats = builder.stats;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public ZonedDateTime getPublished() {
        return published;
    }

    public List<String> getTags() {
        return tags;
    }

    public PostStats getStats() {
        return stats;
    }

    public static final class Builder implements IBuilder<Builder> {
        private Document.Builder baseBuilder = new Document.Builder();
        private String excerpt;
        private ZonedDateTime published;
        private List<String> tags;
        private PostStats stats;

        public Builder() {
        }

        @Override
        public Builder withRelativeUrl(String relativeUrl) {
            baseBuilder.withRelativeUrl(relativeUrl);
            return this;
        }

        @Override
        public Builder withFile(String file) {
            baseBuilder.withFile(file);
            return this;
        }

        @Override
        public Builder withMetadata(DocumentMetadata metadata) {
            baseBuilder.withMetadata(metadata);
            return this;
        }

        @Override
        public Builder withText(String text) {
            baseBuilder.withText(text);
            return this;
        }

        public Builder withExcerpt(String excerpt) {
            this.excerpt = excerpt;
            return this;
        }

        public Builder withPublished(ZonedDateTime published) {
            this.published = published;
            return this;
        }

        public Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withStats(PostStats stats) {
            this.stats = stats;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
