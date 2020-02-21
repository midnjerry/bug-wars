package games.crusader.bugwars.aicrud.repository;

import games.crusader.bugwars.aicrud.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "code", path = "code")
public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findByUserId(Long userId);
}
