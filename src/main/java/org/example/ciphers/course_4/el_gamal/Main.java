package org.example.ciphers.course_4.el_gamal;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ElGamalCipher elGamalCipher = new ElGamalCipher();
        List<Long> encryptedText = elGamalCipher.encrypt();
        System.out.println("encrypted text # " + encryptedText);
        Long decryptedText = elGamalCipher.decrypt(encryptedText);
        System.out.println("decrypted text # " + decryptedText);
    }
}
