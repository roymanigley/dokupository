package ch.bytecrowd.dokupository.tests.services.dbunit;

import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.tests.abstracts.AbstractDbUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class ApplicationDbUnitTest extends AbstractDbUnitTest<Application> {

    @Autowired
    private ApplicationRepository repository;

    @Test
    public void test() {

        final List<Application> applications = repository.findAll();

        assertThat(applications, not(empty()));
        assertThat(applications.size(), is(1));
    }
}