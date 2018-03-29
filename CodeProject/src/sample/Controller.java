package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML private TreeView<ProjectFile> projectTreeView;
    @FXML TabPane tabs;
    private SaveUIController saveUI = new SaveUIController();

    public TabPane getTabs() {
        return tabs;
    }

    public void setTabs(TabPane tabs) {
        this.tabs = tabs;
    }

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

//        //save in this directory
//        projectTreeView.setRoot(null);
//        rootItem = null;
//        this.currentFilePath = fileDirectoryPath;
//        System.out.println(fileDirectoryPath);
//
//        try {
//            processFile(mainDirectory, rootItem);
//        } catch (IOException e) {
//            System.out.println("Main Directory selection FileIO Problem.");
//            e.printStackTrace();
//        }
//
//        //currentFilePath is file path of file chosen
//        //set tree view drop down to elements directory
//        //Adding action listener
//        //sets to child one, because child 0 is the same as rootitem
//        projectTreeView.setRoot(rootItem.getChildren().get(0));
        treeViewfileDirectoryPath = fileDirectoryPath;
        treeViewmainDirectory = mainDirectory;
        updateTreeView();

        projectTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ProjectFile>>() {
            //This function is change in selection of the tree view
            @Override
            public void changed(ObservableValue<? extends TreeItem<ProjectFile>> observable, TreeItem<ProjectFile> oldValue, TreeItem<ProjectFile> newValue) {
                TreeItem<ProjectFile> selectedItem = (TreeItem<ProjectFile>)newValue;
                if (selectedItem.getValue().toString().contains(".")) {//if selected value file name contains .txt
                    String foundFilePath = selectedItem.getValue().getFile().getAbsolutePath();//found file path is the selected folder path in tree view
                    System.out.println(foundFilePath);

                    projectTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getClickCount() == 2) {
                                boolean fileAlreadyOpen = false;
                                // Get TreeViewItem
                                for(Tab tab: tabs.getTabs()){
                                    //check if any of the tabs are that are being clicked in tree view already open
                                    //if they are open then switch to that tab
                                    if(selectedItem.getValue().getFile().getName().equals(tab.getText())){
                                        fileAlreadyOpen = true;
                                        tabs.getSelectionModel().select(tab);
                                    }
                                }

                                //if tab is not open, open and display content
                                if(!fileAlreadyOpen){
                                    fileAlreadyOpen = true;
                                    String tabName = selectedItem.getValue().getFile().getName();
                                    TextArea textArea = addTab(tabName);
                                    //tab added
                                    String content = FileIOFunctions.getData(foundFilePath, "");
                                    textArea.setText(content);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void runTreeViewUpdate(){
        updateTreeView();
    }

    private String treeViewfileDirectoryPath;
    private File treeViewmainDirectory;
    public void updateTreeView(){
        String fileDirectoryPath = treeViewfileDirectoryPath;
        File mainDirectory = treeViewmainDirectory;
        //save in this directory
        projectTreeView.setRoot(null);
        rootItem = null;
        currentFilePath = fileDirectoryPath;
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

//            saveUI.rootItem = subRootItem;///////////////////////////////////////

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

    private String currentFilePath = "";

    @FXML public void save(){
        if(currentFilePath.equals("")){
            //if directory not chosen already
            openWorkspace();
        }else {
            Stage saveUISate = new Stage();
            try {
                Parent layout = FXMLLoader.load(getClass().getResource("saveUI.fxml"));
                saveUISate.setTitle("Save File");
                saveUISate.setScene(new Scene(layout));
                saveUISate.show();
            } catch (IOException e) {
                System.out.println("Couldn't open saveUI.fxml");
                e.printStackTrace();
            }

            saveUI.workspacePath = currentFilePath;
            saveUI.thisStage = saveUISate;
            saveUI.currentTab = tabs.getTabs().get(tabs.getSelectionModel().getSelectedIndex());

            //saveUI.setProjectTreeView(projectTreeView);
            FileIOFunctions.projectTreeView = projectTreeView;
        }
    }

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

    private TextArea addTab(String tabName){
        //create new tab on menu button press of new tab or Ctrl+N
        Tab tab = new Tab(tabName);
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

        return textArea;
    }


    public String getAllTexts(){
        ObservableList<Tab> tabList = tabs.getTabs();
        AnchorPane ap;
        TextArea ta;

        String tabInfo = "";

        for (Tab tab : tabList) {
            ap = (AnchorPane) tab.getContent();
            ta = (TextArea) ap.getChildren().get(0);

            tabInfo += tab.getText();
            tabInfo += "\n" + ta.getText() + "\n";
        }

        return tabInfo;
    }



}
