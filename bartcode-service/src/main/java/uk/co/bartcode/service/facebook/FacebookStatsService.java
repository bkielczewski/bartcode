package uk.co.bartcode.service.facebook;

import uk.co.bartcode.service.post.PostStats;

public interface FacebookStatsService {

    PostStats getStats(String documentId);

}
