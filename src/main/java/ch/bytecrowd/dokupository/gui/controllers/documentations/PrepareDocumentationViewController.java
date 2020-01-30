package ch.bytecrowd.dokupository.gui.controllers.documentations;

import ch.bytecrowd.dokupository.gui.utils.StageManager;
import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractViewController;
import ch.bytecrowd.dokupository.gui.controllers.crud.CrudDocumentationViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.utils.ConverterFactory;
import ch.bytecrowd.dokupository.gui.utils.ValidatorUtil;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


@Component
public class PrepareDocumentationViewController extends AbstractViewController {

    private static final Logger LOG = LoggerFactory.getLogger(PrepareDocumentationViewController.class);

    private GenericCrudService<Application> applicationService;

    @Autowired
    private CrudDocumentationViewController manageDocumentationViewController;

    @FXML
    private JFXComboBox<Application> input_application;

    @FXML
    private JFXComboBox<DocumentationType> input_documentation_type;

    @FXML
    private JFXCheckBox input_load_commits;

    @FXML
    private JFXButton btnNext;

    @Autowired
    public PrepareDocumentationViewController(ApplicationRepository applicationService) {
        this.applicationService = new GenericCrudService<>(applicationService);
    }

    @Override
    public void onShow() {
        super.onShow();

        //input_application.setValue(null);
        input_documentation_type.setValue(null);
        input_load_commits.setSelected(false);

        input_application.getItems().setAll(applicationService.fetchAllRecords());
        input_documentation_type.getItems().setAll(DocumentationType.values());

    }

    @Override
    public void initialize() {
        super.initialize();

        input_application.setConverter(ConverterFactory.createApplicationConverter(applicationService));

        input_documentation_type.setConverter(ConverterFactory.createDocumentationTypeConverter());

        // Bind validator
        ValidatorUtil.addValidator(input_application, ValidatorUtil.REQUIRED_VALIDATOR);
        ValidatorUtil.addValidator(input_documentation_type, ValidatorUtil.REQUIRED_VALIDATOR);


        btnNext.setOnAction(event -> {

            executeUIBlockingTask(() -> {
                try {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("selected application: " + input_application.getValue().getNameDe());
                        LOG.debug("selected documentation type: " + input_documentation_type.getValue());
                    }
                    //applicationRepository.fetchById(input_application.getValue().getId()).orElseThrow(() -> { throw new RuntimeException("Application not in database: " + input_application.getValue());});
                    manageDocumentationViewController.setSelectedApplication(input_application.getValue(), input_load_commits.selectedProperty().get());
                    manageDocumentationViewController.setSelectedDocumentationType(input_documentation_type.getValue());

                    Platform.runLater(() -> {
                        try {
                            StageManager.getInstance().switchScene(FXMLView.CRUD_DOCUMENTATION, true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    LOG.error(String.format("SwitchScene failed for: %s", FXMLView.CRUD_USER), e);
                }
            });
        });

        // Bind button disabling
        btnNext.disableProperty().bind(input_application.valueProperty().isNull().or(input_documentation_type.valueProperty().isNull()));

    }

    @Override
    public FXMLView getFXMLView() {
        return FXMLView.CRUD_DOCUMENTATION_PREPARE;
    }
}
