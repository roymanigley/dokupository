package ch.bytecrowd.dokupository.models.jpa;

import ch.bytecrowd.dokupository.models.enums.ApplicationType;
import ch.bytecrowd.dokupository.models.interfaces.Keyable;
import ch.bytecrowd.dokupository.models.enums.RepoType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "APPLICATION")
@Entity
public class Application implements Keyable {

    @Id
    @GeneratedValue
    @Column(name = "ID_APPLICATION")
    private Long id;

    @Column(name = "APP_TYPE")
    private ApplicationType appType;

    @Column(name = "NAME_DE", nullable = false)
    private String nameDe;

    @Column(name = "NAME_FR", nullable = false)
    private String nameFr;

    @Column(name = "REMARK_DE", nullable = true)
    @Lob
    private String remarkDe;

    @Column(name = "REMARK_FR", nullable = true)
    @Lob
    private String remarkFr;

    @Column(name = "REPO_TYPE", nullable = false)
    private RepoType repoType;

    @Column(name = "REPO_URL", nullable = false)
    private String repositoryUrl;

    @Column(name = "BRANCH", nullable = false)
    private String branch;

    @Column(name = "TOKEN", nullable = true)
    private String token;

    @JoinColumn(name = "ID_USER_RESPONSIBLE", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private User userResponsible;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ApplicationVersion> applicationVersions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameDe() {
        return nameDe;
    }

    public void setNameDe(String nameDe) {
        this.nameDe = nameDe;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getRemarkDe() {
        return remarkDe;
    }

    public void setRemarkDe(String remarkDe) {
        this.remarkDe = remarkDe;
    }

    public String getRemarkFr() {
        return remarkFr;
    }

    public void setRemarkFr(String remarkFr) {
        this.remarkFr = remarkFr;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranch() {
        return branch;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RepoType getRepoType() {
        return repoType;
    }

    public void setRepoType(RepoType repoType) {
        this.repoType = repoType;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public User getUserResponsible() {
        return userResponsible;
    }

    public void setUserResponsible(User userResponsible) {
        this.userResponsible = userResponsible;
    }

    public List<ApplicationVersion> getApplicationVersions() {
        return applicationVersions;
    }

    public ApplicationType getAppType() {
        return appType;
    }

    public void setAppType(ApplicationType appType) {
        this.appType = appType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(appType, that.appType) &&
                Objects.equals(nameDe, that.nameDe) &&
                Objects.equals(nameFr, that.nameFr) &&
                Objects.equals(remarkDe, that.remarkDe) &&
                Objects.equals(remarkFr, that.remarkFr) &&
                repoType == that.repoType &&
                Objects.equals(repositoryUrl, that.repositoryUrl) &&
                Objects.equals(branch, that.branch) &&
                Objects.equals(token, that.token) &&
                Objects.equals(userResponsible, that.userResponsible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appType, nameDe, nameFr, remarkDe, remarkFr, repoType, repositoryUrl, branch, token, userResponsible);
    }
}
