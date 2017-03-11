package uk.co.bartcode.service.post;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Collection;

@Entity
public class Post {

    @Id
    @NotNull
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @NotNull
    @Column(name = "source_file", nullable = false, updatable = false)
    private String sourceFile;

    @NotNull
    @Column(name = "title", nullable = false, updatable = false)
    private String title;

    @Column(name = "description", updatable = false)
    private String description;

    @NotNull
    @Column(name = "text", nullable = false, updatable = false, length = 65536)
    @Lob
    private String text;

    @ElementCollection
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag", updatable = false)
    private Collection<String> tags;

    @NotNull
    @Column(name = "published", nullable = false, updatable = false)
    private LocalTime published;

    @Embedded
    private PostStats stats;

    private int popularity;

    public String getId() {
        return id;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getText() {
        return text;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public LocalTime getPublished() {
        return published;
    }

    public PostStats getStats() {
        return stats;
    }

    public int getPopularity() {
        return popularity;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }

}
