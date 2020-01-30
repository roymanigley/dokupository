package ch.bytecrowd.dokupository.gui.utils;

import ch.bytecrowd.dokupository.DokuPositoryApplication;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.utils.I18nUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StageManager {

    @FunctionalInterface
    public interface OnSceneShow {

        public void onScheneShow();
    }

    private static Map<FXMLView, OnSceneShow> listeners = new HashMap<>();

    private static final Logger LOG = LoggerFactory.getLogger(StageManager.class);


    private FXMLLoader fxmlLoader;
    private Stage primaryStage;
    private Map<FXMLView, Scene> preparedScenes = new HashMap<>();

    private static StageManager instance;

    public void setOnSceneSwitch(FXMLView fxmlView,  OnSceneShow onSceneShow) {

        listeners.put(fxmlView, onSceneShow);
    }

    private StageManager() {
    }

    public void switchScene(FXMLView view) throws IOException {
        if (LOG.isDebugEnabled())
            LOG.debug("Switching to scene: " + view.getFxmlFile());
        switchScene(view, false);
    }

    public void switchScene(FXMLView view, boolean forceReload) throws IOException {

        if (!preparedScenes.containsKey(view) || forceReload) {
            initView(view);
        }

        primaryStage.setScene(preparedScenes.get(view));

        primaryStage.show();

        if (listeners.containsKey(view)) {
            listeners.get(view).onScheneShow();
        }
    }

    public void initView(FXMLView view) throws IOException {
        try {
            LOG.debug("Init view: " + view.getFxmlFile());
            Parent p = loadFXMLView(view);
            Scene scene = new Scene(p);
            loadCSS(scene);
            preparedScenes.put(view, scene);
        } catch (IOException e) {
            throw e;
        }
    }


    public Parent loadFXMLView(FXMLView view) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(view.getFxmlFile()));
        fxmlLoader.setResources(I18nUtil.i18n_BUNDLE);
        fxmlLoader.setControllerFactory(DokuPositoryApplication.getSpringContext()::getBean);
        return fxmlLoader.load();
    }

    private void loadCSS(Scene scene) {
        scene.getStylesheets().add(StageManager.class.getClassLoader().getResource("css/jfoenix-main-theme.css").toExternalForm());
    }

    public Stage getPrimaryStage() {

        return primaryStage;
    }

    private void setStage(Stage primaryStage) {
        if (primaryStage == null)
            throw new IllegalArgumentException("Stage could not be null");
        this.primaryStage = primaryStage;
    }

    public static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public static void initialize(Stage primaryStage) {

        primaryStage.getIcons().add(new Image(StageManager.class.getClassLoader().getResourceAsStream("img/logo.png")));
        StageManager first = getInstance();
        primaryStage.setMinWidth(550);
        primaryStage.setMinHeight(600);
        primaryStage.setWidth(1800);
        primaryStage.setHeight(980);
        first.setStage(primaryStage);
    }
}
