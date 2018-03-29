package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;

public class SaveUIController {
    @FXML public TextField saveUITextField;
    public static String workspacePath;
    public static Stage thisStage;
    public static Tab currentTab;
    public String fileFullPath;

    public boolean updateTree = true;

    private String divider = "";

    @FXML public void saveUISaveButton(){
        //do save if save button is pressed on save UI
        String fileName = saveUITextField.getText();

        // Conditional statement to handle file paths for Linux and Windows systems
        if(workspacePath.contains("\\")){
            divider = "\\";
        }else{
            divider = "/";
        }

        fileFullPath = workspacePath+divider+fileName;

        try {
            AnchorPane ap = (AnchorPane) currentTab.getContent();
            TextArea ta = (TextArea) ap.getChildren().get(0);
            FileIOFunctions.writeToFile(fileFullPath, ta.getText());
        } catch (IOException e) {
            //Writing to file failed
            System.out.println("Writing to file on Save failed!");
            e.printStackTrace();
        }

        currentTab.setText(fileName);
        thisStage.close();

        if(updateTree) {
            updateTreeView();
        }
    }

    @FXML public void saveUICancelButton(){
        //do cancel if save button is pressed on save UI
        thisStage.close();
    }


    public void updateTreeView(){
        //update treeview
        TreeItem<ProjectFile> subRootItem = new TreeItem<>(new ProjectFile(new File(fileFullPath)));
        FileIOFunctions.projectTreeView.getRoot().getChildren().add(subRootItem);
    }
}
