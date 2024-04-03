package DecryptEncrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.MessageDigest;

public class TheDecryptorSymmetrisch {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private SecretKey secretKey;

    public TheDecryptorSymmetrisch(SecretKey secretKey) {
        this.secretKey = secretKey;
    }



    public static SecretKey getSecretKeyFromP12File(String p12FilePath, String password) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        try (FileInputStream fis = new FileInputStream(p12FilePath)) {
            keyStore.load(fis, password.toCharArray());
        }

        String alias = keyStore.aliases().nextElement();
        Key key = keyStore.getKey(alias, password.toCharArray());

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = key.getEncoded();
        byte[] hashedKey = sha.digest(keyBytes);

        return new SecretKeySpec(hashedKey, 0, 16, ALGORITHM);
    }

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

    public static void main(String[] args) {
        try {
            String p12FilePath = "path_to_your_p12_file";
            String password = "your_secure_password";
            SecretKey secretKey = getSecretKeyFromP12File(p12FilePath, password);

            TheDecryptorSymmetrisch decryptor = new TheDecryptorSymmetrisch(secretKey);

            String inputFilePath = "path_to_encrypted_file";
            String outputFilePath = "path_to_decrypted_file";

            decryptor.decryptFile(inputFilePath, outputFilePath);
            System.out.println("File decrypted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}