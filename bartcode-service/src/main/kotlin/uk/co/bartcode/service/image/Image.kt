package uk.co.bartcode.service.image

import uk.co.bartcode.service.file.File
import javax.persistence.*

@Entity
class Image(
        id: Long?,
        relativeUrl: String,
        file: String,
        @OneToMany(cascade = [CascadeType.ALL])
        @MapKeyColumn(name = "size")
        val previews: Map<String, Image.Preview>
) : File(id, relativeUrl, file) {
    @Entity
    data class Preview(
            @Id @GeneratedValue val id: Long?,
            val relativeUrl: String,
            val file: String
    )
}