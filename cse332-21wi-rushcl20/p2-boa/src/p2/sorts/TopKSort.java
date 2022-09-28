package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> heapSortK = new MinFourHeap<E>(comparator);
        if(k >= array.length){
            k = array.length;
        }

        for(int i = 0; i < k; i++){
            heapSortK.add(array[i]);
        }

        for(int i = k; i < array.length; i++){
            if(comparator.compare(array[i], heapSortK.peek()) > 0){
                heapSortK.next();
                heapSortK.add(array[i]);
            }
            array[i] = null;
        }

//        for(int i = k-1; i >= 0; i--){
//            array[i] = heapSortK.next();
//        }
        for(int i = 0; i < k; i++){
            array[i] = heapSortK.next();
        }
    }
}
