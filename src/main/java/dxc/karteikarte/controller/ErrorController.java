package dxc.karteikarte.controller;

import javafx.scene.control.Alert;

public class ErrorController {
    public void zeigeFehlerSpeichern() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setHeaderText("Fehler beim Datei speichern!");
        alert.setContentText("Beim Versuch die Datei zu schreiben ist leider ein Fehler aufgetreten!");
        alert.showAndWait();
    }
}
