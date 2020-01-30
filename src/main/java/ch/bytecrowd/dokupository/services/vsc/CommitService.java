package ch.bytecrowd.dokupository.services.vsc;

import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.vsc.CommitModelWrapper;

import java.util.Set;

public interface CommitService<T extends CommitModelWrapper> {

    public Set<T> fetchAllCommitsByApplication(Application application) throws Exception;

}
