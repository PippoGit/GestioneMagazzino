import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import java.util.Date;

public class LogGestioneMagazzino {
    private final String categoria;
    private final String ipClient;
    private final String ipServer;
    private final String nomeApplicativo = "GestioneMagazzino";
    private final Date timestamp;
    
    private String nomePulsante;
    private String elementoRicercato;
    
    public String getIpClient() {
        return ipClient;
    }

    public String getIpServer() {
        return ipServer;
    }
    
    public LogGestioneMagazzino(String XML) { //(1)
        LogGestioneMagazzino l;
        XStream xs = new XStream();
        l = (LogGestioneMagazzino) xs.fromXML(XML);
        this.categoria = l.categoria;
        this.ipClient = l.ipClient;
        this.ipServer = l.ipServer;          
        this.timestamp = l.timestamp;
    }

    public LogGestioneMagazzino(String c, String ipc, String ips) {//(2)
        categoria = c;
        ipClient = ipc;
        ipServer = ips;
        timestamp = new Date();
    }
    
    public LogGestioneMagazzino(String c, String ipc, String ips, String infoAggiuntive) { //(3)
        this(c, ipc, ips);
        
        if(c.equalsIgnoreCase("pressione")) {
            nomePulsante = infoAggiuntive;
        }
        else if (c.equalsIgnoreCase("ricerca")) {
            elementoRicercato = infoAggiuntive;
        }
    }
    
    @Override
    public String toString() { //(1)
        XStream xs = new XStream();
        xs.registerConverter(new DateConverter("yyyy-MM-dd – HH:mm:ss", null)); //(4)
        return xs.toXML(this);
    }

}

/*
Classe dell'oggetto che sintetizza le informazioni relative al log.

1) Costruisco una nuova istanza di un oggetto di tipo LogGestioneXML a partire da una stringa XML.

2) Costruisco una nuova istanza di un log che non presenta inforamazioni aggiuntive

3) Costruisco una nuova istanza di un log che presenta informazioni aggiuntive. Ovvero se si tratta
di un'operazione di pressione viene specificato il nome del pulsante, se invece è una ricerca viene
inserito il nome dell'elemento ricercato.

4) Converto la stringa della data nel formato corretto.

*/