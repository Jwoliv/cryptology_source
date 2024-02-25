package org.example.ciphers.playfair;

public class Main {
    public static void main(String[] args) {
        String key = "commander";
        PlayfairCipher playfairCipher = new PlayfairCipher();
        String encryptedText = playfairCipher.encrypt(key, "student");
    }
}
