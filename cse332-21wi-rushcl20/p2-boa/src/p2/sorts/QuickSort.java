package p2.sorts;

import java.util.Arrays;
import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        partition(array, comparator, 0, array.length - 1);
    }

    private static <E> E choosePivot(E[] array, Comparator<E> comparator, int low, int high) {
        E pivot;
        int mid = (low + high) >>> 1;

        if ((comparator.compare(array[high], array[low]) < 0 &&
                comparator.compare(array[low], array[mid]) < 0) ||
                (comparator.compare(array[high], array[low]) > 0 &&
                comparator.compare(array[low], array[mid]) > 0)) {
            pivot = array[low];
        } else if ((comparator.compare(array[low], array[high]) < 0 &&
                comparator.compare(array[high], array[mid]) < 0) ||
                (comparator.compare(array[low], array[high]) > 0 &&
                comparator.compare(array[high], array[mid])>0)){
            pivot = array[high];
            swap(array, low, high);
        } else {
            pivot = array[mid];
            swap(array, low, mid);
        }
        return pivot;
    }

    private static <E> void swap(E[] array, int firstInd, int secondInd) {
        E temp = array[firstInd];
        array[firstInd] = array[secondInd];
        array[secondInd] = temp;
    }

    private static <E> void partition(E[] array, Comparator<E> comparator, int low, int high) {
        if (low + 1 == high) {
            if (comparator.compare(array[low], array[high]) > 0) {
                swap(array, low, high);
            }
        } else if (low + 1 < high) {
            E pivot = choosePivot(array, comparator, low, high);
            int pivotInd = low++;
            while (low < high) {
                if (comparator.compare(array[high], pivot) > 0) {
                    high--;
                } else if (comparator.compare(array[low], pivot) <= 0) {
                    low++;
                } else {
                    swap(array, low, high);
                }
            }
            if (comparator.compare(array[pivotInd], array[low]) > 0) {
                swap(array, pivotInd, low);
            }
            partition(array, comparator, pivotInd, Math.min(low, high));
            partition(array, comparator, Math.min(low, high) + 1, high);
        }
    }
}