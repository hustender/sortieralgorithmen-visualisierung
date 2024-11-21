package de.bela.sortieren.sort;

import de.bela.sortieren.screen.VisualizerScreen;

public class MergeSort extends Sort {

    public MergeSort(VisualizerScreen screen) {
        super(screen);
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    public void sort() {
        mergeSort(nums, 0, nums.length - 1);
        resetColors();
    }

    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }

    private void merge(int[] array, int left, int middle, int right) {
        int[] leftArray = new int[middle - left + 1];
        int[] rightArray = new int[right - middle];

        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = array[left + i];
        }
        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = array[middle + 1 + i];
        }

        int leftIndex = 0, rightIndex = 0;
        int currentIndex = left;

        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                array[currentIndex] = leftArray[leftIndex];
                change(currentIndex);
                leftIndex++;
            } else {
                array[currentIndex] = rightArray[rightIndex];
                change(currentIndex);
                rightIndex++;
            }
            currentIndex++;
        }

        while (leftIndex < leftArray.length) {
            array[currentIndex] = leftArray[leftIndex];
            change(currentIndex);
            leftIndex++;
            currentIndex++;
        }

        while (rightIndex < rightArray.length) {
            array[currentIndex] = rightArray[rightIndex];
            change(currentIndex);
            rightIndex++;
            currentIndex++;
        }
    }

    private void change(int index) {
        screen.barColors[index] = 100; // Highlight the index
        screen.repaint();
        update();
    }

    @Override
    public int getDefaultDelay() {
        return 10;
    }
}