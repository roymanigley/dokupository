package ch.bytecrowd.dokupository.services.jpa;

import ch.bytecrowd.dokupository.models.interfaces.Keyable;

import java.util.List;
import java.util.Optional;

public interface CrudService<T extends Keyable > {

    public T saveRecord(T record) throws RuntimeException;
    public List<T> saveRecords(List<T> record) throws RuntimeException;

    public void deleteRecord(T record) throws RuntimeException;
    public void deleteRecords(List<T> record) throws RuntimeException;

    public List<T> fetchAllRecords();
    public Optional<T> fetchById(Long id);
}
