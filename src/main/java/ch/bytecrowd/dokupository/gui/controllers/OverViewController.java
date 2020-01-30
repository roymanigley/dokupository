package ch.bytecrowd.dokupository.gui.controllers;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.utils.StageManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class OverViewController extends AbstractViewController {

    private static final Logger LOG = LoggerFactory.getLogger(OverViewController.class);

    private final ObjectProperty<Image> imgProperty = new SimpleObjectProperty<>();
    private int counter = 0;
    @FXML
    private ImageView image;

    @Override
    public FXMLView getFXMLView() {

        return FXMLView.OVERVIEW;
    }

    @FXML
    @Override
    public void initialize() {
        super.initialize();
        this.image.imageProperty().bind(imgProperty);


        if (!Boolean.valueOf(System.getProperty("lazy-controller-initialisation"))) {
            executeUIBlockingTask(() -> {
                try {
                    StageManager.getInstance().initView(FXMLView.CRUD_USER);
                    StageManager.getInstance().initView(FXMLView.CRUD_COMMIT);
                    StageManager.getInstance().initView(FXMLView.CRUD_APPLICATION);
                    StageManager.getInstance().initView(FXMLView.CRUD_VERSION);
                    StageManager.getInstance().initView(FXMLView.CRUD_WORD_TEMPLATE);
                    StageManager.getInstance().initView(FXMLView.CRUD_DOCUMENTATION);
                    StageManager.getInstance().initView(FXMLView.CRUD_PRINT_SCREEN);

                    StageManager.getInstance().initView(FXMLView.GENERATE_DOCUMENTATION);
                    StageManager.getInstance().initView(FXMLView.CRUD_DOCUMENTATION_PREPARE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onShow() {
        super.onShow();
        counter = 0;
        imgProperty.setValue(new Image("img/logo.png", true));
    }

    @FXML
    void handleClick(MouseEvent event) {
        if (++counter == 11) {
            imgProperty.setValue(new Image("img/icons/.animation.gif", true));
        }
    }
}
