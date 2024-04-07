package org.example.ciphers.gamma_module_k;

public class GammaByModuleK {

    private static final String ALPHABET = "_abcdefghijklmnopqrstuvwxyz";
    private static final Integer ALPHABET_LENGTH = ALPHABET.length();

    public String encrypt(String plainText, String key) {
        StringBuilder fullKey = new StringBuilder();
        do {
            fullKey.append(key);
        } while (plainText.length() > fullKey.length());

        String fullKeyStr = fullKey.substring(0, plainText.length());
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < plainText.length(); i++) {
            int indexText = ALPHABET.indexOf(plainText.charAt(i));
            int indexKey = ALPHABET.indexOf(fullKeyStr.charAt(i));
            String newChar = String.valueOf(ALPHABET.charAt((indexText + indexKey) % ALPHABET_LENGTH));
            encryptedText.append(newChar);
        }
        return encryptedText.toString();
    }

    public String decrypt(String encryptedText, String key) {
        StringBuilder fullKey = new StringBuilder();
        do {
            fullKey.append(key);
        } while (encryptedText.length() > fullKey.length());

        String fullKeyStr = fullKey.substring(0, encryptedText.length());
        StringBuilder plainText = new StringBuilder();

        for (int i = 0; i < encryptedText.length(); i++) {
            int indexText = ALPHABET.indexOf(encryptedText.charAt(i));
            int indexKey = ALPHABET.indexOf(fullKeyStr.charAt(i));
            String newChar = String.valueOf(ALPHABET.charAt((indexText - indexKey + ALPHABET_LENGTH) % ALPHABET_LENGTH));
            plainText.append(newChar);
        }
        return plainText.toString();
    }
}
