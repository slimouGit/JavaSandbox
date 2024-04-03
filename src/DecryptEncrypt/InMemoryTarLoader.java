package DecryptEncrypt;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTarLoader {
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


