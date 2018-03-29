package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveUIController {
    @FXML public TextField saveUITextField;
    public static String workspacePath;
    public static Stage thisStage;
    public static Tab currentTab;
    public String fileFullPath;
    public TreeView<ProjectFile> projectTree = new TreeView<>();
    private static TreeView<ProjectFile> projectTreeView1;

    public TreeItem<ProjectFile> rootItem;

    public void setProjectTreeView(TreeView<ProjectFile> projectTreeView) {
        projectTree = projectTreeView;
        System.out.println(1111111);
    }

    public SaveUIController(){
        System.out.println("SAVE INSTANCE");
    }

    private String divider = "";

    @FXML public void saveUISaveButton(){
        //do save if save button is pressed on save UI
        System.out.println("Save Button clicked");
        String fileName = saveUITextField.getText();

        // Conditional statement to handle file paths for Linux and Windows systems
        if(workspacePath.contains("\\")){
            divider = "\\";
        }else{
            divider = "/";
        }

        fileFullPath = workspacePath+divider+fileName;
        System.out.println(fileFullPath);

        AnchorPane ap = (AnchorPane) currentTab.getContent();
        TextArea ta = (TextArea) ap.getChildren().get(0);
        try {
            FileIOFunctions.writeToFile(fileFullPath, ta.getText());
        } catch (IOException e) {
            //Writing to file failed
            System.out.println("Writing to file on Save failed!");
            e.printStackTrace();
        }

        currentTab.setText(fileName);
        thisStage.close();
        //updateTreeView();
    }

    @FXML public void saveUICancelButton(){
        //do cancel if save button is pressed on save UI
        thisStage.close();
    }


    public void updateTreeView(){
        //update treeview
        TreeItem<ProjectFile> subRootItem = new TreeItem<>(new ProjectFile(new File(fileFullPath)));
        projectTree.getRoot().getChildren().add(subRootItem);
    }
}
