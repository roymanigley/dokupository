package ch.bytecrowd.dokupository.models.jpa;

import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.interfaces.Keyable;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "WORD_TEMPLATE")
public class WordTemplate implements Keyable {

    @Id
    @GeneratedValue
    @Column(name = "ID_WORD_TEMPLATE")
    private Long id;

    @Column(name = "NAME", nullable = false)
    @Lob
    private String name;

    @Column(name = "BYTES", nullable = false)
    @Lob
    private byte[] bytes;

    @Column(name = "DOCUMENTATION_TYPE", nullable = false)
    private DocumentationType documentationType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public DocumentationType getDocumentationType() {
        return documentationType;
    }

    public void setDocumentationType(DocumentationType documentationType) {
        this.documentationType = documentationType;
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
        WordTemplate that = (WordTemplate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Arrays.equals(bytes, that.bytes) &&
                documentationType == that.documentationType &&
                language == that.language;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, documentationType, language);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}
