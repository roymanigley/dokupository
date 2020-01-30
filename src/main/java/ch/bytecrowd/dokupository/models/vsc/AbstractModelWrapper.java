package ch.bytecrowd.dokupository.models.vsc;

import java.util.Objects;

public abstract class AbstractModelWrapper<T> implements CommitModelWrapper<T> {

    private T serializedCommit;

    public AbstractModelWrapper(T serializedCommit) {

        this.serializedCommit = serializedCommit;
    }

    @Override
    public T getSrealizedCommit() {
        return serializedCommit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractModelWrapper<?> that = (AbstractModelWrapper<?>) o;
        return Objects.equals(getCommitCommiter(), that.getCommitCommiter()) &&
                Objects.equals(getCommitHash(), that.getCommitHash()) &&
                Objects.equals(getCommitISODate(), that.getCommitISODate()) &&
                Objects.equals(getCommitMessage(), that.getCommitMessage()) &&
                Objects.equals(getCommitUrl(), that.getCommitUrl());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCommitCommiter(), getCommitHash(), getCommitISODate(), getCommitMessage(), getCommitUrl());
    }

    @Override
    public String getCommitMessage() {
        return null;
    }

    @Override
    public String getCommitCommiter() {
        return null;
    }

    @Override
    public String getCommitISODate() {
        return null;
    }

    @Override
    public String getCommitHash() {
        return null;
    }

    @Override
    public String getCommitUrl() {
        return null;
    }
}
