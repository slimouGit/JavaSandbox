package DecryptEncrypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.util.Base64;

public class DecryptionFactory {

    /**
     * Diese Methode wird verwendet, um den verschlüsselten Schlüssel mit dem privaten Schlüssel zu entschlüsseln.
     * @param encryptedKeyBytes Der verschlüsselte Schlüssel.
     * @param privateKey Der private Schlüssel.
     * @return Der entschlüsselte Schlüssel.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public SecretKey decryptKeyWithPrivateKey(byte[] encryptedKeyBytes, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Create a Cipher instance for RSA
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

        // Initialize the Cipher with the private key for decryption
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Decrypt the key
        byte[] decryptedKeyBytes = cipher.doFinal(encryptedKeyBytes);

        // Convert the decrypted key bytes to a SecretKey
        SecretKey decryptedKey = new SecretKeySpec(decryptedKeyBytes, "AES");

        return decryptedKey;
    }

    /**
     * Diese Methode wird verwendet, um die verschlüsselten Daten mit dem entschlüsselten Schlüssel zu entschlüsseln.
     * @param encryptedData Die verschlüsselten Daten.
     * @param decryptedKey Der entschlüsselte Schlüssel.
     * @param iv Die Initialisierungsvektor.
     * @return Die entschlüsselten Daten.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] decryptDataWithKey(byte[] encryptedData, SecretKey decryptedKey, byte[] iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Create a Cipher instance for AES
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

        // Initialize the Cipher with the decrypted key for decryption
        cipher.init(Cipher.DECRYPT_MODE, decryptedKey, new IvParameterSpec(iv));

        // Decrypt the data
        byte[] decryptedData = cipher.doFinal(encryptedData);

        return decryptedData;
    }
}
