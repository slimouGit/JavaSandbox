package DecryptEncrypt;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class KeyCheck {
    public static void main(String[] args) {
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get("path_to_private_key_file"));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = kf.generatePrivate(spec);
            System.out.println("Private key is valid");
        } catch (Exception e) {
            System.out.println("Invalid private key: " + e.getMessage());
        }
    }
}
