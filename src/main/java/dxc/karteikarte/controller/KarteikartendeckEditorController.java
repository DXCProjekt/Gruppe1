package dxc.karteikarte.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import dxc.karteikarte.model.Karteikarte;

import java.io.File;
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
        frageTextField.setText(karteikarten.get(letzteKarteIndex-1).getFrage());
        antwortTextField.setText(karteikarten.get(letzteKarteIndex-1).getAntwort());
    }

    public boolean enthaeltKarteikarte(final List<Karteikarte> karteikarten, final Karteikarte karteikarte) {
        return karteikarten.stream().filter(k -> k.getFrage().equalsIgnoreCase(karteikarte.getFrage())).findFirst().isPresent();
    }
}
