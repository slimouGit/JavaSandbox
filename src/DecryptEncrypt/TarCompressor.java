package DecryptEncrypt;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TarCompressor {

    public void compressDirectoryToTar(String directoryPath, String tarFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(tarFilePath);
             TarArchiveOutputStream tos = new TarArchiveOutputStream(fos)) {
            File directory = new File(directoryPath);
            for (File file : directory.listFiles()) {
                addFileToTar(tos, file, "");
            }
        }
    }

    private void addFileToTar(TarArchiveOutputStream tos, File file, String parent) throws IOException {
        String entryName = parent + file.getName();
        TarArchiveEntry tarEntry = new TarArchiveEntry(file, entryName);

        tos.putArchiveEntry(tarEntry);

        if (file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                IOUtils.copy(fis, tos);
            }
            tos.closeArchiveEntry();
        } else if (file.isDirectory()) {
            tos.closeArchiveEntry();
            for (File childFile : file.listFiles()) {
                addFileToTar(tos, childFile, entryName + "/");
            }
        }
    }

    public static void main(String[] args) {
        try {
            TarCompressor tarCompressor = new TarCompressor();
            String directoryPath = "path_to_your_directory";
            String tarFilePath = "path_to_your_tar_file";
            tarCompressor.compressDirectoryToTar(directoryPath, tarFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
