package uk.co.bartcode.service.document

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(indexes = arrayOf(
        Index(name = "relativeUrl", columnList = "relativeUrl"),
        Index(name = "path", columnList = "path")
))
open class Document constructor(
        @Id @GeneratedValue val id: Long?,
        val relativeUrl: String,
        @JsonIgnore val path: String,
        @Embedded val metadata: Metadata,
        @Lob val body: String
)

@Embeddable
data class Metadata(
        val canonicalUrl: String,
        val title: String,
        val description: String
)