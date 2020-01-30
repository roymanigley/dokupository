package ch.bytecrowd.dokupository.gui.controllers.abstracts;

import ch.bytecrowd.dokupository.gui.controllers.menu.MenuController;
import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.gui.utils.StageManager;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public abstract class AbstractViewController {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractViewController.class);

    @FXML
    private JFXDrawer loadingDrawer;

    @FXML
    private JFXHamburger btnHamburger;

    @FXML
    private JFXDrawer menuDrawer;


    private HamburgerBackArrowBasicTransition transition;

    @FXML
    public void initialize() {

        loadingDrawer.setVisible(false);
        addMenuToDrawer();
        initHamburgerButton();
        StageManager.getInstance().setOnSceneSwitch(getFXMLView(), () -> onShow()); //onShow());
    }

    public abstract FXMLView getFXMLView();

    public void onShow() {
        closeMenu();
        transition.setRate(-1);
        transition.play();
    }

    private void initHamburgerButton() {

        transition = new HamburgerBackArrowBasicTransition(btnHamburger);
        transition.setRate(-1);

        btnHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {

            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (menuDrawer.isOpened()) {
                closeMenu();
            } else {
                openMenu();
            }
        });
    }

    public void openMenu() {
        menuDrawer.setVisible(true);
        menuDrawer.setDisable(false);
        if (LOG.isDebugEnabled())
            LOG.debug("Opening Hamburger");
        menuDrawer.open();
    }

    @FXML
    public void closeMenu() {
        menuDrawer.setVisible(true);
        menuDrawer.setDisable(true);
        if (LOG.isDebugEnabled())
            LOG.debug("Closeing Hamburger");
        menuDrawer.close();
    }

    private void addMenuToDrawer() {

        try {
            final Parent menu = StageManager.getInstance().loadFXMLView(FXMLView.MENU);
            menuDrawer.setSidePane(menu);
            // Needed, other wise the buttons on the view are not clickable @see https://github.com/jfoenixadmin/JFoenix/issues/721
            // menuDrawer.setDisable(menuDrawer.isOpened());
            menuDrawer.setVisible(false);
        } catch (Exception e) {
            LOG.error("Loading Hamburgermenu failed", e);
        }
    }

    public void executeUIBlockingTask(Runnable runnable) {

        new Thread(() -> {
            try {
                if (LOG.isDebugEnabled())
                    LOG.debug("start UIBlockingTask");
                loadingDrawer.setVisible(true);
                loadingDrawer.open();
                runnable.run();

                // Damit gewartet wird bis die scene gewechselt hat
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadingDrawer.close();
                loadingDrawer.setVisible(false);
                if (LOG.isDebugEnabled())
                    LOG.debug("end UIBlockingTask");
            } catch (Exception e) {
                LOG.error("failed in executeUIBlockingTask", e);
                Platform.runLater(() -> DialogUtil.showErrorDialog(e));
            }
        }).start();
    }

    @FXML
    public void handleComboBoxKeyPress(KeyEvent keyEvent) {

        if (keyEvent.getCode().equals(KeyCode.DELETE) || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
            ((JFXComboBox) keyEvent.getSource()).getSelectionModel().clearSelection();
        }
    }
}
