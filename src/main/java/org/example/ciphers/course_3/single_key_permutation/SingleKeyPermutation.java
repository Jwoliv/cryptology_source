package org.example.ciphers.course_3.single_key_permutation;

import java.util.Arrays;

public class SingleKeyPermutation {
    private static final String ALPHABET = "_abcdefghijklmnopqrstuvwxyz";

    public String encrypt(String plainText, String key) {
        int[] sequenceKey = new int[key.length()];
        int indexSeq = 1;
        for (char symbolAlphabet: ALPHABET.toCharArray()) {
            if (key.contains(String.valueOf(symbolAlphabet))) {
                int indexSequence = key.indexOf(symbolAlphabet);
                sequenceKey[indexSequence] = indexSeq;
                indexSeq++;
            }
        }

        StringBuilder sequenceKeyStr = new StringBuilder();
        Arrays.stream(sequenceKey).forEach(sequenceKeyStr::append);

        int columnsCount = key.length();
        int rowsCount = plainText.length() / columnsCount;

        int index = 0;
        char[][] matrix = new char[columnsCount][rowsCount];
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                matrix[i][j] = plainText.charAt(index);
                index++;
            }
        }

        StringBuilder encryptedText = new StringBuilder();
        for (int i = 1; i <= key.length(); i++) {
            int indexColumn = sequenceKeyStr.indexOf(String.valueOf(i));
            for (int j = 0; j < rowsCount; j++) {
                encryptedText.append(matrix[indexColumn][j]);
            }
        }

        return encryptedText.toString();
    }

    public String decrypt(String encryptedText, String key) {
        int[] sequenceKey = new int[key.length()];
        int indexSeq = 1;
        for (char symbolAlphabet : ALPHABET.toCharArray()) {
            if (key.contains(String.valueOf(symbolAlphabet))) {
                int indexSequence = key.indexOf(symbolAlphabet);
                sequenceKey[indexSequence] = indexSeq;
                indexSeq++;
            }
        }

        StringBuilder sequenceKeyStr = new StringBuilder();
        Arrays.stream(sequenceKey).forEach(sequenceKeyStr::append);

        int columnsCount = key.length();
        int rowsCount = encryptedText.length() / columnsCount;

        char[][] matrix = new char[columnsCount][rowsCount];
        int index = 0;

        for (int i = 1; i <= key.length(); i++) {
            int indexColumn = sequenceKeyStr.indexOf(String.valueOf(i));
            for (int j = 0; j < rowsCount; j++) {
                matrix[indexColumn][j] = encryptedText.charAt(index);
                index++;
            }
        }

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                plaintext.append(matrix[i][j]);
            }
        }

        return plaintext.toString();
    }

}
