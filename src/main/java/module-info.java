module group {
    requires javafx.controls;
    requires javafx.fxml;

    opens group to javafx.fxml;
    exports group;
    exports group.frontend;
    opens group.frontend to javafx.fxml;
}
