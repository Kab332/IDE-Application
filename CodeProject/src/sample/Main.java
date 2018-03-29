package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

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

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent) loader.load();
        controller = (Controller) loader.getController();
        Scene scene = new Scene(root, 759, 600);
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
    }

}
