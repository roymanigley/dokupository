package ch.bytecrowd.dokupository.gui.models;

import ch.bytecrowd.dokupository.models.enums.ApplicationVersionState;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.models.jpa.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class ViewEntityApplicationVersion extends ViewEntity<ApplicationVersion, ViewEntityApplicationVersion> {

    private SimpleStringProperty version = new SimpleStringProperty();
    private SimpleObjectProperty<Application> application = new SimpleObjectProperty();
    private SimpleObjectProperty<User> userResponsible= new SimpleObjectProperty();
    private SimpleObjectProperty<User> userTester = new SimpleObjectProperty();
    private SimpleObjectProperty<ApplicationVersionState> state = new SimpleObjectProperty();
    private SimpleStringProperty applicationName = new SimpleStringProperty();
    private SimpleStringProperty remarkDe = new SimpleStringProperty();
    private SimpleStringProperty remarkFr = new SimpleStringProperty();
    private SimpleObjectProperty<LocalDate> releaseDate = new SimpleObjectProperty<LocalDate>();
    private SimpleStringProperty sortierung = new SimpleStringProperty();

    public ViewEntityApplicationVersion() {

    }

    public ViewEntityApplicationVersion(ApplicationVersion applicationVersion) {
        super(applicationVersion);
        this.version = new SimpleStringProperty(applicationVersion.getVersion());
        this.application = new SimpleObjectProperty(applicationVersion.getApplication());
        this.userResponsible = new SimpleObjectProperty(applicationVersion.getUserResponsible());
        this.userTester = new SimpleObjectProperty(applicationVersion.getUserTester());
        this.state = new SimpleObjectProperty(applicationVersion.getState());
        this.applicationName = new SimpleStringProperty(applicationVersion.getApplication().getNameDe());
        this.remarkDe = new SimpleStringProperty(applicationVersion.getRemarkDe());
        this.remarkFr = new SimpleStringProperty(applicationVersion.getRemarkFr());
        this.releaseDate = new SimpleObjectProperty(applicationVersion.getReleaseDate());
        this.sortierung = new SimpleStringProperty(applicationVersion.getSortierung() != null ? applicationVersion.getSortierung().toString() : "");
    }

    @Override
    public void fillEntity(ApplicationVersion applicationVersion) {

        super.fillEntity(applicationVersion);
        applicationVersion.setVersion(this.getVersion());
        applicationVersion.setApplication(this.getApplication());
        applicationVersion.setUserResponsible(this.getUserResponsible());
        applicationVersion.setUserTester(this.getUserTester());
        applicationVersion.setState(this.getState());
        applicationVersion.setRemarkDe(this.getRemarkDe());
        applicationVersion.setRemarkFr(this.getRemarkFr());
        if (this.getReleaseDate() != null)
            applicationVersion.setReleaseDate(this.getReleaseDate());

        if (this.getSortierung() != null && !this.getSortierung().isBlank())
            applicationVersion.setSortierung(Integer.valueOf(this.getSortierung()));
    }

    public String getVersion() {
        return version.get();
    }

    public SimpleStringProperty versionProperty() {
        return version;
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public Application getApplication() {
        return application.get();
    }

    public void setApplication(Application application) {
        this.application.set(application);
    }

    public SimpleObjectProperty applicationProperty() {
        return application;
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

    public User getUserResponsible() {
        return userResponsible.get();
    }

    public SimpleObjectProperty<User> userResponsibleProperty() {
        return userResponsible;
    }

    public void setUserResponsible(User userResponsible) {
        this.userResponsible.set(userResponsible);
    }

    public User getUserTester() {
        return userTester.get();
    }

    public SimpleObjectProperty<User> userTesterProperty() {
        return userTester;
    }

    public void setUserTester(User userTester) {
        this.userTester.set(userTester);
    }

    public ApplicationVersionState getState() {
        return state.get();
    }

    public SimpleObjectProperty<ApplicationVersionState> stateProperty() {
        return state;
    }

    public void setState(ApplicationVersionState state) {
        this.state.set(state);
    }

    public LocalDate getReleaseDate() {
        return releaseDate.get();
    }

    public SimpleObjectProperty<LocalDate> releaseDateProperty() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate.set(releaseDate);
    }

    public String getRemarkDe() {
        return remarkDe.get();
    }

    public SimpleStringProperty remarkDeProperty() {
        return remarkDe;
    }

    public void setRemarkDe(String remarkDe) {
        this.remarkDe.set(remarkDe);
    }

    public String getRemarkFr() {
        return remarkFr.get();
    }

    public SimpleStringProperty remarkFrProperty() {
        return remarkFr;
    }

    public void setRemarkFr(String remarkFr) {
        this.remarkFr.set(remarkFr);
    }

    public String getSortierung() {
        return sortierung.get();
    }

    public SimpleStringProperty sortierungProperty() {
        return sortierung;
    }

    public void setSortierung(String sortierung) {
        this.sortierung.set(sortierung);
    }

    @Override
    public String toString() {
        return "ViewEntityApplicationVersion{" +
                "id=" + getId() +
                "version=" + version.getValue() +
                ", state=" + state.getValue() +
                ", app=" + applicationName.getValue() +
                '}';
    }
}
