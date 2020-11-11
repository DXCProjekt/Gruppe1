package dxc.karteikarte.controller;

import dxc.karteikarte.MainApplication;
import dxc.karteikarte.model.Karteikarte;
import dxc.karteikarte.model.Karteikartendeck;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class KarteikartenGameController extends Application {
    @FXML
    private TextArea frageTextArea, antwortTextArea;

    @FXML
    private ProgressBar fortschrittsBar;

    @FXML
    private Button vorherigeKarteButton, naechsteKarteButton;

    private Karteikartendeck karteikartendeck;

    private int aktuellerIndex = 0;

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
    public void naechsteKarteAction() {
        if (karteikartendeck != null) {
            if (aktuellerIndex < karteikartendeck.getKarteikarten().size() - 1) {
                aktuellerIndex++;
                String frage = karteikartendeck.getKarteikarten().get(aktuellerIndex).getFrage();

                frageTextArea.setText(frage);
                antwortTextArea.clear();

                aktualisiereFortschrittsBalken();
                vorherigeKarteButton.setDisable(false);
                if (aktuellerIndex + 1 == karteikartendeck.getKarteikarten().size()) {
                    naechsteKarteButton.setDisable(true);
                }
            }
        }
    }

    @FXML
    public void vorherigeKarteAction() {
        if (aktuellerIndex > 0) {
            aktuellerIndex--;
            String frage = karteikartendeck.getKarteikarten().get(aktuellerIndex).getFrage();

            frageTextArea.setText(frage);
            antwortTextArea.clear();

            aktualisiereFortschrittsBalken();
            if (aktuellerIndex <= karteikartendeck.getKarteikarten().size() ) {
                naechsteKarteButton.setDisable(false);
            }
            if (aktuellerIndex == 0) {
                vorherigeKarteButton.setDisable(true);
            }
        }
    }

    @FXML
    public void loesungsButtonAction() {
        if (karteikartendeck != null) {
            String antwort = karteikartendeck.getKarteikarten().get(aktuellerIndex).getAntwort();
            antwortTextArea.setText(antwort);
        }
    }

    @FXML
    public void karteikartendeckLadenAction() {
        FileChooser fileChooser = new FileChooser();
        File ausgwählteDatei = fileChooser.showOpenDialog(new Stage());
        try {
            ladeKarteikartendeck(ausgwählteDatei);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frageTextArea.setText(karteikartendeck.getKarteikarten().get(0).getFrage());
    }

    @FXML
    public void hauptmenuAction() {
        MainApplication.getInstance().geheZuMainApplication();
    }

    @FXML
    public void infoAction() {
        MainApplication.getInstance().zeigeInfo();
    }

    public void ladeKarteikartendeck(File file) throws IOException {
        karteikartendeck = new Karteikartendeck();
        karteikartendeck.setName(file.getName());

        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(isr);

        String zeile;
        while ((zeile = bufferedReader.readLine()) != null) {

            String[] zeileAufgteilt = zeile.split("/");

            String frage = zeileAufgteilt[0];
            String antwort = zeileAufgteilt[1];

            Karteikarte karteikarte = new Karteikarte();
            karteikarte.setFrage(frage);
            karteikarte.setAntwort(antwort);

            karteikartendeck.getKarteikarten().add(karteikarte);
        }

    }

    public void aktualisiereFortschrittsBalken() {
        Double progess = Double.valueOf(aktuellerIndex) / Double.valueOf(karteikartendeck.getKarteikarten().size() - 1);
        fortschrittsBar.setProgress(progess);
    }

    //Nur für die Unit Tests
    public Karteikartendeck getKarteikartendeck() {
        return karteikartendeck;
    }
}

