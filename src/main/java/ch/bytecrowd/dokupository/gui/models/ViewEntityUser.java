package ch.bytecrowd.dokupository.gui.models;

import ch.bytecrowd.dokupository.models.jpa.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewEntityUser extends ViewEntity<User, ViewEntityUser> {

    private SimpleStringProperty firstName = new SimpleStringProperty();
    private SimpleStringProperty lastName = new SimpleStringProperty();
    private SimpleStringProperty username = new SimpleStringProperty();
    private SimpleStringProperty acronym = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleStringProperty passwordRepeat = new SimpleStringProperty();
    private SimpleBooleanProperty active = new SimpleBooleanProperty();

    public ViewEntityUser() {

    }

    public ViewEntityUser(User user) {
        super(user);
        this.firstName = new SimpleStringProperty(user.getFirstname());
        this.lastName = new SimpleStringProperty(user.getLastname());
        this.username = new SimpleStringProperty(user.getUsername());
        this.acronym = new SimpleStringProperty(user.getAcronym());
        this.active = new SimpleBooleanProperty(user.getActive());
    }

    @Override
    public void fillEntity(User user) {
        super.fillEntity(user);
        user.setFirstname(this.getFirstName());
        user.setLastname(this.getLastName());
        user.setAcronym(this.getAcronym());
        user.setUsername(this.getUsername());
        user.setActive(this.isActive());
        if (this.getPassword() != null) {
            user.setPassword(this.getPassword());
        }
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String fName) {
        lastName.set(fName);
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getAcronym() {
        return acronym.get();
    }

    public SimpleStringProperty acronymProperty() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym.set(acronym);
    }

    public String getPassword() {
        return password.get();
    }

    public String getPasswordRepeat() {
        return passwordRepeat.get();
    }

    public SimpleStringProperty passwordRepeatProperty() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat.set(passwordRepeat);
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public boolean isActive() {
        return active.get();
    }

    @Override
    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    @Override
    public String toString() {
        return "ViewEntityUser{" +
                "id=" + getId() +
                ", firstName=" + firstName.getValue() +
                ", lastName=" + lastName.getValue() +
                ", username=" + username.getValue() +
                ", acronym=" + acronym.getValue() +
                '}';
    }
}
