package de.bela.sortieren.sort;

import de.bela.sortieren.screen.VisualizerScreen;

import java.util.Arrays;
import java.util.Random;

public abstract class Sort {

    public int[] nums; // Zu sortierendes Array
    protected VisualizerScreen screen; // VisualizerScreen Deklarierung
    protected int changes; // Anzahl an Array Veränderungen

    public Sort(VisualizerScreen screen) {
        this.screen = screen; // Screen initialisieren
        changes = 0; // Veränderungen auf 0 setzen
    }

    public void tausche(int i, int j, int[] nums) {
        int temp = nums[i]; // Temporärer Wert von i
        nums[i] = nums[j]; // Wert von index i auf Wert von index j
        nums[j] = temp; // Wert von Index j auf Wert von alten Index i

        // Balkenfarbe ändern
        screen.barColors[i] = 100;
        screen.barColors[j] = 100;

        update();
    }

    public void update() {
        changes++; // Änderungen erhöhen
        screen.repaint(); // Screen neu zeichnen
        try {
            Thread.sleep(screen.delay.getValue()); // Für eingestelltes Delay warten
        } catch(Exception _) {}
    }

    public void initArray() {
        int bars = screen.getWidth() / 5; // Anzahl an Array einträgen = Screen Breite / 5
        int[] nums = new int[bars]; // Array initialisieren
        for(int i = 0; i < bars; i++) { // Für jeden Eintrag in nums Größe gleich +1
            nums[i] = i; // Wert eintragen
        }
        Random rng = new Random(); // Random
        for (int i = 0; i < bars; i++) { // Jeden Eintrag mit einem zufälligen Eintrag tauschen
            int swapWithIndex = rng.nextInt(bars - 1); // Zufälliger Tauschindex
            // Tauschen wie in tausche() nur ohne Änderung
            int temp = nums[i];
            nums[i] = nums[swapWithIndex];
            nums[swapWithIndex] = temp;
        }
        this.nums = nums; // Neuen Array Wert zuordnen
    }

    public void resetColors() {
        screen.barColors = Arrays.stream(screen.barColors).map(_ -> 0).toArray(); // Alle Balken Farben = Weiß
        screen.repaint(); // Screen neu zeichnen
    }

    // Getter Veränderungen
    public int getChanges() {
        return this.changes;
    }

    // Abstrakt Sortierfunktion
    public abstract void sort();

    // Abstrakt Getter normal Delay
    public abstract int getDefaultDelay();

    // Abstrakt Getter Name
    public abstract String getName();

    // Sortierart Enum
    public enum SortType {
        SELECTIONSORT("Selection Sort ist ein einfacher Sortieralgorithmus, der in-place arbeitet. Er sortiert ein Array, indem er wiederholt das Minimum (oder Maximum) von den unsortierten Elementen auswählt und es an den Anfang (bzw. das Ende) des sortierten Array platziert."),
        BUBBLESORT("Bubble Sort ist ein einfacher Sortieralgorithmus, der in-place arbeitet. Er sortiert ein Array, indem er wiederholt benachbarte Elemente vertauscht, wenn sie in der falschen Reihenfolge sind."),
        QUICKSORT("Quick Sort ist ein schneller Sortieralgorithmus, der in-place arbeitet. Er wählt ein Element als Pivot und partitioniert das Array um den Pivot, sodass Elemente kleiner als der Pivot links und Elemente größer als der Pivot rechts stehen."),
        INSERTIONSORT("Insertion Sort ist ein einfacher Sortieralgorithmus, der in-place arbeitet. Er sortiert ein Array, indem er Elemente nacheinander durchläuft und sie an die richtige Position im sortierten Array einfügt."),
        MERGESORT("Merge Sort ist ein stabiler Sortieralgorithmus, der nicht in-place arbeitet. Er teilt das Array in zwei Hälften, sortiert die Hälfte jeweils wieder mit Merge Sort und fügt sie wieder zusammen.");

        public final String description; // Beschreibung des Sortieralgorithmus

        SortType(String description) {
            this.description = description; // Beschreibung initialisieren
        }
    }

}
