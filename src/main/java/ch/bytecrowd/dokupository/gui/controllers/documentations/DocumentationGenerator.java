package ch.bytecrowd.dokupository.gui.controllers.documentations;

import ch.bytecrowd.dokupository.gui.controllers.security.LoginController;
import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.utils.ConverterFactory;
import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import ch.bytecrowd.dokupository.models.jpa.WordTemplate;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationVersionRepository;
import ch.bytecrowd.dokupository.repositories.jpa.DocumentationNodeRepository;
import ch.bytecrowd.dokupository.repositories.jpa.WordTemplateRepository;
import ch.bytecrowd.dokupository.services.jpa.ApplicationVersionService;
import ch.bytecrowd.dokupository.services.jpa.DocumentationNodeCrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Component
public class DocumentationGenerator extends AbstractViewController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentationGenerator.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @FXML
    private JFXComboBox<Application> input_application;

    @FXML
    private JFXComboBox<WordTemplate> input_wordTempate;

    @FXML
    private JFXComboBox<ApplicationVersion> input_versionBegin;

    @FXML
    private JFXComboBox<ApplicationVersion> input_versionEnd;

    @FXML
    private JFXButton btnGenerateDocumentation;

    private DocumentationNodeCrudService documentationNodeRepository;

    private GenericCrudService<Application> applicationRepository;

    private GenericCrudService<WordTemplate> wordTemplateRepository;

    private ApplicationVersionService applicationVersionRepository;

    @Autowired
    private LoginController loginController;

    private ViewEntityDocumentationGenerator selectedViewModel;

    @Autowired
    public DocumentationGenerator(DocumentationNodeRepository documentationNodeRepository, ApplicationRepository applicationRepository, WordTemplateRepository wordTemplateRepository, ApplicationVersionRepository applicationVersionRepository) {
        this.documentationNodeRepository = new DocumentationNodeCrudService(documentationNodeRepository);
        this.applicationRepository = new GenericCrudService<>(applicationRepository);
        this.wordTemplateRepository = new GenericCrudService<>(wordTemplateRepository);
        this.applicationVersionRepository = new ApplicationVersionService(applicationVersionRepository);
    }

    @FXML
    @Override
    public void initialize() {

        super.initialize();

        // INIT BTN
        btnGenerateDocumentation.setOnAction(event -> {


            final WordTemplate wordTemplate = input_wordTempate.valueProperty().getValue();
            final ApplicationVersion versionBegin = input_versionBegin.valueProperty().getValue();
            final ApplicationVersion versionEnd = input_versionEnd.valueProperty().getValue();

            String fileName = wordTemplate.getName();
            fileName += " " + ((versionBegin != null && versionBegin.getId() != null) ? versionBegin.getVersion() + " - " : "");
            fileName += versionEnd.getVersion() + ".docx";

            final File targetFile = DialogUtil.showSaveDialogAndGetFile(fileName);

            if (targetFile != null) {
                Platform.runLater(() -> {
                executeUIBlockingTask(() -> {
                    final List<DocumentationNode> documentationNodeList = documentationNodeRepository.findAllByVersionBeginApplicationIdAnAndDocumentationTypeAndVersionSorts(versionEnd.getApplication().getId(), wordTemplate.getDocumentationType(), versionBegin != null ? versionBegin.getSortierung() : Integer.MIN_VALUE, versionEnd.getSortierung());

                    if (LOG.isDebugEnabled())
                        documentationNodeList.forEach(e -> LOG.debug(e.toString()));

                    final HashMap<String, String> map = fillMapWithPlaceholdeValues(wordTemplate, versionBegin, versionEnd);
                    map.put("$CURRENT_USER", loginController.getCurrentUser() != null ? loginController.getCurrentUser().getFirstname_Lastname() : "");
                    map.put("$CURRENT_USER_ACRONYM", loginController.getCurrentUser() != null ? loginController.getCurrentUser().getAcronym() : "");
                    map.put("$CURRENT_DATE", DATE_FORMATTER.format(LocalDate.now()));

                    try {
                        input_wordTempate.valueProperty().getValue().getDocumentationType().generateDocumentation(documentationNodeList, wordTemplate, map, targetFile);
                        Platform.runLater(() -> {
                            DialogUtil.showInfoDialog("label.info.message.documentation.generated");
                        });
                    } catch (IOException e) {
                        Platform.runLater(() -> {
                            DialogUtil.showErrorDialog(e);
                        });
                    }
                });
                });
            }
        });


        btnGenerateDocumentation.disableProperty().bind(
                input_application.valueProperty().isNull().or(
                        input_wordTempate.valueProperty().isNull()
                                .or(input_versionEnd.valueProperty().isNull())
                                .or(input_versionBegin.visibleProperty().and(input_versionBegin.valueProperty().isNull()))));


        // INIT DROPDOWN
        input_application.setConverter(ConverterFactory.createApplicationConverter(applicationRepository));

        input_application.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                final List<ApplicationVersion> appVersions = applicationVersionRepository.findAllByApplicationId(newValue.getId());
                input_versionBegin.getItems().setAll(appVersions);
                input_versionEnd.getItems().setAll(appVersions);
            }
        });

        input_wordTempate.setConverter(ConverterFactory.createWortTemplateConverter(wordTemplateRepository));


        input_versionBegin.setConverter(ConverterFactory.createApplicationVersionConverter(applicationVersionRepository));
        input_versionEnd.setConverter(ConverterFactory.createApplicationVersionConverter(applicationVersionRepository));

        // Binding
        selectedViewModel = new ViewEntityDocumentationGenerator();
        input_application.valueProperty().bindBidirectional(selectedViewModel.applicationProperty());
        input_wordTempate.valueProperty().bindBidirectional(selectedViewModel.wordTemplateProperty());

        input_wordTempate.valueProperty().addListener((observable, oldValue, newValue) -> {
            final boolean isChangeLog = newValue != null && newValue.getDocumentationType() != null && newValue.getDocumentationType().equals(DocumentationType.CHANGE_LOG);
            selectedViewModel.setChangeLogBool(isChangeLog);
        });

        input_versionBegin.valueProperty().bindBidirectional(selectedViewModel.versionBeginProperty());
        input_versionBegin.visibleProperty().bindBidirectional(selectedViewModel.changeLogBoolProperty());
        input_versionEnd.valueProperty().bindBidirectional(selectedViewModel.versionEndProperty());
    }

    public static HashMap<String, String> fillMapWithPlaceholdeValues(WordTemplate wordTemplate, ApplicationVersion versionBegin, ApplicationVersion versionEnd) {
        final HashMap<String, String> map = new HashMap<>();
        if (versionEnd != null && versionEnd.getApplication() != null) {
            map.put("$APP_NAME", wordTemplate.getLanguage().equals(DocumentationLanguage.FR) ? versionEnd.getApplication().getNameFr() : versionEnd.getApplication().getNameDe());
            map.put("$APP_TYPE", versionEnd.getApplication().getAppType().toI18nString(wordTemplate.getLanguage()) != null ? versionEnd.getApplication().getAppType().toI18nString(wordTemplate.getLanguage()) : "");
        }
        map.put("$APP_RESPONSIBLE", versionEnd != null && versionEnd.getApplication() != null && versionEnd.getApplication().getUserResponsible() != null ? versionEnd.getApplication().getUserResponsible().getFirstname_Lastname() : "");
        map.put("$APP_RESPONSIBLE_ACRONYM", versionEnd != null && versionEnd.getApplication() != null && versionEnd.getApplication().getUserResponsible() != null ? versionEnd.getApplication().getUserResponsible().getAcronym() : "");

        map.put("$VERSION_END_STATE", versionEnd != null && versionEnd.getState() != null ? versionEnd.getState().toI18nString(wordTemplate.getLanguage()) : "");
        map.put("$VERSION_END_RESPONSIBLE", versionEnd != null && versionEnd.getUserResponsible() != null ? versionEnd.getUserResponsible().getFirstname_Lastname() : "");
        map.put("$VERSION_END_RESPONSIBLE_ACRONYM", versionEnd != null && versionEnd.getUserResponsible() != null ? versionEnd.getUserResponsible().getAcronym() : "");
        map.put("$VERSION_END_TESTER", versionEnd != null && versionEnd.getUserTester() != null ? versionEnd.getUserTester().getFirstname_Lastname() : "");
        map.put("$VERSION_END_TESTER_ACRONYM", versionEnd != null && versionEnd.getUserTester() != null ? versionEnd.getUserTester().getAcronym() : "");
        map.put("$VERSION_END", versionEnd != null ? versionEnd.getVersion() : "");
        map.put("$VERSION_END_DATE", versionEnd != null && versionEnd.getReleaseDate() != null ? DATE_FORMATTER.format(versionEnd.getReleaseDate()) : "");

        map.put("$VERSION_BEGIN", versionBegin != null ? versionBegin.getVersion() : "");
        map.put("$VERSION_BEGIN_DATE", versionBegin != null && versionBegin.getReleaseDate() != null ? DATE_FORMATTER.format(versionBegin.getReleaseDate()) : "");
        return map;
    }

    @Override
    public void onShow() {
        super.onShow();

        input_versionBegin.setValue(null);
        input_versionEnd.setValue(null);

        input_application.setValue(null);
        final List<Application> applications = applicationRepository.fetchAllRecords();
        input_application.getItems().setAll(FXCollections.observableArrayList(applications));

        input_wordTempate.setValue(null);
        final List<WordTemplate> wordTemplates = wordTemplateRepository.fetchAllRecords();
        input_wordTempate.getItems().setAll(FXCollections.observableArrayList(wordTemplates));
    }

    @Override
    public FXMLView getFXMLView() {

        return FXMLView.GENERATE_DOCUMENTATION;
    }

    class ViewEntityDocumentationGenerator {

        private SimpleObjectProperty<Application> application = new SimpleObjectProperty<>();
        private SimpleObjectProperty<WordTemplate> wordTemplate = new SimpleObjectProperty<>();
        private SimpleObjectProperty<ApplicationVersion> versionBegin = new SimpleObjectProperty<>();
        private SimpleObjectProperty<ApplicationVersion> versionEnd = new SimpleObjectProperty<>();
        private SimpleBooleanProperty changeLogBool = new SimpleBooleanProperty();

        public Application getApplication() {
            return application.get();
        }

        public SimpleObjectProperty<Application> applicationProperty() {
            return application;
        }

        public void setApplication(Application application) {
            this.application.set(application);
        }

        public WordTemplate getWordTemplate() {
            return wordTemplate.get();
        }

        public SimpleObjectProperty<WordTemplate> wordTemplateProperty() {
            return wordTemplate;
        }

        public void setWordTemplate(WordTemplate wordTemplate) {
            this.wordTemplate.set(wordTemplate);
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

        public boolean isChangeLogBool() {
            return changeLogBool.get();
        }

        public SimpleBooleanProperty changeLogBoolProperty() {
            return changeLogBool;
        }

        public void setChangeLogBool(boolean changeLogBool) {
            this.changeLogBool.set(changeLogBool);
        }
    }
}
