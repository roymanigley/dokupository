package ch.bytecrowd.dokupository.gui.controllers.crud;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractCrudViewController;
import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.controllers.crud.edit.EditUserViewController;
import ch.bytecrowd.dokupository.gui.models.ViewEntityUser;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.models.jpa.User;
import ch.bytecrowd.dokupository.repositories.jpa.UserRepository;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXTreeTableColumn;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrudUserViewController extends AbstractCrudViewController<User, ViewEntityUser> {

    private GenericCrudService<User> service;

    @Autowired
    private EditUserViewController userEditController;

    @FXML
    private JFXTreeTableColumn<ViewEntityUser, Long> col_id;

    @FXML
    private JFXTreeTableColumn<ViewEntityUser, String> col_lastname;

    @FXML
    private JFXTreeTableColumn<ViewEntityUser, String> col_firstname;

    @FXML
    private JFXTreeTableColumn<ViewEntityUser, String> col_username;

    @FXML
    private JFXTreeTableColumn<ViewEntityUser, String> col_acronym;


    @Autowired
    public CrudUserViewController(UserRepository service) {
        this.service = new GenericCrudService<>(service);
    }

    @Override
    public List<JFXTreeTableColumn> getMappedTableColumns() {

        final ArrayList<JFXTreeTableColumn> tableColumns = new ArrayList<>();
        col_id.setCellValueFactory(cell -> cell.getValue().getValue().idProperty().asObject());
        col_lastname.setCellValueFactory(cell -> cell.getValue().getValue().lastNameProperty());
        col_firstname.setCellValueFactory(cell -> cell.getValue().getValue().firstNameProperty());
        col_username.setCellValueFactory(cell -> cell.getValue().getValue().usernameProperty());
        col_acronym.setCellValueFactory(cell -> cell.getValue().getValue().acronymProperty());

        tableColumns.add(col_id);
        tableColumns.add(col_lastname);
        tableColumns.add(col_firstname);
        tableColumns.add(col_username);
        tableColumns.add(col_acronym);

        return tableColumns;
    }

    @Override
    public ViewEntityUser toViewEntity(User user) {

        return new ViewEntityUser(user);
    }

    @Override
    public ViewEntityUser createViewEnitiy() {

        return new ViewEntityUser();
    }

    @Override
    public FXMLView getFXMLView() {
        return FXMLView.CRUD_USER;
    }

    @Override
    public AbstractEditViewController<User, ViewEntityUser> getEditController() {

        return userEditController;
    }

    @Override
    public GenericCrudService<User> getService() {

        return service;
    }
}
