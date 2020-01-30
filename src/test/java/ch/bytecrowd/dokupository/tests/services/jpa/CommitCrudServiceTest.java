package ch.bytecrowd.dokupository.tests.services.jpa;

import ch.bytecrowd.dokupository.services.jpa.CommitCrudService;
import ch.bytecrowd.dokupository.services.jpa.CrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.Commit;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.repositories.jpa.CommitRepository;
import ch.bytecrowd.dokupository.tests.abstracts.AbstractCrudServiceTest;
import ch.bytecrowd.dokupository.tests.utils.DummyDataCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class CommitCrudServiceTest extends AbstractCrudServiceTest<Commit> {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private CommitRepository repository;

    @Override
    public Commit createRandomRecord() {

        Application dummyApp = DummyDataCreatorUtil.createDummyApplication();
        dummyApp = applicationRepository.save(dummyApp);

        return DummyDataCreatorUtil.createDummyCommit(dummyApp);
    }

    @Override
    public CrudService<Commit> createController() {

        return new CommitCrudService(repository);
    }
}
