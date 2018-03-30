package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginUIController implements Initializable{

    @FXML Label bottomLabel;
    @FXML TextField IP;
    @FXML TextField PORT;


    @FXML public void connectButton(){
        //Clicking the connect button in LoginUI
        Main.IP = IP.getText();

//        PORT.setText("12888");
//        PORT.setDisable(true);
//        Main.PORT = Integer.parseInt(PORT.getText());


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PORT.setText("12888");
        //PORT.setDisable(true);
        Main.PORT = Integer.parseInt(PORT.getText());
        IP.setText("localhost");
        Main.IP = IP.getText();
    }
}
