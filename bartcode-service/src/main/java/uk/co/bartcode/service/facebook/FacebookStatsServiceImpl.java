package uk.co.bartcode.service.facebook;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.SocialException;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import uk.co.bartcode.service.post.PostStats;

import java.util.Collections;

@Service
class FacebookStatsServiceImpl implements FacebookStatsService {

    private static final Logger logger = LoggerFactory.getLogger(FacebookStatsServiceImpl.class);
    private static final String COMMENT_COUNT = "comment_count";
    private static final String SHARE_COUNT = "share_count";
    private static final FacebookTemplate facebookTemplate = new FacebookTemplate("");

    @Override
    public PostStats getStats(String url) {
        try {
            logger.debug("Getting Facebook stats, url={}", url);
            JsonNode ogObjectNode = facebookTemplate.fetchObject("/", JsonNode.class, getParams(url));
            JsonNode shareNode = ogObjectNode.get("share");
            if (shareNode == null) {
                return new PostStats();
            } else {
                int comments = (shareNode.has(COMMENT_COUNT)) ? shareNode.get(COMMENT_COUNT).asInt() : 0;
                int shares = (shareNode.has(SHARE_COUNT)) ? shareNode.get(SHARE_COUNT).asInt() : 0;
                logger.trace("Received stats, url={}, comments={}, shares={}", url, comments, shares);
                return new PostStats(shares, comments);
            }
        } catch (SocialException e) {
            logger.warn("Error fetching stats from Facebook, url={}, ignoring.", url, e);
            return new PostStats();
        }
    }

    private MultiValueMap<String, String> getParams(String url) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("id", Collections.singletonList(url));
        params.put("fields", Collections.singletonList("share"));
        return params;
    }

}
