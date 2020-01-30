package ch.bytecrowd.dokupository.gui.controllers.crud.edit;

import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.gui.utils.StageManager;
import ch.bytecrowd.dokupository.gui.controllers.vsc.CommitController;
import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.controllers.crud.CrudDocumentationViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityDocumentationNode;
import ch.bytecrowd.dokupository.gui.utils.ConverterFactory;
import ch.bytecrowd.dokupository.gui.utils.ValidatorUtil;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationVersionRepository;
import ch.bytecrowd.dokupository.repositories.jpa.DocumentationNodeRepository;
import ch.bytecrowd.dokupository.services.jpa.ApplicationVersionService;
import ch.bytecrowd.dokupository.services.jpa.DocumentationNodeCrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.*;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class EditDocumentationViewController extends AbstractEditViewController<DocumentationNode, ViewEntityDocumentationNode> {

    private static final Logger LOG = LoggerFactory.getLogger(EditDocumentationViewController.class);

    private DocumentationNodeCrudService service;

    private ApplicationVersionService applicationVersionService;

    @Autowired
    private CommitController commitController;

    @Autowired
    private CrudDocumentationViewController manageDocumentationViewController;

    @FXML
    private JFXComboBox<ApplicationVersion> input_version_begin;

    @FXML
    private JFXComboBox<ApplicationVersion> input_version_end;

    @FXML
    private JFXComboBox<String> input_style;

    @FXML
    private JFXTextArea input_text_de;

    @FXML
    private JFXTextArea input_text_fr;

    @FXML
    private JFXTextField input_sort;

    @FXML
    private JFXButton btnImages;

    @FXML
    private JFXButton btnSaveAndClear;

    @FXML
    private JFXToggleButton commitTypeToggler;

    @FXML
    private JFXListView<HBox> commitsList;

    private ApplicationVersion cachedVersionBegin = new ApplicationVersion();

    @Autowired
    public EditDocumentationViewController(DocumentationNodeRepository service, ApplicationVersionRepository applicationVersionService) {
        this.service = new DocumentationNodeCrudService(service);
        this.applicationVersionService = new ApplicationVersionService(applicationVersionService);
    }

    @Override
    public void onShow() {

        super.onShow();

        LOG.info(getSelectedViewEnity().toString());

        if (getSelectedViewEnity().getVersionBegin() != null) {
            cachedVersionBegin = getSelectedViewEnity().getVersionBegin();
        } else {
            getSelectedViewEnity().setVersionBegin(cachedVersionBegin);
        }

        initDropDowns();
    }

    @FXML
    @Override
    public void initialize() {

        super.initialize();


        btnSaveAndClear.disableProperty().bind(getBindingForSaveBtnDisable());
        btnSaveAndClear.setOnAction(e -> {
            try {
                //executeUIBlockingTask(() -> {
                    DocumentationNode t = createEnitiy();
                    getSelectedViewEnity().fillEntity(t);
                    t = save(t);
                    setSelectedViewEnity(createViewEnitiy());
                    getSelectedViewEnity().setSortierung((t.getSortierung() + 1) + "");
                    LOG.info(String.format("Entity saved: %s", t));

                    Platform.runLater(() -> {
                        try {
                            initMappedEditProperties();
                            if (navigateToParentAfterSave()) {
                                DialogUtil.showInfoDialog("label.info.message.saved");
                                StageManager.getInstance().switchScene(getParentFXMLView());
                            }
                        } catch (IOException ex) {
                            LOG.error(String.format("SwitchScene failed for: %s", getParentFXMLView()), e);
                        }
                    });
                //});
            } catch (Exception ex) {
                LOG.error(String.format("Saving entity failed: : %s", getSelectedViewEnity()), ex);
                DialogUtil.showErrorDialog(ex);
            }
        });

        btnImages.setOnAction(event -> {
            try {
                if (getSelectedViewEnity().getId() == 0L) {
                    DialogUtil.showWarnDialog("label.warn.message.save.before.relating.images");
                } else {
                    StageManager.getInstance().switchScene(FXMLView.CRUD_PRINT_SCREEN, true);
                }
            } catch (IOException ex) {
                LOG.error(String.format("SwitchScene failed for: %s", FXMLView.CRUD_PRINT_SCREEN), ex);
            }
        });

        if (getSelectedApplication().getRepoType() != null && getSelectedApplication().getRepositoryUrl() != null) {
            commitController.initCommits(getSelectedViewEnity().getId());
            //initCommits();
        } else {
            if (LOG.isDebugEnabled())
                LOG.debug("Skiping commit initialisation, no repository for application: " + getSelectedApplication().getNameDe());
        }
    }

    public void initDropDowns() {

        input_style.getItems().setAll(Arrays.asList("Heading 1", "Heading 2", "Heading 3", "Heading 4", "Default"));
        final List<ApplicationVersion> versionsByApplicationId = applicationVersionService.findAllByApplicationId(manageDocumentationViewController.getSelectedApplication().getId());
        input_version_end.getItems().addAll(versionsByApplicationId);
        input_version_begin.getItems().setAll(versionsByApplicationId);
    }

    @Override
    public void initMappedEditProperties() {

        // View Binding
        input_version_begin.valueProperty().bindBidirectional(getSelectedViewEnity().versionBeginProperty());
        input_version_end.valueProperty().bindBidirectional(getSelectedViewEnity().versionEndProperty());
        input_style.valueProperty().bindBidirectional(getSelectedViewEnity().styleProperty());
        input_text_de.textProperty().bindBidirectional(getSelectedViewEnity().textDeProperty());
        input_text_fr.textProperty().bindBidirectional(getSelectedViewEnity().textFrProperty());
        input_sort.textProperty().bindBidirectional(getSelectedViewEnity().sortierungProperty());

        // Dropdowns
        input_version_begin.setConverter(ConverterFactory.createApplicationVersionConverter(applicationVersionService));

        input_version_end.setConverter(ConverterFactory.createApplicationVersionConverter(applicationVersionService));

        input_style.setConverter(ConverterFactory.createStringConverter());

        // Validateion
        addRequiredValidator(input_version_begin);
        addRequiredValidator(input_style);
        addRequiredValidator(input_sort);

        ValidatorBase validatorNumeric = ValidatorUtil.createIntegerValidator(input_sort);
        ValidatorUtil.addValidator(input_sort, validatorNumeric);
    }


    @Override
    public BooleanBinding getBindingForSaveBtnDisable() {

        return input_version_begin.valueProperty().isNull()
                .or(input_style.valueProperty().isNull());
    }

    @Override
    public boolean navigateToParentAfterSave() {
        return false;
    }

    @Override
    public DocumentationNode createEnitiy() {

        final DocumentationNode documentationNode = new DocumentationNode();
        documentationNode.setDocumentationType(manageDocumentationViewController.getSelectedDocumentationType());
        return documentationNode;
    }

    @Override
    public ViewEntityDocumentationNode createViewEnitiy() {
        ViewEntityDocumentationNode viewEntityDocumentationNode;

        if (manageDocumentationViewController != null)
            viewEntityDocumentationNode =  manageDocumentationViewController.createViewEnitiy();
        else
            viewEntityDocumentationNode = new ViewEntityDocumentationNode();

        if (cachedVersionBegin != null)
            viewEntityDocumentationNode.setVersionBegin(cachedVersionBegin);
        return viewEntityDocumentationNode;
    }

    @Override
    public GenericCrudService<DocumentationNode> getService() {

        return service;
    }

    @Override
    public DocumentationNode save(DocumentationNode documentationNode) {

        DocumentationNode save = null;
        try {
            documentationNode.setDocumentationType(manageDocumentationViewController.getSelectedDocumentationType());
            cachedVersionBegin = getSelectedViewEnity().getVersionBegin();
            save = super.save(documentationNode);
            setSelectedViewEnity(new ViewEntityDocumentationNode(save));
            Platform.runLater(() -> {
                DialogUtil.showInfoDialog("label.info.message.saved");
            });
        } catch (Exception e) {
            DialogUtil.showErrorDialog(e);
        }
        return save;
    }

    @Override
    public FXMLView getFXMLView() {

        return FXMLView.EDIT_DOCUMENTATION;
    }

    @Override
    public FXMLView getParentFXMLView() {

        return FXMLView.CRUD_DOCUMENTATION;
    }

    public Application getSelectedApplication() {

        return manageDocumentationViewController.getSelectedApplication();
    }

    public JFXToggleButton getCommitTypeToggler() {
        return commitTypeToggler;
    }

    public JFXListView<HBox> getCommitsList() {
        return commitsList;
    }
}
