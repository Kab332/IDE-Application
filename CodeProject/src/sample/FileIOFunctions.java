package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIOFunctions {
    //memory holder for saveUI
    public static TreeView<ProjectFile> projectTreeView;

    //This method writes to filePath, which is the abs file path, and writes the content
    public static void writeToFile(String filePath, String content) throws IOException {
        FileWriter fileOut = new FileWriter(filePath);
        fileOut.append(content);
        fileOut.close();
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
