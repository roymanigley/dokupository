package ch.bytecrowd.dokupository.gui.controllers.crud;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractCrudViewController;
import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.controllers.crud.edit.EditDocumentationViewController;
import ch.bytecrowd.dokupository.gui.controllers.crud.edit.EditPrintScreenViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityPrintScreen;
import ch.bytecrowd.dokupository.gui.utils.StageManager;
import ch.bytecrowd.dokupository.models.jpa.PrintScreen;
import ch.bytecrowd.dokupository.repositories.jpa.PrintscreenRepository;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import ch.bytecrowd.dokupository.services.jpa.PrintscreenCrudService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrudPrintscreenViewController extends AbstractCrudViewController<PrintScreen, ViewEntityPrintScreen> {

    private static final Logger LOG = LoggerFactory.getLogger(CrudPrintscreenViewController.class);

    private PrintscreenCrudService service;

    @Autowired
    private EditPrintScreenViewController editPrintScreenViewController;

    @Autowired
    private EditDocumentationViewController editDocumentationViewController;

    @FXML
    private JFXTreeTableColumn<ViewEntityPrintScreen, Long> col_id;

    @FXML
    private JFXTreeTableColumn<ViewEntityPrintScreen, ImageView> col_picture;

    @FXML
    private JFXTreeTableColumn<ViewEntityPrintScreen, String> col_version_begin;

    @FXML
    private JFXTreeTableColumn<ViewEntityPrintScreen,String> col_printscreen_language;

    @FXML
    private JFXButton btnCancel;

    @Autowired
    public CrudPrintscreenViewController(PrintscreenRepository service) {
        this.service = new PrintscreenCrudService(service);
    }

    @Override
    public void initialize() {
        super.initialize();

        btnCancel.setOnAction(event -> {
            try {
                StageManager.getInstance().switchScene(FXMLView.EDIT_DOCUMENTATION);
            } catch (IOException e) {
                LOG.error(String.format( "SwitchScene failed for: %s", FXMLView.EDIT_DOCUMENTATION), e);
            }
        });
    }

    @Override
    public List<JFXTreeTableColumn> getMappedTableColumns() {

        final ArrayList<JFXTreeTableColumn> tableColumns = new ArrayList<>();

        col_id.setCellValueFactory(cell -> cell.getValue().getValue().idProperty().asObject());
        col_version_begin.setCellValueFactory(cell -> cell.getValue().getValue().versionBeginnTextProperty());
        col_picture.setCellValueFactory(cell -> {

            final Image image = new Image(new ByteArrayInputStream(cell.getValue().getValue().getBytes()));
            final ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.maxHeight(100);
            return new SimpleObjectProperty<ImageView>(imageView);
        });
        col_printscreen_language.setCellValueFactory(cell -> cell.getValue().getValue().languageStringProperty());

        tableColumns.add(col_id);
        tableColumns.add(col_version_begin);
        tableColumns.add(col_picture);
        tableColumns.add(col_printscreen_language);

        return tableColumns;
    }

    @Override
    public ViewEntityPrintScreen toViewEntity(PrintScreen printScreen) {

        return new ViewEntityPrintScreen(printScreen);
    }

    @Override
    public ViewEntityPrintScreen createViewEnitiy() {

        return new ViewEntityPrintScreen();
    }

    @Override
    public AbstractEditViewController<PrintScreen, ViewEntityPrintScreen> getEditController() {

        return editPrintScreenViewController;
    }

    @Override
    public FXMLView getFXMLView() {
        return FXMLView.CRUD_PRINT_SCREEN;
    }

    @Override
    public GenericCrudService<PrintScreen> getService() {

        return service;
    }

    @Override
    protected List<PrintScreen> fetchTableData() {
        final long idDocumentationNode = editDocumentationViewController.getSelectedViewEnity().getId();
        return service.findAllByDocumentationNodeId(idDocumentationNode);
    }
}
