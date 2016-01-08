import com.thoughtworks.xstream.XStream;
import java.io.FileInputStream;


public class ConfigurazioneXML implements Serializable {
    private static final String FILE_PATH = "configurazione.xml";
    private ConfigurazioneXMLParametri params;
    
    
    private static final ConfigurazioneXML DELEGATIONLINK = new ConfigurazioneXML();

    public static ConfigurazioneXML getDelegationLink() { //(4)
        return DELEGATIONLINK;
    }    
    
    private void deserializzaXML() {
        XStream xs = new XStream(); // (03)
        xs.alias("configurazione", ConfigurazioneXMLParametri.class);

        try (FileInputStream fin = new FileInputStream(FILE_PATH)){

            System.out.print(fin);
            params = (ConfigurazioneXMLParametri) xs.fromXML(fin);
        }
        catch (Exception e) {
            //dosomething..
            System.out.print(e.getMessage());
        }    
    }
    
    public ConfigurazioneXMLParametri getParams() {
        return params;
    }
    
    private ConfigurazioneXML () {
        deserializzaXML();
    }
}
/*
<?xml version="1.0" encoding="UTF-8"?> 
<Parametri>
<economici>
<tasse unita="%">21</tasse> <massimaQuantitaPerTipologia>3</massimaQuantitaPerTipologia> <massimoNumeroTipologieProdotti>5</massimoNumeroTipologieProdotti>
</economici> <stilistici>
<font>Times New Roman</font>
<dimensioneFont unita="pt">12</dimensioneFont> <coloreSfondo>green </coloreSfondo>
</stilistici> <tecnologici>
<indirizzoIPClient>131.114.80.255</indirizzoIPClient> <indirizzoIPServerLog>131.114.90.255</indirizzoIPServerLog> <portaServerLog>80</portaServerLog>
</tecnologici> 
</Parametri>

*/
