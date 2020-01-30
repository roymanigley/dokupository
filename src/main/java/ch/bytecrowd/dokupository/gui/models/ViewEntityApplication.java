package ch.bytecrowd.dokupository.gui.models;

import ch.bytecrowd.dokupository.models.enums.ApplicationType;
import ch.bytecrowd.dokupository.models.enums.RepoType;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewEntityApplication extends ViewEntity<Application, ViewEntityApplication> {

    private SimpleObjectProperty<ApplicationType> appType = new SimpleObjectProperty();
    private SimpleStringProperty nameDe = new SimpleStringProperty();
    private SimpleStringProperty nameFr = new SimpleStringProperty();
    private SimpleStringProperty branch = new SimpleStringProperty();
    private SimpleStringProperty repoUrl = new SimpleStringProperty();
    private SimpleObjectProperty<RepoType> repoType = new SimpleObjectProperty();
    private SimpleObjectProperty<User> userResponsible = new SimpleObjectProperty();
    private SimpleStringProperty token = new SimpleStringProperty();
    private SimpleStringProperty remarkDe = new SimpleStringProperty();
    private SimpleStringProperty remarkFr = new SimpleStringProperty();

    public ViewEntityApplication() {

    }

    public ViewEntityApplication(Application application) {
        super(application);
        this.nameDe = new SimpleStringProperty(application.getNameDe());
        this.appType = new SimpleObjectProperty(application.getAppType());
        this.nameFr = new SimpleStringProperty(application.getNameFr());
        this.branch = new SimpleStringProperty(application.getBranch());
        this.repoUrl = new SimpleStringProperty(application.getRepositoryUrl());
        this.repoType = new SimpleObjectProperty<RepoType>(application.getRepoType());
        this.userResponsible = new SimpleObjectProperty<User>(application.getUserResponsible());
        this.token = new SimpleStringProperty(application.getToken());
        this.remarkDe = new SimpleStringProperty(application.getRemarkDe());
        this.remarkFr = new SimpleStringProperty(application.getRemarkFr());
    }

    @Override
    public void fillEntity(Application application) {

        super.fillEntity(application);
        application.setAppType(this.getAppType());
        application.setNameDe(this.getNameDe());
        application.setNameFr(this.getNameFr());
        application.setBranch(this.getBranch());
        application.setUserResponsible(this.getUserResponsible());
        application.setRepoType(this.getRepoType());
        application.setRepositoryUrl(this.getRepoUrl());
        application.setToken(this.getToken());
        application.setRemarkDe(this.getRemarkDe());
        application.setRemarkFr(this.getRemarkFr());
    }

    public String getNameDe() {
        return nameDe.get();
    }

    public SimpleStringProperty nameDeProperty() {
        return nameDe;
    }

    public void setNameDe(String nameDe) {
        this.nameDe.set(nameDe);
    }

    public String getNameFr() {
        return nameFr.get();
    }

    public SimpleStringProperty nameFrProperty() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr.set(nameFr);
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

    public String getBranch() {
        return branch.get();
    }

    public SimpleStringProperty branchProperty() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch.set(branch);
    }

    public String getRepoUrl() {
        return repoUrl.get();
    }

    public SimpleStringProperty repoUrlProperty() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl.set(repoUrl);
    }

    public RepoType getRepoType() {
        return repoType.get();
    }

    public SimpleObjectProperty<RepoType> repoTypeProperty() {
        return repoType;
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

    public void setRepoType(RepoType repoType) {
        this.repoType.set(repoType);
    }

    public String getToken() {
        return token.get();
    }

    public SimpleStringProperty tokenProperty() {
        return token;
    }

    public void setToken(String token) {
        this.token.set(token);
    }

    public ApplicationType getAppType() {
        return appType.get();
    }

    public SimpleObjectProperty<ApplicationType> appTypeProperty() {
        return appType;
    }

    public void setAppType(ApplicationType appType) {
        this.appType.set(appType);
    }

    @Override
    public String toString() {
        return "ViewEntityApplication{" +
                "id=" + getId() +
                "appType=" + appType.getValue() +
                ", nameDe=" + nameDe.getValue() +
                ", nameFr=" + nameFr.getValue() +
                ", responsible=" + userResponsible.getValue().getFirstname() + " " +  userResponsible.getValue().getLastname() + " (" + userResponsible.getValue().getUsername() +
                ")}";
    }
}
