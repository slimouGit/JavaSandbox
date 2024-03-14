package DecryptEncrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * A class for encrypting and compressing a folder using AES encryption and TAR compression.
 * This class uses the AES encryption algorithm to encrypt a folder and its contents.
 * The encryption is done using a password provided by the user.
 * The encrypted files are compressed into a TAR file and saved in the output file specified by the user.
 *
 * <p>
 * This class uses the Java Cryptography Extension (JCE) for the encryption.
 * The password is hashed using SHA-1 and the first 128 bits are used as the key for the AES encryption.
 * </p>
 *
 * <p>
 * This class can be used as follows:
 * <pre>
 *     AesTarEncryptor encryptor = new AesTarEncryptor("your_secure_password");
 *     encryptor.encryptAndCompressFolder("path_to_input_folder", "path_to_output_file");
 * </pre>
 * </p>
 *
 * @author slimouGit
 */
public class AesTarDecryptor {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private SecretKey secretKey;

    public AesTarDecryptor(String password) throws NoSuchAlgorithmException {
        byte[] key = password.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // Use only first 128 bit
        secretKey = new SecretKeySpec(key, ALGORITHM);
    }

    public void decryptAndDecompressFile(String inputFilePath, String outputFolderPath) throws Exception {
        File inputFile = new File(inputFilePath);

        // Entschlüsseln Sie die Datei zu einer TAR-Datei
        String tarFileName = outputFolderPath + ".tar";
        decryptFile(inputFile, new File(tarFileName));

        // Entkomprimieren Sie die TAR-Datei in den Ordner
        extractTarFile(new File(tarFileName), outputFolderPath);

        // Löschen Sie die temporäre TAR-Datei
        Files.deleteIfExists(Paths.get(tarFileName));
    }

    private void extractTarGzFile(File inputFile, String outputFolderPath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("tar", "-xvzf", inputFile.getAbsolutePath(), "-C", outputFolderPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();
    }


    private void decryptFile(File inputFile, File outputFile) throws Exception {
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

    private void decryptFileR(File inputFile, File outputFile) throws Exception {
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

    private void extractTarFile(File inputFile, String outputFolderPath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("tar", "-xvf", inputFile.getAbsolutePath(), "-C", outputFolderPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();
    }

    public static void main(String[] args) {
        try {
            String password = "your_secure_password";
            AesTarDecryptor decryptor = new AesTarDecryptor(password);

            String inputFilePath = "C:\\Users\\175080724\\Documents\\Projekte\\JavaSandbox\\src\\DecryptEncrypt\\Output\\Test.tar.gz.aes";
            String outputFolderPath = "C:\\Users\\175080724\\Documents\\Projekte\\JavaSandbox\\src\\DecryptEncrypt\\Output\\Decrypted";

            decryptor.decryptAndDecompressFile(inputFilePath, outputFolderPath);
            System.out.println("File decrypted and decompressed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
