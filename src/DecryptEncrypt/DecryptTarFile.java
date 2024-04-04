package DecryptEncrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.FileInputStream;
import java.security.PrivateKey;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

public class DecryptTarFile {

    public void decryptTarFileWithPrivateKey(String encryptedFilePath, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        try (FileInputStream fis = new FileInputStream(encryptedFilePath);
             CipherInputStream cis = new CipherInputStream(fis, cipher);
             TarArchiveInputStream tarInput = new TarArchiveInputStream(cis)) {

            TarArchiveEntry currentEntry;
            while ((currentEntry = tarInput.getNextTarEntry()) != null) {
                // Hier können Sie den Inhalt des aktuellen Eintrags durchsuchen
                System.out.println(currentEntry.getName());
            }
        }
    }
}
