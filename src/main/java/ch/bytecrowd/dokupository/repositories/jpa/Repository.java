package ch.bytecrowd.dokupository.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Repository<T> extends JpaRepository<T, Long> {
}
