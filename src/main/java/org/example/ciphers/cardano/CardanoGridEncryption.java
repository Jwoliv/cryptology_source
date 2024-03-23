
package org.example.ciphers.cardano;
public class CardanoGridEncryption {
    public char[][] createCardanoGrid(String text) {
        int size = calculateGridSize(text.length());
        char[][] grid = new char[size][size];
        int index = 0;
        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                grid[row][col] = (index < text.length()) ? text.charAt(index) : '*';
                index++;
            }
        }
        return grid;
    }

    private int calculateGridSize(int textLength) {
        int sqrt = (int) Math.ceil(Math.sqrt(textLength));
        return (sqrt % 2 == 0) ? sqrt + 1 : sqrt;
    }

    public String encryptText(String text) {
        char[][] grid = createCardanoGrid(text);
        StringBuilder encryptedText = new StringBuilder();
        int size = grid.length;
        for (int col = 0; col < size; col++) {
            for (int row = size - 1; row >= 0; row--) {
                if (grid[row][col] != '*') {
                    encryptedText.append(grid[row][col]);
                }
            }
        }
        return encryptedText.toString();
    }

    public String decryptText(String encryptedText) {
        char[][] grid = createCardanoGrid(encryptedText);
        StringBuilder decryptedText = new StringBuilder();
        int size = grid.length;
        for (int col = 0; col < size; col++) {
            for (int row = size - 1; row >= 0; row--) {
                if (grid[row][col] != '*') {
                    decryptedText.append(grid[row][col]);
                }
            }
        }
        return decryptedText.toString();
    }


}
