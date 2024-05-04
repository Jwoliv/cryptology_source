package org.example.ciphers.course_work_3;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RoundAlgorithm {

    private static final int[] K1 = {
             1, 15, 9, 2, 21, 8, 19, 27,
             2, 6, 24, 18, 3, 20, 14, 25,
             4, 11, 5, 13, 23, 4, 26, 17,
             8, 28, 12, 10, 1, 22, 16, 7
    };

    private static final int[] M2 = {
            21, 9, 12, 27, 23, 29, 5, 19,
            4, 18, 16, 2, 26, 14, 32, 30,
            25, 1, 22, 13, 7, 18,  6, 20,
            11, 17, 10, 24, 3, 15, 31, 8
    };

    public static void main(String[] args) {
        String text = "0100110001101001011100110110100001100011011010000111010101101011";
        String encryptedText = encrypt(text);
        System.out.printf(" Original text: [%s]\n", text);
        System.out.printf("Encrypted text: [%s]\n", encryptedText);
        String decryptedText = decrypt(encryptedText);
        System.out.printf("Decrypted text: [%s]\n", decryptedText);
        System.out.printf("Decrypted text == Original text: [%b]\n", text.equals(decryptedText));
    }

    public static String encrypt(String text) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < text.length(); i += 32) {
            res.append(proceedBlock(text.substring(i, i + 32)));
        }
        return res.toString();
    }

    public static String proceedBlock(String text) {
        int[] textChars = Arrays.stream(text.split("")).mapToInt(Integer::parseInt).toArray();
        for (int i = 0; i < textChars.length; i++) {
            textChars[i] = (textChars[i] + K1[i]) % 2;
        }
        int[] permutedText = new int[textChars.length];
        for (int i = 0; i < M2.length; i++) {
            permutedText[i] = textChars[M2[i] - 1];
        }
        return Arrays.stream(permutedText)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    public static String decrypt(String text) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < text.length(); i += 32) {
            res.append(proceedBlockReverse(text.substring(i, i + 32)));
        }
        return res.toString();
    }

    public static String proceedBlockReverse(String text) {
        int[] textChars = Arrays.stream(text.split("")).mapToInt(Integer::parseInt).toArray();
        int[] reversedPermutedText = new int[textChars.length];
        for (int i = 0; i < M2.length; i++) {
            reversedPermutedText[M2[i] - 1] = textChars[i];
        }
        for (int i = 0; i < reversedPermutedText.length; i++) {
            reversedPermutedText[i] = (reversedPermutedText[i] - K1[i] + 2) % 2;
        }
        return Arrays.stream(reversedPermutedText)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining()).replace("-", "");
    }
}
