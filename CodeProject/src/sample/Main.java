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
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent) loader.load();
        Controller controller = (Controller) loader.getController();
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
