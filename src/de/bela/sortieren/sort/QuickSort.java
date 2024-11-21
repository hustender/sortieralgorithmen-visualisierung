package de.bela.sortieren.sort;

import de.bela.sortieren.screen.VisualizerScreen;

public class QuickSort extends Sort {

    public QuickSort(VisualizerScreen screen) {
        super(screen);
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }

    @Override
    public void sort() {
        int begin = 0;
        int end = nums.length - 1;
        quickSort(begin, end);
        resetColors();
    }

    private void quickSort(int begin, int end) {
        if(begin < end) {
            int partitionIndex = partition(begin, end);
            quickSort(begin, partitionIndex-1);
            quickSort(partitionIndex+1, end);
        }
    }

    private int partition(int begin, int end) {
        int pivot = nums[end];
        int i = begin - 1;

        for (int j = begin; j < end; j++) {
            if (nums[j] <= pivot) {
                i++;
                tausche(i, j, nums);
            }
        }
        tausche(i + 1, end, nums);
        return i + 1;
    }

    @Override
    public int getDefaultDelay() {
        return 25;
    }
}
