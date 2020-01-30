package ch.bytecrowd.dokupository.services.vsc;

import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.vsc.CommitModelWrapper;
import ch.bytecrowd.dokupository.models.vsc.GogsCommitModelWrapper;
import ch.bytecrowd.dokupository.utils.JsonUtil;

import java.util.LinkedHashSet;
import java.util.Set;

public class GogsCommitService implements CommitService<CommitModelWrapper> {

    @Override
    public Set<CommitModelWrapper> fetchAllCommitsByApplication(Application application) throws Exception {
        try {
            final String url = applicationToRecentCommitsUrl(application);
            final Set<CommitModelWrapper>  commitWrappers = new LinkedHashSet<>();
            fetchCommitsRecursive(commitWrappers, url, application.getToken());
            return commitWrappers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String applicationToRecentCommitsUrl(Application application) {

        return application.getRepositoryUrl().replaceAll("/$", "")
                + "/commits/"
                + application.getBranch()
                + "?token="
                + application.getToken();
    }

    private void fetchCommitsRecursive(Set<CommitModelWrapper> commitWrappers, String jsonUrl, String token) {

        try {
            final GogsCommitModelWrapper.GogsCommit gogsCommit = JsonUtil.getFromJsonUrl(jsonUrl, GogsCommitModelWrapper.GogsCommit.class);
            final GogsCommitModelWrapper modelWrapper = gogsCommit.toWrapperModel();
            if (commitWrappers.contains(modelWrapper))
                return;
            commitWrappers.add(modelWrapper);
            for (GogsCommitModelWrapper.GogsCommit.Commit c : gogsCommit.parents) {
                final String parentCommitUrl = c.url + "?token=" + token;
                fetchCommitsRecursive(commitWrappers, parentCommitUrl, token);
            }
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
