package ch.bytecrowd.dokupository.tests.services.jpa;

import ch.bytecrowd.dokupository.services.jpa.CrudService;
import ch.bytecrowd.dokupository.services.jpa.DocumentationNodeCrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationVersionRepository;
import ch.bytecrowd.dokupository.repositories.jpa.DocumentationNodeRepository;
import ch.bytecrowd.dokupository.tests.abstracts.AbstractCrudServiceTest;
import ch.bytecrowd.dokupository.tests.utils.DummyDataCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class DoccumentationNodeCrudServiceTest extends AbstractCrudServiceTest<DocumentationNode> {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationVersionRepository applicationVersionRepository;
    @Autowired
    private DocumentationNodeRepository repository;

    @Override
    public DocumentationNode createRandomRecord() {

        Application dummyApp = DummyDataCreatorUtil.createDummyApplication();
        dummyApp = applicationRepository.save(dummyApp);

        ApplicationVersion dummyAppVersion = DummyDataCreatorUtil.createDummyApplicationVersion(dummyApp);
        dummyAppVersion = applicationVersionRepository.save(dummyAppVersion);

        return DummyDataCreatorUtil.createDummyDocumentationNode(dummyAppVersion, null);
    }

    @Override
    public CrudService<DocumentationNode> createController() {

        return new DocumentationNodeCrudService(repository);
    }
}
