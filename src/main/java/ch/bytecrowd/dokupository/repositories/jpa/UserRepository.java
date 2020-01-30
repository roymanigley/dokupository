package ch.bytecrowd.dokupository.repositories.jpa;

import ch.bytecrowd.dokupository.models.jpa.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User> {

    public List<User> findUsersByActiveIsTrue();
    public Optional<User> findUserByUsername(String username);
    public Optional<User> findUserByUsernameAndPasswordAndActiveIsTrue(String username, String password);
}
