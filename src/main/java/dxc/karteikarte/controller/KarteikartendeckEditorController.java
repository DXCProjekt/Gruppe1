package dxc.karteikarte.controller;

import dxc.karteikarte.MainApplication;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import dxc.karteikarte.model.Karteikarte;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KarteikartendeckEditorController extends Application {
    @FXML
    private TextArea frageTextField;

    @FXML
    private TextArea antwortTextField;

    @FXML
    private TextField nameDeckTextField;

    @FXML
    private Label anzahlKartenLabel;

    private int letzteKarteIndex = 0;
    private int counter = 0;
    private List<Karteikarte> karteikarten = new ArrayList<>();

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

        if (!istFrageTextFieldLeer && !istAntwortTextFieldLeer) {
            Karteikarte karteikarte = new Karteikarte();
            karteikarte.setFrage(frageTextField.getText());
            karteikarte.setAntwort(antwortTextField.getText());
            karteikarten.add(karteikarte);
            letzteKarteIndex++;

            anzahlKartenLabel.setText(String.valueOf(karteikarten.size()));

            frageTextField.clear();
            antwortTextField.clear();
        }
    }

    @FXML
    public void vorherigeKarteAction(ActionEvent event) {
        frageTextField.setText(karteikarten.get(letzteKarteIndex - 1).getFrage());
        antwortTextField.setText(karteikarten.get(letzteKarteIndex - 1).getAntwort());
    }

    @FXML
    public void zurueckZuHauptmenu() {
        MainApplication.getInstance().geheZuMainApplication();
    }

    public boolean enthaeltKarteikarte(final List<Karteikarte> karteikarten, final Karteikarte karteikarte) {
        return karteikarten.stream().filter(k -> k.getFrage().equalsIgnoreCase(karteikarte.getFrage())).findFirst().isPresent();
    }

    @FXML
    public void dateiSpeichern(ActionEvent actionEvent) {
        Karteikarte karte = new Karteikarte();
        karte.setAntwort(antwortTextField.getText());
        karte.setFrage(frageTextField.getText());

        if (antwortTextField.getText().isEmpty() || frageTextField.getText().isEmpty()) {
            System.out.println("LEERES FELD");
            return;
        } else if (!karteikarten.isEmpty()) {
            karteikarten.add(karte);

            FileChooser dateiWahl = new FileChooser();
            dateiWahl.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            dateiWahl.setTitle("Datei speichern");
            if (!nameDeckTextField.getText().isEmpty()) {
                String filename = nameDeckTextField.getText();
                dateiWahl.setInitialFileName(filename);
            }

            File file = dateiWahl.showSaveDialog(new Stage());
            speicherDatei(file);
            //ToDo: Speichern erfolgreich
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehlermeldung");
            alert.setHeaderText("Fehler beim Datei speichern!");
            alert.setContentText("Beim Versuch die Datei zu schreiben ist leider ein Fehler aufgetreten!");
            alert.showAndWait();
        }

    }


    private void speicherDatei(File datei) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(datei));

            for (Karteikarte karte : karteikarten) {
                writer.write(karte.getFrage() + "/");
                writer.write(karte.getAntwort() + "\n");
            }

        } catch (IOException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehlermeldung");
            alert.setHeaderText("Fehler beim Datei speichern!");
            alert.setContentText("Beim Versuch die Datei zu schreiben ist leider ein Fehler aufgetreten!");
            alert.showAndWait();

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
