package org.example.ciphers.playfair;

public class Main {
    public static void main(String[] args) {
        String key = "commander";
        String plainText = "lishchukbohdanstudentuniversity";
        System.out.println(plainText);
        PlayfairCipher playfairCipher = new PlayfairCipher();
        String encryptedText = playfairCipher.encrypt(key, plainText);
        System.out.println(encryptedText);
        String result = playfairCipher.decrypt(key, encryptedText);
        System.out.println(result);
    }
}
