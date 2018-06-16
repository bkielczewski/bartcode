package uk.co.bartcode.service.facebook

import org.slf4j.LoggerFactory
import org.springframework.social.SocialException
import org.springframework.social.facebook.api.impl.FacebookTemplate
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Service
class FacebookService {

    fun getStats(url: String): FacebookOgObject? {
        try {
            logger.debug("Getting Facebook stats, url={}", url)
            return facebookTemplate.fetchObject("/", FacebookOgObject::class.java, getParams(url))
        } catch (e: SocialException) {
            logger.warn("Error fetching stats from Facebook, url={}, ignoring.", url, e)
            return null
        }
    }

    private fun getParams(url: String): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>().apply {
            this["id"] = listOf(url)
            this["fields"] = listOf("share")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FacebookService::class.java)
        private val facebookTemplate = FacebookTemplate("")
    }

}