package form.Car;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class CarStart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        File file = new File("src/form/Car/Car.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);

        primaryStage.setTitle("CAR");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
