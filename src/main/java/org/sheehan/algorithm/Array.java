package org.sheehan.algorithm;

import org.sheehan.algorithm.sort.SortArray;

import java.util.*;
import java.util.List;

/**
 *
 * Created by bob on 6/5/14.
 *
 * Notes:
 *
 * 1. median of array - sort and get middle element
 * 2. arrays do not have the link store that linked list nodes have --> less memory
 * 3. arrays provide better locality for cache hits
 * 4. random access
 */
public class Array {

    // 1. copy array to length +1
    // 2. move items from pos towards right
    // 3. set pos to new val
    public static <T> T[] insertAt(T[] array, Integer pos, T val){
        T[] copyArray = Arrays.copyOf(array, array.length + 1);

        // move items from pos+1 over towards end of new array. iterate from end of array
        for (int i = copyArray.length-1; i > pos; i--)
            copyArray[i] = copyArray[i-1];

        copyArray[pos]= val;

       return copyArray;
    }

    // 1. move items from right to left towards pos
    public static <T> T[] removeAt(T[] array, Integer pos){

        for (int i = pos; i < array.length-1; i++)
            array[i] = array[i+1];

        T[] copyArray = Arrays.copyOf(array, array.length-1);
        return copyArray;
    }

    // Trick here is think of top row up to one before the end.
    // The right column starts with the end of the top row
    // i fixes the ring offset, j is the iterator for swapping
    public static void rotateCW90(Integer[][] a){

        // i is an annular inset from all sides
        for (int i = 0; i < a.length/2; ++i) {
            // iterate CW around annular region
            for (int j = i; j < a.length-i-1; ++j) { // not all the way to end, which is start of next col or row!!!

                // iterate CW around annular region
                int topRow = a[i][j]; // iterate across with j
                int rightCol = a[j][a.length-1-i]; //iterate down on j
                int bottomRow = a[a.length-1-i][a.length-1-j]; //iterate back across on j
                int leftCol = a[a.length-1-j][i]; //iterate up on j

                //shift each 90 degrees
                a[j][a.length-1-i] = topRow;
                a[a.length-1-i][a.length-1-j] = rightCol;
                a[a.length-1-j][i] = bottomRow;
                a[i][j] = leftCol;
            }
        }
        System.out.println();
    }

    // brute force is compute every pair look at +- profit and take max (O(n^2))
    /*int profit = Integer.MIN_VALUE;
    for(int i=0; i<prices.length-1; i++){
        for(int j=0; j< prices.length; j++){
            if(profit < prices[j] - prices[i]){
                profit = prices[j] - prices[i];
            }
        }
    }*/

    // nlogn is a mergesort divide and conquer
    // OR better is O(n) - track min element AND max difference
    // get deltas as array then maximal sub array of deltas !
    public static int maxDiff(Integer arr[])
    {
        int maxDiff = arr[1] - arr[0];
        int min = arr[0];

        for(int i=1; i < arr.length; i++)
        {
            if (arr[i] - min > maxDiff)
                maxDiff = arr[i] - min;
            if (arr[i] < min)
                min = arr[i];
        }
        return maxDiff;
    }

    // knuth shuffle O(n)
    // left side of i is shuffled
    public static <T> void shuffle(T array[]){

        Random r = new Random();

        for (int i=0; i < array.length; ++i ){
             //select random index (i, length)
             int randomIndexAboveI = r.nextInt(array.length-i)+i;

             // swap with i
             T tmp = array[i];
             array[i] = array[randomIndexAboveI];
             array[randomIndexAboveI]=tmp;
        }

        print(array);
    }

    public static int findLongestRun(Integer array[]) {

        int currLength = 1; // minimal length for a run !
        int maxLength = 0;
        int max= array[0];

        for (int i = 0; i < array.length-1; ++i){
            if (array[i] == array[i+1]){
                currLength++;

            }

            if ((array[i] != array[i+1]) || i == array.length-2){ //not 'else if'  because of end condition !
                if (currLength > maxLength) {
                    maxLength = currLength;
                    max = array[i];
                }
                currLength = 1; // minimal length for a run !
            }
        }

        System.out.println("max run " + maxLength);
        return max;
    }

    // print start index, length, element data of longest run in array
    public static <T extends Comparable<T>> void findLongestIncreasingRun(T array[]) {
        int length = 1;
        int maxLength = 0;

        int start = -1; //optional locate run
        int maxStart = -1;//optional locate run

        // limit is 1 less than length for next compare
        for (int i = 0; i < array.length - 1; ++i) {
            if (array[i].compareTo(array[i + 1])< 0) {
                length++;
                if (start == -1) //optional locate run
                    start = i;  //optional locate run
            }

            // change or end of array
            if (array[i].compareTo(array[i + 1])>=0 || i == array.length-2) {
                if (length > maxLength) {
                    maxLength = length;

                    maxStart = start; //optional locate run
                }
                length = 1;
                start = -1; //optional locate run
            }
        }

        System.out.println("start: " + maxStart + " length: " + maxLength);

    }

    // 1. build hash table
    // 2. iterate array and find first hash entry > 1
    public static Integer findFirstNonrepeater(Integer array[]) {

        Map<Integer, Integer> map = new HashMap<Integer,Integer>();

        for (Integer i=0; i < array.length; ++i){
            if (map.containsKey(array[i]))
                map.put(array[i], map.get(array[i])+1);
            else
                map.put(array[i], 1);
        }

        for (Integer i=0; i < array.length; ++i){
            if (map.get(array[i])==1)
                return array[i];

        }

        return null;
    }

    // bitmask - for numbers up to 256 limit - int mask
    public static Set<Integer> findDuplicates(Integer array[]) {

        int checker = 0; //init

        Set<Integer> duplicates = new HashSet<Integer>();

        for (Integer i: array){
            int mask = 1 << i;
            if ((checker & mask) > 0) {
                duplicates.add(i);
             }else{
                checker |= mask;
            }
        }
        return duplicates;
    }

    //HashMapImpl
    public static Set<Integer> findDuplicatesMap(Integer array[]) {

        Set<Integer> duplicates = new HashSet<Integer>();
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();

        for (Integer i: array){
            if (map.get(i) == null) {
                map.put(i, 1);
            }else
                duplicates.add(i);

            map.put(i, map.get(i)+1);
        }

        //duplicates.forEach((Integer i) -> System.out.println(i)); Java 8
        return duplicates;
    }

    //HASHMAP - removes duplicates and fills left over array with -1's
    public static void removeDuplicates(Integer[] array) {

        Map<Integer,Integer> map = new HashMap<>();

        int dst = 0;
        for (int arr_i : array) {
            Integer curr = map.get(arr_i);
            if (curr == null){
                map.put(arr_i, 1);
                array[dst++] = arr_i;
            } else
                map.put(arr_i, curr++);
        }

        for (int i = dst; i < array.length; ++i) {
            array[i] = -1;
        }
    }


    public static void removeDuplicates2(Integer[] array) {

        int dstIndex = 1;
        for (int i = 1; i < array.length; ++i) {

            int j;
            for (j = 0; j < dstIndex; ++j) {
                if (array[i] == array[j]) {
                    break; // this thing already been done friend.
                }
            }

            if (j == dstIndex)
                array[dstIndex++] = array[i];
        }

        // fill leftovers
        for (int i = dstIndex; i < array.length; ++i) {
            array[i] = -1;
        }
    }

    // increment overwriting elements of val
    public static int removeElements(Integer[] array, int val) {

        int dstIndex = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != val)
                array[dstIndex++] = array[i];
        }

        // fill leftovers
        for (int i = dstIndex; i < array.length; ++i) {
            array[i] = -1;
        }

        return dstIndex;
    }

    // 2 counters
    public static <T> void reverse(T[] buffer, int start, int end) {

        final int length = end - start;
        int i=start;
        int j=end;
        while (j > i){
            T c = buffer[i];
            buffer[i++] = buffer[j];
            buffer[j--] = c;
        }
    }


    // 1. reverse entire array
    // 2. reverse each sub array at shift pos
    public static <T> void rotateArray(T[] array, int shift) {
        shift %= array.length;

        //reverse entire array
        reverse(array, 0, array.length - 1);
        //reverse again first sub array
        reverse(array, 0, shift - 1);
        //reverse again second sub array
        reverse(array, shift, array.length - 1);
    }

    //sorted arrays input
    public static boolean isAnagram(Integer []arr1, Integer []arr2){

        if (arr1.length != arr2.length)
            return false;

        return false;
    }


    // merge sorted arrays into new third array.
    // different than merge into larger of two arrays below..
    public static <T extends Comparable> long mergeSortedArrays(T[] array1, T[] array2, T[] merged) {
        long inversions = 0;

        int i = 0;
        int j = 0;
        int k = 0;
        while (i < array1.length && j < array2.length) {
            if (array1[i].compareTo(array2[j]) < 0)
                merged[k++] = array1[i++];
            else {
                merged[k++] = array2[j++];
                inversions += array1.length-i;

            }
        }

        //one array will have left overs
        while (i < array1.length)
            merged[k++] = array1[i++];
        while (j < array2.length)
            merged[k++] = array2[j++];

        return inversions;
    }

    // with only two arrays... nums1 is big enough for merge !
    public void mergeSortedArrays(int[] nums1, int m, int[] nums2, int n) {

        int i = m-1;
        int j = n-1;
        int k = n+m-1;

        while(i >= 0 && j >= 0){
            nums1[k--] = nums2[j] > nums1[i] ? nums2[j--] : nums1[i--];
        }

        while(j >= 0)
            nums1[k--] = nums2[j--];

    }

    // start with k = 0
    static void getPermutations2(java.util.List<Integer> arr, int k) {

        // increasing starting point swapping
        for (int i = k; i < arr.size(); i++) {
            java.util.Collections.swap(arr, i, k);
            getPermutations2(arr, k + 1);
            java.util.Collections.swap(arr, k, i);
        }

        // when we iterate to the end for a given recursion we have a permutation !
        if (k == arr.size() - 1) {
            System.out.println(java.util.Arrays.toString(arr.toArray()));
        }
    }

    // start with k = 0
    static void getPermutationsArr(Integer [] arr, int swapIndex) {

        // for each i we fire off a recursive call
        for (int i = swapIndex; i < arr.length; i++) {

            //swap then recurse
            int tmp = arr[i];
            arr[i] = arr[swapIndex];
            arr[swapIndex] = tmp;

            // each call increments the swap index
            getPermutationsArr(arr, swapIndex + 1);

            //then swap back
            tmp = arr[i];
            arr[i] = arr[swapIndex];
            arr[swapIndex] = tmp;
        }

        // when we iterate to the end for a given recursion we have a permutation !
        if (swapIndex == arr.length - 1) {
            for (int i: arr)
                System.out.print(i + " ");
            System.out.println();
        }
    }

    public static void getPermutations(java.util.List<Integer> prefix, java.util.List<Integer> array, Set<java.util.List<Integer>> cache) {
        int n = array.size();
        if (n == 0) {
            cache.add(prefix); //got one !
        } else {
            for (int i = 0; i < n; i++) {
                java.util.List<Integer> array2 = new ArrayList<Integer>();
                array2.addAll(array.subList(0, i));//exclude i
                array2.addAll(array.subList(i + 1, n));//exclude n

                List<Integer> prefix2 = new ArrayList<Integer>();
                prefix2.addAll(prefix);
                prefix2.add(array.get(i)); //include i
                getPermutations(prefix2, array2, cache); //recursion inside loop for fixed i
            }
        }
    }

    // all contiguous sub arrays
    public static void getSubArrays(Integer[] array, Set<java.util.List<Integer>> cache) {
        for (int i = 0; i < array.length; ++i) {
            for (int j = i + 1; j <= array.length; ++j) {
                cache.add(Arrays.asList(Arrays.copyOfRange(array, i, j)));
            }
        }
    }

    // dynamic programming
    public static int getMaxAndMinSubArray(Integer[] array) {
        int max = array[0]; //init
        int min = array[0];

        // possible sub solution arrays
        int[] solnMax = new int[array.length];
        int[] solnMin = new int[array.length];

        solnMax[0] = array[0]; // initial sub solution

        for (int i = 1; i < array.length; i++) {
            // ith sub soln is max of  either:
            // 1. new element
            // 2. new element + last sub soln (i-1)
            solnMax[i] = Math.max(array[i], solnMax[i - 1] + array[i]);
            solnMin[i] = Math.min(array[i], solnMin[i - 1] + array[i]);

            // is new sum the new max ?
            max = Math.max(max, solnMax[i]);
            min = Math.min(min, solnMin[i]);
        }

        System.out.println(min);
        return max;
    }

    // iterative - get all subarrays and sum them up
    // can track the actual sub array here
    public static int getMaxSubArraySum2(Integer[] array) {
        Set<java.util.List<Integer>> cache = new HashSet<java.util.List<Integer>>();

        getSubArrays(array, cache);

        int sum = 0, max = Integer.MIN_VALUE;
        for (List<Integer> a : cache) {
            sum = 0;
            for (int i : a) {
                sum += i;
            }
            max = Math.max(max, sum);
        }
        return max;
    }

    // iterative - just a running sum
    public static int getMaxSubArraySum3(Integer[] array) {

        int sum = 0, max = 0;
        for (int arr_i : array) {
            sum += arr_i;
            max = Math.max(max, sum);

            if (sum < 0) //resest if negative cause it ain't helpin going forward now is it ?
               sum = 0;
        }
        return max;
    }

    // find sum of two elements adds to sum in unsorted array.
    public static boolean isTwoSum(Integer[] array, int sum){
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (int i=0; i < array.length; ++i){
            map.put(array[i], sum-array[i]);
        }

        for (int i=0; i < array.length; ++i){
            int j = map.get(array[i]);
            if (map.get(j)!=null) {
                System.out.println(array[i] + " " + j);
                return true;
            }
        }

        return false;
    }

    public static int findLargestSumSequence(Integer[] array){
        Integer soln[] = new Integer[array.length];
        soln[0]=array[0];
        int maxSum = array[0];

        int left = 0, right = 0;

        for (int i = 1; i < array.length; ++i){

            if (array[i] > soln[i-1]+ array[i]){
                soln[i] = array[i];
                left = i;
            } else {
                soln[i] = soln[i-1]+ array[i];
            }

            soln[i] = Math.max(array[i], soln[i-1]+ array[i]);
            if (soln[i] > maxSum){
                maxSum = soln[i];
                right = i;
            }
        }

        System.out.println("bounds " + left + " " + right);
        return maxSum;
    }

    // UTILS ----------------------------------------------------------------
    public static Integer[] createArray(int size, int limit, boolean sorted) {

        Random random = new Random();
        java.util.List<Integer> list = new ArrayList<Integer>();
        do {
            list.add(random.nextInt() % (limit) / 2 + limit / 2);
        } while (list.size() != size);
        Integer[] array = list.toArray(new Integer[0]);


        if (sorted)
            SortArray.insertionSort(array);
        return array;
    }

    public static <T> void print(T[] array) {
        System.out.println();
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }


}