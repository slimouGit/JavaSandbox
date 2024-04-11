package XmlValidator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class XmlValidator {
    public static void main(String[] args) {
        try {
            // Erstellen Sie eine JAXBContext-Instanz
            JAXBContext jaxbContext = JAXBContext.newInstance(YourClass.class);

            // Erstellen Sie ein Schema-Objekt
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("path_to_your_schema.xsd"));

            // Erstellen Sie einen Unmarshaller und setzen Sie das Schema
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);

            // Unmarshal the XML document
            YourClass yourObject = (YourClass) unmarshaller.unmarshal(new File("path_to_your_xml.xml"));

            // Wenn wir hier ankommen, dann ist das XML-Dokument gültig
            System.out.println("XML document is valid.");

        } catch (JAXBException | SAXException e) {
            // Ein Fehler ist aufgetreten, das XML-Dokument ist ungültig
            System.out.println("XML document is not valid: " + e.getMessage());
        }
    }
}
