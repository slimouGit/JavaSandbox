package DecryptEncrypt;



import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class DecryptWithPrivateKey {

    public void decryptFileWithPrivateKey(String encryptedFilePath, String privateKeyFilePath, String outputFilePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyFilePath));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        FileInputStream fis = new FileInputStream(encryptedFilePath);
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        byte[] input = new byte[64];
        int bytesRead;

        while ((bytesRead = fis.read(input)) != -1) {
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null) fos.write(output);
        }

        byte[] output = cipher.doFinal();
        if (output != null) fos.write(output);

        fis.close();
        fos.flush();
        fos.close();
    }

    public static void main(String[] args) {
        try {
            DecryptWithPrivateKey decryptor = new DecryptWithPrivateKey();
            String encryptedFilePath = "path_to_encrypted_file";
            String privateKeyFilePath = "path_to_private_key_file";
            String outputFilePath = "path_to_output_file";
            decryptor.decryptFileWithPrivateKey(encryptedFilePath, privateKeyFilePath, outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
