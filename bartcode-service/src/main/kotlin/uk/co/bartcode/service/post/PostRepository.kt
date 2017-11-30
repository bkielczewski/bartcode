package uk.co.bartcode.service.post

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RestResource
import java.util.*

interface PostRepository : JpaRepository<Post, Long> {

    @RestResource(path = "relativeUrl", rel = "relativeUrl")
    fun findOneByRelativeUrl(relativeUrl: String): Optional<Post>

    @RestResource(path = "recent", rel = "recent")
    fun findAllByOrderByPublishedDesc(pageable: Pageable): Page<Post>

    @RestResource(path = "year", rel = "year")
    @Query("SELECT p FROM Post p " + "WHERE FUNCTION('YEAR', p.published) = ?1")
    fun findByYear(year: Int, pageable: Pageable): Page<Post>

    @RestResource(path = "yearMonth", rel = "yearMonth")
    @Query("SELECT p FROM Post p " +
            "WHERE FUNCTION('YEAR', p.published) = ?1 " +
            "AND FUNCTION('MONTH', p.published) = ?2")
    fun findByYearAndMonth(year: Int, month: Int, pageable: Pageable): Page<Post>

    @RestResource(path = "tag", rel = "tag")
    fun findByTags(tag: String, pageable: Pageable): Page<Post>

    @RestResource(path = "popular", rel = "popular")
    fun findTop10ByOrderByStatsSharesDesc(pageable: Pageable): List<Post>

    @RestResource(path = "datePostCounts", rel = "datePostCounts")
    @Query("SELECT NEW uk.co.bartcode.service.post.DatePostCount(" +
            "FUNCTION('YEAR', p.published), FUNCTION('MONTH', p.published), COUNT(p.id)) " +
            "FROM Post p " +
            "GROUP BY FUNCTION('YEAR', p.published), FUNCTION('MONTH', p.published) " +
            "ORDER BY FUNCTION('YEAR', p.published) DESC, FUNCTION('MONTH', p.published) ASC")
    fun getDatePostCounts(): List<DatePostCount>

    @RestResource(path = "tagPostCounts", rel = "tagPostCounts")
    @Query("SELECT NEW uk.co.bartcode.service.post.TagPostCount(t, COUNT(p.id)) " +
            "FROM Post p JOIN p.tags t " +
            "GROUP BY t " +
            "ORDER BY COUNT(p.id) DESC")
    fun getTagPostCounts(): List<TagPostCount>

    @RestResource(exported = false)
    fun findByPath(path: String): Optional<Post>

    @RestResource(exported = false)
    fun deleteByPathStartingWith(path: String)

    @RestResource(exported = false)
    override fun deleteById(id: Long)

    @RestResource(exported = false)
    override fun <S : Post?> save(entity: S): S

}