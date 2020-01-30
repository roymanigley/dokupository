package ch.bytecrowd.dokupository.repositories.jpa;

import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationVersionRepository extends Repository<ApplicationVersion> {

    public List<ApplicationVersion> findAllByApplicationIdOrderBySortierung(Long idApplication);

    @Query("FROM ApplicationVersion av ORDER BY av.sortierung")
    public List<ApplicationVersion> findAll();

    @Query("SELECT MAX(av.sortierung) FROM ApplicationVersion av")
    public Integer findMaxSort();
}
