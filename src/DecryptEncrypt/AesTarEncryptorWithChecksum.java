package DecryptEncrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * A class for encrypting, compressing a folder using AES encryption and TAR compression, and creating a checksum.
 * This class uses the AES encryption algorithm to encrypt a folder and its contents.
 * The encryption is done using a password provided by the user.
 * The encrypted files are compressed into a TAR file and saved in the output file specified by the user.
 * After the encryption and compression, a checksum is created for the output file.
 *
 * <p>
 * This class uses the Java Cryptography Extension (JCE) for the encryption.
 * The password is hashed using SHA-1 and the first 128 bits are used as the key for the AES encryption.
 * </p>
 *
 * <p>
 * This class can be used as follows:
 * <pre>
 *     AesTarEncryptorWithChecksum encryptor = new AesTarEncryptorWithChecksum("your_secure_password");
 *     encryptor.encryptAndCompressFolder("path_to_input_folder", "path_to_output_file");
 * </pre>
 * </p>
 *
 * @author slimouGit
 */
public class AesTarEncryptorWithChecksum {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private SecretKey secretKey;

    public AesTarEncryptorWithChecksum(String password) throws NoSuchAlgorithmException {
        byte[] key = password.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // Use only first 128 bit
        secretKey = new SecretKeySpec(key, ALGORITHM);
    }

    public void encryptAndCompressFolder(String inputFolderPath, String outputFilePath) throws Exception {
        File inputFile = new File(inputFolderPath);

        // Komprimiere den Ordner zu einer TAR-Datei
        String tarFileName = inputFolderPath + ".tar";
        createTarFile(inputFile, tarFileName);

        // Verschlüssele die TAR-Datei
        encryptFile(new File(tarFileName), new File(outputFilePath));

        // Erstelle eine Prüfsumme für die verschlüsselte Datei
        String checksum = createChecksum(outputFilePath);
        System.out.println("Checksum for the encrypted file: " + checksum);

        // Lösche die temporäre TAR-Datei
        Files.deleteIfExists(Paths.get(tarFileName));
    }

    private void createTarFile(File inputFile, String outputTarFileName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("tar", "-cvf", outputTarFileName, inputFile.getAbsolutePath());
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();
    }

    private void encryptFile(File inputFile, File outputFile) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }
            cipherOutputStream.close();
        }
    }

    private String createChecksum(String filePath) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        byte[] checksumBytes = digest.digest(bytes);
        BigInteger bigInt = new BigInteger(1, checksumBytes);
        return bigInt.toString(16);
    }

    public static void main(String[] args) {
        try {
            String password = "your_secure_password";
            AesTarEncryptorWithChecksum encryptor = new AesTarEncryptorWithChecksum(password);

            String inputFolderPath = "C:/Users/175080724/Documents/Projekte/JavaSandbox/src/DecryptEncrypt/Input/Test2";
            String outputFilePath = "C:/Users/175080724/Documents/Projekte/JavaSandbox/src/DecryptEncrypt/Output/Test2.tar.enc";

            encryptor.encryptAndCompressFolder(inputFolderPath, outputFilePath);
            System.out.println("Folder encrypted and compressed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}