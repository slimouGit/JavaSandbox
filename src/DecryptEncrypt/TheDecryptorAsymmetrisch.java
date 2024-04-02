package DecryptEncrypt;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class TheDecryptorAsymmetrisch {
    private static final String ALGORITHM = "RSA";
    private PrivateKey privateKey;

    /**
     * Constructor for the RsaFileDecryptor class.
     * It initializes the private key using the provided key string.
     *
     * @param privateKeyString The private key string used for decryption.
     * @throws Exception If an error occurs during key initialization.
     */
    public TheDecryptorAsymmetrisch(String privateKeyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        privateKey = kf.generatePrivate(spec);
    }

    /**
     * Decrypts the file at the given input file path and writes the decrypted content to the output file path.
     *
     * @param inputFilePath  The path to the encrypted file.
     * @param outputFilePath The path where the decrypted file will be written.
     * @throws Exception If an error occurs during decryption.
     */
    public void decryptFile(String inputFilePath, String outputFilePath) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {

            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);
            outputStream.write(outputBytes);
        }
    }

    /**
     * The main method for the RsaFileDecryptor class.
     * It creates an instance of the RsaFileDecryptor and uses it to decrypt a file.
     *
     * @param args The command line arguments. Not used in this method.
     */
    public static void main(String[] args) {
        try {
            String privateKeyString = "your_private_key";
            TheDecryptorAsymmetrisch decryptor = new TheDecryptorAsymmetrisch(privateKeyString);

            String inputFilePath = "path_to_encrypted_file";
            String outputFilePath = "path_to_decrypted_file";

            decryptor.decryptFile(inputFilePath, outputFilePath);
            System.out.println("File decrypted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
