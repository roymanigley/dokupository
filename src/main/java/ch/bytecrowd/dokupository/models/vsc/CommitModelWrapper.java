package ch.bytecrowd.dokupository.models.vsc;

public interface CommitModelWrapper<T> {

    public T getSrealizedCommit();
    public String getCommitMessage();
    public String getCommitCommiter();
    public String getCommitISODate();
    public String getCommitHash();
    public String getCommitUrl();
}
