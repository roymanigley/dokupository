package ch.bytecrowd.dokupository.gui.models;

import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class ViewEntityDocumentationNode extends ViewEntity<DocumentationNode, ViewEntityDocumentationNode> {

    //private SimpleLongProperty id = new SimpleLongProperty();
    private SimpleStringProperty textDe = new SimpleStringProperty();
    private SimpleStringProperty textFr = new SimpleStringProperty();
    private SimpleStringProperty style = new SimpleStringProperty();
    private SimpleObjectProperty<DocumentationType> documentationType = new SimpleObjectProperty<>();
    private SimpleObjectProperty<ApplicationVersion> versionBegin = new SimpleObjectProperty<>();
    private SimpleObjectProperty<ApplicationVersion> versionEnd = new SimpleObjectProperty<>();
    private SimpleStringProperty sortierung = new SimpleStringProperty();
    private SimpleLongProperty countPrintscreen_de = new SimpleLongProperty();
    private SimpleLongProperty countPrintscreen_fr = new SimpleLongProperty();
    private SimpleStringProperty versionAsText = new SimpleStringProperty();

    public ViewEntityDocumentationNode() {

    }

    public ViewEntityDocumentationNode(DocumentationNode documentationNode) {
        super(documentationNode);
        this.textDe = new SimpleStringProperty(documentationNode.getTextDe());
        this.textFr = new SimpleStringProperty(documentationNode.getTextFr());
        this.style = new SimpleStringProperty(documentationNode.getStyle());
        this.documentationType = new SimpleObjectProperty(documentationNode.getDocumentationType());
        this.versionBegin = new SimpleObjectProperty(documentationNode.getVersionBegin());
        this.versionEnd = new SimpleObjectProperty(documentationNode.getVersionEnd());
        this.sortierung = new SimpleStringProperty(documentationNode.getSortierung() != null ? documentationNode.getSortierung().toString() : "");
        final long count_de = documentationNode.getPrintscreens().stream().filter(p -> p.getLanguage().equals(DocumentationLanguage.DE)).count();
        final long count_fr = documentationNode.getPrintscreens().stream().filter(p -> p.getLanguage().equals(DocumentationLanguage.FR)).count();
        this.countPrintscreen_de = new SimpleLongProperty(count_de);
        this.countPrintscreen_fr = new SimpleLongProperty(count_fr);
        this.versionAsText = new SimpleStringProperty(
                (getVersionBegin() != null ? getVersionBegin().getVersion() : "")
                //+
                //(getVersionEnd() != null ? " - " + getVersionEnd().getVersion() : "")
        );
    }

    public void fillEntity(DocumentationNode documentationNode) {

        documentationNode.setId(this.getId());
        documentationNode.setTextDe(this.getTextDe());
        documentationNode.setTextFr(this.getTextFr());
        documentationNode.setStyle(this.getStyle());
        documentationNode.setDocumentationType(this.getDocumentationType());
        documentationNode.setVersionBegin(this.getVersionBegin());
        documentationNode.setVersionEnd(this.getVersionEnd());
        if (this.getSortierung() != null && !this.getSortierung().isBlank())
            documentationNode.setSortierung(Integer.valueOf(this.getSortierung()));
    }

    @Override
    public ViewEntityDocumentationNode toViewEntity(DocumentationNode t) {

        return new ViewEntityDocumentationNode(t);
    }

    public String getTextDe() {
        return textDe.get();
    }

    public SimpleStringProperty textDeProperty() {
        return textDe;
    }

    public void setTextDe(String textDe) {
        this.textDe.set(textDe);
    }

    public String getTextFr() {
        return textFr.get();
    }

    public SimpleStringProperty textFrProperty() {
        return textFr;
    }

    public void setTextFr(String textFr) {
        this.textFr.set(textFr);
    }

    public String getStyle() {
        return style.get();
    }

    public SimpleStringProperty styleProperty() {
        return style;
    }

    public void setStyle(String style) {
        this.style.set(style);
    }

    public ApplicationVersion getVersionBegin() {
        return versionBegin.get();
    }

    public SimpleObjectProperty<ApplicationVersion> versionBeginProperty() {
        return versionBegin;
    }

    public void setVersionBegin(ApplicationVersion versionBegin) {
        this.versionBegin.set(versionBegin);
    }

    public ApplicationVersion getVersionEnd() {
        return versionEnd.get();
    }

    public SimpleObjectProperty<ApplicationVersion> versionEndProperty() {
        return versionEnd;
    }

    public void setVersionEnd(ApplicationVersion versionEnd) {
        this.versionEnd.set(versionEnd);
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

    public String getSortierung() {
        return sortierung.get();
    }

    public SimpleStringProperty sortierungProperty() {
        return sortierung;
    }

    public void setSortierung(String sortierung) {
        this.sortierung.set(sortierung);
    }

    public long getCountPrintscreen_de() {
        return countPrintscreen_de.get();
    }

    public SimpleLongProperty countPrintscreen_deProperty() {
        return countPrintscreen_de;
    }

    public void setCountPrintscreen_de(long countPrintscreen_de) {
        this.countPrintscreen_de.set(countPrintscreen_de);
    }

    public long getCountPrintscreen_fr() {
        return countPrintscreen_fr.get();
    }

    public SimpleLongProperty countPrintscreen_frProperty() {
        return countPrintscreen_fr;
    }

    public void setCountPrintscreen_fr(long countPrintscreen_fr) {
        this.countPrintscreen_fr.set(countPrintscreen_fr);
    }

    @Override
    public ObservableList<ViewEntityDocumentationNode> getChildren() {
        return super.getChildren();
    }

    public String getVersionAsText() {
        return versionAsText.get();
    }

    public SimpleStringProperty versionAsTextProperty() {
        return versionAsText;
    }

    public void setVersionAsText(String versionAsText) {
        this.versionAsText.set(versionAsText);
    }

    @Override
    public String toString() {
        return "ViewEntityDocumentationNode{" +
                "id=" + getId() +
                ", textDe=" + textDe.getValue() +
                ", textFr=" + textFr.getValue() +
                ", style=" + style +
                ", docType=" + documentationType.getValue() +
                ", versionBegin=" + ((versionBegin.getValue() != null) ? versionBegin.getValue().getVersion() : null) +
                ", versionEnd=" + ((versionEnd.getValue() != null) ? versionEnd.getValue().getVersion() : null) +
                ", missingDe=" + (textDe.getValue() == null || textDe.getValue().trim().isEmpty() || countPrintscreen_de.lessThan(countPrintscreen_fr).get()) +
                ", missingFr=" + (textFr.getValue() == null || textFr.getValue().trim().isEmpty() || countPrintscreen_fr.lessThan(countPrintscreen_de).get()) +
                '}';
    }
}
