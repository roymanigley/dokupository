package ch.bytecrowd.dokupository.tests.services.jpa;

import ch.bytecrowd.dokupository.services.jpa.CrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import ch.bytecrowd.dokupository.models.jpa.User;
import ch.bytecrowd.dokupository.repositories.jpa.UserRepository;
import ch.bytecrowd.dokupository.tests.abstracts.AbstractCrudServiceTest;
import ch.bytecrowd.dokupository.tests.utils.DummyDataCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class UserCrudServiceTest extends AbstractCrudServiceTest<User> {

    @Autowired
    private UserRepository repository;

    @Override
    public User createRandomRecord() {

        return DummyDataCreatorUtil.createDummyUser();
    }

    @Override
    public CrudService<User> createController() {

        return new GenericCrudService<>(repository);
    }
}
