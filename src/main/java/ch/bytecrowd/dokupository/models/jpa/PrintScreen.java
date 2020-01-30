package ch.bytecrowd.dokupository.models.jpa;

import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.interfaces.Keyable;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "PRINT_SCREEN")
public class PrintScreen implements Keyable {

    @Id
    @GeneratedValue
    @Column(name = "ID_PRINT_SCREEN")
    private Long id;

    @JoinColumn(name = "ID_DOCUMENTATION_NODE", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private DocumentationNode documentationNode;

    @Column(name = "FOOTNOTE", nullable = true)
    @Lob
    private String footnote;

    @Column(name = "DATA", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] data;

    @Column(name = "WIDTH", nullable = true)
    private Double width;

    @Column(name = "HEIGHT", nullable = true)
    private Double height;

    @Column(name = "LANGUAGE", nullable = false)
    private DocumentationLanguage language;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public DocumentationNode getDocumentationNode() {
        return documentationNode;
    }

    public void setDocumentationNode(DocumentationNode documentationNode) {
        this.documentationNode = documentationNode;
    }

    public String getFootnote() {
        return footnote;
    }

    public void setFootnote(String footnote) {
        this.footnote = footnote;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public DocumentationLanguage getLanguage() {
        return language;
    }

    public void setLanguage(DocumentationLanguage language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintScreen that = (PrintScreen) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(documentationNode, that.documentationNode) &&
                Objects.equals(footnote, that.footnote) &&
                Arrays.equals(data, that.data) &&
                Objects.equals(width, that.width) &&
                Objects.equals(height, that.height) &&
                language == that.language;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, documentationNode, footnote, width, height, language);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
