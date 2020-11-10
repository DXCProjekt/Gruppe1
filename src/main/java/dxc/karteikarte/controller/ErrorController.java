package dxc.karteikarte.controller;

import javafx.scene.control.Alert;

public class ErrorController {
    public static void zeigeFehlermeldung(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
