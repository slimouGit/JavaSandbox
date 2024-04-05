package DecryptEncrypt;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.Cipher;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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

    public void listXmlContent(byte[] xmlData) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new ByteArrayInputStream(xmlData));

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("*");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Node Name :" + eElement.getNodeName() + " [OPEN]");
                    System.out.println("Node Value :" + eElement.getTextContent());
                    System.out.println("Node Name :" + eElement.getNodeName() + " [CLOSE]");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}