package uk.co.bartcode.service.file

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(indexes = [
    Index(name = "relativeUrl", columnList = "relativeUrl"),
    Index(name = "file", columnList = "file")
])
open class File(
        @Id @GeneratedValue val id: Long?,
        val relativeUrl: String,
        val file: String
)