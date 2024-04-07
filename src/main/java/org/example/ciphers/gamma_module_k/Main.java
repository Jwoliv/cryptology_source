package org.example.ciphers.gamma_module_k;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITHOUT_SPACE;

public class Main {
    public static void main(String[] args) {
        GammaByModuleK gammaByModuleK = new GammaByModuleK();
        String encryptedText = gammaByModuleK.encrypt(PLAIN_TEXT_ENG_WITHOUT_SPACE, "key_example");
        System.out.printf("[+] Plain text: %s%n", PLAIN_TEXT_ENG_WITHOUT_SPACE);
        System.out.printf("[+] Encrypted text: %s%n", encryptedText);
        String decryptedText = gammaByModuleK.decrypt(encryptedText, "key_example");
        System.out.printf("[+] Decrypted text: %s%n", decryptedText);
    }
}
