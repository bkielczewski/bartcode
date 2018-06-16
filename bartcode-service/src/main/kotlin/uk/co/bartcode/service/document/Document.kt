package uk.co.bartcode.service.document

import uk.co.bartcode.service.file.File
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Lob

@Entity
open class Document(
        id: Long?,
        relativeUrl: String,
        file: String,
        @Embedded val metadata: DocumentMetadata,
        @Lob val body: String
) : File(id, relativeUrl, file)