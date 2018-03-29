package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginUIController {

    @FXML TextField IP;
    @FXML TextField PORT;

    @FXML public void connectButton(){
        //Clicking the connect button in LoginUI
        Main.IP = IP.getText();
        Main.PORT = Integer.parseInt(PORT.getText());


        Main.primaryStage.close();
        Main.primaryStage.setScene(Main.studentUIScene);
        Main.primaryStage.show();

        //This code connects this client as client using PORT AND IP
        Main.connectThisClient();

    }

    @FXML public void cancelButton(){
        Main.primaryStage.close();
        Main.primaryStage.setScene(Main.teacherScene);
        Main.primaryStage.show();
    }

}
