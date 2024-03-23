package org.example.ciphers.double_permutation;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL;

public class Main {
    public static void main(String[] args) {
        DoublePermutation doublePermutation = new DoublePermutation();
        System.out.printf("[+] Plain text: %s%n", PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL);
        String encryptedText = doublePermutation.encrypt(PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL, "543210", "032145");
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = doublePermutation.decrypt(PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL, "012345", "543210");
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
