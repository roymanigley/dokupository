package ch.bytecrowd.dokupository.services.jpa;

import ch.bytecrowd.dokupository.models.interfaces.Keyable;
import ch.bytecrowd.dokupository.repositories.jpa.Repository;

import java.util.List;
import java.util.Optional;

public class GenericCrudService<T extends Keyable> implements CrudService<T> {

    public Repository repository;

    public GenericCrudService(Repository<T> repository) {

        this.repository = repository;
    }

    public T saveRecord(T record) throws RuntimeException{

        return (T) repository.saveAndFlush(record);
    }

    public List<T> saveRecords(List<T> records) throws RuntimeException {
        return repository.saveAll(records);
    }

    public void deleteRecord(T record) throws RuntimeException {

        deleteRecord(record.getId());
    }

    public void deleteRecord(Long id) throws RuntimeException {

        repository.deleteById(id);
    }

    public void deleteRecords(List<T> records) throws RuntimeException {

        repository.deleteAll(records);
    }

    public List<T> fetchAllRecords() {

        return repository.findAll();
    }

    public Optional<T> fetchById(Long id) {

        return (Optional<T>) repository.findById(id);
    }
}
