package de.bela.sortieren.sort;

import de.bela.sortieren.screen.VisualizerScreen;

public class BubbleSort extends Sort {

    public BubbleSort(VisualizerScreen screen) {
        super(screen);
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    public void sort() {
        for(int outer = nums.length-1; outer > 0; outer--) {
            for(int inner = 0; inner < outer; inner++) {
                if(nums[inner] > nums[inner+1]) {
                    tausche(inner, inner+1, nums);
                }
            }
        }
        resetColors();
    }

    @Override
    public int getDefaultDelay() {
        return 1;
    }
}
