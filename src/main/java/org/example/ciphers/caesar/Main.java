package org.example.ciphers.caesar;

import java.util.Scanner;

import static org.example.values.MsgText.PLAIN_TEXT;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("[?] Enter a step: ");
        int step = scanner.nextInt();
        CaesarCipher caesarCipher = new CaesarCipher();
        String encryptedText = caesarCipher.encrypt(PLAIN_TEXT, step);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String plainText = caesarCipher.decrypt(encryptedText, step);
        System.out.printf("[+] Decrypted text: %s%n", plainText);
    }
}