package ch.bytecrowd.dokupository.models.vsc;

import java.util.List;

public class GogsCommitModelWrapper extends AbstractModelWrapper<GogsCommitModelWrapper.GogsCommit> {

    public GogsCommitModelWrapper(GogsCommit serializedCommit) {

        super(serializedCommit);
    }

    @Override
    public String getCommitMessage() {

        return getSrealizedCommit() != null && getSrealizedCommit().commit != null ? getSrealizedCommit().commit.message : null;
    }

    @Override
    public String getCommitCommiter() {

        return getSrealizedCommit() != null && getSrealizedCommit().commit != null && getSrealizedCommit().commit.committer != null ? getSrealizedCommit().commit.committer.name : null;
    }

    @Override
    public String getCommitHash() {

        return getSrealizedCommit() != null ? getSrealizedCommit().sha : null;
    }

    @Override
    public String getCommitUrl() {

        return getSrealizedCommit() != null ? getSrealizedCommit().html_url.replaceAll("/commits/", "/commit/") : null;
    }

    @Override
    public String getCommitISODate() {

        return getSrealizedCommit() != null && getSrealizedCommit().commit != null && getSrealizedCommit().commit.committer != null ? getSrealizedCommit().commit.committer.date : null;
    }

    public class GogsCommit {

        public String sha;
        public Commit commit;
        public String html_url;
        public List<Commit> parents;

        public GogsCommitModelWrapper toWrapperModel() {

            return new GogsCommitModelWrapper(this);
        }

        public class Commit {

            public Committer committer;
            public String message;
            public String url;

            @Override
            public String toString() {
                return "Commit{" +
                        "committer=" + committer +
                        ", message='" + message + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }
        }

        public class Committer {

            public String name;
            public String date;

            @Override
            public String toString() {
                return "Committer{" +
                        "name='" + name + '\'' +
                        ", date='" + date + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "GogsCommit{" +
                    "sha='" + sha + '\'' +
                    ", commit=" + commit +
                    ", html_url='" + html_url + '\'' +
                    ", parents=" + parents +
                    '}';
        }
    }
}
