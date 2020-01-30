package ch.bytecrowd.dokupository.tests.services.jpa;

import ch.bytecrowd.dokupository.services.jpa.CrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import ch.bytecrowd.dokupository.models.jpa.*;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationVersionRepository;
import ch.bytecrowd.dokupository.repositories.jpa.DocumentationNodeRepository;
import ch.bytecrowd.dokupository.repositories.jpa.PrintscreenRepository;
import ch.bytecrowd.dokupository.services.jpa.PrintscreenCrudService;
import ch.bytecrowd.dokupository.tests.abstracts.AbstractCrudServiceTest;
import ch.bytecrowd.dokupository.tests.utils.DummyDataCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class PrintscreenCrudServiceTest extends AbstractCrudServiceTest<PrintScreen> {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationVersionRepository applicationVersionRepository;
    @Autowired
    private DocumentationNodeRepository documentationNodeRepository;
    @Autowired
    private PrintscreenRepository repository;

    @Override
    public PrintScreen createRandomRecord() {

        Application dummyApp = DummyDataCreatorUtil.createDummyApplication();
        dummyApp = applicationRepository.save(dummyApp);
        ApplicationVersion dummyAppVersion = DummyDataCreatorUtil.createDummyApplicationVersion(dummyApp);
        dummyAppVersion = applicationVersionRepository.save(dummyAppVersion);
        DocumentationNode dummyDocumentationNode = DummyDataCreatorUtil.createDummyDocumentationNode(dummyAppVersion, null);
        dummyDocumentationNode = documentationNodeRepository.save(dummyDocumentationNode);

        return DummyDataCreatorUtil.createDummyPrintScreen(dummyDocumentationNode);
    }

    @Override
    public CrudService<PrintScreen> createController() {

        return new PrintscreenCrudService(repository);
    }
}
