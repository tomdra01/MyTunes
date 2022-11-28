package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Main.class.getResource("view/MainWindowView.fxml"));
        primaryStage.setTitle("Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}