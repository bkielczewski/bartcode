package uk.co.bartcode.service.article

import uk.co.bartcode.service.document.Document
import uk.co.bartcode.service.document.Metadata
import javax.persistence.Entity

@Entity
class Article(
        id: Long?,
        relativeUrl: String,
        file: String,
        metadata: Metadata,
        body: String
) : Document(id, relativeUrl, file, metadata, body)