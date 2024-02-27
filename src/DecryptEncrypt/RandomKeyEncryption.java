package DecryptEncrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

/**
 * This class provides methods to generate a random key and encrypt it using a public key.
 */
public class RandomKeyEncryption {

    /**
     * Generates a random key of a given length.
     *
     * @param keyLength the length of the key to generate
     * @return the generated key, encoded as a Base64 string
     */
    public String generateRandomKey(int keyLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[keyLength];
        secureRandom.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    /**
     * Encrypts a given key using a public key.
     *
     * @param randomKey the key to encrypt, encoded as a Base64 string
     * @param publicKey the public key to use for encryption
     * @return the encrypted key, encoded as a Base64 string
     * @throws NoSuchAlgorithmException if the RSA algorithm is not available
     * @throws NoSuchPaddingException if the padding scheme specified is not available
     * @throws InvalidKeyException if the given key is inappropriate for initializing this cipher
     * @throws IllegalBlockSizeException if the size of the resulting bytes is not correct
     * @throws BadPaddingException if a particular padding mechanism is expected for the input data but the data is not padded properly
     */
    public String encryptRandomKeyWithPublicKey(String randomKey, PublicKey publicKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKey = cipher.doFinal(Base64.getDecoder().decode(randomKey));
        return Base64.getEncoder().encodeToString(encryptedKey);
    }

    /**
     * The main method that generates a random key, encrypts it using a public key, and writes the encrypted key to a file.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Generate a key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Generate a random key
            RandomKeyEncryption randomKeyEncryption = new RandomKeyEncryption();
            String randomKey = randomKeyEncryption.generateRandomKey(16);

            // Encrypt the random key with the public key
            String encryptedKey = randomKeyEncryption.encryptRandomKeyWithPublicKey(randomKey, keyPair.getPublic());

            // Write the encrypted key to the file "meta.xml"
            try (FileWriter fileWriter = new FileWriter("meta.xml")) {
                fileWriter.write(encryptedKey);
                System.out.println("Encrypted key " +encryptedKey +" written to meta.xml successfully!");
            } catch (IOException e) {
                System.out.println("An error occurred while writing to meta.xml");
                e.printStackTrace();
            }
        }
        catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}