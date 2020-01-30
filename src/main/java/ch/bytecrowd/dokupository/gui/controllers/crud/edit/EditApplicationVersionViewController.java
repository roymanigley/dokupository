package ch.bytecrowd.dokupository.gui.controllers.crud.edit;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityApplicationVersion;
import ch.bytecrowd.dokupository.gui.utils.ConverterFactory;
import ch.bytecrowd.dokupository.gui.utils.ValidatorUtil;
import ch.bytecrowd.dokupository.models.enums.ApplicationVersionState;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.models.jpa.User;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationVersionRepository;
import ch.bytecrowd.dokupository.repositories.jpa.UserRepository;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EditApplicationVersionViewController extends AbstractEditViewController<ApplicationVersion, ViewEntityApplicationVersion> {

    private GenericCrudService<ApplicationVersion> service;

    private GenericCrudService<Application> applicationService;

    private GenericCrudService<User> userService;

    @FXML
    private JFXTextField input_version;

    @FXML
    private JFXComboBox<Application> input_application;

    @FXML
    private JFXComboBox<ApplicationVersionState> input_state;

    @FXML
    private JFXComboBox<User> input_user_responsible;

    @FXML
    private JFXComboBox<User> input_user_tester;

    @FXML
    private JFXTextField input_remarkDe;

    @FXML
    private JFXTextField input_remarkFr;

    @FXML
    private DatePicker input_release_date;

    @FXML
    private JFXTextField input_sort;

    @Autowired
    public EditApplicationVersionViewController(ApplicationVersionRepository service, ApplicationRepository applicationService, UserRepository userService) {
        this.service = new GenericCrudService<>(service);
        this.applicationService = new GenericCrudService<>(applicationService);
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
        initDropdowns();
    }

    private void initDropdowns() {

        input_release_date.setConverter(ConverterFactory.createLocalDateConverter());

        input_application.getItems().setAll(applicationService.fetchAllRecords());
        input_application.setConverter(ConverterFactory.createApplicationConverter(applicationService));

        final List<User> users = userService.fetchAllRecords();

        input_user_responsible.getItems().setAll(users);
        input_user_responsible.setConverter(ConverterFactory.createUserConverter(userService));

        input_user_tester.getItems().setAll(users);
        input_user_tester.setConverter(ConverterFactory.createUserConverter(userService));

        input_state.getItems().setAll(ApplicationVersionState.values());
        input_state.setConverter(ConverterFactory.createApplicationVersionStateConverter());
    }

    @Override
    public void initMappedEditProperties() {

        // View Binding
        input_version.textProperty().bindBidirectional(getSelectedViewEnity().versionProperty());
        input_application.valueProperty().bindBidirectional(getSelectedViewEnity().applicationProperty());
        input_user_responsible.valueProperty().bindBidirectional(getSelectedViewEnity().userResponsibleProperty());
        input_user_tester.valueProperty().bindBidirectional(getSelectedViewEnity().userTesterProperty());
        input_state.valueProperty().bindBidirectional(getSelectedViewEnity().stateProperty());
        input_remarkDe.textProperty().bindBidirectional(getSelectedViewEnity().remarkDeProperty());
        input_remarkFr.textProperty().bindBidirectional(getSelectedViewEnity().remarkFrProperty());
        input_release_date.valueProperty().bindBidirectional(getSelectedViewEnity().releaseDateProperty());
        input_sort.textProperty().bindBidirectional(getSelectedViewEnity().sortierungProperty());

        // Validation
        addRequiredValidator(input_version);
        addRequiredValidator(input_application);
        addRequiredValidator(input_state);
        addRequiredValidator(input_sort);
        addRequiredValidator(input_user_responsible);

        ValidatorBase validatorNumeric = ValidatorUtil.createIntegerValidator(input_sort);
        ValidatorUtil.addValidator(input_sort, validatorNumeric);
    }

    @Override
    public BooleanBinding getBindingForSaveBtnDisable() {

        return input_version.textProperty().isEmpty()
                .or(input_sort.textProperty().isEmpty())
                .or(input_application.valueProperty().isNull());
    }

    @Override
    public ApplicationVersion createEnitiy() {

        return new ApplicationVersion();
    }

    @Override
    public ViewEntityApplicationVersion createViewEnitiy() {

        return new ViewEntityApplicationVersion();
    }

    @Override
    public GenericCrudService<ApplicationVersion> getService() {

        return service;
    }

    @Override
    public FXMLView getFXMLView() {

        return FXMLView.CRUD_VERSION_EDIT;
    }

    @Override
    public FXMLView getParentFXMLView() {

        return FXMLView.CRUD_VERSION;
    }
}
