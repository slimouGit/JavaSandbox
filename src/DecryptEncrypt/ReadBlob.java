package DecryptEncrypt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;

public class ReadBlob {
    public void readBlobFromDatabase() {
        String url = "jdbc:oracle:thin:@your_database_url";
        String user = "your_username";
        String password = "your_password";
        String query = "SELECT your_blob_column FROM your_table WHERE your_condition";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                Blob blob = rs.getBlob(1);
                byte[] blobBytes = blob.getBytes(1, (int) blob.length());

                try (ByteArrayInputStream bis = new ByteArrayInputStream(blobBytes);
                     TarArchiveInputStream tarInput = new TarArchiveInputStream(bis)) {

                    TarArchiveEntry currentEntry;
                    while ((currentEntry = tarInput.getNextTarEntry()) != null) {
                        System.out.println(currentEntry.getName());
                        // Hier können Sie den Inhalt des aktuellen Eintrags durchsuchen
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
