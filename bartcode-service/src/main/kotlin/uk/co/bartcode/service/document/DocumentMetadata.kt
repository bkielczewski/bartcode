package uk.co.bartcode.service.document

import javax.persistence.Embeddable

@Embeddable
data class DocumentMetadata(
        val canonicalUrl: String,
        val title: String,
        val description: String
)