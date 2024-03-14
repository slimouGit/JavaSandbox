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

public class AesTarDecryptor {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private static SecretKey secretKey;

    public AesTarDecryptor(String password) throws NoSuchAlgorithmException {
        byte[] key = password.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // Use only first 128 bit
        secretKey = new SecretKeySpec(key, ALGORITHM);
    }

    public static void decryptAndDecompressFile(String inputFilePath, String outputFolderPath) throws Exception {
        File inputFile = new File(inputFilePath);
        String tarGzFileName = outputFolderPath + ".tar.gz";
        decryptFile(inputFile, new File(tarGzFileName));
        extractTarGzFile(new File(tarGzFileName), outputFolderPath);
        Files.deleteIfExists(Paths.get(tarGzFileName));
    }

    private static void decryptFile(File inputFile, File outputFile) throws Exception {
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

    private static void extractTarGzFile(File inputFile, String outputFolderPath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("tar", "-xvzf", inputFile.getAbsolutePath(), "-C", outputFolderPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String password = "your_secure_password";
        AesTarDecryptor decryptor = new AesTarDecryptor(password);
        try {
            String inputFilePath = "C:\\Users\\175080724\\Documents\\Projekte\\JavaSandbox\\src\\DecryptEncrypt\\Output\\Test.tar.gz.aes";
            String outputFolderPath = "C:\\Users\\175080724\\Documents\\Projekte\\JavaSandbox\\src\\DecryptEncrypt\\Output\\Decrypted";
            decryptor.decryptAndDecompressFile(inputFilePath, outputFolderPath);

            System.out.println("File decrypted and decompressed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}