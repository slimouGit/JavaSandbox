package DecryptEncrypt;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTarLoader2 {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("path_to_your_tar_file")) {
            InMemoryTarLoader2 loader = new InMemoryTarLoader2();
            loader.loadTar(fis);

            Map<String, byte[]> files = loader.getFiles();
            for (String path : files.keySet()) {
                System.out.println("File path: " + path);
                byte[] content = files.get(path);
                // Verwenden Sie den Inhalt der Datei...
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, byte[]> files = new HashMap<>();

    public void loadTar(InputStream tarInputStream) throws IOException {
        try (TarArchiveInputStream tarInput = new TarArchiveInputStream(tarInputStream)) {
            TarArchiveEntry currentEntry;
            while ((currentEntry = tarInput.getNextTarEntry()) != null) {
                if (!currentEntry.isDirectory()) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = tarInput.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                    files.put(currentEntry.getName(), bos.toByteArray());
                }
            }
        }
    }

    public Map<String, byte[]> getFiles() {
        return files;
    }
}