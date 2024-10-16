package org.example.ciphers.course_4.ec_signature;

import lombok.SneakyThrows;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class ECSignature {

    private static final Logger log = LoggerFactory.getLogger(ECSignature.class);
    private static final String KEY_PAIR_GENERATOR_ALGORITHM = "EC";
    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA-256";
    private static final String SIGNATURE_ALGORITHM = "SHA256withECDSA";
    private static final String PLAIN_TEXT_FILE = "file_plain_text.txt";
    private static final String SIGNATURE_FILE = "file_plain_text.sig.txt";

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Generating key pair...");
        KeyPair keyPair = generateKeys();

        log.info("Generating hash from file: {}", PLAIN_TEXT_FILE);
        byte[] fileHash = generateHashFromFile(PLAIN_TEXT_FILE);

        log.info("Signing the hash...");
        byte[] encryptedHash = encryptHash(keyPair.getPrivate(), fileHash);

        log.info("Writing the signature to file: {}", SIGNATURE_FILE);
        writeSignatureFile(encryptedHash);

        log.info("Verifying the signature...");
        boolean isVerified = verifyFileHash(keyPair.getPublic(), PLAIN_TEXT_FILE, encryptedHash);
        log.info("Signature verified: {}", isVerified);
    }

    public static KeyPair generateKeys() {
        log.info("Generating EC key pair...");
        KeyPair keyPair = generateKeyPair();
        log.info("Writing private key to file: private_key.pem");
        writeKeyToFile("private_key.pem", keyPair.getPrivate().getEncoded(), "PRIVATE KEY");
        log.info("Writing public key to file: public_key.pem");
        writeKeyToFile("public_key.pem", keyPair.getPublic().getEncoded(), "PUBLIC KEY");
        return keyPair;
    }

    @SneakyThrows
    private static KeyPair generateKeyPair() {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_PAIR_GENERATOR_ALGORITHM);
        keyGen.initialize(new ECGenParameterSpec("secp256r1"));
        return keyGen.generateKeyPair();
    }

    @SneakyThrows
    private static void writeKeyToFile(String fileName, byte[] keyBytes, String description) {
        log.info("Writing {} to file: {}", description, fileName);
        try (PemWriter pemWriter = new PemWriter(new FileWriter(fileName))) {
            PemObject pemObject = new PemObject(description, keyBytes);
            pemWriter.writeObject(pemObject);
        }
    }

    @SneakyThrows
    public static byte[] generateHashFromFile(String filePath) {
        log.info("Reading file for hash generation: {}", filePath);
        MessageDigest digest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        return digest.digest(fileBytes);
    }

    @SneakyThrows
    public static byte[] encryptHash(PrivateKey privateKey, byte[] hash) {
        log.info("Encrypting hash with private key...");
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(hash);
        return signature.sign();
    }

    @SneakyThrows
    private static void writeSignatureFile(byte[] signature) {
        log.info("Writing signature to file: {}", SIGNATURE_FILE);
        try (FileWriter sigFile = new FileWriter(ECSignature.SIGNATURE_FILE)) {
            sigFile.write(Base64.getEncoder().encodeToString(signature));
        }
    }

    @SneakyThrows
    public static boolean verifyFileHash(PublicKey publicKey, String filePath, byte[] encryptedHash) {
        log.info("Verifying file hash for file: {}", filePath);
        byte[] fileHash = generateHashFromFile(filePath);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(fileHash);
        return signature.verify(encryptedHash);
    }
}
