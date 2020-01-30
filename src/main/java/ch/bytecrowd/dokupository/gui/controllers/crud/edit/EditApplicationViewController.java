package ch.bytecrowd.dokupository.gui.controllers.crud.edit;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityApplication;
import ch.bytecrowd.dokupository.gui.utils.ConverterFactory;
import ch.bytecrowd.dokupository.models.enums.ApplicationType;
import ch.bytecrowd.dokupository.models.enums.RepoType;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.User;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.repositories.jpa.UserRepository;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditApplicationViewController extends AbstractEditViewController<Application, ViewEntityApplication> {

    private  GenericCrudService<Application> service;

    private GenericCrudService<User> userService;

    @FXML
    private JFXComboBox<ApplicationType> input_app_type;

    @FXML
    private JFXTextField input_nameDe;
    @FXML
    private JFXTextField input_nameFr;

    @FXML
    private JFXComboBox<RepoType> input_repo_type;

    @FXML
    private JFXComboBox<User> input_user_responsible;

    @FXML
    private JFXTextField input_repo_url;

    @FXML
    private JFXTextField input_repo_branch;

    @FXML
    private JFXTextField input_repo_token;

    @FXML
    private JFXTextField input_remarkDe;

    @FXML
    private JFXTextField input_remarkFr;

    @Autowired
    public EditApplicationViewController(ApplicationRepository service, UserRepository userService) {
        this.service = new GenericCrudService<>(service);
        this.userService = new GenericCrudService<>(userService);
    }

    @FXML
    @Override
    public void initialize() {

        super.initialize();
    }

    @Override
    public void onShow() {
        super.onShow();
        initDropDowns();
    }

    private void initDropDowns() {

        input_app_type.getItems().setAll(ApplicationType.values());
        input_app_type.setConverter(ConverterFactory.createAppTypeConverter());

        input_repo_type.getItems().setAll(RepoType.values());
        input_repo_type.setConverter(ConverterFactory.createRepoTypeConverter());

        input_user_responsible.getItems().setAll(userService.fetchAllRecords());
        input_user_responsible.setConverter(ConverterFactory.createUserConverter(userService));
    }

    @Override
    public void initMappedEditProperties() {

        // View Binding
        input_app_type.valueProperty().bindBidirectional(getSelectedViewEnity().appTypeProperty());
        input_nameDe.textProperty().bindBidirectional(getSelectedViewEnity().nameDeProperty());
        input_nameFr.textProperty().bindBidirectional(getSelectedViewEnity().nameFrProperty());
        input_repo_type.valueProperty().bindBidirectional(getSelectedViewEnity().repoTypeProperty());
        input_user_responsible.valueProperty().bindBidirectional(getSelectedViewEnity().userResponsibleProperty());
        input_repo_url.textProperty().bindBidirectional(getSelectedViewEnity().repoUrlProperty());
        input_repo_branch.textProperty().bindBidirectional(getSelectedViewEnity().branchProperty());
        input_repo_token.textProperty().bindBidirectional(getSelectedViewEnity().tokenProperty());
        input_remarkDe.textProperty().bindBidirectional(getSelectedViewEnity().remarkDeProperty());
        input_remarkFr.textProperty().bindBidirectional(getSelectedViewEnity().remarkFrProperty());

        // Validation
        addRequiredValidator(input_app_type);
        addRequiredValidator(input_nameDe);
        addRequiredValidator(input_nameFr);
        addRequiredValidator(input_repo_url);
        addRequiredValidator(input_repo_type);
        addRequiredValidator(input_repo_branch);
        addRequiredValidator(input_user_responsible);
    }

    @Override
    public BooleanBinding getBindingForSaveBtnDisable() {

        return input_nameDe.textProperty().isEmpty()
                .or(input_nameFr.textProperty().isEmpty())
                .or(input_repo_url.textProperty().isEmpty())
                .or(input_repo_branch.textProperty().isEmpty())
                .or(input_repo_type.valueProperty().isNull())
                .or(input_user_responsible.valueProperty().isNull());
    }

    @Override
    public Application createEnitiy() {

        return new Application();
    }

    @Override
    public ViewEntityApplication createViewEnitiy() {

        return new ViewEntityApplication();
    }

    @Override
    public GenericCrudService<Application> getService() {

        return service;
    }

    @Override
    public FXMLView getFXMLView() {

        return FXMLView.CRUD_APPLICATION_EDIT;
    }

    @Override
    public FXMLView getParentFXMLView() {

        return FXMLView.CRUD_APPLICATION;
    }
}
