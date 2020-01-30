package ch.bytecrowd.dokupository.tests.abstracts;

import ch.bytecrowd.dokupository.models.interfaces.Keyable;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.junit.Assert.assertThat;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(AbstractDbUnitTest.DATASET)
@DatabaseTearDown(type = com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL, value = {AbstractDbUnitTest.DATASET})
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public abstract class AbstractDbUnitTest<T extends Keyable> {

    protected static final String DATASET = "classpath:db-unit/application.xml";

}
