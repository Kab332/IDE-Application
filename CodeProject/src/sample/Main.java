package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

    public static Stage primaryStage;
    public static double SceneWidth;
    public static double SceneHeight;
    public static Scene teacherScene;
    public static Scene loginUIScene;
    public static Controller controller;
    public static Scene studentUIScene;

    public static Server TEACHER;
    public static Client STUDENT;
    public static String IP;
    public static int PORT;

    public static TabPane teacherPane;
    public static TabPane studentPane;

    ////////////////////////CON SOL SUTFF
    public static TextArea consoleLines;
    public static String compileCodeLocation;
    public static Tab currentCompileTab;
    //////////////////////////////////////////

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent) loader.load();
        Controller controller = (Controller) loader.getController();
        controller = (Controller) loader.getController();
        Scene scene = new Scene(root, 759, 600);
        primaryStage.setTitle("Smart IDE-A");
        teacherScene = scene;

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);

        this.primaryStage = primaryStage;

        SceneWidth = scene.getWidth();
        SceneHeight = scene.getHeight();

        //initialize login UI
        Parent loginUIlayout = FXMLLoader.load(getClass().getResource("LoginUI.fxml"));
        Scene loginUIScene = new Scene(loginUIlayout, Main.SceneWidth, Main.SceneHeight);
        Main.loginUIScene = loginUIScene;

        //initilize student UI
        Parent studentUIlayout = FXMLLoader.load(getClass().getResource("student.fxml"));
        Scene studentUIScene = new Scene(studentUIlayout, Main.SceneWidth, Main.SceneHeight);
        Main.studentUIScene = studentUIScene;

        primaryStage.show();
        System.out.println("Main Thread Number: "+Thread.currentThread().getId());

        ////testing
        //add tabs to teacherPane
        studentPane = FileIOFunctions.studentPane;
        teacherPane = FileIOFunctions.teacherPane;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setScene(Scene scene){
        primaryStage.setScene(scene);
    }

    public static void connectThisClient(){

        //Connect code client here
        STUDENT = new Client(IP, PORT);
        STUDENT.start();
        STUDENT.sendMessage("GET ALL TEXT");
    }


    private void ThomasMain() {

        String oldCode =
                        "A\n" +
                        "B\n" +
                        "\n" +
                        "C\n";
        String newCode =
                        "A\n" +
                        "\n" +
                        "B\n" +
                        "C;\n";


        Document rightDoc = new Document(oldCode);
        for(int i = 0; i < rightDoc.numOfLines; i++){
            System.out.print("[");
            System.out.print(rightDoc.myLines.get(i));
            System.out.print("]\n");
        }
        Document leftDoc = new Document(newCode);
        System.out.println("\n");
        for(int i = 0; i < leftDoc.numOfLines; i++){
            System.out.print("[");
            System.out.print(leftDoc.myLines.get(i));
            System.out.print("]\n");
        }

        String changes = rightDoc.changed(leftDoc.toString());
        System.out.println("\n");
        System.out.println(changes);

        rightDoc.change(changes);

        System.out.println("\n"+rightDoc.myLines.size()+" : "+rightDoc.numOfLines+"\n");
        for(int i = 0; i < rightDoc.myLines.size(); i++){
            System.out.print("[");
            System.out.print(rightDoc.myLines.get(i));
            System.out.print("]\n");
        }




        System.exit(0);


    }


}
