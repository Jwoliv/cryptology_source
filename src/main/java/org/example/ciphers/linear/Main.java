package org.example.ciphers.linear;

import java.util.Scanner;

import static org.example.utils.MsgText.PLAIN_TEXT;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("[?] Enter a step: ");
        Integer key = scanner.nextInt();
        LinearCipher linearCipher = new LinearCipher();
        String encryptedText = linearCipher.proceedCipher(PLAIN_TEXT, key);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = linearCipher.proceedCipher(encryptedText, -key);
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
