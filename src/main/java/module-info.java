module group {
    requires javafx.controls;
    requires javafx.fxml;

    opens group to javafx.fxml;
    exports group.frontend;
    opens group.frontend to javafx.fxml;
    exports group.backend;
    opens group.backend to javafx.fxml;
}
