package org.example.ciphers.course_3.double_permutation;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL;

public class Main {
    public static void main(String[] args) {
        DoublePermutation doublePermutation = new DoublePermutation();
        System.out.printf("[+] Plain text: %s%n", PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL);
        String encryptedText = doublePermutation.encrypt(PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL, new int[] {6,5,4,3,2,1}, new int[] {1,4,3,2,5,6,7});
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = doublePermutation.decrypt(encryptedText,  new int[] {6,5,4,3,2,1}, new int[] {1,4,3,2,5,6,7});
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
