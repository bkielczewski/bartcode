package uk.co.bartcode.service.post;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PostStats {

    @Column(name = "shares", nullable = false)
    private int shares = 0;

    @Column(name = "comments", nullable = false)
    private int comments = 0;

    public PostStats() {
    }

    public PostStats(int shares, int comments) {
        this.shares = shares;
        this.comments = comments;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("shares", shares)
                .add("comments", comments)
                .toString();
    }
}
