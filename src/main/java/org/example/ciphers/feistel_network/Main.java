package org.example.ciphers.feistel_network;

public class Main {
    public static void main(String[] args) {
        FeistelNetwork feistelNetwork = new FeistelNetwork();
        String text = "Lishchuk";
        String key = "Bohdan";
        System.out.printf("[+] Key: %s\n", key);
        System.out.printf("[+] Text: %s\n", text);
        System.out.printf("[+] Result: %s\n", feistelNetwork.hashMethod(text, key));
    }
}
