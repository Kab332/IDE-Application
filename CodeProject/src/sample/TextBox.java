package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class TextBox implements Initializable{

    @FXML TextArea text;
    private TextEditorAI ai = new TextEditorAI();
    private int call = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                System.out.println("change happened");

                ai.set_newValue(newValue);


                ai.textAreaListener(text);








            }
        });
    }


}
