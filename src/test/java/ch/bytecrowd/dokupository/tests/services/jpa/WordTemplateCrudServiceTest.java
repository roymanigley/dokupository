package ch.bytecrowd.dokupository.tests.services.jpa;

import ch.bytecrowd.dokupository.models.jpa.WordTemplate;
import ch.bytecrowd.dokupository.repositories.jpa.WordTemplateRepository;
import ch.bytecrowd.dokupository.services.jpa.CrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import ch.bytecrowd.dokupository.tests.abstracts.AbstractCrudServiceTest;
import ch.bytecrowd.dokupository.tests.utils.DummyDataCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class WordTemplateCrudServiceTest extends AbstractCrudServiceTest<WordTemplate> {

    @Autowired
    private WordTemplateRepository repository;

    @Override
    public WordTemplate createRandomRecord() {

        return DummyDataCreatorUtil.createDummyWordTemplate();
    }

    @Override
    public CrudService<WordTemplate> createController() {

        return new GenericCrudService<>(repository);
    }
}
