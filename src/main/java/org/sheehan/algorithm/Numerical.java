package org.sheehan.algorithm;

import java.util.Collections;
import java.util.List;

/**
 * Created by bob on 2/27/16.
 */
public class Numerical {

    static public String countAndSay(String s){

        char arr[] = s.toCharArray();

        StringBuilder sb = new StringBuilder();
        int cnt = 1;
        int i = 0;
        for ( i=0; i < s.length()-1; ++i){
            if (arr[i] != arr[i+1]){
                if (cnt != 0){
                    sb.append(String.valueOf(cnt));
                    sb.append(arr[i]);
                }
                cnt = 1;
            } else {
                cnt++;
            }
        }
        if (i < s.length()){
            sb.append(String.valueOf(cnt));
            sb.append(arr[i]);

        }
        return sb.toString();
    }

    //Euclids algorithm b-r-a-b-a
    static public int gcd(int a, int b){
        while (b!= 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    static void getFactors(int n, List<Integer> factors){
        int upperlimit = (int)(Math.sqrt(n));
        for(int i=1;i <= upperlimit; i++){
            if(n%i == 0){
                factors.add(i);
                if(i != n/i){
                    factors.add(n/i);
                }
            }
        }
        Collections.sort(factors);
    }

    public static void getPrimes(int n) {

        // initially assume all integers are prime
        boolean[] isPrime = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        // mark non-primes <= N using Sieve of Eratosthenes
        int upperlimit = (int)(Math.sqrt(n));
        for (int i = 2; i <= upperlimit; i++) {

            // if i is prime, then mark multiples of i as nonprime
            // suffices to consider multiples i, i+1, ..., N/i
            // start marking not prime from i*i ! e.g.  3 -> 9, 12, 15, 18
            if (isPrime[i]) {
                for (int j = i; i*j <= n; j++) {
                    isPrime[i*j] = false;
                }
            }
        }

        // count primes
        int primes = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes++;
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println("The number of primes <= " + n + " is " + primes);
    }
}
