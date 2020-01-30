package ch.bytecrowd.dokupository.gui.utils;

import ch.bytecrowd.dokupository.utils.I18nUtil;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;


/**
 * see: https://github.com/controlsfx/controlsfx/issues/1100
 *
 * Die to the issue with JFXDialog an Java 11 i use the default JavaFX Dialogs
 */
public class DialogUtil {

    public static <T> void showDeleteDialog(T viewEntity, Consumer<T> onConfirm) {

        final String titleI18n = I18nUtil.getI18nString("label.delete");
        final String headerTextI18n = I18nUtil.getI18nString("text.confirm.delete");
        showConformationDialog(viewEntity, onConfirm, titleI18n, headerTextI18n);
    }

    public static void showInfoDialog(String hederTextI18n) {

        final String title = I18nUtil.getI18nString("label.info.message");
        final String hederText = I18nUtil.getI18nString(hederTextI18n);
        showDialog(title, hederText, Alert.AlertType.INFORMATION);
    }

    public static void showWarnDialog(String hederTextI18n) {

        final String title = I18nUtil.getI18nString("label.warn.message");
        final String hederText = I18nUtil.getI18nString(hederTextI18n);
        showDialog(title, hederText, Alert.AlertType.WARNING);
    }



    public static <T> void showConformationDialog(T viewEntity, Consumer<T> onConfirm, String titleI18n, String headerTextI18n) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        addIconToDialog(alert);
        alert.setTitle(titleI18n);
        alert.setHeaderText(headerTextI18n );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            onConfirm.accept(viewEntity);
        }
    }

    private static void showDialog(String title, String hederText, Alert.AlertType type) {
        Alert alert = new Alert(type);
        addIconToDialog(alert);
        alert.setTitle(title);
        alert.setHeaderText(hederText);
        alert.showAndWait();
    }


    public static void showErrorDialog(Exception ex) {

        showErrorDialog(ex, "label.error.message.occurred");
    }

    public static void showErrorDialog(Exception ex, String hederTextI18n) {

        final String title = I18nUtil.getI18nString("label.error.message");
        final String hederText = I18nUtil.getI18nString(hederTextI18n);
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        addIconToDialog(alert);

        alert.setTitle(title);
        alert.setHeaderText(hederText);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Stacktrace:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    /**
     * @see "https://code.makery.ch/blog/javafx-dialogs-official/#custom-login-dialog"
     */
    public static boolean showLoginDialog(BiFunction<String, String, Boolean> authentification) {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        addIconToDialog(dialog);
        dialog.setTitle(I18nUtil.getI18nString("label.title.login"));
        dialog.setHeaderText(I18nUtil.getI18nString("label.header.login"));

        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText(I18nUtil.getI18nString("label.user.username"));
        PasswordField password = new PasswordField();
        password.setPromptText(I18nUtil.getI18nString("label.user.password"));

        grid.add(new Label(I18nUtil.getI18nString("label.user.username") + ":"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label(I18nUtil.getI18nString("label.user.password") + ":"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        final AtomicBoolean loginSuccess = new AtomicBoolean(false);

        result.ifPresent(usernamePassword -> {
            if (authentification.apply(usernamePassword.getKey(), usernamePassword.getValue())) {
                DialogUtil.showInfoDialog("label.login.success");
                loginSuccess.set(true);
            } else {
                DialogUtil.showWarnDialog("label.login.failed");
                loginSuccess.set(showLoginDialog(authentification));
            }
        });
        return loginSuccess.get();
    }

    public static byte[] showFileChooserAndGetBytes() throws IOException {
        final FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(I18nUtil.getI18nString("label.choose.file"));
        /*fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Word Document (docx)", "*.docx")
        );*/
        final File file = fileChooser.showOpenDialog(StageManager.getInstance().getPrimaryStage());
        try {
            return file != null ? Files.readAllBytes(file.toPath()) : null;
        } catch (IOException e) {
            throw e;
        }
    }

    public static File showSaveDialogAndGetFile(String targetFilename) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.docx", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(targetFilename);
        //Show save file dialog
        return fileChooser.showSaveDialog(StageManager.getInstance().getPrimaryStage());

    }

    private static void addIconToDialog(Dialog dialog) {
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(StageManager.class.getClassLoader().getResourceAsStream("img/logo.png")));
    }
}
