package DecryptEncrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Encryption {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private SecretKey secretKey;

    public Encryption(String password) throws NoSuchAlgorithmException {
        byte[] key = password.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        secretKey = new SecretKeySpec(key, ALGORITHM);
    }

    public void encryptFile(String inputFilePath, String outputFilePath) throws Exception {
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

    public static void main(String[] args) {
        try {
            String password = "your_secure_password";
            Encryption encryption = new Encryption(password);

            String inputFilePath = "C:\\Users\\175080724\\Documents\\Projekte\\JavaSandbox\\src\\DecryptEncrypt\\EncryptionDecryption\\package4.tar";
            String outputFilePath = "C:\\Users\\175080724\\Documents\\Projekte\\JavaSandbox\\src\\DecryptEncrypt\\EncryptionDecryption\\Encrypted\\package4.tar.aes";

            encryption.encryptFile(inputFilePath, outputFilePath);
            System.out.println("File encrypted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


