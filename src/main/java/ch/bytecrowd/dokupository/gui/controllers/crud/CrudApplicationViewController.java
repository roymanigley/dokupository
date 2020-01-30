package ch.bytecrowd.dokupository.gui.controllers.crud;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractCrudViewController;
import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.controllers.crud.edit.EditApplicationViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityApplication;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.User;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationRepository;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXTreeTableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrudApplicationViewController extends AbstractCrudViewController<Application, ViewEntityApplication> {

    private GenericCrudService<Application> service;

    @Autowired
    private EditApplicationViewController applicationEditController;

    @FXML
    private JFXTreeTableColumn<ViewEntityApplication, Long> col_id;
    @FXML
    private JFXTreeTableColumn<ViewEntityApplication, String> col_name;
    @FXML
    private JFXTreeTableColumn<ViewEntityApplication, String> col_responsible;

    @Autowired
    public CrudApplicationViewController(ApplicationRepository service) {
        this.service = new GenericCrudService<>(service);
    }

    @Override
    public List<JFXTreeTableColumn> getMappedTableColumns() {

        final ArrayList<JFXTreeTableColumn> tableColumns = new ArrayList<>();
        col_id.setCellValueFactory(cell -> cell.getValue().getValue().idProperty().asObject());
        col_name.setCellValueFactory(cell -> cell.getValue().getValue().nameDeProperty().concat(" / ").concat(cell.getValue().getValue().nameFrProperty()).concat(" (").concat(cell.getValue().getValue().getAppType().toI18nString()).concat(")"));
        col_responsible.setCellValueFactory(cell -> {
            final User responsible = cell.getValue().getValue().getUserResponsible();
            return new SimpleStringProperty(responsible.getFirstname() + " " + responsible.getLastname() + " (" + responsible.getUsername() + ")");
        });

        tableColumns.add(col_id);
        tableColumns.add(col_name);
        tableColumns.add(col_responsible);

        return tableColumns;
    }

    @Override
    public ViewEntityApplication toViewEntity(Application user) {

        return new ViewEntityApplication(user);
    }

    @Override
    public ViewEntityApplication createViewEnitiy() {

        return new ViewEntityApplication();
    }

    @Override
    public FXMLView getFXMLView() {
        return FXMLView.CRUD_APPLICATION;
    }

    @Override
    public AbstractEditViewController<Application, ViewEntityApplication> getEditController() {

        return applicationEditController;
    }

    @Override
    public GenericCrudService<Application> getService() {

        return service;
    }
}
