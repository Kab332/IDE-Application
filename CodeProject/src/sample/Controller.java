package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML private TreeView<ProjectFile> projectTreeView;
    @FXML TabPane tabs;

    @FXML public void openNewTab(){
        addTab();
    }

    @FXML public void startServer(){
        //Opens up server connection UI
        //receives PORT number
        //Displays all IP addresses
        //Start server button
    }

    @FXML public void startAsStudent(){
        //Current UI scene switches to student UI
    }

    @FXML public void openWorkspace(){
        //open directory chooser
        Stage fileChooserStage = new Stage();
        fileChooserStage.initModality(Modality.APPLICATION_MODAL);
        //directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(fileChooserStage);
        String fileDirectoryPath = mainDirectory.getPath();

        //save in this directory
        projectTreeView.setRoot(null);
        rootItem = null;
        this.currentFilePath = fileDirectoryPath;
        System.out.println(fileDirectoryPath);

        try {
            processFile(mainDirectory, rootItem);
        } catch (IOException e) {
            System.out.println("Main Directory selection FileIO Problem.");
            e.printStackTrace();
        }

        //currentFilePath is file path of file chosen
        //set tree view drop down to elements directory
        //Adding action listener
        //sets to child one, because child 0 is the same as rootitem
        projectTreeView.setRoot(rootItem.getChildren().get(0));

        projectTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ProjectFile>>() {
            //This function is change in selection of the tree view
            @Override
            public void changed(ObservableValue<? extends TreeItem<ProjectFile>> observable, TreeItem<ProjectFile> oldValue, TreeItem<ProjectFile> newValue) {
                TreeItem<ProjectFile> selectedItem = (TreeItem<ProjectFile>)newValue;
                if (selectedItem.getValue().toString().contains(".")) {//if selected value file name contains .txt

//                    //now open the file and set content of text inside this
//                    String foundFilePath = selectedItem.getValue().getFile().getAbsolutePath();//found file path is the selected folder path in tree view
//                    Main.getStage().setTitle(selectedItem.getValue().toString());
//                    if(!foundFilePath.equals("")){
//                        String content = getData(foundFilePath, " ");
//                        editor.setText(content);
//
//
//                    }
                }
            }
        });
    }

    /**
     * This function recursively goes thru file directory and makes TreeItem as parent or child
     */
    private TreeItem<ProjectFile> rootItem;
    public void processFile(File file, TreeItem<ProjectFile> root) throws IOException {
        //if main root is null
        if(null == this.rootItem){
            this.rootItem = new TreeItem<>(new ProjectFile(file));
            this.rootItem.setExpanded(true);
            root = this.rootItem;
        }

        //recursive calls
        if (file.isDirectory()) {
            //if directory set expandable and add to root
            TreeItem<ProjectFile> subRootItem = new TreeItem<>(new ProjectFile(file));
            subRootItem.setExpanded(true);
            root.getChildren().add(subRootItem);

            // process all the files in that directory
            File[] contents = file.listFiles();
            for (File current: contents) {
                root = subRootItem;
                processFile(current, root);
            }
        } else if (file.exists()) {
            //if file don't set expandable and connect to root
            TreeItem<ProjectFile> subRootItem = new TreeItem<>(new ProjectFile(file));
            subRootItem.setExpanded(false);
            root.getChildren().add(subRootItem);
        }
    }

    private String currentFilePath;

    @FXML public void save(){
        ////run save UI
        //runSaveUI();
//        try {
//            Parent layout = FXMLLoader.load(getClass().getResource("saveUI.fxml"));
//            Stage saveUISate = new Stage();
//            saveUISate.setTitle("Save File");
//            saveUISate.setScene(new Scene(layout));
//            saveUISate.show();
//        } catch (IOException e) {
//            System.out.println("Couldn't open saveUI.fxml");
//            e.printStackTrace();
//        }


        ////get file name, add file name to currentFilePath
        //String fileName = saveUITextField.getText();
        //this.currentFilePath += fileName
        ////get selected name, and set it to written name then write to file
        //Tab currentTab = getSelectedTab();
        //currentTab.setName(fileName);
//
//        if(!this.currentFilePath.equals("")) {
//            writeToFile(this.currentFilePath, .getText());
//        }
    }

    //This method writes to filePath, which is the abs file path, and writes the content
    private void writeToFile(String filePath, String content) throws IOException {
        FileWriter fileOut = new FileWriter(filePath);
        fileOut.append(content);
        fileOut.close();
    }

//    @FXML public void saveAs(){
//
//    }

    @FXML public void formatCode(){
        //this is where the text is formatted
    }

    @FXML public void exit(){
        System.exit(0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //adds the initial tab
        addTab();
    }

    private void addTab(){
        String name = "Untitled ";
        //create new tab on menu button press of new tab or Ctrl+N
        Tab tab = new Tab(name + "Tab " + (tabs.getTabs().size() + 1));
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tab);
        tab.getStyleClass().add("jfx-tab-pane");

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
    }



}
