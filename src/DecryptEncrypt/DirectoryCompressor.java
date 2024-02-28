package DecryptEncrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class DirectoryCompressor {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private SecretKey secretKey;

    public DirectoryCompressor(String password) throws NoSuchAlgorithmException {
        byte[] key = password.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // Use only first 128 bit
        secretKey = new SecretKeySpec(key, ALGORITHM);
    }

    public void compressAndEncryptDirectory(String inputFolderPath, String outputFilePath) throws Exception {
        // Compress the folder into a tar.gz file
        String tarGzFileName = inputFolderPath + ".tar.gz";
        compressDirectory(inputFolderPath, tarGzFileName);

        // Encrypt the tar.gz file
        encryptFile(tarGzFileName, outputFilePath);

        // Delete the temporary tar.gz file
        new File(tarGzFileName).delete();
    }

    public void decryptAndDecompressFile(String inputFilePath, String outputFolderPath) throws Exception {
        // Decrypt the .aes file
        String tarGzFileName = outputFolderPath + "/package.tar.gz";
        decryptFile(inputFilePath, tarGzFileName);

        // Decompress the tar.gz file
        decompressFile(tarGzFileName, outputFolderPath);

        // Delete the temporary tar.gz file
        new File(tarGzFileName).delete();
    }

    private void compressDirectory(String inputFolderPath, String outputTarGzFileName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("tar", "-czvf", outputTarGzFileName, "-C", inputFolderPath, ".");
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();
    }

    private void encryptFile(String inputFilePath, String outputFilePath) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(inputFilePath);
             FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {

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

    private void decryptFile(String inputFilePath, String outputFilePath) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(inputFilePath);
             FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {

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

    private void decompressFile(String inputFilePath, String outputFolderPath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("tar", "-xzvf", inputFilePath, "-C", outputFolderPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();
    }

    public static void main(String[] args) {
        try {
            String password = "your_secure_password";
            DirectoryCompressor compressor = new DirectoryCompressor(password);

            String inputFolderPath = "C:/Users/175080724/Documents/Projekte/JavaSandbox/src/DecryptEncrypt/Input/Test4";
            String outputFilePath = "C:/Users/175080724/Documents/Projekte/JavaSandbox/src/DecryptEncrypt/Output/package4.tar.gz.aes";

            compressor.compressAndEncryptDirectory(inputFolderPath, outputFilePath);
            System.out.println("Directory compressed and encrypted successfully!");

            String decryptedFolderPath = "C:/Users/175080724/Documents/Projekte/JavaSandbox/src/DecryptEncrypt/Output/Decrypted";
            compressor.decryptAndDecompressFile(outputFilePath, decryptedFolderPath);
            System.out.println("File decrypted and decompressed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}