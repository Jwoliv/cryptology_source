package org.example.ciphers.playfair;

import static org.example.utils.MsgText.PLAIN_TEXT_ENG_WITHOUT_SPACE;

public class Main {
    public static void main(String[] args) {
        String key = "exampleKey";
        PlayfairCipher playfairCipher = new PlayfairCipher();
        String encryptedText = playfairCipher.encrypt(key, PLAIN_TEXT_ENG_WITHOUT_SPACE);
        System.out.println(encryptedText);
        String result = playfairCipher.decrypt(key, encryptedText);
        System.out.println(result);
    }
}
