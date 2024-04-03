package DecryptEncrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class TheDecryptorSymmetrisch {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private SecretKey secretKey;

    public TheDecryptorSymmetrisch(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public static SecretKey getSecretKeyFromP12File(String p12FilePath, String password) throws KeyStoreException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException, InvalidKeySpecException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        try (FileInputStream fis = new FileInputStream(p12FilePath)) {
            keyStore.load(fis, password.toCharArray());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }

        String alias = keyStore.aliases().nextElement();
        Key key = keyStore.getKey(alias, password.toCharArray());

        if (key instanceof SecretKey) {
            SecretKey secretKey = (SecretKey) key;
            // Verwenden Sie secretKey...
        } else if (key instanceof PrivateKey) {
            PrivateKey privateKey = (PrivateKey) key;
            // Verwenden Sie privateKey...
        } else if (key instanceof PublicKey) {
            PublicKey publicKey = (PublicKey) key;
            // Verwenden Sie publicKey...
        } else {
            throw new IllegalArgumentException("Unbekannter Schlüsseltyp: " + key.getClass().getName());
        }
        return new SecretKeySpec(key.getEncoded(), ALGORITHM);
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