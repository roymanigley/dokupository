package ch.bytecrowd.dokupository.tests.abstracts;

import ch.bytecrowd.dokupository.services.jpa.CrudService;
import ch.bytecrowd.dokupository.models.interfaces.Keyable;
import ch.bytecrowd.dokupository.tests.utils.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class AbstractCrudServiceTest<T extends Keyable> {

    public abstract CrudService<T> createController();
    public abstract T createRandomRecord();

    @Test
    public void saveRecordTest() {

        T record = null;
        int recordsBeforeSave = 0;
        int recordsAfterSave = 0;
        try {
            record = createRandomRecord();
            final CrudService<T> controller = createController();
            recordsBeforeSave = controller.fetchAllRecords().size();
            record = controller.saveRecord(record);
            recordsAfterSave = controller.fetchAllRecords().size();
        } catch (RuntimeException e) {
            fail(e.getMessage());
        }

        assertThat("Test: amount of records", recordsBeforeSave, lessThan(recordsAfterSave));
        assertThat("Test: amount of records", recordsAfterSave - recordsBeforeSave, is(1));
        assertThat("Test: saved record is not null", record, notNullValue());
        assertThat("Test: saved record.id is not null", record.getId(), notNullValue());
    }

    @Test
    public void saveRecordsTest() {

        List<T> records = new ArrayList();
        int recordsToCreate = 0;
        int recordsBeforeSave = 0;
        int recordsAfterSave = 0;
        try {
            recordsToCreate = (RandomUtil.randomPositiveInteger() % 23) + 4;
            for (int i = 0; i < recordsToCreate; i++) {

                records.add(createRandomRecord());
            }

            final CrudService<T> controller = createController();
            recordsBeforeSave = controller.fetchAllRecords().size();
            records = controller.saveRecords(records);
            recordsAfterSave = controller.fetchAllRecords().size();
        } catch (RuntimeException e) {
            fail(e.getMessage());
        }


        assertThat("Test: amount of records", recordsBeforeSave, lessThan(recordsAfterSave));
        assertThat("Test: amount of records", recordsAfterSave - recordsBeforeSave, is(recordsToCreate));
        for (T record : records) {

            assertThat("Test: saved record is not null", record, notNullValue());
            assertThat("Test: saved record.id is not null", record.getId(), notNullValue());
        }
    }

    @Test
    public void deleteRecordTest() {

        int recordsBeforeDelete = 0;
        int recordsAfterDelete = 0;
        try {
            T record = createRandomRecord();
            final CrudService<T> controller = createController();
            record = controller.saveRecord(record);
            recordsBeforeDelete = controller.fetchAllRecords().size();
            controller.deleteRecord(record);
            recordsAfterDelete = controller.fetchAllRecords().size();
        } catch (RuntimeException e) {
            fail(e.getMessage());
        }

        assertThat("Test 01: amount of records", recordsBeforeDelete, greaterThan(recordsAfterDelete));
        assertThat("Test 02: amount of records", recordsBeforeDelete - recordsAfterDelete, is(1));
    }

    @Test
    public void deleteRecordsTest() {

        List<T> records = new ArrayList();
        int recordsToCreate = 0;
        final CrudService<T> controller = createController();;
        int recordsBeforeDelete = 0;
        int recordsAfterDelete = 0;
        try {

            recordsToCreate = (RandomUtil.randomPositiveInteger() % 23) + 4;
            for (int i = 0; i < recordsToCreate; i++) {

                records.add(createRandomRecord());
            }

            records = controller.saveRecords(records);

            recordsBeforeDelete = controller.fetchAllRecords().size();
            controller.deleteRecords(records);
            recordsAfterDelete = controller.fetchAllRecords().size();
        } catch (RuntimeException e) {
            fail(e.getMessage());
        }


        assertThat("Test: amount of records", recordsAfterDelete, lessThan(recordsBeforeDelete));
        assertThat("Test: amount of records", recordsBeforeDelete - recordsAfterDelete, is(recordsToCreate));

        for (T record : records) {
            final Optional<T> deletedRecord = controller.fetchById(record.getId());
            assertThat("Test: deleted record is not in DB", deletedRecord.isEmpty(), is(true));
        }
    }
}
