import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        URL fxmlDocPath = Objects.requireNonNull(getClass().getClassLoader().getResource("mainScene.fxml"));

        VBox vbox = (VBox) FXMLLoader.load(fxmlDocPath);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("application.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}