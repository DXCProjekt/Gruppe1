package dxc.karteikarte.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class KarteikartenGameControllerTest {
    private static KarteikartenGameController karteikartenGameController;

    @BeforeAll
    public static void init() {
        karteikartenGameController = new KarteikartenGameController();
    }

    @Test
    void testLadeKarteikartendeck() throws IOException {
        karteikartenGameController.ladeKarteikartendeck(new File("src/test/java/dxc/karteikarte/controller/resources/Testquiz.txt"));

        assertFalse(karteikartenGameController.getKarteikartendeck().getKarteikarten().isEmpty());
    }

    @Test
    void testLadeKarteikartendeckFileNotFound() {
        Exception exception = assertThrows(IOException.class, () -> karteikartenGameController.ladeKarteikartendeck(new File("src/test/java/dxc/karteikarte/controller/resources/TestquizNichtExistent.txt")));

        String erwarteteNachricht = "Das System kann die angegebene Datei nicht finden";
        String wirklicheNachricht = exception.getMessage();

        assertTrue(wirklicheNachricht.contains(erwarteteNachricht));
    }

    @Test
    void testLadeKarteikartendeckVollständigkeit() throws IOException {
        karteikartenGameController.ladeKarteikartendeck(new File("src/test/java/dxc/karteikarte/controller/resources/Testquiz.txt"));

        assertEquals(100, karteikartenGameController.getKarteikartendeck().getKarteikarten().size());
    }
}