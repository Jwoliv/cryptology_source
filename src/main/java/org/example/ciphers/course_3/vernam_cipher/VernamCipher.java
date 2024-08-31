package org.example.ciphers.course_3.vernam_cipher;

import java.nio.charset.StandardCharsets;

public class VernamCipher {
    private static final String ALPHABET = "_abcdefghijklmnopqrstuvwxyz";
    private static final Integer ALPHABET_LENGTH = ALPHABET.length();

    public String encrypt(String plainText, String key) {
        return process(plainText, key);
    }

    public String decrypt(String encryptedText, String key) {
        return process(encryptedText, key);
    }

    private String process(String plainText, String key) {
        String fullKeyStr = generateFullKey(plainText, key);
        byte[] textBytes = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = fullKeyStr.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[textBytes.length];
        for (int i = 0; i < textBytes.length; i++) {
            result[i] = (byte) (textBytes[i] ^ keyBytes[i]);
        }
        return new String(result, StandardCharsets.UTF_8);
    }

    private String generateFullKey(String plainText, String key) {
        StringBuilder fullKey = new StringBuilder();
        do {
            fullKey.append(key);
        } while (plainText.length() > fullKey.length());

        return fullKey.substring(0, plainText.length());
    }
}
