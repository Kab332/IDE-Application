package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StudentFxmlController implements Initializable{

    @FXML TabPane teacherTabPane;
    @FXML TabPane studentTabPane;

    private SaveUIController saveUI = new SaveUIController();

    public StudentFxmlController(){
        System.out.println("Student FXML Constructor.");
    }

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

    @FXML public void chooseSaveLocation(){
        //open directory chooser
        Stage fileChooserStage = new Stage();
        fileChooserStage.initModality(Modality.APPLICATION_MODAL);
        //directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(fileChooserStage);
        String fileDirectoryPath = mainDirectory.getPath();

        currentFilePath = fileDirectoryPath;
        //currentFilePath = mainDirectory;
    }

    private String currentFilePath = "";
    @FXML public void save(){
//        saveUI.updateTree = false;
//        String divider;
//        if(currentFilePath.contains("\\")){
//            divider = "\\";
//        }else{
//            divider = "/";
//        }
//        Tab currentTab = studentTabPane.getTabs().get(studentTabPane.getSelectionModel().getSelectedIndex());
//
//        if(!currentTab.getText().contains("Untitled")){
//            String fileFullPath = currentFilePath+divider+currentTab.getText();
//            AnchorPane ap = (AnchorPane) currentTab.getContent();
//            TextArea ta = (TextArea) ap.getChildren().get(0);
//            String content = ta.getText();
//            try {
//                FileIOFunctions.writeToFile(fileFullPath, content);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }else {
//            if (currentFilePath.equals("")) {
//                //if directory not chosen already
//                chooseSaveLocation();
//            } else {
//                Stage saveUISate = new Stage();
//                try {
//                    Parent layout = FXMLLoader.load(getClass().getResource("saveUI.fxml"));
//                    saveUISate.setTitle("Save File");
//                    saveUISate.setScene(new Scene(layout));
//                    saveUISate.show();
//                } catch (IOException e) {
//                    System.out.println("Couldn't open saveUI.fxml");
//                    e.printStackTrace();
//                }
//
//                saveUI.workspacePath = currentFilePath;
//                saveUI.thisStage = saveUISate;
//                saveUI.currentTab = studentTabPane.getTabs().get(studentTabPane.getSelectionModel().getSelectedIndex());
//            }
//        }
        System.out.println("Save not implemented Yet.");

    }

    @FXML public void exit(){
        System.exit(0);
    }

    private TextArea addTab(String tabName, TabPane tabs){
        //create new tab on menu button press of new tab or Ctrl+N
        if(tabName.contains("Untitled")){
            tabName += " " + tabName + "Tab " + (tabs.getTabs().size() + 1);
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Student FXML Controller Initialized.");
//        System.out.println(studentTabPane == null);
//        System.out.println(teacherTabPane == null);
        FileIOFunctions.studentPane = studentTabPane;
        FileIOFunctions.teacherPane = teacherTabPane;
    }
}
