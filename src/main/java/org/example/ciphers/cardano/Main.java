package org.example.ciphers.cardano;

import org.example.ciphers.simple_permutation.SimplePermutation;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL;

public class Main {
    public static void main(String[] args) {
        CardanoGridEncryption cardanoGridEncryption = new CardanoGridEncryption();
        String encryptedText = cardanoGridEncryption.encryptText(PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL);
        System.out.printf("[+] Plain text: %s%n", PLAIN_TEXT_ENG_WITHOUT_SPACES_AND_EXTRA_SYMBOL);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = cardanoGridEncryption.decryptText(encryptedText);
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
