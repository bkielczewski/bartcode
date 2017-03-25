package uk.co.bartcode.service.post;

import org.springframework.data.rest.core.config.Projection;
import uk.co.bartcode.service.document.DocumentMetadata;

import java.time.ZonedDateTime;
import java.util.List;

@Projection(name = "excerpt", types = {Post.class})
interface PostExcerptProjection {

    String getId();

    String getRelativeUrl();

    DocumentMetadata getMetadata();
    String getExcerpt();
    ZonedDateTime getPublished();
    List<String> getTags();
    PostStats getStats();

}
