package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Чатик");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.getIcons().add(new Image("file:resources/images/chat.png"));

        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}

