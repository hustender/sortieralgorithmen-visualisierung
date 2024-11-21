package de.bela.sortieren.screen;

import de.bela.sortieren.sort.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VisualizerScreen extends JPanel {

    public Sort sortingAlgorithm; // Ausgewählter Sortieralgorithmus
    public int[] barColors; // Array für die Farben der Balken
    public JSlider delay; // Slider zum Einstellen vom delay
    private int MAX_VALUE; // Maximaler Wert des Arrays

    private final Screen screen; // Fenster

    public VisualizerScreen(Sort.SortType type, Screen screen) {
        this.setSize(screen.getSize()); // Gleiche größe wie das Fenster
        this.screen = screen; // Fenster initialisieren
        init(type);
    }

    public void init(Sort.SortType type) {
        // Von SortType enum sortingAlgorithm initialisieren
        this.sortingAlgorithm = switch(type) {
            case SELECTIONSORT -> new SelectionSort(this);
            case BUBBLESORT -> new BubbleSort(this);
            case QUICKSORT -> new QuickSort(this);
            case INSERTIONSORT -> new InsertionSort(this);
            case MERGESORT -> new MergeSort(this);
        };
        this.delay = new JSlider(JSlider.HORIZONTAL, 1, 500, sortingAlgorithm.getDefaultDelay()); // Slider initialisieren
        this.sortingAlgorithm.initArray(); // Array initialisieren
        this.barColors = new int[this.sortingAlgorithm.nums.length]; // Leeres array von der Größe des zu sortierendes Arrays
        for (int i = 0; i < sortingAlgorithm.nums.length; i++) {
            this.barColors[i] = 0; // Jedem Balken die Farbe weiß zuordnen
        }
        add(this.delay); // Hinzufügen
        setBackground(Color.DARK_GRAY); // Hintergrund Dunkelgrau
        this.MAX_VALUE = Arrays.stream(sortingAlgorithm.nums).max().getAsInt(); // Maximalen Array Wert initialisieren
        new Thread(this::run).start(); // Sortieren starten
    }

    public void run() {
        this.sortingAlgorithm.sort(); // Sortieren
        this.longWait(); // Kurz Ergebnis zeigen
        this.screen.setContentPane(new MenuScreen(screen)); // Auf Hauptbildschirm zurück
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.sortingAlgorithm == null) return; // Wenn noch kein Algorithmus deklariert wurde, dann nicht zeichnen
        Graphics2D g2d = (Graphics2D) g.create(); // Graphik initialisieren

        // Antialaising
        Map<RenderingHints.Key, Object> renderingHints = new HashMap<>();
        renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(renderingHints);

        try {
            this.writeInfo(g2d); // Informationen zeichnen
            this.drawBars(g2d); // Balken zeichnen
        } finally {
            g2d.dispose(); // Zeichnen beenden
        }
    }

    private void writeInfo(Graphics2D g2d) {
        g2d.setColor(Color.WHITE); // Farbe weiß
        g2d.setFont(new Font("Monospaced", Font.BOLD, 20)); // Schriftart Monospaced
        int standardY = delay.getY() + 50; // Y Position 50 px unter dem Delay slider
        g2d.drawString("Delay: " + this.delay.getValue() + "ms", delay.getX(), standardY); // Delay angeben
        g2d.drawString("Algorithmus: " + this.sortingAlgorithm.getName(), 20, standardY); // Algorithmus name
        g2d.drawString("Veränderungen: " + this.sortingAlgorithm.getChanges(), 20, standardY + 30); // Veränderungen am Array
    }

    private void drawBars(Graphics2D g2d) {
        double barWidth = (double) this.getWidth() / this.sortingAlgorithm.nums.length; // Balkenbreite = Fenster Breite / Array Einträge
        int buffImgWidth = this.getWidth(); // Bild Breite = Fenster Breite
        int buffImgHeight = this.getHeight(); // Bild Höhe = Fenster Höhe
        BufferedImage bufferedImage = new BufferedImage(buffImgWidth, buffImgHeight, BufferedImage.TYPE_INT_ARGB);
        makeBufferedImageTransparent(bufferedImage); // Bild vorerst transparent machen

        try {
            for(int i = 0; i < this.sortingAlgorithm.nums.length; i++) { // Für jeden Balken
                Graphics2D bufferedGraphics = bufferedImage.createGraphics();
                double percentOfMax = (double) sortingAlgorithm.nums[i] / this.MAX_VALUE; // Maximale Balken Höhe
                double heightPercentOfPanel = percentOfMax * 512.0/720.0; // Maximale Balken Höhe
                int height = (int) (heightPercentOfPanel * (double) this.getHeight()); // Balken Höhe
                int xBegin = (int) Math.round(i * barWidth); // Linke untere Ecke vom Balken
                int nextXBegin = (int) Math.round((i + 1) * barWidth);  // Linke untere Ecke vom nächsten Balken
                int actualBarWidth = nextXBegin - xBegin; // Breite vom Balken
                int yBegin = this.getHeight() - height; // Linke obere Ecke vom Balken

                // Veränderung = Grün
                // Veränderung im letzten Durchgang = Rot
                int val = barColors[i] * 2;
                if (val > 190) {
                    bufferedGraphics.setColor(new Color(255 - val, 255, 255 - val));
                }
                else {
                    bufferedGraphics.setColor(new Color(255, 255 - val, 255 - val));
                }

                // Balken Zeichnen
                bufferedGraphics.fillRect(xBegin, yBegin, actualBarWidth, height);

                // Ränder von den Balken
                bufferedGraphics.setColor(Color.BLACK);
                bufferedGraphics.drawRect(xBegin, yBegin, actualBarWidth, height);

                if (barColors[i] > 0) {
                    barColors[i] -= 20;
                    if (barColors[i] < 0) {
                        barColors[i] = 0;
                    }
                }
                bufferedGraphics.dispose(); // Zeichen beenden
            }
        } catch (Exception _) {}

        g2d.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null); // Bild auf Fenster zeichnen
    }

    private void makeBufferedImageTransparent(BufferedImage image)
    {
        Graphics2D bufferedGraphics = null;
        try
        {
            bufferedGraphics = image.createGraphics(); // Graphik erstellen

            bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR)); // Alphakanal auswählen
            bufferedGraphics.fillRect(0, 0, image.getWidth(), image.getHeight()); // Graphik mit Alphakanal füllen
            bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); // Alphakanal
        }
        finally
        {
            if(bufferedGraphics != null)
            {
                bufferedGraphics.dispose(); // Graphik beenden
            }
        }
    }

    private void longWait() {
        try {
            Thread.sleep(1000); // 1000 ms warten
        } catch(InterruptedException _) {}
    }

}
