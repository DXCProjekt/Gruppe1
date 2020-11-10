package dxc.karteikarte;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
            URL url = new File("src/main/java/dxc/karteikarte/view/MainApplication.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("Kalemaro");
            primaryStage.getIcons().add(new Image("file:src/main/java/dxc/karteikarte/resources/kalemaro_icon128.png"));
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

    public void geheZuMainApplication() {
        try {
            starteNeueSzene("MainApplication.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void geheZuEditor() {
        try {
            starteNeueSzene("KarteikartendeckEditor.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void geheZuGame() {
        try {
            starteNeueSzene("KarteikartenGameUI.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Parent starteNeueSzene(String fxml) throws IOException {
        URL url = new File("src/main/java/dxc/karteikarte/view/" + fxml).toURI().toURL();
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
