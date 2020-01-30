package ch.bytecrowd.dokupository;

import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.gui.utils.StageManager;
import ch.bytecrowd.dokupository.gui.controllers.security.LoginController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.models.jpa.User;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;

@SpringBootApplication
public class DokuPositoryApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(DokuPositoryApplication.class);

    private static ConfigurableApplicationContext springContext;
    private static HostServices applicationHostServices;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {

        try {
            springContext = SpringApplication.run(DokuPositoryApplication.class, "");
            applicationHostServices = getHostServices();
        } catch (Exception e) {
            LOG.error("Starting application failed: " + e);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        springContext.stop();
    }

    @Override
    public void start(Stage stage) throws Exception {

        final StageManager stateManager = StageManager.getInstance();
        stateManager.initialize(stage);

        final LoginController loginController = springContext.getBean(LoginController.class);
        // injectDevUser(loginController);
        final boolean loginSuccess = loginController.tryLogin();

        if (loginSuccess) {
            stateManager.switchScene(FXMLView.OVERVIEW);
        }
    }

    private static void injectDevUser(LoginController loginController) throws NoSuchFieldException, IllegalAccessException {

        final Field currentUser = loginController.getClass().getDeclaredField("currentUser");
        currentUser.setAccessible(true);
        final User devUser = new User();
        devUser.setUsername("dev-user");
        devUser.setFirstname("John");
        devUser.setLastname("Doe");
        devUser.setAcronym("JD");
        currentUser.set(loginController, devUser);
    }

    public static ConfigurableApplicationContext getSpringContext() {

        return springContext;
    }

    public static HostServices getApplicationHostServices() {

        return applicationHostServices;
    }
}
