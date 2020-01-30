package ch.bytecrowd.dokupository.models.jpa;

import ch.bytecrowd.dokupository.models.interfaces.Keyable;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.interfaces.Sortable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "DOCUMENTATION_NODE")
@Entity
public class DocumentationNode implements Keyable, Sortable {

    @Id
    @GeneratedValue
    @Column(name = "ID_DOCUMENTATION_NODE")
    private Long id;

    @Column(name = "STYLE", nullable = true)
    private String style;

    @Column(name = "TEXT_DE", nullable = true)
    @Lob
    private String textDe;

    @Column(name = "TEXT_FR", nullable = true)
    @Lob
    private String textFr;

    @Column(name = "DOCUMENTATION_TYPE", nullable = false)
    private DocumentationType documentationType;

    /*
     * when null get from parentDocumentationNode
     * */
    @JoinColumn(name = "ID_VERSION_BEGIN", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private ApplicationVersion versionBegin;

    /*
     * when null get from parentDocumentationNode
     * */
    @JoinColumn(name = "ID_VERSION_END", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private ApplicationVersion versionEnd;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "documentationNode")
    private List<PrintScreen> printscreens = new ArrayList<>();

    /*@OneToMany(fetch = FetchType.LAZY)
    private List<DocumentationNode> childDocumentationNodes = new ArrayList<>();*/

    /*@JoinColumn(name = "ID_PARENT_DOCUMENTATION_NODE", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentationNode parentDocumentationNode;*/


    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "DOCUMENTATION_NODE_COMMIT",
            joinColumns = {@JoinColumn(name = "ID_DOCUMENTATION_NODE")},
            inverseJoinColumns = {@JoinColumn(name = "ID_COMMIT")}
    )
    private List<Commit> commits = new ArrayList<>();


    @Column(name = "SORTIERUNG", nullable = true)
    private Integer sortierung;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTextDe() {
        return textDe;
    }

    public void setTextDe(String textDe) {
        this.textDe = textDe;
    }

    public String getTextFr() {
        return textFr;
    }

    public void setTextFr(String textFr) {
        this.textFr = textFr;
    }

    public DocumentationType getDocumentationType() {
        return documentationType;
    }

    public void setDocumentationType(DocumentationType documentationType) {
        this.documentationType = documentationType;
    }

    public ApplicationVersion getVersionBegin() {
        return versionBegin;
    }

    public void setVersionBegin(ApplicationVersion versionBegin) {
        this.versionBegin = versionBegin;
    }

    public ApplicationVersion getVersionEnd() {
        return versionEnd;
    }

    public void setVersionEnd(ApplicationVersion versionEnd) {
        this.versionEnd = versionEnd;
    }

    public List<PrintScreen> getPrintscreens() {
        return printscreens;
    }
/*
    public List<DocumentationNode> getChildDocumentationNodes() {
        return childDocumentationNodes;
    }

    public DocumentationNode getParentDocumentationNode() {
        return parentDocumentationNode;
    }

    public void setParentDocumentationNode(DocumentationNode parentDocumentationNode) {
        this.parentDocumentationNode = parentDocumentationNode;
    }
*/
    @Override
    public Integer getSortierung() {
        return sortierung;
    }

    @Override
    public void setSortierung(Integer sortierung) {
        this.sortierung = sortierung;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    @Override public String toString() {
        return "DocumentationNode{" + "id=" + id + ", style='" + style + '\''
                + ", textDe='" + (textDe != null && textDe.length() > 10 ? textDe.substring(0, 10) : textDe) + '\'' + ", textFr='" + (textFr != null && textFr.length() > 10 ? textFr.substring(0, 10) : textFr) + '\''
                + ", documentationType=" + documentationType + ", versionBegin="
                + versionBegin + ", versionEnd=" + versionEnd
                + ", printscreens=" + printscreens + ", commits=" + commits
                + ", sortierung=" + sortierung + '}';
    }
}
