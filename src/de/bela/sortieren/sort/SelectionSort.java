package de.bela.sortieren.sort;

import de.bela.sortieren.screen.VisualizerScreen;

public class SelectionSort extends Sort {

    public SelectionSort(VisualizerScreen screen) {
        super(screen);
    }

    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    public void sort() {
        for(int outer = 0; outer < this.nums.length; outer++) {
            int min = outer;
            for(int inner = outer + 1; inner < this.nums.length; inner++) {
                if (this.nums[inner] < this.nums[min]) min = inner;
            }
            this.tausche(outer, min, this.nums);
        }
        resetColors();
    }

    @Override
    public int getDefaultDelay() {
        return 80;
    }
}
