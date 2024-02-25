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
        char[][] matrix = generateMatrix(getListWithoutDuplicates(key));
        StringBuilder encryptedText = new StringBuilder();
        prepareBigrams(plainText).forEach(x -> encryptSymbol(x, encryptedText, matrix));
        return encryptedText.toString();
    }

    public String decrypt(String key, String encryptedText) {
        key = key.toLowerCase();
        encryptedText = encryptedText.toLowerCase();;
        char[][] matrix = generateMatrix(getListWithoutDuplicates(key));
        StringBuilder decryptedText = new StringBuilder();
        prepareBigrams(encryptedText).forEach(x -> decryptSymbol(x, decryptedText, matrix));
        return decryptedText.toString();
    }

    private void encryptSymbol(String bigram, StringBuilder text, char[][] matrix) {
        int[] positionFirstChar = findIndex(matrix, bigram.charAt(0)).clone();
        int[] positionSecondChar = findIndex(matrix, bigram.charAt(1)).clone();
        if (positionFirstChar[0] == positionSecondChar[0]) {
            increaseIndex(1, positionFirstChar, positionSecondChar);
            addSymbolsFromSameLine(text, positionFirstChar, positionSecondChar, matrix);
        } else if (positionFirstChar[1] == positionSecondChar[1]) {
            increaseIndex(0, positionFirstChar, positionSecondChar);
            addSymbolsFromSameLine(text, positionFirstChar, positionSecondChar, matrix);
        } else {
            addSymbolsFromDifferLines(text, positionFirstChar, positionSecondChar, matrix);
        }
    }

    private void decryptSymbol(String bigram, StringBuilder text, char[][] matrix) {
        int[] positionFirstChar = findIndex(matrix, bigram.charAt(0)).clone();
        int[] positionSecondChar = findIndex(matrix, bigram.charAt(1)).clone();
        if (positionFirstChar[0] == positionSecondChar[0]) {
            decreaseIndex(1, positionFirstChar, positionSecondChar);
            addSymbolsFromSameLine(text, positionFirstChar, positionSecondChar, matrix);
        } else if (positionFirstChar[1] == positionSecondChar[1]) {
            decreaseIndex(0, positionFirstChar, positionSecondChar);
            addSymbolsFromSameLine(text, positionFirstChar, positionSecondChar, matrix);
        } else {
            addSymbolsFromDifferLines(text, positionFirstChar, positionSecondChar, matrix);
        }
    }

    private void decreaseIndex(int x, int[] positionFirstChar, int[] positionSecondChar) {
        validatePositionDecrypt(x, positionFirstChar);
        validatePositionDecrypt(x, positionSecondChar);
    }

    private void validatePositionDecrypt(int x, int[] positionChar) {
        positionChar[x]--;
        if (positionChar[x] < 0) {
            positionChar[x] = SIZE_MATRIX - 1;
        }
    }

    private void increaseIndex(int x, int[] positionFirstChar, int[] positionSecondChar) {
        validatePositionEncrypt(x, positionFirstChar);
        validatePositionEncrypt(x, positionSecondChar);
    }

    private void validatePositionEncrypt(int x, int[] positionChar) {
        positionChar[x]++;
        if (positionChar[x] >= SIZE_MATRIX) {
            positionChar[x] = 0;
        }
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
        allowedSymbols.removeAll(getListWithoutDuplicates(text));
        return allowedSymbols;
    }

    private List<Character> getListWithoutDuplicates(String text) {
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
        for (int i = 0; i < newRow.toArray().length; i++) {
            newChar[i] = newRow.get(i);
        }
        matrix[indexMatrix] = newChar;
    }

    private int[] findIndex(char[][] matrix, char target) {
        for (int i = 0; i < SIZE_MATRIX; i++) {
            for (int j = 0; j < SIZE_MATRIX; j++) {
                char element = matrix[i][j];
                if (element == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[] {};
    }


    public void addSymbolsFromSameLine(StringBuilder text, int[] positionFirstChar, int[] positionSecondChar, char[][] matrix) {
        text.append(matrix[positionFirstChar[0]][positionFirstChar[1]]);
        text.append(matrix[positionSecondChar[0]][positionSecondChar[1]]);
    }

    public void addSymbolsFromDifferLines(StringBuilder text, int[] positionFirstChar, int[] positionSecondChar, char[][] matrix) {
        text.append(matrix[positionFirstChar[0]][positionSecondChar[1]]);
        text.append(matrix[positionSecondChar[0]][positionFirstChar[1]]);
    }

}
