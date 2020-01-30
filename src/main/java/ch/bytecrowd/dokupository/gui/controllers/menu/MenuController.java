package ch.bytecrowd.dokupository.gui.controllers.menu;

import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.utils.StageManager;
import com.jfoenix.controls.JFXDrawer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;

@Component
public class MenuController {

    private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);

    @FXML
    private Button addDocumentationEntryBtn;

    @FXML
    private Button generateDocumentationEntryBtn;

    @FXML
    private Button menuApplicationBtn;

    @FXML
    private Button menuVersionBtn;

    @FXML
    private Button menuCommitBtn;

    @FXML
    private Button menuWortTemplateBtn;

    @FXML
    private Button menuUserBtn;

    @FXML
    private JFXDrawer menuDrawer;

    @FXML
    private ImageView logo;

    @FXML
    public void initialize() {

        addDocumentationEntryBtn.setOnAction(e -> {
            try {
                StageManager.getInstance().switchScene(FXMLView.CRUD_DOCUMENTATION_PREPARE);
            } catch (IOException ex) {
                LOG.error(String.format( "SwitchScene failed for: %s", FXMLView.CRUD_DOCUMENTATION_PREPARE), ex);
            }
        });

        generateDocumentationEntryBtn.setOnAction(e -> {
            try {
                StageManager.getInstance().switchScene(FXMLView.GENERATE_DOCUMENTATION);
            } catch (IOException ex) {
                LOG.error(String.format( "SwitchScene failed for: %s", FXMLView.GENERATE_DOCUMENTATION), ex);
            }
        });

        menuApplicationBtn.setOnAction(e -> {
            try {
                StageManager.getInstance().switchScene(FXMLView.CRUD_APPLICATION);
            } catch (IOException ex) {
                LOG.error(String.format( "SwitchScene failed for: %s", FXMLView.CRUD_APPLICATION), ex);
            }
        });

        menuVersionBtn.setOnAction(e -> {
            try {
                StageManager.getInstance().switchScene(FXMLView.CRUD_VERSION);
            } catch (IOException ex) {
                LOG.error(String.format( "SwitchScene failed for: %s", FXMLView.CRUD_VERSION), ex);
            }
        });

        menuCommitBtn.setOnAction(e -> {
            try {
                StageManager.getInstance().switchScene(FXMLView.CRUD_COMMIT);
            } catch (IOException ex) {
                LOG.error(String.format( "SwitchScene failed for: %s", FXMLView.CRUD_COMMIT), ex);
            }
        });

        menuWortTemplateBtn.setOnAction(e -> {
            try {
                StageManager.getInstance().switchScene(FXMLView.CRUD_WORD_TEMPLATE);
            } catch (IOException ex) {
                LOG.error(String.format( "SwitchScene failed for: %s", FXMLView.CRUD_WORD_TEMPLATE), ex);
            }
        });

        menuUserBtn.setOnAction(e -> {
            try {
                StageManager.getInstance().switchScene(FXMLView.CRUD_USER);
            } catch (IOException ex) {
                LOG.error(String.format( "SwitchScene failed for: %s", FXMLView.CRUD_USER), ex);
            }
        });

        logo.setOnMouseClicked(e -> {
            try {
                StageManager.getInstance().switchScene(FXMLView.OVERVIEW);
            } catch (IOException ex) {
                LOG.error(String.format( "SwitchScene failed for: %s", FXMLView.OVERVIEW), ex);
            }
        });
    }

    public JFXDrawer getMenuDrawer() {
        return menuDrawer;
    }
}
