package org.example.ciphers.course_3.caesar;

import java.util.Scanner;

import static org.example.utils.MsgText.PLAIN_TEXT;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("[?] Enter a step: ");
        Integer step = scanner.nextInt();
        CaesarCipher caesarCipher = new CaesarCipher();
        String encryptedText = caesarCipher.proceedCipher(PLAIN_TEXT, step, false);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String plainText = caesarCipher.proceedCipher(encryptedText, step, true);
        System.out.printf("[+] Decrypted text: %s%n", plainText);
    }
}