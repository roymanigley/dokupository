package ch.bytecrowd.dokupository.gui.controllers.security;

import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.models.jpa.User;
import ch.bytecrowd.dokupository.repositories.jpa.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository repository;

    private User currentUser;

    public boolean tryLogin() {

        if (repository.findUsersByActiveIsTrue().size() < 1) {

            LOG.info("Creating first user, username: admin password: admin");
            final User user = new User();
            user.setFirstname("admin");
            user.setLastname("admin");
            user.setAcronym("admin");
            user.setUsername("admin");
            user.setPassword(DigestUtils.sha512Hex("admin"));
            user.setActive(true);
            currentUser = repository.save(user);

            LOG.info("First user saved, username: admin password: admin");
            DialogUtil.showInfoDialog("label.first.login.username.and.password");
        }

        return DialogUtil.showLoginDialog((username, password) -> {

            LOG.info(String.format("Start login for user: %s", username));
            final String hashedPassword = DigestUtils.sha512Hex(password);
            final Optional<User> user = repository.findUserByUsernameAndPasswordAndActiveIsTrue(username, hashedPassword);
            LOG.info(String.format("End login for user: %s, login success: %s", username, user.isPresent()));
            currentUser = user.orElse(null);
            return user.isPresent();
        });
    }

    public User getCurrentUser() {
        return currentUser;
    }

}