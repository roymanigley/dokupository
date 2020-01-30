package ch.bytecrowd.dokupository.models.jpa;

import ch.bytecrowd.dokupository.models.interfaces.Keyable;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "COMMIT")
public class Commit implements Keyable {

    @Id
    @GeneratedValue
    @Column(name = "ID_COMMIT")
    private Long id;

    @Column(name = "HASH", nullable = false)
    private String hash;

    @Column(name = "IGNORE_FOR_DOCUMENTATION", nullable = false)
    private Boolean ignoreForDocumentation = false;

    @JoinColumn(name = "ID_APPLICATION", nullable = false)
    @ManyToOne(targetEntity = Application.class, fetch = FetchType.LAZY)
    private Application application;

    @JoinColumn(name = "ID_DOCUMENTATION_NODE", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private DocumentationNode documentationNode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean getIgnoreForDocumentation() {
        return ignoreForDocumentation;
    }

    public void setIgnoreForDocumentation(Boolean ignoreForDocumentation) {
        this.ignoreForDocumentation = ignoreForDocumentation;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public DocumentationNode getDocumentationNode() {
        return documentationNode;
    }

    public void setDocumentationNode(DocumentationNode documentationNode) {
        this.documentationNode = documentationNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commit commit = (Commit) o;
        return Objects.equals(id, commit.id) &&
                Objects.equals(hash, commit.hash) &&
                Objects.equals(ignoreForDocumentation, commit.ignoreForDocumentation) &&
                Objects.equals(application, commit.application) &&
                Objects.equals(documentationNode, commit.documentationNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hash, ignoreForDocumentation, application, documentationNode);
    }
}
