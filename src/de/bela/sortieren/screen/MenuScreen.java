package de.bela.sortieren.screen;

import de.bela.sortieren.sort.Sort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class MenuScreen extends JPanel {

    private Screen window; // Fenster

    public MenuScreen(Screen window) {
        this.window = window; // Hauptfenster initialisieren

        this.setBounds(0, 0, window.getWidth(), window.getHeight()); // Größe des Panels
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); // Layout des Panels
        this.setBackground(Color.DARK_GRAY); // Hintergrundfarbe
        this.setVisible(true);

        Arrays.stream(Sort.SortType.values()).forEach(type -> addSortButton(type, type.name())); // Alle buttons für Sortieralgorithmen hinzufügen
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.addTexts(g); // Texte hinzufügen
    }

    private void addTexts(Graphics g) {
        g.setColor(Color.WHITE); // Farbe des Textes
        g.setFont(new Font("Monospaced", Font.BOLD, 40)); // Schriftart und Größe
        g.drawString("Sortieralgorithmus auswählen", window.getWidth() / 4, 50); // Text hinzufügen
    }

    private void addSortButton(Sort.SortType type, String name) {
        JButton button = new JButton(name); // Initialisierung
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Ausrichtung links

        button.setPreferredSize(new Dimension(600, 50)); // Größe des buttons
        button.setMaximumSize(new Dimension(600, 50));

        // Farben des buttons
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);

        // Durch Knopfdruck wird ein neues Fenster mit dem Sortieralgorithmus geöffnet
        button.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizerScreen screen = new VisualizerScreen(type, window); // VisualizerScreen initialisieren
                window.setContentPane(screen);
                window.validate();
            }
        });

        button.setText(name); // Beschriftung
        button.setToolTipText(type.description); // Beschreibung

        int y = window.getHeight() / (2 * Sort.SortType.values().length) + 10; // Vertikalen Abstand berechnen
        this.add(Box.createRigidArea(new Dimension(0, y))); // Vertikalen Abstand zwischen den Buttons hinzufügen
        this.add(button); // Button hinzufügen
    }


}
