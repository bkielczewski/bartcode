package uk.co.bartcode.service.post;

import org.springframework.stereotype.Service;

@Service
class PostStatsService {

    PostStats getPostsStats(String id) {
        return new PostStats();
    }

}
