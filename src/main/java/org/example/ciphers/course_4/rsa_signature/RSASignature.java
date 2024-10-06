package org.example.ciphers.course_4.rsa_signature;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.asymmetric.RSA;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public class RSASignature {

    private static final Logger log = LoggerFactory.getLogger(RSASignature.class);

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
            byte[] key = keyPair.getPrivate().getEncoded();
            log.info("private key {}", key);
            pemWriter.writeObject(new PemObject("PRIVATE KEY", key));
        }
        try (PemWriter pemWriter = new PemWriter(new FileWriter("id_rsa.pub"))) {
            byte[] key = keyPair.getPublic().getEncoded();
            log.info("public key {}", key);
            pemWriter.writeObject(new PemObject("PUBLIC KEY", key));
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
        log.info("elapsed time for signature {} milliseconds ", elapsedTime);
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
