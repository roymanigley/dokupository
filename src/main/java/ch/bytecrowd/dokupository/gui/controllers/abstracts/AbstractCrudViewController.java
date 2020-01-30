package ch.bytecrowd.dokupository.gui.controllers.abstracts;

import ch.bytecrowd.dokupository.gui.utils.StageManager;
import ch.bytecrowd.dokupository.gui.models.ViewEntity;
import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.models.interfaces.Keyable;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class AbstractCrudViewController<T extends Keyable, K extends ViewEntity<T, K>> extends AbstractViewController {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCrudViewController.class);

    @FXML
    private JFXButton btnCreate;

    @FXML
    private JFXTreeTableColumn<K, Void> col_btn_edit;

    @FXML
    private JFXTreeTableColumn<K, Void> col_btn_del;

    @FXML
    protected JFXTextField input_filter;

    @FXML
    protected JFXTreeTableView<K> table;

    public abstract List<JFXTreeTableColumn> getMappedTableColumns();

    public abstract K toViewEntity(T t);

    public abstract K createViewEnitiy();

    public abstract GenericCrudService<T> getService();

    public abstract AbstractEditViewController<T, K> getEditController();

    @FXML
    @Override
    public void initialize() {

        LOG.debug("initialize");
        super.initialize();
        initCreateButton();
        initEditButton();
        initDeleteButton();
        initColumns();
        initFilter();
    }

    private void initFilter() {
        input_filter.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {

                final String query = input_filter.getText();
                if (LOG.isDebugEnabled())
                    LOG.debug("Search for: " + query);

                table.setPredicate(treeItem -> {
                    final K viewEntity = treeItem.getValue();
                    if (LOG.isDebugEnabled())
                        LOG.debug("Compare: " + query + " with " + viewEntity);
                    return filterMatchPredicate(query, viewEntity);
                });

                // Nach dem Filtern müssen die Columns initialisiert werden.
                // Wenn keine Einträge in der Tabelle sind werden dann die Buttons nicht mehr angezeigt
                new Thread(() -> {

                    Platform.runLater(() -> {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        initColumns();
                    });
                }).start();
            }
        });
    }

    protected boolean filterMatchPredicate(String query, K viewEntity) {

        return viewEntity.toString().toLowerCase().contains(query.toLowerCase());
    }

    @Override
    public void onShow() {

        LOG.debug("init Data CrudController");
        super.onShow();
        fillTable();
    }

    protected void initCreateButton() {

        btnCreate.setOnAction((ActionEvent event) -> {

            executeUIBlockingTask(() -> {
                getEditController().setSelectedViewEnity(createViewEnitiy());
                LOG.info(String.format("ViewEnitiy created"));
                Platform.runLater(() -> {
                    try {
                        StageManager.getInstance().switchScene(getEditController().getFXMLView(), true);
                    } catch (IOException e) {
                        LOG.error(String.format("SwitchScene failed for: %s", getEditController().getFXMLView()), e);
                    }
                });
            });
        });
    }

    protected void initEditButton() {

        col_btn_edit.setCellFactory(viewUserVoidTableColumn -> {

            final JFXTreeTableCell<K, Void> cell = new JFXTreeTableCell<K, Void>() {

                private final JFXButton btnUpdate = new JFXButton();

                {
                    btnUpdate.getStyleClass().add("button-edit");
                    btnUpdate.setMaxHeight(10.);
                    btnUpdate.setOnAction((ActionEvent event) -> {

                        executeUIBlockingTask(() -> {

                            K selectedViewEnitiy = getTreeTableView().getRoot().getChildren().get(getIndex()).getValue();

                            LOG.info(String.format("selectedViewEnitiy: %s", selectedViewEnitiy));
                            getEditController().setSelectedViewEnity(selectedViewEnitiy);
                            Platform.runLater(() -> {
                                try {
                                    StageManager.getInstance().switchScene(getEditController().getFXMLView(), true);
                                } catch (IOException e) {
                                    LOG.error(String.format("SwitchScene failed for: %s", getEditController().getFXMLView()), e);
                                }
                            });
                        });
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnUpdate);
                    }
                }
            };
            return cell;
        });
    }

    protected void initDeleteButton() {

        col_btn_del.setCellFactory(viewUserVoidTableColumn -> {

            final JFXTreeTableCell<K, Void> cell = new JFXTreeTableCell<K, Void>() {

                private final JFXButton btnDelete = new JFXButton();

                {
                    btnDelete.getStyleClass().add("button-delete");
                    btnDelete.setMaxHeight(10.);
                    btnDelete.setOnAction((ActionEvent event) -> {
                        try {
                            K selectedViewEnitiy = getTreeTableView().getRoot().getChildren().get(getIndex()).getValue();
                            showDeleteDialog(selectedViewEnitiy);
                        } catch (Exception e) {
                            DialogUtil.showErrorDialog(e);
                        }
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnDelete);
                    }
                }
            };
            return cell;
        });
    }

    protected void showDeleteDialog(K selectedViewEnitiy) {

        DialogUtil.showDeleteDialog(selectedViewEnitiy, viewENtity -> {

            getService().deleteRecord(selectedViewEnitiy.getId());
            LOG.info(String.format("deleted: %s", viewENtity));
            fillTable();
        });
    }

    protected void initColumns() {

        final List list = getMappedTableColumns();
        list.add(col_btn_edit);
        list.add(col_btn_del);
        table.getColumns().setAll(list);
    }

    public void fillTable() {

        final List<T> all = fetchTableData();
        final List<K> viewEntities = all.stream().map(t -> toViewEntity((T) t)).collect(Collectors.toList());
        final ObservableList<K> entities = FXCollections.observableArrayList(viewEntities);
        final TreeItem<K> root = new RecursiveTreeItem<K>(entities, RecursiveTreeObject::getChildren);

        root.getChildren().forEach(c -> c.setExpanded(true));
        table.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);
        table.setRoot(root);
        table.setShowRoot(false);
    }

    protected List<T> fetchTableData() {

        return getService().fetchAllRecords();
    }
}
