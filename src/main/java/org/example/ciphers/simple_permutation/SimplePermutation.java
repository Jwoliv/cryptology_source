package org.example.ciphers.simple_permutation;

import java.util.ArrayList;
import java.util.List;

public class SimplePermutation {
    public String encrypt(String plainText) {
        plainText = plainText.replaceAll(" ", "");
        List<Integer> availableColumnsMatrix = getMaxColumns(plainText);
        int columnsMatrix = availableColumnsMatrix.get(availableColumnsMatrix.size() / 2);
        int rowMatrix = plainText.length() / columnsMatrix;
        char[][] matrix = new char[rowMatrix][columnsMatrix];
        int indexPlainText = 0;
        for (int i = 0; i < rowMatrix; i++) {
            for (int j = 0; j < columnsMatrix; j++) {
                matrix[i][j] = plainText.charAt(indexPlainText++);
            }
        }
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < columnsMatrix - 1; i++) {
            for (int j = 0; j < rowMatrix; j++) {
                encryptedText.append(matrix[j][i]);
            }
            if (i != columnsMatrix - 1) {
                encryptedText.append("_");
            }
        }
        System.out.println(encryptedText);
        return encryptedText.toString();
    }

    public String decrypt(String encryptedText) {
        int columnsMatrix = encryptedText.indexOf("_") - 1;
        int rowMatrix = encryptedText.length() / columnsMatrix;
        char[][] matrix = new char[rowMatrix][columnsMatrix];
        int indexEncryptedText = 0;

        for (int i = 0; i < columnsMatrix; i++) {
            for (int j = 0; j < rowMatrix; j++) {
                matrix[j][i] = encryptedText.charAt(indexEncryptedText++);
            }
        }

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < rowMatrix; i++) {
            for (int j = 0; j < columnsMatrix; j++) {
                plaintext.append(matrix[i][j]);
            }
        }

        return plaintext.toString().replaceAll("_", "");
    }


    public List<Integer> getMaxColumns(String text) {
        List<Integer> maxColumnsItems = new ArrayList<>();
        for (int i = 1; i < text.length() - 1; i++) {
            if (text.length() % i == 0) {
                maxColumnsItems.add(i);
            }
        }
        return maxColumnsItems;
    }

}
