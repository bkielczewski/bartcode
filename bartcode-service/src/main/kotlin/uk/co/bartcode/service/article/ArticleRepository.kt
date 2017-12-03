package uk.co.bartcode.service.article

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RestResource
import java.util.*

interface ArticleRepository : JpaRepository<Article, Long> {

    @RestResource(path = "relativeUrl", rel = "relativeUrl")
    fun findOneByRelativeUrl(relativeUrl: String): Optional<Article>

    @RestResource(exported = false)
    fun findByFile(path: String): Optional<Article>

    @RestResource(exported = false)
    fun deleteByFileStartingWith(prefix: String)

    @RestResource(exported = false)
    override fun deleteById(id: Long)

    @RestResource(exported = false)
    override fun <S : Article?> save(entity: S): S

}