package org.example;

import org.example.ciphers.CaesarCipher;

import static org.example.values.MsgText.PLAIN_TEXT;

public class Main {
    public static void main(String[] args) {
        int step = 10;
        CaesarCipher caesarCipher = new CaesarCipher();
        String encryptedText = caesarCipher.encrypt(PLAIN_TEXT, step);
        System.out.printf("Encrypted text: %s%n", encryptedText);
        String plainText = caesarCipher.decrypt(encryptedText, step);
        System.out.printf("Decrypted text: %s%n", plainText);
    }
}