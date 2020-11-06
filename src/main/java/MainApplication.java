import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {
    private Stage stage;

    private static MainApplication instance;

    public MainApplication() {
        instance = this;
    }

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            URL url = new File("src/main/java/view/MainApplication.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("KarteiKarte KALEMARO");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
            stage = primaryStage;
            Platform.setImplicitExit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void geheZuEditor() {
        try {
            replaceSceneContent("KarteikartendeckEditor.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Parent replaceSceneContent(String fxml) throws IOException {
        URL url = new File("src/main/java/view/" + fxml).toURI().toURL();
        Parent page = FXMLLoader.load(url);
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }

}
