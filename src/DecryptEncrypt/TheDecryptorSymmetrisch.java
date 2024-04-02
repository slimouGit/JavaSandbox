package DecryptEncrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class TheDecryptorSymmetrisch {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private SecretKey secretKey;

    /**
     * Constructor for the AesTarDecryptor class.
     * It initializes the secret key using the provided password.
     */
    public TheDecryptorSymmetrisch(String password) throws NoSuchAlgorithmException {
        byte[] key = password.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // Use only first 128 bit
        secretKey = new SecretKeySpec(key, ALGORITHM);
    }

    /**
     * Decrypts the file at the given input file path and writes the decrypted content to the output file path.
     */
    public void decryptFile(String inputFilePath, String outputFilePath) throws Exception {
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) >= 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * The main method for the AesTarDecryptor class.
     * It creates an instance of the AesTarDecryptor and uses it to decrypt a file.
     */
    public static void main(String[] args) {
        try {
            String password = "your_secure_password";
            TheDecryptorSymmetrisch decryptor = new TheDecryptorSymmetrisch(password);

            String inputFilePath = "path_to_encrypted_file";
            String outputFilePath = "path_to_decrypted_file";

            decryptor.decryptFile(inputFilePath, outputFilePath);
            System.out.println("File decrypted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
