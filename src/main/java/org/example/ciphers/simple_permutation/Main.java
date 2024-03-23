package org.example.ciphers.simple_permutation;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITH_SPACES;

public class Main {
    public static void main(String[] args) {
        SimplePermutation simplePermutation = new SimplePermutation();
        String encryptedText = simplePermutation.encrypt(PLAIN_TEXT_ENG_WITH_SPACES);
        System.out.println(encryptedText);
        String plainText = simplePermutation.decrypt(encryptedText);
        System.out.println(plainText);
    }
}
