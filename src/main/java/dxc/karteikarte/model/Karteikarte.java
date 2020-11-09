package dxc.karteikarte.model;

import java.io.Serializable;

public class Karteikarte {
    private String frage;
    private String antwort;

    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }

    public String getAntwort() {
        return antwort;
    }

    public void setAntwort(String antwort) {
        this.antwort = antwort;
    }
}
