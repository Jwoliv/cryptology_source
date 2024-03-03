package org.example.ciphers.vigenere;

import java.util.Scanner;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITHOUT_SPACE;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("[?] Enter a key: ");
        String key = scanner.nextLine();
        VigenereCipher vigenereCipher = new VigenereCipher();
        String encryptedText = vigenereCipher.encrypt(PLAIN_TEXT_ENG_WITHOUT_SPACE, key);
        System.out.printf("[+] Plain text: %s%n", PLAIN_TEXT_ENG_WITHOUT_SPACE);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = vigenereCipher.decrypt(encryptedText, key);
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
