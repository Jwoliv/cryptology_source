package org.example.ciphers.feistel_network;

public class Main {
    public static void main(String[] args) {
        FeistelNetwork feistelNetwork = new FeistelNetwork();
        String text = "Lishchuk";
        String key = "Bohdan";
        System.out.printf("[+] Key: %s\n", key);
        System.out.printf("[+] Text: %s\n", text);
        String encryptedBinaryText = feistelNetwork.encrypt(text, key);
        System.out.printf("[+] Encrypt: %s\n", encryptedBinaryText);
        System.out.printf("[+]  Origin: %s\n", feistelNetwork.convertToBinary(text));
    }
}
