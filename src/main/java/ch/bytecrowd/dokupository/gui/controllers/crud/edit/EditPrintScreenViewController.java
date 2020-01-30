package ch.bytecrowd.dokupository.gui.controllers.crud.edit;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityPrintScreen;
import ch.bytecrowd.dokupository.gui.utils.ConverterFactory;
import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.gui.utils.ValidatorUtil;
import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import ch.bytecrowd.dokupository.models.jpa.PrintScreen;
import ch.bytecrowd.dokupository.repositories.jpa.DocumentationNodeRepository;
import ch.bytecrowd.dokupository.repositories.jpa.PrintscreenRepository;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import ch.bytecrowd.dokupository.utils.ImageUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Component
public class EditPrintScreenViewController extends AbstractEditViewController<PrintScreen, ViewEntityPrintScreen> {

    private GenericCrudService service;

    private GenericCrudService<DocumentationNode> documentationNodeService;

    @Autowired
    private EditDocumentationViewController editDocumentationViewController;

    @FXML
    private JFXTextField input_footnote;

    @FXML
    private JFXComboBox<DocumentationLanguage> input_printscreen_language;

    @FXML
    private JFXButton btnImportPrintScreen;

    @FXML
    private JFXTextField input_width;

    @FXML
    private JFXTextField input_height;

    private SimpleBooleanProperty areBytesSet;

    @Autowired
    public EditPrintScreenViewController(PrintscreenRepository service, DocumentationNodeRepository documentationNodeService) {
        this.service = new GenericCrudService<>(service);
        this.documentationNodeService = new GenericCrudService<>(documentationNodeService);
    }

    @FXML
    @Override
    public void initialize() {

        areBytesSet = new SimpleBooleanProperty(getSelectedViewEnity().getBytes() != null);

        super.initialize();

        btnImportPrintScreen.setOnAction(event -> {
            try {
                final byte[] bytes = DialogUtil.showFileChooserAndGetBytes();
                getSelectedViewEnity().setBytes(bytes);
                areBytesSet.set(true);
                try {
                    final BufferedImage image = ImageUtil.bytesToImage(bytes);
                    getSelectedViewEnity().setWidth(Double.valueOf(image.getWidth()).toString());
                    getSelectedViewEnity().setHeigth(Double.valueOf(image.getHeight()).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                DialogUtil.showErrorDialog(e);
            }
        });

        input_printscreen_language.getItems().setAll(DocumentationLanguage.values());
        input_printscreen_language.setConverter(ConverterFactory.createDocumentationLanguageConverter());
    }

    @Override
    public PrintScreen save(PrintScreen printScreen) {

        final long idDocumentationNode = editDocumentationViewController.getSelectedViewEnity().getId();
        final DocumentationNode selectedDocumentationNode = documentationNodeService.fetchById(idDocumentationNode).orElseThrow(() -> {
            throw new IllegalArgumentException("idDocumentationNode is not valid, Entity not found: " + idDocumentationNode);
        });
        printScreen.setDocumentationNode(selectedDocumentationNode);
        return super.save(printScreen);
    }

    @Override
    public void initMappedEditProperties() {

        input_footnote.textProperty().bindBidirectional(getSelectedViewEnity().footnoteProperty());
        input_printscreen_language.valueProperty().bindBidirectional(getSelectedViewEnity().languageProperty());

        input_width.textProperty().bindBidirectional(getSelectedViewEnity().widthProperty());
        input_height.textProperty().bindBidirectional(getSelectedViewEnity().heigthProperty());


        ValidatorBase validatorNumericWidth = ValidatorUtil.createDoubleValidator(input_width);
        ValidatorUtil.addValidator(input_width, validatorNumericWidth);
        ValidatorBase validatorNumericHeigth = ValidatorUtil.createDoubleValidator(input_width);
        ValidatorUtil.addValidator(input_width, validatorNumericHeigth);

        addRequiredValidator(input_printscreen_language);
    }

    @Override
    public List<ValidatorBase> getValidators() {
        return super.getValidators();
    }

    @Override
    public BooleanBinding getBindingForSaveBtnDisable() {

        return areBytesSet.not().or(getSelectedViewEnity().languageProperty().isNull());
    }

    @Override
    public PrintScreen createEnitiy() {

        return new PrintScreen();
    }

    @Override
    public ViewEntityPrintScreen createViewEnitiy() {

        return new ViewEntityPrintScreen();
    }

    @Override
    public GenericCrudService<PrintScreen> getService() {

        return service;
    }

    @Override
    public FXMLView getFXMLView() {

        return FXMLView.CRUD_PRINT_SCREEN_EDIT;
    }

    @Override
    public FXMLView getParentFXMLView() {

        return FXMLView.CRUD_PRINT_SCREEN;
    }
}
