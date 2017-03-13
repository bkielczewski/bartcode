package uk.co.bartcode.service.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "slug", nullable = false, updatable = false)
    private String slug;

    @JsonIgnore
    @Column(name = "file", nullable = false, updatable = false)
    private String file;

    @Column(name = "canonicalUrl", nullable = false, updatable = false)
    private String canonicalUrl;

    @Column(name = "title", nullable = false, updatable = false)
    private String title;

    @Column(name = "description", updatable = false)
    private String description;

    @Column(name = "excerpt", nullable = false, updatable = false, length = 1024)
    @Lob
    private String excerpt;

    @Column(name = "text", nullable = false, updatable = false, length = 65536)
    @Lob
    private String text;

    @Column(name = "published", nullable = false, updatable = false)
    private ZonedDateTime published;

    @ElementCollection
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag", updatable = false)
    private List<String> tags;

    @Embedded
    private PostStats stats;

    @JsonIgnore
    @Column(name = "popularity", nullable = false)
    private int popularity;

    Post() {
    }

    private Post(Builder builder) {
        slug = builder.slug;
        file = builder.file;
        canonicalUrl = builder.canonicalUrl;
        title = builder.title;
        description = builder.description;
        excerpt = builder.excerpt;
        text = builder.text;
        published = builder.published;
        tags = builder.tags;
        stats = builder.stats;
        popularity = builder.popularity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getFile() {
        return file;
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

    public String getExcerpt() {
        return excerpt;
    }

    public String getText() {
        return text;
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

    public void setStats(PostStats stats) {
        this.stats = stats;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }

    public static final class Builder {
        private String slug;
        private String file;
        private String canonicalUrl;
        private String title;
        private String description;
        private String excerpt;
        private String text;
        private List<String> tags;
        private ZonedDateTime published;
        private PostStats stats = new PostStats();
        private int popularity = 0;

        private Builder() {
        }

        public Builder withSlug(String slug) {
            this.slug = slug;
            return this;
        }

        public Builder withFile(String file) {
            this.file = file;
            return this;
        }

        public Builder withCanonicalUrl(String canonicalUrl) {
            this.canonicalUrl = canonicalUrl;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withExcerpt(String excerpt) {
            this.excerpt = excerpt;
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withPublished(ZonedDateTime published) {
            this.published = published;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }

}
