import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

//public class Main {
//    public static void main(String[] args) throws IOException {
//        // Pfad des Temp-Verzeichnisses
//        String tempDir = System.getProperty("java.io.tmpdir");
//        File dir = new File(tempDir);
//
//        // Ermittle die letzte Datei im Temp-Verzeichnis
//        File lastModifiedFile = dir.listFiles()[dir.listFiles().length - 1];
//
//        // Erstelle den Pfad der neuen Datei
//        Path newFilePath = Paths.get(tempDir, lastModifiedFile.getName().replace(".tmp", ".csv"));
//
//        // Kopiere die Datei und ändere das Suffix
//        Files.copy(lastModifiedFile.toPath(), newFilePath);
//    }
//}
