package org.example.ciphers.double_permutation;

import java.util.ArrayList;
import java.util.List;

public class DoublePermutation {
    public String encrypt(String plainText, String orderColumns, String orderRows) {
        List<Integer> availableColumns = getMaxColumns(plainText);
        Integer columnsCount = availableColumns.get(availableColumns.size() / 2);
        int rowsCount = plainText.length() / columnsCount;
        int index = 0;
        char[][] matrix = new char[columnsCount][rowsCount];
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                matrix[i][j] = plainText.charAt(index);
                index++;
            }
        }

        List<Integer> orderColumnsItems = orderColumns.chars().mapToObj(ch -> ch - '0').toList();
        List<Integer> orderRowsItems = orderRows.chars().mapToObj(ch -> ch - '0').toList();

        int newIndexMatrix1 = 0;
        char[][] newMatrix1 = new char[columnsCount][rowsCount];
        for (Integer orderRowItem: orderRowsItems) {
            newMatrix1[newIndexMatrix1] = matrix[orderRowItem];
            newIndexMatrix1++;
        }

        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < rowsCount; i++) {
            for (Integer orderColumnItem: orderColumnsItems) {
                encryptedText.append(newMatrix1[orderColumnItem][i]);
            }
        }
        return encryptedText.toString();
    }


    public String decrypt(String encryptedText, String orderColumns, String orderRows) {
        return null;
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
