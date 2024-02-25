package org.example.ciphers.hill;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITHOUT_SPACE;

public class Main {
    public static void main(String[] args) {
        HillCipher hillCipher = new HillCipher();
        String encryptedText = hillCipher.encrypt(PLAIN_TEXT_ENG_WITHOUT_SPACE);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = hillCipher.decrypt(encryptedText);
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
