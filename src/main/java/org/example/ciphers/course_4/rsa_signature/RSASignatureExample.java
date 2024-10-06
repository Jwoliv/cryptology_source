package org.example.ciphers.course_4.rsa_signature;

import lombok.SneakyThrows;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public class RSASignatureExample {

    @SneakyThrows
    public static void main(String[] args) {
        KeyPair keyPair = generateKeys();
        String filePath = "file_plain_text.txt";
        byte[] hashValue = hashFile(filePath);
        byte[] signature = signHashWithTime(keyPair.getPrivate(), hashValue);
        try (FileWriter sigFile = new FileWriter(filePath + ".sig")) {
            sigFile.write(Base64.getEncoder().encodeToString(signature));
        }
    }

    @SneakyThrows
    public static KeyPair generateKeys() {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(4096);
        KeyPair keyPair = keyGen.generateKeyPair();
        try (PemWriter pemWriter = new PemWriter(new FileWriter("id_rsa"))) {
            pemWriter.writeObject(new PemObject("PRIVATE KEY", keyPair.getPrivate().getEncoded()));
        }
        try (PemWriter pemWriter = new PemWriter(new FileWriter("id_rsa.pub"))) {
            pemWriter.writeObject(new PemObject("PUBLIC KEY", keyPair.getPublic().getEncoded()));
        }
        return keyPair;
    }

    @SneakyThrows
    public static byte[] hashFile(String filePath) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        return digest.digest(fileBytes);
    }

    public static byte[] signHashWithTime(PrivateKey privateKey, byte[] hashValue) {
        long startTime = System.nanoTime();
        byte[] signedHash = getSignedHash(privateKey, hashValue);
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000.0;
        System.out.printf("Hash signed in %.6f milliseconds.%n", elapsedTime);
        return signedHash;
    }

    @SneakyThrows
    private static byte[] getSignedHash(PrivateKey privateKey, byte[] hashValue) {
        Signature signature = Signature.getInstance("SHA512withRSA");
        signature.initSign(privateKey);
        signature.update(hashValue);
        return signature.sign();
    }
}
