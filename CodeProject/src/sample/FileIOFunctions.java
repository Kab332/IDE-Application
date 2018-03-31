package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIOFunctions {
    //memory holder for saveUI
    public static TreeView<ProjectFile> projectTreeView;
//    public static Controller c;

    public static TabPane teacherPane;
    public static TabPane studentPane;

    public static String tabNames;
    public static String tabContents;

    public static List<Document> docList;
    public static TabPane tabs;

    //This method writes to filePath, which is the abs file path, and writes the content
    public static void writeToFile(String filePath, String content) throws IOException {
        FileWriter fileOut = new FileWriter(filePath);
        fileOut.append(content);
        fileOut.close();
    }

    public static TextArea addTab(String tabName, TabPane tabs, boolean editable){
        Tab tab = new Tab(tabName);
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tab);

        //Instantiate New Text area
        TextArea textArea = new TextArea();
        textArea.getStyleClass().add("code");
        textArea.setEditable(editable);

        //Intantiate anchor
        AnchorPane textAreaAnchor = new AnchorPane(textArea);
        textAreaAnchor.setTopAnchor(textArea, 0.0);
        textAreaAnchor.setBottomAnchor(textArea, 0.0);
        textAreaAnchor.setLeftAnchor(textArea, 0.0);
        textAreaAnchor.setRightAnchor(textArea, 0.0);
        tab.setContent(textAreaAnchor);

        return textArea;
    }

    public static String [] getAllTexts(){
        ObservableList<Tab> tabList = tabs.getTabs();
        String [] array = new String[2];

        AnchorPane ap;
        TextArea ta;

        String names = "";
        String contents= "";

        String text;

        for (int i = 0; i < tabList.size(); i++) {
            ap = (AnchorPane) tabList.get(i).getContent();
            ta = (TextArea) ap.getChildren().get(0);
            text = ta.getText();

            if (i > 0) {
                names += "@";
                contents += "---------------------123456---------------------";
            }

            names += tabList.get(i).getText();
            contents += text;
        }

        array[0] = names;
        array[1] = contents;

        return array;
    }

    public static void addTabsToTeacherPane(){
        String [] tabNames = FileIOFunctions.tabNames.split("@");
        String [] tabContents = FileIOFunctions.tabContents.split("---------------------123456---------------------");

        FileIOFunctions.teacherPane.getTabs().clear();

        for(int i = 0; i < tabNames.length; i++){
            //System.out.println(tabNames[i]);
            //System.out.println(tabContents[i]);

            TextArea ta = FileIOFunctions.addTab(tabNames[i], FileIOFunctions.teacherPane, false);
            try {
                ta.setText(tabContents[i]);
            } catch (ArrayIndexOutOfBoundsException e) {
                ta.setText("");
            }
        }
    }

    // Setting IDE tabs to the studentPane's tabs
    public static void setTabs() {
        // If you want to remove the tabs currently on the IDE
        tabs.getTabs().clear();

        // If the studentPane tabs do not have the same style, we set the TextArea's text manually
        AnchorPane spAp;
        TextArea spTa;

        for (int i = 0; i < studentPane.getTabs().size(); i++) {
            spAp = (AnchorPane) studentPane.getTabs().get(i).getContent();
            spTa = (TextArea) spAp.getChildren().get(0);

            TextArea ta = FileIOFunctions.addTab(studentPane.getTabs().get(i).getText(), tabs, true);
            ta.setText(spTa.getText());
        }

//        // if the studentPane tabs have the same style then we can use this
//        tabs.getTabs().addAll(studentPane.getTabs());
    }

    //This method reads a file using path name and delimitor
    public static String getData(String path, String delimiter) {

        String content = "";
        List<String> srcList = new ArrayList<>();
        // Special Case
        if (path == null || path.length() == 0) {
            return "";
        }
        if(delimiter == null || delimiter.length() == 0) {
            delimiter = ",";
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            //Read the next line until end of file
            for (String line; (line = br.readLine()) != null;) {
                content+= line +  "\n";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

}
