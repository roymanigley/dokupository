package ch.bytecrowd.dokupository.tests.services.jpa;

import ch.bytecrowd.dokupository.services.jpa.CrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.tests.abstracts.AbstractCrudServiceTest;
import ch.bytecrowd.dokupository.tests.utils.DummyDataCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationCrudServiceTest extends AbstractCrudServiceTest<Application> {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public Application createRandomRecord() {
        return DummyDataCreatorUtil.createDummyApplication();
    }

    @Override
    public CrudService<Application> createController() {

        return new GenericCrudService<>(applicationRepository);
    }
}
