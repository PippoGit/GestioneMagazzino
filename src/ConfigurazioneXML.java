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
            params = null;
            System.out.print(e.getMessage());
        }    
    }
    
    public ConfigurazioneXMLParametri getParams() throws Exception {
        if (params == null) {
            params = new ConfigurazioneXMLParametri(0, "", "", "", new Categoria[]{new Categoria(-1, "")}, "", "", -1);
            throw new Exception("Errore nell'apertuda del file di configurazione");
        }
        return params;
    }
    
    private ConfigurazioneXML () {
        if(ValidazioneXML.valida(FILE_NAME))
            deserializzaXML();
    }
}
