package org.example.ciphers.course_4.rsa_signature;

import lombok.SneakyThrows;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public class RSASignature {

    private static final String KEY_PAIR_GENERATOR_ALGORITHM = "RSA";
    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA-256";
    private static final String SIGNATURE_ALGORITHM = "SHA512withRSA";
    private static final Logger log = LoggerFactory.getLogger(RSASignature.class);

    @SneakyThrows
    public static void main(String[] args) {
        KeyPair keyPair = generateKeys();
        String filePath = "file_plain_text.txt";
        byte[] hashValue = generateHashByFile(filePath);
        byte[] signature = generateSignature(keyPair.getPrivate(), hashValue);
        writeSignatureFile(filePath, signature);

        Boolean isVerified = verifySignature(keyPair.getPublic(), hashValue, signature);
        log.info("Signature verified: {}", isVerified);

        byte[] alteredSignature = alterSignature(signature);
        Boolean isAlteredVerified = verifySignature(keyPair.getPublic(), hashValue, alteredSignature);
        log.info("Altered signature verified: {}", isAlteredVerified);
    }

    private static void writeSignatureFile(String filePath, byte[] signature) throws IOException {
        try (FileWriter sigFile = new FileWriter(filePath + ".sig")) {
            sigFile.write(Base64.getEncoder().encodeToString(signature));
        }
    }

    public static KeyPair generateKeys() {
        KeyPair keyPair = generateKeyPair();
        writeInFile("private_key.pem", keyPair.getPrivate().getEncoded(), "private key {}", "PRIVATE KEY");
        writeInFile("public_key.pem", keyPair.getPublic().getEncoded(), "public key {}", "PUBLIC KEY");
        return keyPair;
    }

    @SneakyThrows
    private static KeyPair generateKeyPair() {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_PAIR_GENERATOR_ALGORITHM);
        keyGen.initialize(4096);
        return keyGen.generateKeyPair();
    }

    @SneakyThrows
    private static void writeInFile(String file, byte[] key, String logMsg, String alias) {
        try (PemWriter pemWriter = new PemWriter(new FileWriter(file))) {
            log.info(logMsg, key);
            pemWriter.writeObject(new PemObject(alias, key));
        }
    }

    @SneakyThrows
    public static byte[] generateHashByFile(String filePath) {
        MessageDigest digest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        return digest.digest(fileBytes);
    }

    public static byte[] generateSignature(PrivateKey privateKey, byte[] hashValue) {
        long startTime = System.nanoTime();
        byte[] signedHash = signHash(privateKey, hashValue);
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000.0;
        log.info("Elapsed time for signature: {} milliseconds", elapsedTime);
        return signedHash;
    }

    @SneakyThrows
    private static byte[] signHash(PrivateKey privateKey, byte[] hashValue) {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(hashValue);
        return signature.sign();
    }

    @SneakyThrows
    public static Boolean verifySignature(PublicKey publicKey, byte[] hashValue, byte[] signature) {
        try {
            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initVerify(publicKey);
            sig.update(hashValue);
            return sig.verify(signature);
        } catch (SignatureException e) {
            log.error("Signature verification failed: {}", e.getMessage());
            return false;
        }
    }

    private static byte[] alterSignature(byte[] originalSignature) {
        byte[] alteredSignature = originalSignature.clone();
        alteredSignature[alteredSignature.length - 1] += 1;
        return alteredSignature;
    }
}
