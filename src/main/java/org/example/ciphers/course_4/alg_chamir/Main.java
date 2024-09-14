package org.example.ciphers.course_4.alg_chamir;

public class Main {
    public static void main(String[] args) {
        AlgChamir algChamir = new AlgChamir();
        var result = algChamir.proceed();
        System.out.printf("M = %d\n", AlgChamir.M);
        System.out.printf("Result = %d\n", result);
    }
}
