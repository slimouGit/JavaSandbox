package DecryptEncrypt;

import javax.crypto.*;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;

public class SymmetricKey {

    public SecretKey generateRandomKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // for example
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }

    public byte[] encryptKeyWithPublicKey(SecretKey secretKey, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Get the bytes of the secret key
        byte[] keyBytes = secretKey.getEncoded();

        // Create a Cipher instance for RSA
        Cipher cipher = Cipher.getInstance("RSA");

        // Initialize the Cipher with the public key for encryption
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Encrypt the key
        byte[] encryptedKeyBytes = cipher.doFinal(keyBytes);

        return encryptedKeyBytes;
    }

    public byte[] generateSHA256Hash(byte[] encryptedKeyBytes) throws NoSuchAlgorithmException {
        // Create a MessageDigest instance for SHA-256
        MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");

        // Calculate the SHA-256 hash of the encrypted key bytes
        byte[] hash = sha256Digest.digest(encryptedKeyBytes);

        return hash;
    }

    public byte[] generateSHA256Hash(SecretKey secretKey) throws NoSuchAlgorithmException {
        // Get the bytes of the secret key
        byte[] keyBytes = secretKey.getEncoded();

        // Create a MessageDigest instance for SHA-256
        MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");

        // Calculate the SHA-256 hash of the key
        byte[] hash = sha256Digest.digest(keyBytes);

        return hash;
    }

    public byte[] encryptHashWithPrivateKey(byte[] hash, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Create a Cipher instance for RSA
        Cipher cipher = Cipher.getInstance("RSA");

        // Initialize the Cipher with the private key for encryption
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        // Encrypt the hash
        byte[] encryptedHash = cipher.doFinal(hash);

        return encryptedHash;
    }
}

