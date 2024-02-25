package org.example.ciphers.playfair;

import java.util.ArrayList;
import java.util.List;

public class PlayfairCipher {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final List<Character> skipOneElements = List.of('i', 'j');
    private static final Integer SIZE_MATRIX = 5;

    public String encrypt(String key, String plainText) {
        key = key.toLowerCase();
        plainText = plainText.toLowerCase();
        char[][] matrix = new char[5][5];
        List<Character> newRow;
        List<Character> chars = key.chars().distinct().mapToObj(e->(char)e).toList();
        int indexKey = 0;
        int indexAlphabet = 0;
        int indexMatrix = 0;
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
            char[] newChar = new char[5];
            for (int k = 0; k < newRow.toArray().length; k++) {
                newChar[k] = newRow.get(k);
            }
            matrix[indexMatrix] = newChar;
            indexMatrix++;
        }

        List<String> plainTextBigrams = new ArrayList<>();

        int start = 0;
        int end = 2;

        List<Character> allowedSymbols = new ArrayList<>(ALPHABET.chars().distinct().mapToObj(e -> (char) e).toList());
        allowedSymbols.removeAll(plainText.chars().distinct().mapToObj(e->(char)e).toList());
        int indexAllowedSymbols = 0;

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

        StringBuilder stringBuilder = new StringBuilder();

        for (String el : plainTextBigrams) {
            char firstChar = el.charAt(0);
            char secondChar = el.charAt(1);

            int[] positionFirstChar = findIndex(matrix, firstChar).clone();
            int[] positionSecondChar = findIndex(matrix, secondChar).clone();

            if (positionFirstChar[0] == positionSecondChar[0]) {
                positionFirstChar[1]++;
                positionSecondChar[1]++;
                if (positionFirstChar[1] >= SIZE_MATRIX) {
                    positionFirstChar[1] = 0;
                }
                if (positionSecondChar[1] >= SIZE_MATRIX) {
                    positionSecondChar[1] = 0;
                }
                stringBuilder.append(matrix[positionFirstChar[0]][positionFirstChar[1]]);
                stringBuilder.append(matrix[positionSecondChar[0]][positionSecondChar[1]]);
            } else if (positionFirstChar[1] == positionSecondChar[1]) {
                positionFirstChar[0]++;
                positionSecondChar[0]++;
                if (positionFirstChar[0] >= SIZE_MATRIX) {
                    positionFirstChar[0] = 0;
                }
                if (positionSecondChar[0] >= SIZE_MATRIX) {
                    positionSecondChar[0] = 0;
                }
                stringBuilder.append(matrix[positionFirstChar[0]][positionFirstChar[1]]);
                stringBuilder.append(matrix[positionSecondChar[0]][positionSecondChar[1]]);
            } else {
                stringBuilder.append(matrix[positionFirstChar[0]][positionSecondChar[1]]);
                stringBuilder.append(matrix[positionSecondChar[0]][positionFirstChar[1]]);
            }
        }

        System.out.println(plainText);
        System.out.println(stringBuilder);


        // decrypt
        plainTextBigrams = new ArrayList<>();
        indexAllowedSymbols = 0;
        start = 0;
        end = 2;
        while (end <= stringBuilder.length()) {
            if (stringBuilder.charAt(start) == stringBuilder.charAt(end - 1)) {
                plainTextBigrams.add(String.valueOf(stringBuilder.charAt(start)).concat(String.valueOf(allowedSymbols.get(indexAllowedSymbols))));
                indexAllowedSymbols++;
                start++;
                end++;
            } else {
                plainTextBigrams.add(stringBuilder.substring(start, end));
                start = end;
                end += 2;
            }
        }


        StringBuilder decryptText = new StringBuilder();

        for (String el : plainTextBigrams) {
            char firstChar = el.charAt(0);
            char secondChar = el.charAt(1);

            int[] positionFirstChar = findIndex(matrix, firstChar).clone();
            int[] positionSecondChar = findIndex(matrix, secondChar).clone();

            if (positionFirstChar[0] == positionSecondChar[0]) {
                positionFirstChar[1]--;
                positionSecondChar[1]--;
                if (positionFirstChar[1] < 0) {
                    positionFirstChar[1] = SIZE_MATRIX - 1;
                }
                if (positionSecondChar[1] < 0) {
                    positionSecondChar[1] = SIZE_MATRIX - 1;
                }
                decryptText.append(matrix[positionFirstChar[0]][positionFirstChar[1]]);
                decryptText.append(matrix[positionSecondChar[0]][positionSecondChar[1]]);
            } else if (positionFirstChar[1] == positionSecondChar[1]) {
                positionFirstChar[0]--;
                positionSecondChar[0]--;
                if (positionFirstChar[0] < 0) {
                    positionFirstChar[0] = SIZE_MATRIX - 1;
                }
                if (positionSecondChar[0] < 0) {
                    positionSecondChar[0] = SIZE_MATRIX - 1;
                }
                decryptText.append(matrix[positionFirstChar[0]][positionFirstChar[1]]);
                decryptText.append(matrix[positionSecondChar[0]][positionSecondChar[1]]);
            } else {
                decryptText.append(matrix[positionFirstChar[0]][positionSecondChar[1]]);
                decryptText.append(matrix[positionSecondChar[0]][positionFirstChar[1]]);
            }
        }

        System.out.println(decryptText.substring(0, plainText.length()));

        return null;
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
