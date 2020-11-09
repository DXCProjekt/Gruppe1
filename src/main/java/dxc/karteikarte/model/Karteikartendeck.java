package dxc.karteikarte.model;

import java.util.ArrayList;
import java.util.List;

public class Karteikartendeck {
    private String name;
    private List<Karteikarte> karteikarten = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Karteikarte> getKarteikarten() {
        return karteikarten;
    }

    public void setKarteikarten(List<Karteikarte> karteikarten) {
        this.karteikarten = karteikarten;
    }
}
