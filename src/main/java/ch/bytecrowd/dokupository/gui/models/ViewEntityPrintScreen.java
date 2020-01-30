package ch.bytecrowd.dokupository.gui.models;

import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import ch.bytecrowd.dokupository.models.jpa.PrintScreen;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class ViewEntityPrintScreen extends ViewEntity<PrintScreen, ViewEntityPrintScreen> {

    private SimpleStringProperty footnote = new SimpleStringProperty();
    private SimpleStringProperty versionBeginnText = new SimpleStringProperty();
    private SimpleStringProperty heigth = new SimpleStringProperty();
    private SimpleStringProperty width = new SimpleStringProperty();
    private SimpleObjectProperty<byte[]> bytes = new SimpleObjectProperty<>();
    private SimpleObjectProperty<DocumentationLanguage> language = new SimpleObjectProperty<>();
    private SimpleStringProperty languageString = new SimpleStringProperty();
    private SimpleObjectProperty<DocumentationNode> documentationNode = new SimpleObjectProperty<>();

    public ViewEntityPrintScreen() {

    }

    public ViewEntityPrintScreen(PrintScreen printScreen) {

        super(printScreen);
        this.footnote = new SimpleStringProperty(printScreen.getFootnote());
        this.language = new SimpleObjectProperty<>(printScreen.getLanguage());
        if (printScreen.getLanguage() != null)
            this.languageString = new SimpleStringProperty(printScreen.getLanguage().toString());
        if (printScreen.getHeight() != null)
            this.heigth = new SimpleStringProperty(printScreen.getHeight().toString());
        if (printScreen.getWidth() != null)
            this.width = new SimpleStringProperty(printScreen.getWidth().toString());
        this.bytes = new SimpleObjectProperty<byte[]>(printScreen.getData());
        if (printScreen.getDocumentationNode() != null) {
            this.documentationNode = new SimpleObjectProperty<>(printScreen.getDocumentationNode());

            if (printScreen.getDocumentationNode().getVersionBegin() != null)
                this.versionBeginnText = new SimpleStringProperty(printScreen.getDocumentationNode().getVersionBegin().getVersion());
        }
    }

    @Override
    public void fillEntity(PrintScreen printscreen) {

        super.fillEntity(printscreen);
        printscreen.setFootnote(getFootnote());
        printscreen.setLanguage(getLanguage());
        printscreen.setHeight(null);
        try {
            if (getHeigth() != null && !this.getHeigth().isBlank())
                printscreen.setHeight(Double.valueOf(getHeigth()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        printscreen.setWidth(null);
        try {
            if (getWidth() != null && !this.getWidth().isBlank())
                printscreen.setWidth(Double.valueOf(getWidth()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        printscreen.setData(getBytes());
        printscreen.setDocumentationNode(getDocumentationNode());
    }

    public String getFootnote() {
        return footnote.get();
    }

    public SimpleStringProperty footnoteProperty() {
        return footnote;
    }

    public void setFootnote(String footnote) {
        this.footnote.set(footnote);
    }

    public String getVersionBeginnText() {
        return versionBeginnText.get();
    }

    public SimpleStringProperty versionBeginnTextProperty() {
        return versionBeginnText;
    }

    public void setVersionBeginnText(String versionBeginnText) {
        this.versionBeginnText.set(versionBeginnText);
    }

    public String getHeigth() {
        return heigth.get();
    }

    public SimpleStringProperty heigthProperty() {
        return heigth;
    }

    public void setHeigth(String heigth) {
        this.heigth.set(heigth);
    }

    public String getWidth() {
        return width.get();
    }

    public SimpleStringProperty widthProperty() {
        return width;
    }

    public void setWidth(String width) {
        this.width.set(width);
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

    public DocumentationNode getDocumentationNode() {
        return documentationNode.get();
    }

    public SimpleObjectProperty<DocumentationNode> documentationNodeProperty() {
        return documentationNode;
    }

    public void setDocumentationNode(DocumentationNode documentationNode) {
        this.documentationNode.set(documentationNode);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewEntityPrintScreen that = (ViewEntityPrintScreen) o;
        return Objects.equals(footnote, that.footnote) &&
                Objects.equals(versionBeginnText, that.versionBeginnText) &&
                Objects.equals(heigth, that.heigth) &&
                Objects.equals(width, that.width) &&
                Objects.equals(bytes, that.bytes) &&
                Objects.equals(language, that.language) &&
                Objects.equals(documentationNode, that.documentationNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(footnote, versionBeginnText, heigth, width, bytes, documentationNode);
    }

    @Override
    public String toString() {
        return "ViewEntityPrintScreen{" +
                "id=" + getId() +
                "footnote=" + footnote.getValue() +
                ", lang=" + language.getValue() +
                '}';
    }
}
