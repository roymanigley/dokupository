package ch.bytecrowd.dokupository.gui.models;

import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.WordTemplate;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewEntityWordTemplate extends ViewEntity<WordTemplate, ViewEntityWordTemplate> {

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleObjectProperty<byte[]> bytes = new SimpleObjectProperty<>();
    private SimpleObjectProperty<DocumentationType> documentationType = new SimpleObjectProperty();
    private SimpleObjectProperty<DocumentationLanguage> language = new SimpleObjectProperty();
    private SimpleStringProperty documentationTypeString = new SimpleStringProperty();
    private SimpleStringProperty languageString = new SimpleStringProperty();

    public ViewEntityWordTemplate() {

    }

    public ViewEntityWordTemplate(WordTemplate wordTemplate) {
        super(wordTemplate);
        this.name = new SimpleStringProperty(wordTemplate.getName());
        this.bytes = new SimpleObjectProperty(wordTemplate.getBytes());
        this.documentationType = new SimpleObjectProperty(wordTemplate.getDocumentationType());
        this.language = new SimpleObjectProperty(wordTemplate.getLanguage());
        this.documentationTypeString = new SimpleStringProperty(wordTemplate.getDocumentationType().toString());
        this.languageString = new SimpleStringProperty(wordTemplate.getLanguage().toString());
    }

    @Override
    public void fillEntity(WordTemplate application) {

        super.fillEntity(application);
        application.setName(this.getName());
        application.setLanguage(this.getLanguage());
        application.setBytes(this.getBytes());
        application.setDocumentationType(this.getDocumentationType());
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public DocumentationLanguage getLanguage() {
        return language.get();
    }

    public SimpleObjectProperty<DocumentationLanguage> languageProperty() {
        return language;
    }

    public void setLanguage(DocumentationLanguage language) {
        this.language.set(language);
    }

    public byte[] getBytes() {
        return bytes.get();
    }

    public SimpleObjectProperty<byte[]> bytesProperty() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes.set(bytes);
    }

    public DocumentationType getDocumentationType() {
        return documentationType.get();
    }

    public SimpleObjectProperty<DocumentationType> documentationTypeProperty() {
        return documentationType;
    }

    public void setDocumentationType(DocumentationType documentationType) {
        this.documentationType.set(documentationType);
    }

    public String getDocumentationTypeString() {
        return documentationTypeString.get();
    }

    public SimpleStringProperty documentationTypeStringProperty() {
        return documentationTypeString;
    }

    public void setDocumentationTypeString(String documentationTypeString) {
        this.documentationTypeString.set(documentationTypeString);
    }

    public String getLanguageString() {
        return languageString.get();
    }

    public SimpleStringProperty languageStringProperty() {
        return languageString;
    }

    public void setLanguageString(String languageString) {
        this.languageString.set(languageString);
    }

    @Override
    public String toString() {
        return "ViewEntityWordTemplate{" +
                "id=" + getId() +
                ", name=" + name.getValue() +
                ", docType=" + documentationTypeString.getValue() +
                ", lang=" + language.getValue() +
                '}';
    }
}
