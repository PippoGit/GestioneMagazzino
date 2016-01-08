import com.thoughtworks.xstream.XStream;
import java.io.FileInputStream;

public class ConfigurazioneXML {
    private static final String FILE_NAME = "configurazione";
    private ConfigurazioneXMLParametri params;
    
    private static final ConfigurazioneXML DELEGATIONLINK = new ConfigurazioneXML();

    public static ConfigurazioneXML getDelegationLink() { //(4)
        return DELEGATIONLINK;
    }    
       
    private void deserializzaXML() {
        XStream xs = new XStream(); // (03)
        ConfigurazioneXMLParametri.setAlias(xs);
        
        try (FileInputStream fin = new FileInputStream(FILE_NAME + ".xml")){
            params = (ConfigurazioneXMLParametri) xs.fromXML(fin);
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
        }    
    }
    
    public ConfigurazioneXMLParametri getParams() {
        return params;
    }
    
    private ConfigurazioneXML () {
        ValidazioneXML.valida(FILE_NAME);
        deserializzaXML();
    }
}
