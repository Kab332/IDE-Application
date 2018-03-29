package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginUIController {

    @FXML TextField IP;
    @FXML TextField PORT;

    @FXML public void connectButton(){
        //Clicking the connect button in LoginUI
    }

    @FXML public void exitButton(){
        System.exit(0);
    }

}
