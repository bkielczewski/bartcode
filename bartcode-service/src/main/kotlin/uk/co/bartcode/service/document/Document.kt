package uk.co.bartcode.service.document

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(indexes = arrayOf(
        Index(name = "relativeUrl", columnList = "relativeUrl"),
        Index(name = "file", columnList = "file")
))
open class Document constructor(
        @Id @GeneratedValue val id: Long?,
        val relativeUrl: String,
        val file: String,
        @Embedded val metadata: Metadata,
        @Lob val body: String
)

@Embeddable
data class Metadata(
        val canonicalUrl: String,
        val title: String,
        val description: String
)