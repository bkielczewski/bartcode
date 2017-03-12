package uk.co.bartcode.service.post;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PostStats {

    @Column(name = "shares", nullable = false)
    private int shares = 0;

    @Column(name = "comments", nullable = false)
    private int comments = 0;

    public int getShares() {
        return shares;
    }

    public int getComments() {
        return comments;
    }

}
