package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;


import java.net.URL;
import java.util.ResourceBundle;

public class ProgramConsole implements Initializable{
    @FXML public TextArea consoleTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.consoleLines = consoleTextArea;
        Main.currentCompileTab = FileIOFunctions.tabs.getSelectionModel().getSelectedItem();
    }
}
