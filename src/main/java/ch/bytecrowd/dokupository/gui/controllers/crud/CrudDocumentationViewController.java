package ch.bytecrowd.dokupository.gui.controllers.crud;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractCrudViewController;
import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.controllers.crud.edit.EditDocumentationViewController;
import ch.bytecrowd.dokupository.gui.controllers.vsc.CommitController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityDocumentationNode;
import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import ch.bytecrowd.dokupository.repositories.jpa.DocumentationNodeRepository;
import ch.bytecrowd.dokupository.services.jpa.DocumentationNodeCrudService;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CrudDocumentationViewController extends AbstractCrudViewController<DocumentationNode, ViewEntityDocumentationNode> {

    private static final Logger LOG = LoggerFactory.getLogger(CrudDocumentationViewController.class);

    private Application selectedApplication;
    private DocumentationType selectedDocumentationType;

    private Integer maxSort = -1;
    protected Integer unFilteredRecordCount = 0;

    public static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    private ArrayList<TreeItem<ViewEntityDocumentationNode>> selections = new ArrayList<>();

    private DocumentationNodeCrudService service;

    @Autowired
    private EditDocumentationViewController editDocumentationViewController;

    @Autowired
    private CommitController commitController;


    @FXML
    private JFXButton btnSaveSort;

    @FXML
    private JFXTreeTableColumn<ViewEntityDocumentationNode, String>  col_version;

    @FXML
    private JFXTreeTableColumn<ViewEntityDocumentationNode, String> col_text_de;

    @FXML
    private JFXTreeTableColumn<ViewEntityDocumentationNode, String> col_text_fr;

    @FXML
    private JFXTreeTableColumn<ViewEntityDocumentationNode, String> col_style;

    @FXML
    private JFXTreeTableColumn<ViewEntityDocumentationNode, Long> col_count_printscreens_de;

    @FXML
    private JFXTreeTableColumn<ViewEntityDocumentationNode, Long> col_count_printscreens_fr;

    @FXML
    private AnchorPane root;

    @Autowired
    public CrudDocumentationViewController(DocumentationNodeRepository service) {
        this.service = new DocumentationNodeCrudService(service);
    }


    @Override
    public List<JFXTreeTableColumn> getMappedTableColumns() {

        final ArrayList<JFXTreeTableColumn> tableColumns = new ArrayList<>();

        // Map columns
        col_version.setCellValueFactory(cell -> cell.getValue() != null && cell.getValue().getValue() != null ? cell.getValue().getValue().versionAsTextProperty() : null);
        col_text_de.setCellValueFactory(cell -> cell.getValue() != null && cell.getValue().getValue() != null ? cell.getValue().getValue().textDeProperty() : null);
        col_text_fr.setCellValueFactory(cell -> cell.getValue() != null && cell.getValue().getValue() != null ? cell.getValue().getValue().textFrProperty() : null);
        col_style.setCellValueFactory(cell -> cell.getValue() != null && cell.getValue().getValue() != null ? cell.getValue().getValue().styleProperty() : null);

        col_count_printscreens_de.setCellValueFactory( cell -> cell.getValue() != null && cell.getValue().getValue() != null ? cell.getValue().getValue().countPrintscreen_deProperty().asObject(): null);
        col_count_printscreens_fr.setCellValueFactory( cell -> cell.getValue() != null && cell.getValue().getValue() != null ? cell.getValue().getValue().countPrintscreen_frProperty().asObject(): null);


        root.prefWidthProperty().bind(root.widthProperty());
        root.prefHeightProperty().bind(root.heightProperty());

        tableColumns.add(col_version);
        tableColumns.add(col_text_de);
        tableColumns.add(col_text_fr);
        tableColumns.add(col_style);
        tableColumns.add(col_count_printscreens_de);
        tableColumns.add(col_count_printscreens_fr);

        return tableColumns;
    }

    @Override
    protected List<DocumentationNode> fetchTableData() {

        final List<DocumentationNode> allByVersionBeginApplicationIdAnAndDocumentationType = service.findAllByVersionBeginApplicationIdAnAndDocumentationType(selectedApplication.getId(), selectedDocumentationType);
        unFilteredRecordCount = allByVersionBeginApplicationIdAnAndDocumentationType.size();
        return allByVersionBeginApplicationIdAnAndDocumentationType;
    }

    @Override
    public ViewEntityDocumentationNode toViewEntity(DocumentationNode documentationNode) {
        return new ViewEntityDocumentationNode(documentationNode);
    }

    @Override
    public ViewEntityDocumentationNode createViewEnitiy() {
        final ViewEntityDocumentationNode viewEntity = new ViewEntityDocumentationNode();
        viewEntity.setSortierung((maxSort+1)+"");
        return viewEntity;
    }

    @Override
    public GenericCrudService<DocumentationNode> getService() {
        return service;
    }

    @Override
    public AbstractEditViewController<DocumentationNode, ViewEntityDocumentationNode> getEditController() {
        return editDocumentationViewController;
    }

    public Application getSelectedApplication() {
        return selectedApplication;
    }

    public void setSelectedApplication(Application selectedApplication, boolean loadCommits) {

        this.selectedApplication = selectedApplication;
        commitController.getCommitModelWrappers().clear();
        if (loadCommits && selectedApplication.getRepositoryUrl() != null) {
            try {
                commitController.initCommitsByApplication(selectedApplication);
            } catch (Exception e) {
                LOG.error("initCommitsByApplication failed for Application.id: " + selectedApplication.getId());
            }
        }
    }

    public DocumentationType getSelectedDocumentationType() {
        return selectedDocumentationType;
    }

    public void setSelectedDocumentationType(DocumentationType selectedDocumentationType) {
        this.selectedDocumentationType = selectedDocumentationType;
    }


    @Override
    public FXMLView getFXMLView() {

        return FXMLView.CRUD_DOCUMENTATION;
    }

    @FXML
    @Override
    public void initialize() {
        super.initialize();

        prepareSortableTable();
        initSaveSortButton();
    }

    private void initSaveSortButton() {
        btnSaveSort.setOnAction(actionEvent -> {
            if (unFilteredRecordCount > table.getRoot().getChildren().size()) {
                DialogUtil.showWarnDialog("label.warn.clear.filter.before.save.sort");
            } else {
                try {
                    for (Integer i = 0; i < table.getRoot().getChildren().size(); i++) {
                        table.getRoot().getChildren().get(i).getValue().setSortierung(i.toString());
                    }

                    final List<DocumentationNode> documentationNodes = table.getRoot().getChildren().stream().map(child -> {
                        final DocumentationNode documentationNode = new DocumentationNode();
                        child.getValue().fillEntity(documentationNode);
                        return documentationNode;
                    }).collect(Collectors.toList());

                    service.saveRecords(documentationNodes);
                    DialogUtil.showInfoDialog("label.info.message.sort.saved");
                } catch (Exception e) {
                    DialogUtil.showErrorDialog(e);
                }
            }
        });
    }

    private void prepareSortableTable() {
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        table.setRowFactory(tv -> {
            JFXTreeTableRow<ViewEntityDocumentationNode> row = new JFXTreeTableRow<>();

            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();

                    selections.clear();//important...

                    final ObservableList<TreeItem<ViewEntityDocumentationNode>> items = table.getSelectionModel().getSelectedItems();

                    for(TreeItem<ViewEntityDocumentationNode> iI:items) {
                        selections.add(iI);
                    }


                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();

                if (db.hasContent(SERIALIZED_MIME_TYPE)) {

                    int dropIndex;
                    TreeItem<ViewEntityDocumentationNode> dI=null;

                    if (row.isEmpty()) {
                        dropIndex = table.getRoot().getChildren().size() ;
                    } else {
                        dropIndex = row.getIndex();
                        dI = table.getRoot().getChildren().get(dropIndex);
                    }
                    int delta=0;
                    if(dI!=null)
                        while(selections.contains(dI)) {
                            delta=1;
                            --dropIndex;
                            if(dropIndex<0) {
                                dI=null;dropIndex=0;
                                break;
                            }
                            dI = table.getRoot().getChildren().get(dropIndex);
                        }

                    for(TreeItem<ViewEntityDocumentationNode> sI:selections) {
                        table.getRoot().getChildren().remove(sI);
                    }

                    if(dI!=null)
                        dropIndex=table.getRoot().getChildren().indexOf(dI)+delta;
                    else if(dropIndex!=0)
                        dropIndex=table.getRoot().getChildren().size();



                    table.getSelectionModel().clearSelection();

                    for(TreeItem<ViewEntityDocumentationNode> sI:selections) {
                        //draggedIndex = selections.get(i);
                        table.getRoot().getChildren().add(dropIndex, sI);
                        table.getSelectionModel().select(dropIndex);
                        dropIndex++;

                    }

                    event.setDropCompleted(true);
                    selections.clear();
                    event.consume();
                }
            });

            return row ;
        });
    }

    @Override
    public void fillTable() {
        super.fillTable();
        maxSort = service.findMaxSort(selectedApplication.getId(), selectedDocumentationType);
        maxSort = (maxSort == null) ? -1 : maxSort;
    }
}
