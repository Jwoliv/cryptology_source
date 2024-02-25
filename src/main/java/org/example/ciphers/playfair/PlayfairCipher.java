package org.example.ciphers.playfair;

import java.util.ArrayList;
import java.util.List;

public class PlayfairCipher {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final List<Character> skipOneElements = List.of('i', 'j');
    private static final Integer SIZE_MATRIX = 5;

    public String encrypt(String key, String plainText) {
        key = key.toLowerCase();
        plainText = plainText.toLowerCase();;
        char[][] matrix = generateMatrix(removeAllRepeatedSymbols(key));
        StringBuilder encryptedText = new StringBuilder();
        for (String el :  prepareBigrams(plainText)) {
            int[] positionFirstChar = findIndex(matrix, el.charAt(0)).clone();
            int[] positionSecondChar = findIndex(matrix, el.charAt(1)).clone();
            processSymbol(positionFirstChar, positionSecondChar, encryptedText, matrix, true);
        }
        return encryptedText.toString();
    }

    public String decrypt(String key, String encryptedText) {
        key = key.toLowerCase();
        char[][] matrix = generateMatrix(removeAllRepeatedSymbols(key));
        StringBuilder decryptedText = new StringBuilder();
        for (String el : prepareBigrams(encryptedText)) {
            int[] positionFirstChar = findIndex(matrix, el.charAt(0)).clone();
            int[] positionSecondChar = findIndex(matrix, el.charAt(1)).clone();
            processSymbol(positionFirstChar, positionSecondChar, decryptedText, matrix, false);
        }
        return decryptedText.toString();
    }

    private void processSymbol(int[] positionFirstChar, int[] positionSecondChar, StringBuilder text, char[][] matrix, Boolean isEncryption) {
        if (positionFirstChar[0] == positionSecondChar[0]) {
            if (isEncryption) {
                increaseIndex(positionFirstChar, 1, positionSecondChar);
            } else {
                decreaseIndex(positionFirstChar, 1, positionSecondChar);
            }
        } else if (positionFirstChar[1] == positionSecondChar[1]) {
            if (isEncryption) {
                increaseIndex(positionFirstChar, 0, positionSecondChar);
            } else {
                decreaseIndex(positionFirstChar, 0, positionSecondChar);
            }
        }
        addSymbolsToResponse(text, matrix, positionFirstChar, positionSecondChar);
    }

    private void decreaseIndex(int[] positionFirstChar, int x, int[] positionSecondChar) {
        positionFirstChar[x]--;
        positionSecondChar[x]--;
        if (positionFirstChar[x] < 0) {
            positionFirstChar[x] = SIZE_MATRIX - 1;
        }
        if (positionSecondChar[x] < 0) {
            positionSecondChar[x] = SIZE_MATRIX - 1;
        }
    }

    private void increaseIndex(int[] positionFirstChar, int x, int[] positionSecondChar) {
        positionFirstChar[x]++;
        positionSecondChar[x]++;
        if (positionFirstChar[x] >= SIZE_MATRIX) {
            positionFirstChar[x] = 0;
        }
        if (positionSecondChar[x] >= SIZE_MATRIX) {
            positionSecondChar[x] = 0;
        }
    }

    private void addSymbolsToResponse(StringBuilder decryptedText, char[][] matrix, int[] positionFirstChar, int[] positionSecondChar) {
        decryptedText.append(matrix[positionFirstChar[0]][positionFirstChar[1]]);
        decryptedText.append(matrix[positionSecondChar[0]][positionSecondChar[1]]);
    }

    private List<String> prepareBigrams(String plainText) {
        List<String> plainTextBigrams = new ArrayList<>();
        int start = 0, end = 2, indexAllowedSymbols = 0;

        List<Character> allowedSymbols = getAllowedSymbols(plainText);

        while (end <= plainText.length()) {
            if (plainText.charAt(start) == plainText.charAt(end - 1)) {
                plainTextBigrams.add(String.valueOf(plainText.charAt(start)).concat(String.valueOf(allowedSymbols.get(indexAllowedSymbols))));
                indexAllowedSymbols++;
                start++;
                end++;
            } else {
                plainTextBigrams.add(plainText.substring(start, end));
                start = end;
                end += 2;
            }
        }

        if (end - plainText.length() == 1) {
            Character symbol = plainText.charAt(plainText.length() - 1);
            Character extraSymbol = allowedSymbols.get(indexAllowedSymbols);
            plainTextBigrams.add(String.valueOf(symbol).concat(String.valueOf(extraSymbol)));
        }

        return plainTextBigrams;
    }

    private List<Character> getAllowedSymbols(String text) {
        List<Character> allowedSymbols = new ArrayList<>(ALPHABET.chars().distinct().mapToObj(e -> (char) e).toList());
        allowedSymbols.removeAll(text.chars().distinct().mapToObj(e -> (char) e).toList());
        return allowedSymbols;
    }

    private List<Character> removeAllRepeatedSymbols(String text) {
        return text.chars().distinct().mapToObj(e -> (char) e).toList();
    }

    private char[][] generateMatrix(List<Character> chars) {
        char[][] matrix = new char[5][5];
        int indexKey = 0, indexAlphabet = 0, indexMatrix = 0;
        List<Character> newRow;
        for (int i = 0; i < SIZE_MATRIX; i++) {
            newRow = new ArrayList<>();
            for (int j = 0; j < SIZE_MATRIX; j++) {
                if (indexKey < chars.size()) {
                    newRow.add(chars.get(indexKey));
                    indexKey++;
                } else {
                    while (chars.contains(ALPHABET.charAt(indexAlphabet))) {
                        indexAlphabet++;
                    }
                    Character itemToMatrix = ALPHABET.charAt(indexAlphabet);
                    if (skipOneElements.contains(itemToMatrix)) {
                        newRow.add(itemToMatrix);
                        indexAlphabet += 2;
                    } else {
                        newRow.add(itemToMatrix);
                        indexAlphabet++;
                    }
                }
            }
            addRowToMatrix(matrix, newRow, indexMatrix);
            indexMatrix++;
        }
        return matrix;
    }

    private void addRowToMatrix(char[][] matrix, List<Character> newRow, int indexMatrix) {
        char[] newChar = new char[5];
        for (int k = 0; k < newRow.toArray().length; k++) {
            newChar[k] = newRow.get(k);
        }
        matrix[indexMatrix] = newChar;
    }

    private int[] findIndex(char[][] matrix, char target) {
        for (int i = 0; i < SIZE_MATRIX; i++) {
            for (int j = 0; j < SIZE_MATRIX; j++) {
                char el = matrix[i][j];
                if (el == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[] {};
    }
}
