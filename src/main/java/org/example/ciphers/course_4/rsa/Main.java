package org.example.ciphers.course_4.rsa;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        RsaCipher rsaCipher = new RsaCipher();
        List<Long> encrypted = rsaCipher.encrypt();
        System.out.println(encrypted);
        System.out.println(rsaCipher.decrypt(encrypted));
    }
}
