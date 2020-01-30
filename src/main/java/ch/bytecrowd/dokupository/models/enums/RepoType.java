package ch.bytecrowd.dokupository.models.enums;

import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.vsc.CommitModelWrapper;
import ch.bytecrowd.dokupository.services.vsc.CommitService;
import ch.bytecrowd.dokupository.services.vsc.GogsCommitService;

import java.util.Set;

public enum RepoType {

    GOGS(new GogsCommitService());

    private CommitService service;

    RepoType(CommitService service) {

        this.service = service;
    }

    public Set<CommitModelWrapper> fetchAllCommitsByApplication(Application app) throws Exception {

            return service.fetchAllCommitsByApplication(app);
    }
}
