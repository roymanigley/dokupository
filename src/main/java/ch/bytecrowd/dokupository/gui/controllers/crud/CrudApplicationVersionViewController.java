package ch.bytecrowd.dokupository.gui.controllers.crud;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractCrudViewController;
import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.controllers.crud.edit.EditApplicationVersionViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityApplicationVersion;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.repositories.jpa.ApplicationVersionRepository;
import ch.bytecrowd.dokupository.services.jpa.ApplicationVersionService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXTreeTableColumn;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrudApplicationVersionViewController extends AbstractCrudViewController<ApplicationVersion, ViewEntityApplicationVersion> {

    private ApplicationVersionService service;

    @Autowired
    private EditApplicationVersionViewController applicationEditController;

    private Integer maxSort = -1;

    @FXML
    private JFXTreeTableColumn<ViewEntityApplicationVersion,Long> col_id;
    @FXML
    private JFXTreeTableColumn<ViewEntityApplicationVersion,String> col_version;
    @FXML
    private JFXTreeTableColumn<ViewEntityApplicationVersion,String> col_application;

    @Autowired
    public CrudApplicationVersionViewController(ApplicationVersionRepository service) {
        this.service = new ApplicationVersionService(service);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public List<JFXTreeTableColumn> getMappedTableColumns() {

        final ArrayList<JFXTreeTableColumn> tableColumns = new ArrayList<>();
        col_id.setCellValueFactory(cell -> cell.getValue().getValue().idProperty().asObject());
        col_version.setCellValueFactory(cell -> cell.getValue().getValue().versionProperty());
        col_application.setCellValueFactory(cell -> cell.getValue().getValue().applicationNameProperty());

        tableColumns.add(col_id);
        tableColumns.add(col_version);
        tableColumns.add(col_application);

        return tableColumns;
    }

    @Override
    public ViewEntityApplicationVersion toViewEntity(ApplicationVersion applicationVersion) {

        return new ViewEntityApplicationVersion(applicationVersion);
    }

    @Override
    public ViewEntityApplicationVersion createViewEnitiy() {

        final ViewEntityApplicationVersion viewEntity = new ViewEntityApplicationVersion();
        viewEntity.setSortierung((maxSort+1)+"");
        return viewEntity;
    }

    @Override
    public void fillTable() {
        super.fillTable();
        maxSort = service.findMaxSort();
        maxSort = (maxSort == null) ? -1 : maxSort;
    }
    @Override
    public AbstractEditViewController<ApplicationVersion, ViewEntityApplicationVersion> getEditController() {

        return applicationEditController;
    }

    @Override
    public FXMLView getFXMLView() {
        return FXMLView.CRUD_VERSION;
    }

    @Override
    public GenericCrudService<ApplicationVersion> getService() {

        return service;
    }

    @Override
    protected List<ApplicationVersion> fetchTableData() {

        return service.fetchAllRecords();
    }
}
