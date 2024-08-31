package org.example.ciphers.course_3.hill;

import java.util.ArrayList;
import java.util.List;

public class HillCipher {
    private static final String ALPHABET = "_ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int[][] matrix = new int[][] {
            {4, 5},
            {3, 4}
    };

    public String encrypt(String plainText) {
        List<int[]> bigrams = generateBigrams(plainText, generateIndexes(plainText));
        return processBigrams(bigrams, matrix);
    }

    public String decrypt(String encryptedText) {
        int[][] inverseMatrix = getInverseMatrix();
        List<int[]> bigrams = generateBigrams(encryptedText, generateIndexes(encryptedText));
        return processBigrams(bigrams, inverseMatrix);
    }

    private String processBigrams(List<int[]> bigrams, int[][] matrix) {
        StringBuilder result = new StringBuilder();
        int lengthAlphabet = ALPHABET.length();
        for (int[] bigram : bigrams) {
            int firstIndex = (((bigram[0] * matrix[0][0]) + (bigram[1] * matrix[1][0])) % lengthAlphabet + lengthAlphabet) % lengthAlphabet;
            int secondIndex = (((bigram[0] * matrix[0][1]) + (bigram[1] * matrix[1][1])) % lengthAlphabet + lengthAlphabet) % lengthAlphabet;
            String firstElement = String.valueOf(ALPHABET.charAt(firstIndex));
            String secondElement = String.valueOf(ALPHABET.charAt(secondIndex));
            result.append(firstElement).append(secondElement);
        }
        return result.toString();
    }

    private List<int[]> generateBigrams(String plainText, int[] indexes) {
        List<int[]> bigrams = new ArrayList<>();
        for (int i = 0; i < indexes.length; i += 2) {
            bigrams.add(new int[] {
                    indexes[i],
                    i + 1 < indexes.length ? indexes[i + 1] : getUniqueIndex(plainText)
            });
        }
        return bigrams;
    }

    private int[] generateIndexes(String plainText) {
        int[] indexes = new int[plainText.length()];
        int indexIndexes = 0;
        for (Character el: plainText.toCharArray()) {
            indexes[indexIndexes] = ALPHABET.indexOf(el);
            indexIndexes++;
        }
        return indexes;
    }

    private int getUniqueIndex(String plainText) {
        List<Character> alphabetChars = new ArrayList<>(getUniqueCharacters(ALPHABET));
        alphabetChars.removeAll(getUniqueCharacters(plainText));
        return alphabetChars.indexOf(alphabetChars.getFirst());
    }

    private List<Character> getUniqueCharacters(String text) {
        return text.chars().distinct().mapToObj(x -> (char) x).toList();
    }

    public int[][] getInverseMatrix() {
        int determinant = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        int inverseDeterminant = (int) (1.0 / determinant);
        return new int[][] {
                {  matrix[1][1] * inverseDeterminant, -matrix[0][1] * inverseDeterminant},
                { -matrix[1][0] * inverseDeterminant,  matrix[0][0] * inverseDeterminant}
        };
    }
}
