package org.example.ciphers.course_work_3;

import java.util.ArrayList;
import java.util.List;

public class KeyAlgorithm {
    private static final int VALID_LENGTH_KEY = 64;
    private static final int HALF_KEY_LENGTH = VALID_LENGTH_KEY / 2;

    private static final List<Integer> K2 = List.of(
            23, 11, 31,  3, 14, 28, 29, 18,
             8, 17,  2, 25,  5, 30,  9, 32,
             1, 22, 12, 21, 16,  6, 20, 10,
            15, 13, 24,  4, 27, 19,  7, 26
    );

    public static void main(String[] args) {
        String key = "0100110001101001011100110110100001100011011010000111010101101011";
        System.out.println(proceedKeyByRound(key, 0));
        System.out.println(proceedKeyByRound(key, 1));
    }

    public static String proceedKeyByRound(String key, int round) {
        if (VALID_LENGTH_KEY != key.length()) return null;
        String leftHalf = key.substring(0, HALF_KEY_LENGTH);
        String rightHalf = key.substring(HALF_KEY_LENGTH);
        return round % 2 == 0
                ? proceedRightHalf(rightHalf)
                : proceedLeftHalf(leftHalf, rightHalf);
    }

    private static String proceedRightHalf(String rightHalf) {
        List<String> result = new ArrayList<>(30);
        for (int i = 0; i < K2.size(); i++) {
            String element = String.valueOf(rightHalf.charAt(i));
            result.add(element);
        }
        return String.join("", result);
    }


    private static String proceedLeftHalf(String leftHalf, String rightHalf) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < HALF_KEY_LENGTH; i++) {
            int val1 = Integer.parseInt(String.valueOf(leftHalf.charAt(i)));
            int val2 = Integer.parseInt(String.valueOf(rightHalf.charAt(i)));
            result.append(val1 ^ val2);
        }
        return result.toString();
    }

}
