package org.example.ciphers.course_3.vernam_cipher;

import org.example.ciphers.course_3.gamma_module_k.GammaByModuleK;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITHOUT_SPACE;

public class Main {
    public static void main(String[] args) {
        VernamCipher vernamCipher   = new VernamCipher();
        String encryptedText = vernamCipher.encrypt(PLAIN_TEXT_ENG_WITHOUT_SPACE, "key_example");
        System.out.printf("[+] Plain text: %s%n", PLAIN_TEXT_ENG_WITHOUT_SPACE);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = vernamCipher.decrypt(encryptedText, "key_example");
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
