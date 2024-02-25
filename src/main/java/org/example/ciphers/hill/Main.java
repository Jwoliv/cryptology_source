package org.example.ciphers.hill;

public class Main {
    public static void main(String[] args) {
        HillCipher hillCipher = new HillCipher();
        String encryptedText = hillCipher.encrypt("lishchukbohdan");
        System.out.println(encryptedText);
        String decryptedText = hillCipher.decrypt(encryptedText);
        System.out.println(decryptedText);
    }
}
