package dxc.karteikarte.controller;

import dxc.karteikarte.MainApplication;
import dxc.karteikarte.model.Karteikartendeck;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import dxc.karteikarte.model.Karteikarte;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KarteikartendeckEditorController extends Application {
    @FXML
    private TextArea frageTextField, antwortTextField;

    @FXML
    private TextField nameDeckTextField;

    @FXML
    private Button vorherigeKarteButton, naechsteKarteButton;

    @FXML
    private Label anzahlKartenLabel;
    private Karteikartendeck karteikartendeck;
    private boolean inEditMode = false;
    private int letzteKarteIndex = 0;

    private List<Karteikarte> karteikarten = new ArrayList<>();
    private ErrorController erCtr = new ErrorController();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/java/view/KarteikartendeckEditor.fxml").toURI().toURL());
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    public void naechsteKarteAction(ActionEvent event) {
        boolean istFrageTextFieldLeer = frageTextField.getText().trim().isEmpty();
        boolean istAntwortTextFieldLeer = antwortTextField.getText().trim().isEmpty();
        if(inEditMode) {
            letzteKarteIndex++;
            frageTextField.setText(karteikartendeck.getKarteikarten().get(letzteKarteIndex).getFrage());
            antwortTextField.setText(karteikartendeck.getKarteikarten().get(letzteKarteIndex).getAntwort());
            vorherigeKarteButton.setDisable(false);
        } else {
            if (!istFrageTextFieldLeer && !istAntwortTextFieldLeer) {
                if (karteikartendeck == null) { karteikartendeck = new Karteikartendeck();}
                Karteikarte karteikarte = new Karteikarte();
                karteikarte.setFrage(frageTextField.getText());
                karteikarte.setAntwort(antwortTextField.getText());
                karteikartendeck.getKarteikarten().add(karteikarte);
                letzteKarteIndex++;
                anzahlKartenLabel.setText(String.valueOf(karteikartendeck.getKarteikarten().size()));
                frageTextField.clear();
                antwortTextField.clear();
                vorherigeKarteButton.setDisable(false);
            }
        }
    }

    @FXML
    public void vorherigeKarteAction(ActionEvent event) {
        if (karteikartendeck != null) {
            letzteKarteIndex--;
            frageTextField.setText(karteikartendeck.getKarteikarten().get(letzteKarteIndex).getFrage());
            antwortTextField.setText(karteikartendeck.getKarteikarten().get(letzteKarteIndex).getAntwort());
            if (letzteKarteIndex == 0) {
                vorherigeKarteButton.setDisable(true);
            }
        }
    }

    @FXML
    public void zurueckZuHauptmenu() {
        MainApplication.getInstance().geheZuMainApplication();
    }

    @FXML
    public void dateiSpeichern(ActionEvent actionEvent) {
        Karteikarte karte = new Karteikarte();
        karte.setAntwort(antwortTextField.getText());
        karte.setFrage(frageTextField.getText());

        if (antwortTextField.getText().isEmpty() || frageTextField.getText().isEmpty()) {
            System.out.println("LEERES FELD");
            return;
        } else if (!karteikartendeck.getKarteikarten().isEmpty()) {
            karteikartendeck.getKarteikarten().add(karte);

            FileChooser dateiWahl = new FileChooser();
            dateiWahl.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            dateiWahl.setTitle("Datei speichern");
            if (!nameDeckTextField.getText().isEmpty()) {
                String filename = nameDeckTextField.getText();
                dateiWahl.setInitialFileName(filename);
            }

            File file = dateiWahl.showSaveDialog(new Stage());
            speicherDatei(file);
        }
    }

    @FXML
    public void dateiLadenAction() {
        FileChooser fileChooser = new FileChooser();
        File ausgwählteDatei = fileChooser.showOpenDialog(new Stage());
        inEditMode = true;
        dateiLaden(ausgwählteDatei);
        nameDeckTextField.setText(karteikartendeck.getName());
        frageTextField.setText(karteikartendeck.getKarteikarten().get(0).getFrage());
        antwortTextField.setText(karteikartendeck.getKarteikarten().get(0).getAntwort());
    }

    public void dateiLaden (File file) {
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
            ErrorController.zeigeFehlermeldung("Fehlermeldung", "Fehler beim Laden der Datei", "Die Datei konnte nicht gefunden werden");
            e.printStackTrace();
        } catch (IOException e) {
            ErrorController.zeigeFehlermeldung("Fehlermeldung", "Fehler beim Lesen der Datei", "Die Datei konnte nicht korrekt eingelesen werden");
            e.printStackTrace();
        }
    }
    @FXML
    public void infoAction() {
        MainApplication.getInstance().zeigeInfo();
    }

    private void speicherDatei(File datei) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(datei));

            for (Karteikarte karte : karteikartendeck.getKarteikarten()) {
                writer.write(karte.getFrage() + "/");
                writer.write(karte.getAntwort() + "\n");
            }

        } catch (IOException ex) {
            ErrorController.zeigeFehlermeldung("Fehlermeldung", "Fehler beim Datei speichern!", "Beim Versuch die Datei zu schreiben ist leider ein Fehler aufgetreten!");
            ex.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
