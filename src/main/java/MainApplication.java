import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MainApplication extends Application {

    public void start(Stage primaryStage) {
        try {
            URL url = new File("src/main/java/view/MainApplication.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("KarteiKarte KALEMARO");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
            Platform.setImplicitExit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
