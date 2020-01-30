package ch.bytecrowd.dokupository.gui.controllers.crud.edit;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.models.ViewEntityUser;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.utils.ValidatorUtil;
import ch.bytecrowd.dokupository.models.jpa.User;
import ch.bytecrowd.dokupository.repositories.jpa.UserRepository;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EditUserViewController extends AbstractEditViewController<User, ViewEntityUser> {

    private ValidatorBase passwordValidator;

    private GenericCrudService<User> service;

    @FXML
    private JFXTextField input_firstname;

    @FXML
    private JFXTextField input_lastname;

    @FXML
    private JFXTextField input_username;

    @FXML
    private JFXTextField input_acronym;

    @FXML
    private JFXPasswordField input_password;
    @FXML
    private JFXPasswordField input_passwordRepeat;

    @FXML
    private JFXCheckBox input_active;

    @Autowired
    public EditUserViewController(UserRepository service) {
        this.service = new GenericCrudService<>(service);
    }

    @Override
    public User save(User user) {

        if (user.getPassword() == null && user.getId() != null) {
            final User userFromDB = getService().fetchById(user.getId()).orElse(new User());
            user.setPassword(userFromDB.getPassword());
        } else if (user.getPassword() != null) {
            user.setPassword(DigestUtils.sha512Hex(user.getPassword()));
        }
        return super.save(user);
    }

    @Override
    public void initMappedEditProperties() {

        // View Binding
        input_firstname.textProperty().bindBidirectional(getSelectedViewEnity().firstNameProperty());
        input_lastname.textProperty().bindBidirectional(getSelectedViewEnity().lastNameProperty());
        input_username.textProperty().bindBidirectional(getSelectedViewEnity().usernameProperty());
        input_acronym.textProperty().bindBidirectional(getSelectedViewEnity().acronymProperty());
        input_password.textProperty().bindBidirectional(getSelectedViewEnity().passwordProperty());
        input_passwordRepeat.textProperty().bindBidirectional(getSelectedViewEnity().passwordRepeatProperty());
        input_active.selectedProperty().bindBidirectional(getSelectedViewEnity().activeProperty());

        // Validation
        addRequiredValidator(input_lastname);
        addRequiredValidator(input_firstname);
        addRequiredValidator(input_username);
        addRequiredValidator(input_acronym);

        if (getSelectedViewEnity().getId() == 0L) {

            addRequiredValidator(input_password);
            addRequiredValidator(input_passwordRepeat);
        }
        passwordValidator = ValidatorUtil.createPasswordValidator(input_password, input_passwordRepeat);
        ValidatorUtil.addValidator(input_password, passwordValidator);
        ValidatorUtil.addValidator(input_passwordRepeat, passwordValidator);
    }

    @Override
    public BooleanBinding getBindingForSaveBtnDisable() {

        BooleanBinding binding = input_firstname.textProperty().isEmpty()
                .or(input_lastname.textProperty().isEmpty())
                .or(input_username.textProperty().isEmpty())
                .or(input_acronym.textProperty().isEmpty())
                .or(input_password.textProperty().isNotEqualTo(input_passwordRepeat.textProperty()));

        if (getSelectedViewEnity().getId() == 0L) {

            binding = binding
                .or(input_password.textProperty().isEmpty())
                .or(input_passwordRepeat.textProperty().isEmpty());
        }

        return binding;
    }

    @Override
    public List<ValidatorBase> getValidators() {

        final List<ValidatorBase> validators = new ArrayList<>(super.getValidators());
        validators.add(passwordValidator);
        return validators;
    }

    @Override
    public User createEnitiy() {

        return new User();
    }

    @Override
    public ViewEntityUser createViewEnitiy() {

        return new ViewEntityUser();
    }

    @Override
    public GenericCrudService<User> getService() {

        return service;
    }

    @Override
    public FXMLView getFXMLView() {

        return FXMLView.CRUD_USER_EDIT;
    }

    @Override
    public FXMLView getParentFXMLView() {

        return FXMLView.CRUD_USER;
    }
}
