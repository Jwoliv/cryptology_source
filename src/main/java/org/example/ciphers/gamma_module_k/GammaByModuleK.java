package org.example.ciphers.gamma_module_k;

public class GammaByModuleK {

    private static final String ALPHABET = "_abcdefghijklmnopqrstuvwxyz";
    private static final Integer ALPHABET_LENGTH = ALPHABET.length();

    public String encrypt(String plainText, String key) {
        String fullKeyStr = generateFullKey(plainText, key);
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            int indexText = getIndexFromAlphabet(plainText, i);
            int indexKey = getIndexFromAlphabet(fullKeyStr, i);
            addSymbolToResult(indexText + indexKey, encryptedText);
        }
        return encryptedText.toString();
    }

    public String decrypt(String encryptedText, String key) {
        String fullKeyStr = generateFullKey(encryptedText, key);
        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            int indexText = getIndexFromAlphabet(encryptedText, i);
            int indexKey = getIndexFromAlphabet(fullKeyStr, i);
            addSymbolToResult(indexText - indexKey + ALPHABET_LENGTH, plainText);
        }
        return plainText.toString();
    }

    private int getIndexFromAlphabet(String plainText, int i) {
        return ALPHABET.indexOf(plainText.charAt(i));
    }

    private void addSymbolToResult(int index, StringBuilder plainText) {
        String newChar = String.valueOf(ALPHABET.charAt(index % ALPHABET_LENGTH));
        plainText.append(newChar);
    }

    private String generateFullKey(String plainText, String key) {
        StringBuilder fullKey = new StringBuilder();
        do {
            fullKey.append(key);
        } while (plainText.length() > fullKey.length());

        return fullKey.substring(0, plainText.length());
    }
}
