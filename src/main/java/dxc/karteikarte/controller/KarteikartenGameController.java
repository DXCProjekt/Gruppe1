package dxc.karteikarte.controller;

import dxc.karteikarte.model.Karteikarte;
import dxc.karteikarte.model.Karteikartendeck;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class KarteikartenGameController extends Application {
    @FXML
    private TextArea frageTextArea;

    @FXML
    private TextArea antwortTextArea;

    @FXML
    private ProgressBar fortschrittsBar;

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
        if (aktuellerIndex < karteikartendeck.getKarteikarten().size() - 1) {
            aktuellerIndex++;
            String frage = karteikartendeck.getKarteikarten().get(aktuellerIndex).getFrage();

            frageTextArea.setText(frage);
            antwortTextArea.clear();

            aktualisiereFortschrittsBalken();
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
        }
    }

    @FXML
    public void loesungsButtonAction() {
        String antwort = karteikartendeck.getKarteikarten().get(aktuellerIndex).getAntwort();
        antwortTextArea.setText(antwort);
    }

    @FXML
    public void karteikartendeckLadenAction() {
        FileChooser fileChooser = new FileChooser();
        File ausgwählteDatei = fileChooser.showOpenDialog(new Stage());
        ladeKarteikartendeck(ausgwählteDatei);
        frageTextArea.setText(karteikartendeck.getKarteikarten().get(0).getFrage());
    }

    public void ladeKarteikartendeck(File file) {
        karteikartendeck = new Karteikartendeck();
        karteikartendeck.setName(file.getName());

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aktualisiereFortschrittsBalken() {
        Double progess = Double.valueOf(aktuellerIndex) / Double.valueOf(karteikartendeck.getKarteikarten().size() - 1);
        fortschrittsBar.setProgress(progess);
    }
}

