package uk.co.bartcode.service.post;

import org.springframework.data.rest.core.config.Projection;

import java.time.ZonedDateTime;
import java.util.List;

@Projection(name = "excerpt", types = {Post.class})
public interface PostExcerptProjection {
    String getId();

    String getTitle();

    String getExcerpt();

    ZonedDateTime getPublished();

    List<String> getTags();

    PostStats getStats();
}
