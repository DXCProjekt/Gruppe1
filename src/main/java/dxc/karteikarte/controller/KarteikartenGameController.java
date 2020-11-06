package dxc.karteikarte.controller;

import dxc.karteikarte.model.Karteikarte;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KarteikartenGameController extends Application {
    @FXML
    private TextArea frageTextArea;

    @FXML
    private TextArea antwortTextArea;

    @FXML
    private ProgressBar fortschrittsBar;

    @FXML
    private Button naechsteKarteButton;

    @FXML
    private Button loesungsButton;

    @FXML
    private Button vorherigeKarteButton;

    @FXML
    private Label deckkartenZahl;

    private int letzteKarteIndex = 0;

    private List<Karteikarte> karteikarten = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/java/view/KarteikartenGameUI.fxml").toURI().toURL());
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }
    @FXML
    public void naechsteKarteAction(){
        System.out.println("next");
    }
    @FXML
    public void vorherigeKarteAction(){
        System.out.println("vorher");
    }
    @FXML
    public void loesungsButtonAction(){
        System.out.println("lösung");
    }
}

