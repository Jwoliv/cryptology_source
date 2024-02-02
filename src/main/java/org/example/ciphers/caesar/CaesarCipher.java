package org.example.ciphers.caesar;

public class CaesarCipher {
    private static final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя ";

    public String encrypt(String plainMsg, int step) {
        step = validateStep(step);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainMsg.length(); i++) {
            char currentChar = plainMsg.charAt(i);
            int indexOfSymbol = ALPHABET.indexOf(currentChar);
            if (indexOfSymbol == -1) {
                sb.append(currentChar);
            } else {
                int newIndex = (indexOfSymbol + step) % ALPHABET.length();
                sb.append(ALPHABET.charAt(newIndex));
            }
        }
        return sb.toString();
    }

    public String decrypt(String encryptedMsg, int step) {
        step = validateStep(step);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encryptedMsg.length(); i++) {
            char currentChar = encryptedMsg.charAt(i);
            int indexOfSymbol = ALPHABET.indexOf(currentChar);
            if (indexOfSymbol == -1) {
                sb.append(currentChar);
            } else {
                int newIndex = (indexOfSymbol - step + ALPHABET.length()) % ALPHABET.length();
                sb.append(ALPHABET.charAt(newIndex));
            }
        }
        return sb.toString();
    }

    private int validateStep(int step) {
        return step % ALPHABET.length();
    }
}
