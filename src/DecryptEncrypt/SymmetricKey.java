package DecryptEncrypt;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SymmetricKey {

    public SecretKey generateRandomKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // for example
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
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
}

