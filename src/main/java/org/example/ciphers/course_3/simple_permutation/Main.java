package org.example.ciphers.course_3.simple_permutation;

import static org.example.utils.MsgText.*;

public class Main {
    public static void main(String[] args) {
        SimplePermutation simplePermutation = new SimplePermutation();
        String encryptedText = simplePermutation.encrypt(PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL);
        System.out.printf("[+] Plain text: %s%n", PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = simplePermutation.decrypt(encryptedText);
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
