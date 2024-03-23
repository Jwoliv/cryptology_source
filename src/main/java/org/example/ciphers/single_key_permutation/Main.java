package org.example.ciphers.single_key_permutation;

public class Main {
    public static void main(String[] args) {
        SingleKeyPermutation singleKeyPermutation = new SingleKeyPermutation();
        String encryptedText = singleKeyPermutation.encrypt("example of the text!", "bcadw");
        System.out.println(encryptedText);
        String plainText = singleKeyPermutation.decrypt(encryptedText, "bcadw");
        System.out.println(plainText);
    }
}
