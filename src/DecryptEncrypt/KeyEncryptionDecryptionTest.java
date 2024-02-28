package DecryptEncrypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class KeyEncryptionDecryptionTest {


    /**
     * https://www.baeldung.com/java-aes-encryption-decryption
     */

    /**
     * This method, generateKey, is used to generate a SecretKey for AES encryption.
     * It uses the KeyGenerator class provided by Java's Cryptography Architecture (JCA).
     */
    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }


    /**
     * This method, getKeyFromPassword, is used to generate a SecretKey from a given password and salt.
     * It uses the PBKDF2WithHmacSHA256 algorithm to derive the key.
     */
    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
        return secret;
    }

    /**
     * This method, getKeyFromPasswordAes, is used to generate a SecretKeySpec from a given password.
     * It uses the SHA-1 algorithm to hash the password and then truncates or pads the hash to 128 bits (16 bytes),
     * which is a valid length for an AES key.
     */
    public static SecretKeySpec getKeyFromPasswordAes(String password)
            throws NoSuchAlgorithmException {
        byte[] key = password.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // Use only first 128 bit
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }

    /**
     * This method, generateIv, is used to generate an initialization vector (IV) for AES encryption.
     * The IV is used in certain encryption modes to provide randomness for the encryption process,
     * which can help to ensure the security of the encrypted data.
     */
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     * This method, encrypt, is used to encrypt a given input string using a specified encryption algorithm,
     * a SecretKey, and an IvParameterSpec.
     * It uses the Cipher class provided by Java's Cryptography Architecture (JCA).
     *
     * algorithm: A String that represents the name of the encryption algorithm to use. This should be a standard string name recognized by the JCA.
     * input: A String that represents the data to be encrypted.
     * key: A SecretKey that is used for the encryption.
     * iv: An IvParameterSpec that is used to provide randomness for the encryption process.
     */
    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretKey key1 = generateKey(128);
        System.out.println(key1);
        SecretKey key2 = getKeyFromPassword("123456", "12345");
        System.out.println(key2);
        SecretKey key3 = getKeyFromPasswordAes("123456");
        System.out.println(key3);


        // Generate a 128-bit AES key
        SecretKey key = KeyEncryptionDecryptionTest.generateKey(128);

        // Generate an IV
        IvParameterSpec iv = KeyEncryptionDecryptionTest.generateIv();

        // The input string to be encrypted
        String input = "Hello, World!";

        // Call the encrypt method
        String encrypted = KeyEncryptionDecryptionTest.encrypt("AES/CBC/PKCS5Padding", input, key, iv);

        // Print the encrypted string
        System.out.println("Encrypted: " + encrypted);

        // Call the decrypt method
        String decrypted = KeyEncryptionDecryptionTest.decrypt("AES/CBC/PKCS5Padding", encrypted, key, iv);

        // Print the decrypted string
        System.out.println("Decrypted: " + decrypted);

    }
}
