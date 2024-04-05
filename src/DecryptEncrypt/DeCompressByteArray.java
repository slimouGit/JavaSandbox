package DecryptEncrypt;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DeCompressByteArray {

    public void decompressAndListFiles(byte[] compressedTar) {
        try (InputStream byteInputStream = new ByteArrayInputStream(compressedTar);
             TarArchiveInputStream tarInput = new TarArchiveInputStream(byteInputStream)) {

            TarArchiveEntry currentEntry;
            Map<String, byte[]> files = new HashMap<>();

            while ((currentEntry = tarInput.getNextTarEntry()) != null) {
                if (!currentEntry.isDirectory()) {
                    byte[] content = new byte[(int) currentEntry.getSize()];
                    tarInput.read(content, 0, content.length);
                    files.put(currentEntry.getName(), content);
                }
            }

            processFiles(files);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processFiles(Map<String, byte[]> files) {
        for (String path : files.keySet()) {
            System.out.println("File path: " + path);
            byte[] content = files.get(path);
            // Verarbeiten Sie den Inhalt der Datei...
        }
    }

    public static void main(String[] args) {
        // Erstellen Sie ein komprimiertes Tar-Verzeichnis als Byte-Array
        byte[] compressedTar = new byte[0]; // Platzhalter

        // Erstellen Sie eine Instanz von DeCompressByteArray und rufen Sie die Methode decompressAndListFiles auf
        DeCompressByteArray deCompressByteArray = new DeCompressByteArray();
        deCompressByteArray.decompressAndListFiles(compressedTar);
    }

    public byte[] decryptWithPrivateKey(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}