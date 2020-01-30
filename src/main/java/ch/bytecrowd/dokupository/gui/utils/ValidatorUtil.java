package ch.bytecrowd.dokupository.gui.utils;

import ch.bytecrowd.dokupository.utils.I18nUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

public class ValidatorUtil {

    public static final ValidatorBase REQUIRED_VALIDATOR = new RequiredFieldValidator(I18nUtil.getI18nString("message.input.required"));

    private ValidatorUtil() {

    }

    public static ValidatorBase createPasswordValidator(JFXPasswordField input_password, JFXPasswordField input_passwordRepeat) {

        return new ValidatorBase(I18nUtil.getI18nString("message.passworts.dont.match")) {
            @Override
            protected void eval() {
                final boolean arePasswordsNotEqual = !input_password.textProperty().getValueSafe().equals(input_passwordRepeat.textProperty().getValueSafe());
                hasErrors.set(arePasswordsNotEqual);
            }
        };
    }

    public static ValidatorBase createIntegerValidator(JFXTextField input_number) {

        return new ValidatorBase(I18nUtil.getI18nString("message.format.should.be.integer")) {
            @Override
            protected void eval() {
                final boolean isStringDigit = input_number.textProperty().getValueSafe().matches("^\\d*$");
                hasErrors.set(!isStringDigit);
            }
        };
    }

    public static ValidatorBase createDoubleValidator(JFXTextField input_number) {

        return new ValidatorBase(I18nUtil.getI18nString("message.format.should.be.double")) {
            @Override
            protected void eval() {
                final boolean isStringDigit = input_number.textProperty().getValueSafe().matches("^\\d*.?\\d*$");
                hasErrors.set(!isStringDigit);
            }
        };
    }

    public static void addValidator(JFXTextArea toValidate, ValidatorBase validator) {
        toValidate.getValidators().add(validator);
        toValidate.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                toValidate.validate();
            }
        });
        toValidate.validate();
    }

    public static void addValidator(JFXTextField toValidate, ValidatorBase validator) {
        toValidate.getValidators().add(validator);
        toValidate.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                toValidate.validate();
            }
        });
        toValidate.validate();
    }

    public static void addValidator(JFXComboBox toValidate, ValidatorBase validator) {

        toValidate.getValidators().add(validator);
        toValidate.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                toValidate.validate();
            }
        });
        toValidate.validate();
    }

    public static void addValidator(JFXPasswordField toValidate, ValidatorBase validator) {
        toValidate.getValidators().add(validator);
        toValidate.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                toValidate.validate();
            }
        });
        toValidate.validate();
    }
}
