package dxc.karteikarte.controller;

import dxc.karteikarte.MainApplication;
import javafx.fxml.FXML;

public class MainApplicationController {
    @FXML
    public void quizErstellen() {
        MainApplication.getInstance().geheZuEditor();
    }
}
