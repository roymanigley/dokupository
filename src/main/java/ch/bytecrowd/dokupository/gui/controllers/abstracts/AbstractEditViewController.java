package ch.bytecrowd.dokupository.gui.controllers.abstracts;

import ch.bytecrowd.dokupository.gui.utils.StageManager;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntity;
import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.gui.utils.ValidatorUtil;
import ch.bytecrowd.dokupository.models.interfaces.Keyable;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public abstract class AbstractEditViewController<T extends Keyable, K extends ViewEntity<T, K>> extends AbstractViewController { //implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEditViewController.class);
    @FXML
    private Button btnCancel;

    @FXML
    protected Button btnSave;

    private K selectedViewEnity = createViewEnitiy();

    public abstract void initMappedEditProperties();

    public abstract T createEnitiy();

    public abstract K createViewEnitiy();

    public abstract GenericCrudService<T> getService();

    public abstract FXMLView getParentFXMLView();

    public abstract BooleanBinding getBindingForSaveBtnDisable();

    @Override
    public void onShow() {

        LOG.debug("init Data EditViewController");
        super.onShow();
    }

    @FXML
    public void initialize() {

        super.initialize();
        initMappedEditProperties();
        initCancelButton();
        initSaveButton();
    }

    private void initSaveButton() {

        btnSave.disableProperty().bind(getBindingForSaveBtnDisable());

        btnSave.setOnAction(e -> {

            try {
                executeUIBlockingTask(() -> {
                    T t = createEnitiy();
                    selectedViewEnity.fillEntity(t);
                    t = save(t);
                    getSelectedViewEnity().setId(t.getId());
                    initMappedEditProperties();
                    LOG.info(String.format("Entity saved: %s", t));

                    Platform.runLater(() -> {
                        try {
                            if (navigateToParentAfterSave()) {
                                    DialogUtil.showInfoDialog("label.info.message.saved");
                                    StageManager.getInstance().switchScene(getParentFXMLView());
                            }
                        } catch (IOException ex) {
                            LOG.error(String.format("SwitchScene failed for: %s", getParentFXMLView()), e);
                        }
                    });
                });
            } catch (Exception ex) {
                LOG.error(String.format("Saving entity failed: : %s", selectedViewEnity), ex);
                DialogUtil.showErrorDialog(ex);
            }

        });
    }

    private void initCancelButton() {

        btnCancel.setOnAction(e -> {
            try {
                StageManager.getInstance().switchScene(getParentFXMLView());
            } catch (IOException ex) {
                LOG.error(String.format("SwitchScene failed for: %s", getParentFXMLView()), ex);
            }
        });
    }

    public T save(T t) {

        t = getService().saveRecord(t);
        return t;
    }


    protected void addRequiredValidator(JFXTextArea toValidate) {

        ValidatorUtil.addValidator(toValidate, ValidatorUtil.REQUIRED_VALIDATOR);
    }

    protected void addRequiredValidator(JFXTextField toValidate) {

        ValidatorUtil.addValidator(toValidate, ValidatorUtil.REQUIRED_VALIDATOR);
    }

    protected void addRequiredValidator(JFXComboBox toValidate) {

        ValidatorUtil.addValidator(toValidate, ValidatorUtil.REQUIRED_VALIDATOR);
    }

    protected void addRequiredValidator(JFXPasswordField toValidate) {

        ValidatorUtil.addValidator(toValidate, ValidatorUtil.REQUIRED_VALIDATOR);
    }

    public boolean navigateToParentAfterSave() {
        return true;
    }

    /**
     * Should be overwritten by child classes
     *
     * @return validators
     */
    public List<ValidatorBase> getValidators() {

        return Arrays.asList(ValidatorUtil.REQUIRED_VALIDATOR);
    }

    public K getSelectedViewEnity() {
        return selectedViewEnity;
    }

    public void setSelectedViewEnity(K selectedViewEnity) {

        this.selectedViewEnity = selectedViewEnity;
    }
}