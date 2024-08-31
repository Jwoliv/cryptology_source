package org.example.ciphers.course_4.rsa;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        RsaCipher rsaCipher = new RsaCipher();
        List<Long> encrypted = rsaCipher.encrypt();
        System.out.printf("encrypted: %s\n", encrypted);
        System.out.printf("decrypted: %s\n", rsaCipher.decrypt(encrypted));
    }
}
