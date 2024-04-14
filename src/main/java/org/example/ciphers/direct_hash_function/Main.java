package org.example.ciphers.direct_hash_function;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITHOUT_SPACE;

public class Main {
    public static void main(String[] args) {
        DirectHashFunction directHashFunction = new DirectHashFunction();
        Integer hash = directHashFunction.encrypt(PLAIN_TEXT_ENG_WITHOUT_SPACE);
        System.out.printf("[+] Plain text: %s%n", PLAIN_TEXT_ENG_WITHOUT_SPACE);
        System.out.printf("[+] Encrypted hash: %d%n", hash);
        Boolean res = directHashFunction.validate(PLAIN_TEXT_ENG_WITHOUT_SPACE, hash);
        System.out.printf("[+] Hash is valid: %s%n", res);
    }
}
