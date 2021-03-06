package uk.co.bartcode.service.post

import org.springframework.data.rest.core.config.Projection
import uk.co.bartcode.service.document.Document
import uk.co.bartcode.service.document.DocumentMetadata
import java.util.*
import javax.persistence.*

@Entity
class Post(
        id: Long?,
        relativeUrl: String,
        file: String,
        metadata: DocumentMetadata,
        body: String,
        @Lob val excerpt: String,
        val published: Date,
        @ElementCollection @CollectionTable val tags: List<String>,
        val stats: PostStats = PostStats(0, 0)
) : Document(id, relativeUrl, file, metadata, body)

@Projection(name = "excerpt", types = [Post::class])
interface PostExcerptProjection {
    val id: Long
    val relativeUrl: String
    val metadata: DocumentMetadata
    val excerpt: String
    val published: Date
    val tags: List<String>
    val stats: PostStats
}

@Embeddable
data class PostStats(var shares: Int, var comments: Int)
