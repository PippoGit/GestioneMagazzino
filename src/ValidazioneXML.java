import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class ValidazioneXML {
    
    public static void valida(String name) {
            try {  
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 

            Document d = db.parse(new File(name + ".xml")); 
            Schema s = sf.newSchema(new StreamSource(new File(name + ".xsd"))); 
            s.newValidator().validate(new DOMSource(d)); 
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            if (e instanceof SAXException) 
                System.out.println("Errore di validazione: " + e.getMessage());
            else
                System.out.println(e.getMessage());    
        }
    }
}
