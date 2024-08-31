package org.example.ciphers.course_3.affine;

import java.util.Scanner;

import static org.example.utils.MsgText.PLAIN_TEXT;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("[?] Enter a key1: ");
        Integer key1 = scanner.nextInt();
        System.out.print("[?] Enter a key2: ");
        Integer key2 = scanner.nextInt();
        AffineCipher affineCipher = new AffineCipher();
        String encryptedText = affineCipher.encrypt(PLAIN_TEXT, key1, key2);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String plainText = affineCipher.decrypt(encryptedText, key1, key2);
        System.out.printf("[+] Decrypted text: %s%n", plainText);
    }
}
