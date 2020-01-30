package ch.bytecrowd.dokupository.gui.controllers.crud;

import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractCrudViewController;
import ch.bytecrowd.dokupository.gui.controllers.abstracts.AbstractEditViewController;
import ch.bytecrowd.dokupository.gui.controllers.crud.edit.EditWordTemplateViewController;
import ch.bytecrowd.dokupository.gui.enums.FXMLView;
import ch.bytecrowd.dokupository.gui.models.ViewEntityWordTemplate;
import ch.bytecrowd.dokupository.models.jpa.WordTemplate;
import ch.bytecrowd.dokupository.repositories.jpa.WordTemplateRepository;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import com.jfoenix.controls.JFXTreeTableColumn;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrudWordTemplateViewController extends AbstractCrudViewController<WordTemplate, ViewEntityWordTemplate> {

    private GenericCrudService<WordTemplate> service;

    @Autowired
    private EditWordTemplateViewController wordTemplateViewController;

    @FXML
    private JFXTreeTableColumn<ViewEntityWordTemplate,Long> col_id;
    @FXML
    private JFXTreeTableColumn<ViewEntityWordTemplate,String> col_name;
    @FXML
    private JFXTreeTableColumn<ViewEntityWordTemplate,String> col_documentation_type;
    @FXML
    private JFXTreeTableColumn<ViewEntityWordTemplate,String> col_template_language;


    @Autowired
    public CrudWordTemplateViewController(WordTemplateRepository service) {
        this.service = new GenericCrudService<>(service);
    }

    @Override
    public List<JFXTreeTableColumn> getMappedTableColumns() {

        final ArrayList<JFXTreeTableColumn> tableColumns = new ArrayList<>();

        col_id.setCellValueFactory(cell -> cell.getValue().getValue().idProperty().asObject());
        col_name.setCellValueFactory(cell -> cell.getValue().getValue().nameProperty());
        col_documentation_type.setCellValueFactory(cell -> cell.getValue().getValue().documentationTypeStringProperty());
        col_template_language.setCellValueFactory(cell -> cell.getValue().getValue().languageStringProperty());

        tableColumns.add(col_id);
        tableColumns.add(col_name);
        tableColumns.add(col_documentation_type);
        tableColumns.add(col_template_language);

        return tableColumns;
    }

    @Override
    public ViewEntityWordTemplate toViewEntity(WordTemplate wordTemplate) {

        return new ViewEntityWordTemplate(wordTemplate);
    }

    @Override
    public ViewEntityWordTemplate createViewEnitiy() {

        return new ViewEntityWordTemplate();
    }

    @Override
    public FXMLView getFXMLView() {
        return FXMLView.CRUD_WORD_TEMPLATE;
    }

    @Override
    public AbstractEditViewController<WordTemplate, ViewEntityWordTemplate> getEditController() {

        return wordTemplateViewController;
    }

    @Override
    public GenericCrudService<WordTemplate> getService() {

        return service;
    }
}
