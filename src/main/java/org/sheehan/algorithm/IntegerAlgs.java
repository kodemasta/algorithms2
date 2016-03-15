package org.sheehan.algorithm;

import java.util.*;

/**
 * Created by bsheehan on 2/12/16.
 */
public class IntegerAlgs {

    public static void getPascalRow(int rowIndex) {

        int a[] = new int[rowIndex+1];
        a[0] = 1;
        int b[] = new int[rowIndex+1];
        for (int i=0; i<=rowIndex;++i){
            for (int j=0; j<=i; ++j){
                b[j] = a[j];
                if (j-1 >=0)
                    b[j] += a[j-1];
            }
            for (int k=0; k<=rowIndex;++k)
                a[k]=b[k];
            }

        for (int i=0; i<=rowIndex;++i){
            System.out.println(b[i]);
        }

    }

    // O(n!) permutations !
    public static Set<Integer[]> getPermutationsRecursive(Integer[] num){
        if (num == null)
            return null;

        Set<Integer[]> perms = new HashSet<>();

        //base case
        if (num.length == 0){
            perms.add(new Integer[0]);

            return perms;
        }

        //shave off first char then get sub perms on remaining chars.
        //...then insert the first into each position of each sub perm.
        int first = num[0];
        Integer[] remainder = Arrays.copyOfRange(num,1,num.length);
        Set<Integer[]> subPerms = getPermutationsRecursive(remainder);
        for (Integer[] subPerm: subPerms){
            for (int i=0; i <= subPerm.length; ++i){ // '<='   IMPORTANT !!!
                Integer[] newPerm = Arrays.copyOf(subPerm, subPerm.length+1);
                for (int j=newPerm.length-1; j>i; --j)
                    newPerm[j] = newPerm[j-1];
                newPerm[i]=first;
                if (!duplicate(perms,newPerm))
                    perms.add(newPerm);
            }
        }

        return perms;
    }

    private static boolean duplicate(Set<Integer[]> perms, Integer[] newPerm) {
        if (perms.size() == 0)
            return false;
        for (Integer[] perm : perms) {
            for (int i = 0; i < perm.length; ++i)
                if (perm[i] != newPerm[i])
                    return false;
        }
        return true;

    }


    public static int reverseInt(int n) {

        long reverse = 0;

        while(n != 0){
            long digit = n%10;
            reverse = reverse*10 + digit;
            n /= 10;
        }

        if (reverse < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        if (reverse > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        return (int)reverse;
    }

    public static String int2Str(int n) {
        StringBuilder builder = new StringBuilder();

        // Handle negative
        int startPos = 0;
        if (n < 0) {
             n*=-1;
            builder.insert(0, "-");
            startPos = 1;
        }

        while(n != 0){
            long digit = n%10;
            builder.insert(startPos, (char)(digit +'0'));
            n /= 10;
        }

        return builder.toString();
    }

    public static int str2int(String str) {

        str = str.trim();

        int limit = 0;
        boolean negative = false;
        if (str.charAt(0) == '-') {
            negative = true;
            limit = 1;
        }

        long sum = 0;
        long mult = 1;

        for (int pos = str.length() - 1; pos >= limit; pos--) {
            sum += (str.charAt(pos) - '0') * mult;
            mult *= 10;
        }

        if (negative)
            sum *= -1;

        if (sum < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        if (sum > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        return (int)sum;
    }

    // cool !
    //2^n ===> use each integer 0 to n as mask to build subset
    static public List<List<Integer>> getAllSubsets(int[] array) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();

        int integerUpperLimit = 1 << array.length; // max size of int to generate all ints up to

        //each loop iter adds a subset
        for (int integerCurr = 0; integerCurr < integerUpperLimit; ++integerCurr) {
            List<Integer> subset = new ArrayList<Integer>();

            int i = 0; // reset to index from beginning or array
            int curr = integerCurr; //temp shift variable

            // build the subset
            // got each integer shift through 1's and only add array index if a 1 is found
            while (curr > 0) { // keep shifting the current integer
                if ((curr & 1) > 0) // is current lsb a 1 or 0 ?
                    subset.add(array[i]); // cool if a 1 then that index is added to subset

                // shift int to next 0 or 1 and increment index into array
                curr >>= 1;
                i++;
            }
            result.add(subset); //one subset added
        }
        return result;
    }



//    // 2^n subsets
//    static public ArrayList<ArrayList<Integer>> subsets(int[] array) {
//        if (array == null)
//            return null;
//
//        Arrays.sort(array);
//
//        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
//
//        for (int i = 0; i < array.length; i++) {
//            ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
//
//            //get sets that are already in result
//            for (ArrayList<Integer> a : result) {
//                temp.add(new ArrayList<Integer>(a));
//            }
//
//            //add S[i] to existing sets
//            for (ArrayList<Integer> a : temp) {
//                a.add(array[i]);
//            }
//
//            //add S[i] only as a set
//            ArrayList<Integer> single = new ArrayList<Integer>();
//            single.add(array[i]);
//            temp.add(single);
//
//            result.addAll(temp);
//        }
//
//        //add empty set
//        result.add(new ArrayList<Integer>());
//
//        return result;
//    }

}