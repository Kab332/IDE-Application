package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;


public class StudentFxmlController{

    @FXML TabPane teacherTabPane;
    @FXML TabPane studentTabPane;

    @FXML public void backToTeacherScene(){
        Main.primaryStage.close();
        Main.primaryStage.setScene(Main.teacherScene);
        Main.primaryStage.show();
    }

    @FXML public void openNewTab(){
        addTab("Untitled", studentTabPane);
    }

    @FXML public void copyTeacherCodeToUser(){
        //Copy text from teacher code to user text area;
    }

    private TextArea addTab(String tabName, TabPane tabs){
        //create new tab on menu button press of new tab or Ctrl+N
        Tab tab = new Tab(tabName);
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tab);

        //Instantiate New Text area
        TextArea textArea = new TextArea();
        textArea.getStyleClass().add("code");

        //Intantiate anchor
        AnchorPane textAreaAnchor = new AnchorPane(textArea);
        textAreaAnchor.setTopAnchor(textArea, 0.0);
        textAreaAnchor.setBottomAnchor(textArea, 0.0);
        textAreaAnchor.setLeftAnchor(textArea, 0.0);
        textAreaAnchor.setRightAnchor(textArea, 0.0);
        tab.setContent(textAreaAnchor);

        return textArea;
    }
}
