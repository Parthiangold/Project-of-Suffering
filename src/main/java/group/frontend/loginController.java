package group.frontend;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class loginController {

    @FXML
    private Button loginButton;

    // Switches from loginView to menuView
    @FXML
    private void switchToMenuView() throws IOException {
        MainApplication.setRoot("menuView");
    }
}
