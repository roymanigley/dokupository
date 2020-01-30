package ch.bytecrowd.dokupository.gui.controllers.crud;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractCrudViewController;
import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityCommit;
import ch.bytecrowd.dokupository.models.jpa.Commit;
import ch.bytecrowd.dokupository.repositories.jpa.CommitRepository;
import ch.bytecrowd.dokupository.services.jpa.CommitCrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXTreeTableColumn;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrudCommitViewController extends AbstractCrudViewController<Commit, ViewEntityCommit> {

    private CommitCrudService service;

    @FXML
    private JFXTreeTableColumn<ViewEntityCommit,Long> col_id;
    @FXML
    private JFXTreeTableColumn<ViewEntityCommit,String> col_hash;
    @FXML
    private JFXTreeTableColumn<ViewEntityCommit,String> col_application_name;

    @Autowired
    public CrudCommitViewController(CommitRepository service) {
        this.service = new CommitCrudService(service);
    }

    @Override
    public List<JFXTreeTableColumn> getMappedTableColumns() {

        final ArrayList<JFXTreeTableColumn> tableColumns = new ArrayList<>();

        col_id.setCellValueFactory(cell -> cell.getValue().getValue().idProperty().asObject());
        col_application_name.setCellValueFactory(cell -> cell.getValue().getValue().applicationNameProperty());
        col_hash.setCellValueFactory(cell -> cell.getValue().getValue().hashProperty());

        tableColumns.add(col_id);
        tableColumns.add(col_application_name);
        tableColumns.add(col_hash);

        return tableColumns;
    }

    @Override
    protected List<Commit> fetchTableData() {
        return service.findAllByIgnoreForDocumentationIsTrue();
    }

    @Override
    public ViewEntityCommit toViewEntity(Commit commit) {

        return new ViewEntityCommit(commit);
    }

    @Override
    public ViewEntityCommit createViewEnitiy() {

        return new ViewEntityCommit();
    }

    @Override
    public FXMLView getFXMLView() {
        return FXMLView.CRUD_COMMIT;
    }

    @Override
    public AbstractEditViewController<Commit, ViewEntityCommit> getEditController() {

        final String message = "getEditController for CrudCommitViewController is not supported";
        throw new RuntimeException(message);
    }

    @Override
    public GenericCrudService<Commit> getService() {

        return service;
    }
}
