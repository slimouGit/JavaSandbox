package DecryptEncrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AesFolderEncryptor {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private SecretKey secretKey;

    public AesFolderEncryptor(String password) throws NoSuchAlgorithmException {
        byte[] key = password.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // Nur die ersten 128 Bit verwenden
        secretKey = new SecretKeySpec(key, ALGORITHM);
    }

    public void encryptFolder(String inputFolderPath, String outputFolderPath) throws Exception {
        File inputFile = new File(inputFolderPath);
        File outputFile = new File(outputFolderPath);

        // Überprüfe, ob inputFile ein Verzeichnis ist
        if (inputFile.isDirectory()) {
            // Stelle sicher, dass das Ausgabeverzeichnis existiert
            if (!outputFile.exists()) {
                outputFile.mkdirs();
            }
            // Rekursiv für alle Dateien im Verzeichnis
            for (String child : inputFile.list()) {
                encryptFolder(inputFolderPath + File.separator + child,
                        outputFolderPath + File.separator + child);
            }
        } else {
            // Verarbeitung für einzelne Dateien
            File parentFolder = outputFile.getParentFile();
            if (!parentFolder.exists()) {
                parentFolder.mkdirs(); // Erstellt den übergeordneten Ordner für die Datei, falls nicht vorhanden
            }
            encryptFile(inputFile, outputFile);
        }
    }

    private void encryptFile(File inputFile, File outputFile) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
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
            AesFolderEncryptor encryptor = new AesFolderEncryptor(password);

            String inputFolderPath = "C:/Users/175080724/Documents/Projekte/JavaSandbox/src/DecryptEncrypt/Input/Test";
            String outputFolderPath = "C:/Users/175080724/Documents/Projekte/JavaSandbox/src/DecryptEncrypt/Output/Test";

            encryptor.encryptFolder(inputFolderPath, outputFolderPath);
            System.out.println("Folder encrypted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


