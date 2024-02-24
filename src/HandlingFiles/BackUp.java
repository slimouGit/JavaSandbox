package HandlingFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class BackUp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the source file path:");
        String sourceFilePath = scanner.nextLine();

        System.out.println("Enter the target directory:");
        String targetDirectory = scanner.nextLine();

        try {
            copyFile(sourceFilePath, targetDirectory);
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while copying the file.");
            e.printStackTrace();
        }
    }

    public static void copyFile(String sourceFilePath, String targetDirectory) throws IOException {
        Path sourcePath = Paths.get(sourceFilePath);
        Path targetPath = Paths.get(targetDirectory, sourcePath.getFileName().toString());

        Files.copy(sourcePath, targetPath);
    }
}
