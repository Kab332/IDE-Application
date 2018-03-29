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

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginUI.fxml"));

        Parent root = (Parent) loader.load();
        //Controller controller = (Controller) loader.getController();
        Scene scene = new Scene(root, 759, 600);

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);

        this.primaryStage = primaryStage;

        SceneWidth = scene.getWidth();
        SceneHeight = scene.getHeight();
        primaryStage.show();


//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 600, 500));
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
