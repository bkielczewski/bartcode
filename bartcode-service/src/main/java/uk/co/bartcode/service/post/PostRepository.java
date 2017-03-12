package uk.co.bartcode.service.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = PostExcerptProjection.class)
public interface PostRepository extends JpaRepository<Post, String> {

    @RestResource(path = "year", rel = "year")
    @Query("SELECT p FROM Post p " +
            "WHERE FUNCTION('YEAR', p.published) = ?1")
    Page<Post> findByYear(@Param("year") int year, Pageable pageable);

    @RestResource(path = "yearMonth", rel = "yearMonth")
    @Query("SELECT p FROM Post p " +
            "WHERE FUNCTION('YEAR', p.published) = ?1 " +
            "AND FUNCTION('MONTH', p.published) = ?2")
    Page<Post> findByYearAndMonth(@Param("year") int year, @Param("month") int month, Pageable pageable);

    @RestResource(path = "tag", rel = "tag")
    Page<Post> findByTags(@Param("tag") String tag, Pageable pageable);

    @RestResource(path = "popular", rel = "popular")
    List<Post> findTop10ByOrderByPopularityDesc(Pageable pageable);

    @RestResource(path = "datePostCounts", rel = "datePostCounts")
    @Query("SELECT NEW uk.co.bartcode.service.post.DatePostCount(" +
            "FUNCTION('YEAR', p.published), FUNCTION('MONTH', p.published), COUNT(p.id)) " +
            "FROM Post p " +
            "GROUP BY FUNCTION('YEAR', p.published), FUNCTION('MONTH', p.published) " +
            "ORDER BY FUNCTION('YEAR', p.published) DESC, FUNCTION('MONTH', p.published) ASC")
    List<DatePostCount> getDatePostCounts();

    @RestResource(path = "tagPostCounts", rel = "tagPostCounts")
    @Query("SELECT NEW uk.co.bartcode.service.post.TagPostCount(t, COUNT(p.id)) " +
            "FROM Post p JOIN p.tags t " +
            "GROUP BY t")
    List<TagPostCount> getTagPostCounts();

    @RestResource(exported = false)
    Optional<Post> getByFile(String file);

    @Transactional
    @RestResource(exported = false)
    void deleteByFileStartingWith(String file);

}