package DecryptEncrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class Decryptor {
    private static final String ALGORITHM = "RSA";
    private Key privateKey;
    private PublicKey publicKey;

    public Decryptor(String p12FilePath, String password, String cerFilePath) throws Exception {
        // Laden des privaten Schlüssels aus der .p12-Datei
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(p12FilePath)) {
            keyStore.load(fis, password.toCharArray());
        }
        String alias = keyStore.aliases().nextElement();
        this.privateKey = keyStore.getKey(alias, password.toCharArray());

        // Laden des öffentlichen Schlüssels aus der .cer-Datei
        try (InputStream inStream = new FileInputStream(cerFilePath)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cert = cf.generateCertificate(inStream);
            this.publicKey = cert.getPublicKey();
        }
    }

    public void decryptFile(String inputFilePath, String outputFilePath) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[384]; // Blockgröße auf 384 Bytes setzen
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] outputBuffer = cipher.doFinal(buffer, 0, bytesRead);
                outputStream.write(outputBuffer);
            }
        }
    }

    public static void main(String[] args) {
        try {
            String p12FilePath = "path_to_your_p12_file";
            String password = "your_secure_password";
            String cerFilePath = "path_to_partyB_cer_file";

            Decryptor decryptor = new Decryptor(p12FilePath, password, cerFilePath);

            String inputFilePath = "path_to_encrypted_file";
            String outputFilePath = "path_to_decrypted_file";

            decryptor.decryptFile(inputFilePath, outputFilePath);
            System.out.println("File decrypted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
