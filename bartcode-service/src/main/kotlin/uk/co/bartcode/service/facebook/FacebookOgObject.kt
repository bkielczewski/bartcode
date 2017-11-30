package uk.co.bartcode.service.facebook

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class FacebookOgObject {

    val share: Share? = null

    class Share {
        @JsonProperty("comment_count")
        val commentCount: Int = 0
        @JsonProperty("share_count")
        val shareCount: Int = 0
    }

}

