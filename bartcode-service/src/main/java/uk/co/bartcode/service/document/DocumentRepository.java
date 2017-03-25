package uk.co.bartcode.service.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.transaction.Transactional;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @RestResource(path = "relativeUrl", rel = "relativeUrl")
    Document findOneByRelativeUrl(@Param("relativeUrl") String relativeUrl);

    @RestResource(exported = false)
    Optional<Document> getByFile(String file);

    @Transactional
    @RestResource(exported = false)
    void deleteByFileStartingWith(String file);

}