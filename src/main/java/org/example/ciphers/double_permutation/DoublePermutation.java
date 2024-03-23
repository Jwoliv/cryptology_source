package org.example.ciphers.double_permutation;

import java.util.Arrays;

public class DoublePermutation {
    public String encrypt(String plaintext, int[] columnKey, int[] rowKey) {
        return getResult(plaintext, columnKey, rowKey);
    }

    public String decrypt(String ciphertext, int[] columnKey, int[] rowKey) {
        return getResult(ciphertext, columnKey, rowKey);
    }

    private String getResult(String ciphertext, int[] columnKey, int[] rowKey) {
        int rows = rowKey.length;
        int cols = columnKey.length;
        char[][] grid = new char[rows][cols];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = ciphertext.charAt(index++);
            }
        }

        char[][] tempGrid = new char[rows][cols];
        for (int i = 0; i < cols; i++) {
            int colIndex = columnKey[i] - 1;
            for (int j = 0; j < rows; j++) {
                tempGrid[j][i] = grid[j][colIndex];
            }
        }

        char[][] encryptedGrid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            int rowIndex = rowKey[i] - 1;
            encryptedGrid[i] = Arrays.copyOf(tempGrid[rowIndex], cols);
        }

        StringBuilder plaintext = new StringBuilder();
        for (char[] row : encryptedGrid) {
            plaintext.append(row);
        }
        return plaintext.toString();
    }
}
