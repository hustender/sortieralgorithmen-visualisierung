package de.bela.sortieren.screen;

import javax.swing.*;

public class Screen extends JFrame {

    public Screen() {
        super("Sortieralgorithmen Präsentation");  // Fenster Titel
        this.setSize(1280, 680); // Fenster Größe
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fenster schließen
        this.setVisible(true); // Sichtbar
        this.setContentPane(new MenuScreen(this)); // MenuScreen als Inhalt
    }

}
