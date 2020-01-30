package ch.bytecrowd.dokupository.tests.services.jpa;

import ch.bytecrowd.dokupository.services.jpa.ApplicationVersionService;
import ch.bytecrowd.dokupository.services.jpa.CrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationVersionRepository;
import ch.bytecrowd.dokupository.tests.abstracts.AbstractCrudServiceTest;
import ch.bytecrowd.dokupository.tests.utils.DummyDataCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationVersionCrudServiceTest extends AbstractCrudServiceTest<ApplicationVersion> {

    @Autowired
    private ApplicationVersionRepository repository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public ApplicationVersion createRandomRecord() {

        Application app = DummyDataCreatorUtil.createDummyApplication();
        app = applicationRepository.save(app);

        return DummyDataCreatorUtil.createDummyApplicationVersion(app);
    }

    @Override
    public CrudService<ApplicationVersion> createController() {

        return new ApplicationVersionService(repository);
    }
}
