package sample;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
import javafx.util.Duration;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML Label bottomLabel;
    @FXML private TreeView<ProjectFile> projectTreeView;
    @FXML TabPane tabs;
    private SaveUIController saveUI = new SaveUIController();
    private TextEditorAI ai = new TextEditorAI();

//    Map<Tab, Document> tabDocMap = new TreeMap<>();
    List<Document> docList = new ArrayList<>();

    @FXML MenuItem connectServerButton;
    @FXML MenuItem connectClientButton;

    public Controller(){
        System.out.println("Controller Constructor.");
    }

    @FXML public void openNewTab(){
        addTab();
    }

    @FXML public void startServer(){
        Main.TEACHER = new Server(Main.PORT);
        Main.TEACHER.start();
        connectClientButton.setDisable(true);
        connectServerButton.setDisable(true);
        bottomLabel.setText("Server Started with PORT:" + Main.PORT);
    }

    @FXML public void startAsStudent(){
        Main.primaryStage.setTitle("Log In as Client");
        Main.primaryStage.setScene(Main.loginUIScene);
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

        treeViewfileDirectoryPath = fileDirectoryPath;

        /////////compiler
        Main.compileCodeLocation = fileDirectoryPath;
        /////////compoiler

        treeViewmainDirectory = mainDirectory;
        updateTreeView();

        projectTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ProjectFile>>() {
            //This function is change in selection of the tree view
            @Override
            public void changed(ObservableValue<? extends TreeItem<ProjectFile>> observable, TreeItem<ProjectFile> oldValue, TreeItem<ProjectFile> newValue) {
                TreeItem<ProjectFile> selectedItem = (TreeItem<ProjectFile>)newValue;
                if (selectedItem.getValue().toString().contains(".")) {//if selected value file name contains .txt
                    String foundFilePath = selectedItem.getValue().getFile().getAbsolutePath();//found file path is the selected folder path in tree view
//                    System.out.println(foundFilePath);

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
        Tab currentTab = tabs.getTabs().get(tabs.getSelectionModel().getSelectedIndex());
        if(!currentTab.getText().contains("Untitled")){
            String divider;
            if(currentFilePath.contains("\\")){
                divider = "\\";
            }else{
                divider = "/";
            }
            String fileFullPath = currentFilePath+divider+currentTab.getText();
            AnchorPane ap = (AnchorPane) currentTab.getContent();
            TextArea ta = (TextArea) ap.getChildren().get(0);
            String content = ta.getText();
            try {
                FileIOFunctions.writeToFile(fileFullPath, content);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            if (currentFilePath.equals("")) {
                //if directory not chosen already
                openWorkspace();
            } else {
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

    }

    @FXML public void compileCode(){
        Stage compileConsoleStage = new Stage();
        try {
            Parent consoleLayout = FXMLLoader.load(getClass().getResource("ProgramConsole.fxml"));
            compileConsoleStage.setTitle("Console");
            compileConsoleStage.setScene(new Scene(consoleLayout));
            compileConsoleStage.show();
        } catch (IOException e) {
            System.out.println("Couldn't open saveUI.fxml");
            e.printStackTrace();
        }

        //TextArea Main.consoleLines;
        //String Main.compileCodeLocation;
        //Tab Main.currentCompileTab;
        RunProgram runCode = new RunProgram(Main.currentCompileTab.getText(), Main.compileCodeLocation);
        runCode.run();
    }

    @FXML public void exit(){
        if (Main.TEACHER != null && Main.TEACHER.isOpen) {
            Main.TEACHER.closeServer();
        }

        if (Main.STUDENT != null && Main.STUDENT.isOpen) {
            try {
                Main.STUDENT.closeClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.exit(0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //adds the initial tab
        bottomLabel.setText("Server Not Yet Started.");
        System.out.println("Controller Initialized.");
        addTab();
        FileIOFunctions.tabs = tabs;
        FileIOFunctions.docList = docList;
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

        textAreaAddListener(textArea, tab);

        textArea.getStyleClass().add("code");

        //Intantiate anchor
        AnchorPane textAreaAnchor = new AnchorPane(textArea);
        textAreaAnchor.setTopAnchor(textArea, 0.0);
        textAreaAnchor.setBottomAnchor(textArea, 0.0);
        textAreaAnchor.setLeftAnchor(textArea, 0.0);
        textAreaAnchor.setRightAnchor(textArea, 0.0);
        tab.setContent(textAreaAnchor);

        addDoc(tab, textArea);

    }

//    public String getTabText(Tab tab){
//        //AnchorPane ap = (AnchorPane) tab.getContent();
//        //TextArea ta = (TextArea) ap.getChildren().get(0);
//        //return ta.getText();
//    }

    private void addDoc(Tab tab, TextArea textArea){

//        tabDocMap.put(tab, new Document(textArea.getText(), tab)  ) ;
        docList.add(new Document(textArea.getText(), tab));
    }

    private TextArea addTab(String tabName){
        //create new tab on menu button press of new tab or Ctrl+N
        Tab tab = new Tab(tabName);
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tab);
        tab.getStyleClass().add("jfx-tab-pane");

        //Instantiate New Text area
        TextArea textArea = new TextArea();

        textAreaAddListener(textArea, tab);

        textArea.getStyleClass().add("code");

        //Intantiate anchor
        AnchorPane textAreaAnchor = new AnchorPane(textArea);
        textAreaAnchor.setTopAnchor(textArea, 0.0);
        textAreaAnchor.setBottomAnchor(textArea, 0.0);
        textAreaAnchor.setLeftAnchor(textArea, 0.0);
        textAreaAnchor.setRightAnchor(textArea, 0.0);
        tab.setContent(textAreaAnchor);
        addDoc(tab, textArea);
        return textArea;
    }

    private Document getDoc(Tab tab){
        for(Document i : docList){
            if(i.tab.equals(tab)){
                return i;
            }
        }
        return null;
    }


    private boolean isPaused = false;
    private boolean True = true;
    private boolean False = false;

    private void timedSend(Tab tab, TextArea ta){
        System.out.println("Send to Users");
        String serialized = getDoc(tab).changed(ta.getText());


        //Main.TEACHER.sendAll("CHANGE",String.valueOf(docList.indexOf(getDoc(tab))),serialized);
        String [] array = FileIOFunctions.getAllTexts();

        if (Main.TEACHER != null && Main.TEACHER.isOpen) {
            Main.TEACHER.sendAll("GET ALL TEXT", array[0], array[1]);
        }

        isPaused = False;
    }

    private void textAreaAddListener(TextArea ta, Tab tab){

        ta.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.print("Changes in tab # <vs> index in doc list | [ "+tabs.getTabs().indexOf(tab)+" : "+docList.indexOf(getDoc(tab))+" ]\n" );

                ai.set_newValue(newValue);
                ai.textAreaListener(ta);

                if( tabs.getTabs().indexOf(tab) != docList.indexOf(getDoc(tab))){
                    System.out.println("THE FUCKERY IS HIGH! I REPEAT! THE FUCKERY IS HIGH! ABORT MISSION");   //MEME
                }
                if(!isPaused){

                    isPaused = True;
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event ->
                            timedSend(tab,ta)
                    );

                    pause.play();

                }
            }
        });

        tab.setOnCloseRequest(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                //close event
                docList.remove(getDoc(tab));
            }
        });


    }

}
