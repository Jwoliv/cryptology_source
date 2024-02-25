package org.example.ciphers.playfair;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("[?] Enter a key: ");
//        String key = scanner.nextLine();
        String key = "commander";
        PlayfairCipher playfairCipher = new PlayfairCipher();
        String encryptedText = playfairCipher.encrypt(key, "studentq");
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = playfairCipher.decrypt(key, encryptedText);
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
