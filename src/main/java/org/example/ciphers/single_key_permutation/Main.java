package org.example.ciphers.single_key_permutation;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL;

public class Main {
    public static void main(String[] args) {
        String key = "bcadghf";
        SingleKeyPermutation singleKeyPermutation = new SingleKeyPermutation();
        String encryptedText = singleKeyPermutation.encrypt(PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL, key);
        System.out.printf("[+] Plain text: %s%n", PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = singleKeyPermutation.decrypt(encryptedText, key);
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
