package uk.co.bartcode.service.image

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RestResource
import java.util.*

interface ImageRepository : JpaRepository<Image, Long> {

    @RestResource(path = "relativeUrl", rel = "relativeUrl")
    fun findOneByRelativeUrl(relativeUrl: String): Optional<Image>

    @RestResource(exported = false)
    fun findByFile(path: String): Optional<Image>

    @RestResource(exported = false)
    fun deleteByFileStartingWith(prefix: String)

    @RestResource(exported = false)
    override fun deleteById(id: Long)

    @RestResource(exported = false)
    override fun <S : Image?> save(entity: S): S

}