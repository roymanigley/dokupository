package ch.bytecrowd.dokupository.services.jpa;

import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationVersionRepository;

import java.util.List;

public class ApplicationVersionService extends GenericCrudService<ApplicationVersion> {

    private final ApplicationVersionRepository applicationVersionRepository;

    public ApplicationVersionService(ApplicationVersionRepository repository) {
        super(repository);
        this.applicationVersionRepository = repository;
    }

    public List<ApplicationVersion> findAllByApplicationId(Long appId) {
        return applicationVersionRepository.findAllByApplicationIdOrderBySortierung(appId);
    }

    public Integer findMaxSort() {
        return applicationVersionRepository.findMaxSort();
    }

}
