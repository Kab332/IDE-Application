package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SaveUIController {
    @FXML public TextField saveUITextField;
    public String workspacePath;


    @FXML public void saveUISaveButton(){
        //do save if save button is pressed on save UI
        System.out.println("Save Button clicked");
        String fileName = saveUITextField.getText();

    }

    @FXML public void saveUICancelButton(){
        //do cancel if save button is pressed on save UI
        System.out.println("Cancel Button Clicked");
    }
}
