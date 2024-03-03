package org.example.ciphers.vigenere;

public class Main {
    public static void main(String[] args) {
        VigenereCipher vigenereCipher = new VigenereCipher();
        var encryptText = vigenereCipher.encrypt("she_is_listening", "pascal");
        System.out.println(encryptText);
        var decryptText = vigenereCipher.decrypt(encryptText, "pascal");
        System.out.println(decryptText);
    }
}
