package org.sheehan.algorithm;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: bsheehan
 * Date: 6/27/14
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class Strings {

    public static String reverse(String str) {
        char[] buffer = str.toCharArray();
        return reverse(buffer, 0, buffer.length - 1);
    }

    public static String reverse(char[] buffer, int start, int end) {

        final int length = end - start + 1;
        final int pivot = start + length / 2;

        for (int i = start, cnt = 0; i < pivot; ++i, ++cnt) {
            char c = buffer[i];
            buffer[i] = buffer[end - cnt];
            buffer[end - cnt] = c;
        }
        return new String(buffer);
    }

    static String reverseWords(String str) {
        int start = 0;
        int end = 0;
        char[] buffer = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (str.toCharArray()[i] == ' ') {
                end = i - 1;
                reverse(buffer, start, end);
                start = end + 2;
            } else if (i == str.length() - 1) {
                end = i;
                reverse(buffer, start, end);
            }
        }
        return new String(buffer);
    }


    public static String reverseRecursively(String str) {

        //base case to handle one char string and empty string
        if (str.length() <= 1) {
            return str;
        }

        return reverseRecursively(str.substring(1)) + str.charAt(0);
    }


    public static void getPermutations(String prefix, String str, Set<String> cache) {
        //System.out.println("\tpermutation pre:" + prefix + " str:" + str + " level:" + level);
        int n = str.length();
        if (n == 0) {
            //System.out.println("\tEND permutation pre:" + prefix + " str:" + str + " level:" + level);
            //System.out.println();
            //System.out.println(prefix);
            cache.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                //System.out.println("\t\tloop in  i:" + i + " pre:" + prefix + " str:" + str + " level:" + level);
                //String prefix2 = prefix + str.charAt(i);
                //String str2 = str.substring(0, i) + str.substring(i + 1, n);
                //System.out.println("\t\tloop out i:" + i + " pre:" + prefix2 + " str:" + str2 + " level:" + level);

                getPermutations(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), cache);
            }
        }
    }


    // start with k = 0
    //  swap i and k
    // inorder recursion k+1
    // swap k with i
    public static void getPermutations2(java.util.List<Character> arr, int k, Set<String> cache) {

        //loop of recursions !
        for (int i = k; i < arr.size(); i++) {
            java.util.Collections.swap(arr, i, k);
            getPermutations2(arr, k + 1, cache);
            java.util.Collections.swap(arr, k, i);
        }

        // when we iterate to the end for a given recursion we have a permutation !
        if (k == arr.size() - 1) {
            Character[] cArr = (Character[]) arr.toArray(new Character[0]);
            char str[] = new char[cArr.length];
            int i = 0;
            for (Character c : cArr)
                str[i++] = c;
            cache.add(new String(str));
            //System.out.println(java.util.Arrays.toString(arr.toArray()));
        }
    }

    // number of unique substrings is n(n+1)/2
    public static void getSubstrings(String s, Set<String> cache) {
        for (int i = 0; i < s.length(); ++i)
            for (int j = i; j < s.length(); ++j)
                cache.add(substring(s, i, j));

    }

    private static String substring(String s, int i, int j) {
        StringBuffer buffer = new StringBuffer();

        for (int k = i; k <= j; k++)
            buffer.append(s.charAt(k));

        return buffer.toString();
    }

    public static int findSubstring(String s1, String s2) {
        boolean found = true;
        for (int i = 0; i < s1.length(); ++i) {
            found = true;
            for (int j = 0; j < s2.length(); ++j) {
                if (s1.charAt(i + j) != s2.charAt(j)) {
                    found = false;
                    break;
                }
            }
            if (found)
                return i;
        }
        return -1;
    }

    public static int binStr2Int(String s) {

        int length = s.length();

        int value = 0;
        for (int i=length-1, cnt = 0; i>=0; --i, ++cnt){

            // get char
            char c = s.charAt(i);

            //convert to int (0 or 1)
            int bit = (int)(c-'0');

            // multiply by power of 2
            int mult = 1 << cnt;
            value += bit * mult;
        }

        return value;
    }

    public static int binStr2Int2(String str) {
        final int BASE = 2;

        int sum = 0;
        int mult = 1;

        for (int pos = str.length() - 1; pos >= 0; pos--) {
            sum += (str.charAt(pos) - '0') * mult;
            mult *= BASE;
        }


        return sum;
    }

    public static int decStr2Int(String str) {
        final int BASE = 10;

        int limit = 0;
        boolean negative = false;
        if (str.charAt(0) == '-') {
            negative = true;
            limit = 1;
        }

        int sum = 0;
        int mult = 1;

        for (int pos = str.length() - 1; pos >= limit; pos--) {
            sum += (str.charAt(pos) - '0') * mult;
            mult *= BASE;
        }

        if (negative)
            sum *= -1;

        return sum;
    }

    public static String decInt2Str(int n) {

        StringBuffer buffer = new StringBuffer();

        int number = n;
        if (number < 0) {
            buffer.append("-");
            number *= -1;
        }

        // figure out the length of the number
        int length = 1;
        int mult = 1;
        while (number/mult != 0) {
            mult *= 10;
            length++;
        }

        // starting at LEFT MSB end (using calculated length)
        // break off each digit and add to string buffer
        mult = 1;
        for (int i = 0; i < length; ++i) {

            int val = number / (int) Math.pow(10, length -1 - i);

            // shave off the 10's position off leftish sub int
            val = val % 10;

            //convert digit to char
            buffer.append((char) (val + '0')); //digit to char
        }

       return buffer.toString();
    }

    public static Character getFirstNonRepeatingChar(String str) {
        char[] chars = str.toCharArray();

        Map<Character, Integer> map = new HashMap<>();
        for (char c : chars) {
            if (map.get(c) == null)
                map.put(c, 1);
            else {
                map.replace(c, map.get(c) + 1);
            }
        }

        for (char c : chars) {
            if (map.get(c) == 1)
                return c;
        }

        return null;
    }

    // brute force
    public static Set<Character> findDuplicatesBrute(String str) {

        char[] chars = str.toCharArray();

        Set<Character> duplicates = new HashSet<Character>();

        for (int i = 0; i < str.length(); ++i) {
            for (int j = 0; j < str.length(); ++j) {
                if (i != j && chars[i] == chars[j]) {
                    duplicates.add(chars[i]);
                }
            }
        }

        return duplicates;
    }

    //ASCII
    public static Set<Character> findDuplicates(String str) {

        char[] chars = str.toCharArray();

        int checker = 0; //init or use array of 256 for ASCII

        Set<Character> duplicates = new HashSet<Character>();

        for (char s : chars) {
            int mask = 1 << (s - 'a');
            if ((checker & mask) > 0) {
                duplicates.add(s);
            } else {
                checker |= mask;
            }
        }
        return duplicates;
    }

    //ASCII
    public static Set<Character> findDuplicatesSet(String str) {
        char[] chars = str.toCharArray();

        //List inputList = Arrays.asList(str);
        //Set inputSet = new HashSet(inputList);

        Set<Character> duplicates = new HashSet<Character>();
        Set<Character> set1 = new HashSet<>();
        for (char c : chars) {
            if (!set1.add(c))
                duplicates.add(c);
        }

        return duplicates;
    }

    // removes duplicates
    // reset array skipping dupes
    public static String removeDuplicates(String str) {
        char[] chars = str.toCharArray();

        int checker = 0; //init

        int dst = 0;
        for (int i = 0; i < chars.length; ++i) {
            int mask = 1 << (chars[i] - 'a');
            // not a duplicate so remove from array and increment
            if ((checker & mask) == 0) {
                chars[dst++] = chars[i];
            }
            checker |= mask;
        }
        chars[dst] = 0;

        return new String(chars, 0, dst);
    }

    public static boolean isPalindrome(String str) {
        if (reverse(str).equals(str))
            return true;
        return false;
    }

    public static boolean isPalindrome2(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length/2; ++i){
            if (chars[i] != chars[chars.length-1-i])
                return false;
        }

        return true;
    }

    public static boolean isRotation(String str1, String str2) {
        if (str1.length() != str2.length())
            return false;

        if ((str1 + str1).contains(str2))
            return true;

        return false;
    }
}
