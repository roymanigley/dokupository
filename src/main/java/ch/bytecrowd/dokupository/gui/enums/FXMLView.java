package ch.bytecrowd.dokupository.gui.enums;

public enum FXMLView {

    OVERVIEW("views/overview.fxml"),

    MENU("views/menu/menu.fxml"),

    CRUD_DOCUMENTATION_PREPARE("views/documentations/prepareDocumentation.fxml"),
    CRUD_DOCUMENTATION("views/crud/crudDocumentation.fxml"),
    EDIT_DOCUMENTATION("views/crud/edit/editDocumentationNode.fxml"),
    CRUD_PRINT_SCREEN_EDIT("views/crud/edit/editPrintScreen.fxml"),
    CRUD_PRINT_SCREEN("views/crud/crudPrintScreen.fxml"),

    GENERATE_DOCUMENTATION("views/documentations/generateDocumentation.fxml"),

    CRUD_USER("views/crud/crudUser.fxml"),
    CRUD_USER_EDIT("views/crud/edit/editUser.fxml"),
    CRUD_APPLICATION("views/crud/crudApplication.fxml"),
    CRUD_APPLICATION_EDIT("views/crud/edit/editApplication.fxml"),
    CRUD_VERSION("views/crud/crudApplicationVersion.fxml"),
    CRUD_VERSION_EDIT("views/crud/edit/editApplicationVersion.fxml"),
    CRUD_WORD_TEMPLATE("views/crud/crudWordTemplate.fxml"),
    CRUD_WORD_TEMPLATE_EDIT("views/crud/edit/editWordTemplate.fxml"),
    CRUD_COMMIT("views/crud/crudCommit.fxml");

    private final String fxmlFile;

    FXMLView(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }
}
