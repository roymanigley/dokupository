package ch.bytecrowd.dokupository.gui.controllers.crud.edit;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityWordTemplate;
import ch.bytecrowd.dokupository.gui.utils.ConverterFactory;
import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.WordTemplate;
import ch.bytecrowd.dokupository.repositories.jpa.WordTemplateRepository;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class EditWordTemplateViewController extends AbstractEditViewController<WordTemplate, ViewEntityWordTemplate> {

    private static final Logger LOG = LoggerFactory.getLogger(EditWordTemplateViewController.class);

    private GenericCrudService<WordTemplate> service;

    @FXML
    private JFXTextField input_name;

    @FXML
    private JFXComboBox<DocumentationType> input_documentation_type;

    @FXML
    private JFXComboBox<DocumentationLanguage> input_template_language;

    @FXML
    private JFXButton btnImportTemplate;
    @FXML
    private JFXButton btnExportTemplate;

    private SimpleBooleanProperty areBytesSet;

    @Autowired
    public EditWordTemplateViewController(WordTemplateRepository service) {
        this.service = new GenericCrudService<>(service);
    }

    @FXML
    @Override
    public void initialize() {
        areBytesSet = new SimpleBooleanProperty(getSelectedViewEnity().getBytes() != null);

        super.initialize();
        initDropDown();


        btnImportTemplate.setOnAction(event -> {
            try {
                final byte[] bytes = DialogUtil.showFileChooserAndGetBytes();
                if (bytes == null)
                    return;
                getSelectedViewEnity().setBytes(bytes);
                areBytesSet.set(true);
            } catch (IOException e) {
                DialogUtil.showErrorDialog(e);
            }
        });

        btnExportTemplate.setOnAction(e -> {

            if (getSelectedViewEnity().getBytes() != null) {

                final File file = DialogUtil.showSaveDialogAndGetFile(getSelectedViewEnity().getName() + ".docx");
                try (final FileOutputStream outputStream = new FileOutputStream(file);) {
                    outputStream.write(getSelectedViewEnity().getBytes());
                    outputStream.flush();
                    DialogUtil.showInfoDialog("label.word.template.exported");
                } catch (IOException ex) {
                    LOG.error("Exporting Wordtemplate failed", e);
                    DialogUtil.showErrorDialog(ex);
                }
            } else {
                DialogUtil.showInfoDialog("info.no.bytes.in.word");
            }
        });
    }

    private void initDropDown() {


        input_documentation_type.getItems().setAll(DocumentationType.values());
        input_documentation_type.setConverter(ConverterFactory.createDocumentationTypeConverter());

        input_template_language.getItems().setAll(DocumentationLanguage.values());
        input_template_language.setConverter(ConverterFactory.createDocumentationLanguageConverter());
    }

    @Override
    public void initMappedEditProperties() {

        // View Binding
        input_name.textProperty().bindBidirectional(getSelectedViewEnity().nameProperty());
        input_documentation_type.valueProperty().bindBidirectional(getSelectedViewEnity().documentationTypeProperty());
        input_template_language.valueProperty().bindBidirectional(getSelectedViewEnity().languageProperty());


        // Validation
        addRequiredValidator(input_name);
        addRequiredValidator(input_template_language);
        addRequiredValidator(input_documentation_type);
    }

    @Override
    public BooleanBinding getBindingForSaveBtnDisable() {

        return input_name.textProperty().isEmpty()
                .or(input_documentation_type.valueProperty().isNull())
                .or(input_template_language.valueProperty().isNull())
                .or(areBytesSet.not());
    }

    @Override
    public WordTemplate createEnitiy() {

        return new WordTemplate();
    }

    @Override
    public ViewEntityWordTemplate createViewEnitiy() {

        return new ViewEntityWordTemplate();
    }

    @Override
    public GenericCrudService<WordTemplate> getService() {

        return service;
    }

    @Override
    public FXMLView getFXMLView() {

        return FXMLView.CRUD_WORD_TEMPLATE_EDIT;
    }

    @Override
    public FXMLView getParentFXMLView() {

        return FXMLView.CRUD_WORD_TEMPLATE;
    }
}
