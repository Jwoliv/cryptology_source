package org.example.ciphers.course_4.ec_signature;

import lombok.SneakyThrows;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class ECSignature {

    private static final String KEY_PAIR_GENERATOR_ALGORITHM = "EC";
    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA-256";
    private static final String SIGNATURE_ALGORITHM = "SHA256withECDSA";
    private static final String PLAIN_TEXT_FILE = "file_plain_text.txt";
    private static final String SIGNATURE_FILE = "file_plain_text.sig.txt";

    @SneakyThrows
    public static void main(String[] args) {
        KeyPair keyPair = generateKeys();
        byte[] fileHash = generateHashFromFile(PLAIN_TEXT_FILE);
        byte[] encryptedHash = encryptHash(keyPair.getPrivate(), fileHash);
        writeSignatureFile(encryptedHash);
        boolean isVerified = verifyFileHash(keyPair.getPublic(), PLAIN_TEXT_FILE, encryptedHash);
        System.out.println("Підпис перевірено: " + isVerified);
    }

    public static KeyPair generateKeys() {
        KeyPair keyPair = generateKeyPair();
        writeKeyToFile("private_key.pem", keyPair.getPrivate().getEncoded(), "PRIVATE KEY");
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
        try (PemWriter pemWriter = new PemWriter(new FileWriter(fileName))) {
            PemObject pemObject = new PemObject(description, keyBytes);
            pemWriter.writeObject(pemObject);
        }
    }

    @SneakyThrows
    public static byte[] generateHashFromFile(String filePath) {
        MessageDigest digest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        return digest.digest(fileBytes);
    }

    @SneakyThrows
    public static byte[] encryptHash(PrivateKey privateKey, byte[] hash) {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(hash);
        return signature.sign();
    }

    @SneakyThrows
    private static void writeSignatureFile(byte[] signature) {
        try (FileWriter sigFile = new FileWriter(ECSignature.SIGNATURE_FILE)) {
            sigFile.write(Base64.getEncoder().encodeToString(signature));
        }
    }

    @SneakyThrows
    public static boolean verifyFileHash(PublicKey publicKey, String filePath, byte[] encryptedHash) {
        byte[] fileHash = generateHashFromFile(filePath);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(fileHash);
        return signature.verify(encryptedHash);
    }
}
