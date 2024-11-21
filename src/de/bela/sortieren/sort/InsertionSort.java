package de.bela.sortieren.sort;

import de.bela.sortieren.screen.VisualizerScreen;

public class InsertionSort extends Sort {

    public InsertionSort(VisualizerScreen screen) {
        super(screen);
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    public void sort() {
        for(int outer = 1; outer < nums.length; outer++) {
            int key = nums[outer];
            int inner = outer - 1;
            while(inner >= 0 && nums[inner] > key) {
                tausche(inner + 1, inner, nums);
                inner--;
            }
            nums[inner + 1] = key;
        }
        resetColors();
    }

    @Override
    public int getDefaultDelay() {
        return 1;
    }
}
