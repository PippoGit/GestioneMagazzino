import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class ValidazioneXML {
 
    public static boolean valida(String name) { //(1)
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
            return false;
        }
        return true;
    }
    
    public static boolean valida(String xml, String xsdFile) { //(2)
        try {
            StringReader r = new StringReader(xml);
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);     
            Schema s = sf.newSchema(new StreamSource(new File(xsdFile + ".xsd"))); 
            s.newValidator().validate(new StreamSource(r)); 
        }
        catch (SAXException | IOException e) {
            System.out.println("Errore di validazione: " + e.getMessage());
            return false;
        }
        return true;
    }
}
/*
Classe utilizzata dall'app e dal server per effettuare la validazione di un file XML a partire dal file XSD.

1) Permette di validare un file xml di nome "name" utilizzando il file di grammatica "name.xsd";
2) Permette di validare una stringa XML utilizzando il file di grammatica xsdFile.xsd

*/