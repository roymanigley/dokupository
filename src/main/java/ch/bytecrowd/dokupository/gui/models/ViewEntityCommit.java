package ch.bytecrowd.dokupository.gui.models;

import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.Commit;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewEntityCommit extends ViewEntity<Commit, ViewEntityCommit> {

    private SimpleStringProperty hash = new SimpleStringProperty();
    private SimpleStringProperty applicationName = new SimpleStringProperty();
    private SimpleObjectProperty<Application> application = new SimpleObjectProperty<>();

    public ViewEntityCommit() {

    }

    public ViewEntityCommit(Commit commit) {
        super(commit);
        this.hash = new SimpleStringProperty(commit.getHash());
        this.applicationName = new SimpleStringProperty(commit.getApplication() != null ? commit.getApplication().getNameDe() : "");
        this.application = new SimpleObjectProperty<>(commit.getApplication());
    }

    @Override
    public void fillEntity(Commit commit) {

        super.fillEntity(commit);
        commit.setHash(this.getHash());
        commit.setApplication(this.getApplication());
    }

    public String getHash() {
        return hash.get();
    }

    public SimpleStringProperty hashProperty() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash.set(hash);
    }

    public String getApplicationName() {
        return applicationName.get();
    }

    public SimpleStringProperty applicationNameProperty() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName.set(applicationName);
    }

    public Application getApplication() {
        return application.get();
    }

    public SimpleObjectProperty<Application> applicationProperty() {
        return application;
    }

    public void setApplication(Application application) {
        this.application.set(application);
    }

    @Override
    public String toString() {
        return "ViewEntityCommit{" +
                "id=" + getId() +
                "hash=" + hash.getValue() +
                ", app=" + applicationName.getValue() +
                '}';
    }
}
