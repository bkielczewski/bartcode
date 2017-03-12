package uk.co.bartcode.service.post;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TagPostCount {

    @Id
    @Column(name = "tag", nullable = false, updatable = false)
    private String tag;

    @Column(name = "count", nullable = false, updatable = false)
    private long count;

    TagPostCount() {
    }

    public TagPostCount(String tag, long count) {
        this.tag = tag;
        this.count = count;
    }

    public String getTag() {
        return tag;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tag", tag)
                .add("count", count)
                .toString();
    }
}
