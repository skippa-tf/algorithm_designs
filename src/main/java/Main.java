import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        test(100);
    }

    public static void test(int startSize){
        int size = startSize;
        boolean sorting = true;
        double[] array = makeArray(size);
        StringBuilder log = new StringBuilder("Size;MS_Elapsed\n");
        while (sorting) {
            sorting = false;
            System.out.println("---------------------------------" + "\nStart sort of size: " + size);

            long start = System.currentTimeMillis();
            HeapSort.sort(array);
            long end = System.currentTimeMillis();
            long timespan = end - start;
            System.out.println("Sort took: " + timespan);
            if (timespan < 90000 && checkSorted(array) && size < 200000000) { // Double input if it took less than 1.5 minutes to sort.
                log.append(size + ";" + timespan + "\n");
                System.out.print(" Doubling input...");
                size *= 2;
                sorting = true;
                array = makeArray(size);

            }
        }
        System.out.println(log);
    }

    /***
     * This method checks if an array is properly sorted in ascending order.
     * @param arr The array to check.
     * @return true if the list is sorted. false otherwise.
     */
    public static boolean checkSorted(double[] arr){
        System.out.println(Arrays.toString(arr));
        for (int i = 1; i < arr.length; i++){
            if (arr[i] < arr[i - 1]) {
                System.err.println("Not sorted!");
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a random array of doubles.
     * @param size Size of the array to generate.
     * @return Array of random doubles.
     */
    public static double[] makeArray(int size){
        double[] arr = new double[size];
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++){
            arr[i] = Math.round((rand.nextDouble() * 100))/ 100.0;
        }
        return arr;
    }


    static class HeapSort {
        public static void sort(double[] arr) {
            /* Base cases */
            if (arr.length < 2) {
                return;
            } else if(arr.length == 3){
                if(arr[0] > arr[1]){
                    swap(arr, 0, 1);
                }
                if(arr[1] > arr[2]){
                    swap(arr, 1, 2);
                }
            }

            /* First arrange the array into a max-heap */
            heapify(arr);

            /* Sort portion */
            int lastNode = arr.length - 1;
            while (lastNode > 0){
                // Swap the root to the last position, and decrement lastindex.
                // This simulates us removing the root node.
                swap(arr, lastNode--, 0);
                int currentNode = 0;
                int left = 1;
                int right = 2;
                while(arr[currentNode] < arr[left] && arr[currentNode] < arr[right]){
                    /* Compare the children and swap the current node for its greatest child. */
                    int best = arr[left] > arr[right] ? left : right;
                    if (arr[best] > arr[currentNode]){
                        swap(arr, best, currentNode);
                        currentNode = best;
                    }
                }
            }
        }

        private static void heapify(double[] arr) {
            for (int currentNode = arr.length - 1; currentNode >= 0; currentNode--) {

                int left = (currentNode * 2) + 1;
                int right = (currentNode * 2) + 2;
                /* Compare the children and swap the current node for its greatest child. */
                if (left < arr.length && right < arr.length) {
                    int best = arr[left] > arr[right] ? left : right;
                    if (arr[best] > arr[currentNode]) {
                        swap(arr, best, currentNode);
                        currentNode = best;
                    }
                }
            }
            System.out.println(Arrays.toString(arr));
        }

        /***
         * Swap index x with index y in the array.
         * @param arr
         * @param x
         * @param y
         */
        private static void swap(double[] arr, int x, int y){
            double temp = arr[x];
            arr[x] = arr[y];
            arr[y] = temp;
        }
    }

    static class MergeSort {
        public static void sort(double[] arr) {

            if (arr.length > 1){
                double[] firstHalf = Arrays.copyOfRange(arr, 0, arr.length / 2);
                double[] secondHalf = Arrays.copyOfRange(arr, arr.length / 2, arr.length);

                sort(firstHalf);
                sort(secondHalf);

                merge(arr, firstHalf, secondHalf);
            }
        }

        public static void merge(double[] out, double[] firstHalf, double[] secondHalf){
            int f = 0;
            int s = 0;
            int i;
            for (i = 0; i < out.length; i++){
                if (f < firstHalf.length && s < secondHalf.length) {
                    /* Check the both halves' indices and put the least of them into out[i] */
                    out[i] = firstHalf[f] < secondHalf[s] ? firstHalf[f++] : secondHalf[s++];
                } else {
                    break;
                }
            }

            /* Add the leftover items in the halves to the output */
            while(f < firstHalf.length) {
                out[i++] = firstHalf[f++];
            }
            while(s < secondHalf.length) {
                out[i++] = secondHalf[s++];
            }
        }
    }

    static class InsertionSort {
        public static void sort(double[] arr) {
            /* Array is sorted if it has less than 2 items */
            if (arr.length < 2) {
                return;
            }
            /* Go through each element in the array */
            for (int i = 1; i < arr.length; i++){
                /* If the item is less than the previous one, it is unsorted */
                if (arr[i] < arr[i-1]) {
                    int j = i;
                    /* Go backwards through the sorted portion and swap it back till it is in the correct position   */
                    while(j > 0 && arr[j] < arr[j - 1]) {
                        double temp = arr[j];
                        arr[j] = arr[j-1];
                        arr[j - 1] = temp;
                        j--;
                    }
                }
            }
        }
    }


    /***
     * This class holds all the logic for a variation of mergesort that merges using a linked list.
     */
    static class LinkedMergeSort {

        public static void sort(double[] arr) {
            Node head = linkedSplit(arr, 0, arr.length - 1);

            rearrangeArray(arr, head);
        }

        public static void rearrangeArray(double[] arr, Node head) {
            for (int i = 0; i < arr.length; i++){
                if (head != null) {
                    arr[i] = head.value;

                    head = head.next;
                }
            }
        }

        public static Node linkedSplit(double[] arr, int start, int end) {
            System.out.println("start: " + start + " end: " + end);
            if (end - start <= 1) {
                System.out.println("if ");
                return new Node(arr, start);
            }
            int mid = start + (end-start) / 2;
            Node firstHalf = linkedSplit(arr, start, mid);
            System.out.println("first half complete. " + "start: " + start + " end: " + end / 2);
            Node secondHalf = linkedSplit(arr, mid + 1, end);
            System.out.println("second half complete. " + "start: " + (end / 2) + 1 + " end: " + end);

            return linkedMerge(firstHalf, secondHalf);
        }

        public static Node linkedMerge(Node x, Node y){
            Node head = null;
            Node tail = null;

            while (x != null && y != null) {
                if (x.compareTo(y) <= 0){
                    if (head == null){
                        head = x;
                        tail = x;
                    } else {
                        tail.next = x;
                        x.prev = tail;
                        tail = x;
                    }
                    x = x.next;
                } else {
                    if (head == null) {
                        head = y;
                        tail = y;
                    } else {
                        tail.next = y;
                        y.prev = tail;
                        tail = y;
                    }
                    y = y.next;
                }
            }
            if (x != null) {
                tail.next = x;
                x.prev = tail;
            } else if (y != null) {
                tail.next = y;
                y.prev = tail;
            }
            return head;
        }
    }




}

class Node implements Comparable<Node>{

    Node prev;
    Node next;

    double[] array; // reference to the array that is being sorted.
    int index;
    double value;

    /***
     *
     * @param index The index to this node's value in {@code array}.
     * @param array The array that is being sorted.
     */
    public Node (double[] array, int index) {
        next = null;
        this.array = array;
        this.index = index;
        value = array[index];
    }

    /***
     * Travel to the end of the linked list and append the node to it.
     * @param node The node to append the to the end of the list.
     */
    public void append(Node node) {
        if (next != null) {
            next.append(node);
        } else {
            next = node;
            node.prev = this;
        }
    }

    /***
     * Insert the node right before this node.
     * @param node The node to be inserted.
     */
    public void insertBefore(Node node) {
        if (node != null) {
            if (prev != null) {
                prev.next = node;
                node.next = this;
            }
            else {
                node.next = this;
            }
        }
    }

    @Override
    public int compareTo(Node n) {
        return Double.compare(this.value, n.value);
    }

    @Override
    public String toString() {
        return value + "@[" + index + "]";
    }
}
