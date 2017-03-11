package uk.co.bartcode.service.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    List<Post> findAllOrderByPopularity(Pageable pageable);

    @RestResource(path = "count", rel = "count")
    @Query("SELECT FUNCTION('YEAR', p.published) AS year, FUNCTION('MONTH', p.published) as month, COUNT(p.id) AS count " +
            "FROM Post p " +
            "GROUP BY FUNCTION('YEAR', p.published), FUNCTION('MONTH', p.published) " +
            "ORDER BY year DESC, month ASC")
    List<YearMonthCount> getCount();

    @RestResource(path = "countYear", rel = "countYear")
    @Query("SELECT FUNCTION('MONTH', p.published) AS month, COUNT(p.id) AS count " +
            "FROM Post p " +
            "WHERE FUNCTION('YEAR', p.published) = ?1 " +
            "GROUP BY FUNCTION('MONTH', p.published) " +
            "ORDER BY month ASC")
    List<MonthCount> getCountByYear(@Param("year") Integer year);

    @RestResource(path = "countYearMonth", rel = "countYearMonth")
    @Query("SELECT COUNT(p.id) FROM Post p " +
            "WHERE FUNCTION('YEAR', p.published) = ?1 " +
            "AND FUNCTION('MONTH', p.published) = ?2")
    Integer getCountByYearMonth(@Param("year") Integer year, @Param("month") Integer month);

    @RestResource(path = "countTag", rel = "countTag")
    @Query("SELECT t, COUNT(p.id) as count FROM Post p JOIN p.tags t " +
            "GROUP BY t")
    List<TagCount> getCountByTag();

    @RestResource(exported = false)
    Optional<Post> getBySourceFile(String sourceFile);

    @Transactional
    @RestResource(exported = false)
    void deleteBySourceFileStartingWith(String sourceFile);

}