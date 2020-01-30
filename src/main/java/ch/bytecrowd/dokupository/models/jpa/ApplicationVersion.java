package ch.bytecrowd.dokupository.models.jpa;

import ch.bytecrowd.dokupository.models.enums.ApplicationVersionState;
import ch.bytecrowd.dokupository.models.interfaces.Keyable;
import ch.bytecrowd.dokupository.models.interfaces.Sortable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "APPLICATION_VERSION")
public class ApplicationVersion implements Keyable, Sortable {

    @Id
    @GeneratedValue
    @Column(name = "ID_VERSION")
    private Long id;

    @Column(name = "VERSION", nullable = false)
    private String version;

    @JoinColumn(name = "ID_APPLICATION", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Application application;

    @JoinColumn(name = "ID_USER_RESPONSIBLE", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private User userResponsible;

    @JoinColumn(name = "ID_USER_TESTER", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private User userTester;

    @Column(name = "VERSION_STATE", nullable = true)
    private ApplicationVersionState state;

    @Column(name = "REMARK_DE", nullable = true)
    private String remarkDe;

    @Column(name = "REMARK_FR", nullable = true)
    private String remarkFr;

    @Column(name = "RELEASE_DATE", nullable = true)
    private LocalDate releaseDate;

    @Column(name = "SORTIERUNG", nullable = true)
    private Integer sortierung;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public User getUserResponsible() {
        return userResponsible;
    }

    public void setUserResponsible(User userResponsible) {
        this.userResponsible = userResponsible;
    }

    public User getUserTester() {
        return userTester;
    }

    public void setUserTester(User userTester) {
        this.userTester = userTester;
    }

    public ApplicationVersionState getState() {
        return state;
    }

    public void setState(ApplicationVersionState state) {
        this.state = state;
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public Integer getSortierung() {
        return sortierung;
    }

    @Override
    public void setSortierung(Integer sortierung) {
        this.sortierung = sortierung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationVersion that = (ApplicationVersion) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(version, that.version) &&
                Objects.equals(application, that.application) &&
                Objects.equals(userResponsible, that.userResponsible) &&
                Objects.equals(userTester, that.userTester) &&
                state == that.state &&
                Objects.equals(remarkDe, that.remarkDe) &&
                Objects.equals(remarkFr, that.remarkFr) &&
                Objects.equals(releaseDate, that.releaseDate) &&
                Objects.equals(sortierung, that.sortierung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, application, userResponsible, userTester, state, remarkDe, remarkFr, releaseDate, sortierung);
    }
}
