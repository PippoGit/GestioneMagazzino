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

    public String getCategoria() {
        return categoria;
    }

    public String getIpClient() {
        return ipClient;
    }

    public String getIpServer() {
        return ipServer;
    }

    public String getNomeApplicativo() {
        return nomeApplicativo;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getNomePulsante() {
        return nomePulsante;
    }
    
    public LogGestioneMagazzino(String XML) {
        LogGestioneMagazzino l;
        XStream xs = new XStream();
        l = (LogGestioneMagazzino) xs.fromXML(XML);
        this.categoria = l.categoria;
        this.ipClient = l.ipClient;
        this.ipServer = l.ipServer;          
        this.timestamp = l.timestamp;
    }

    public LogGestioneMagazzino(String c, String ipc, String ips) {
        categoria = c;
        ipClient = ipc;
        ipServer = ips;
        timestamp = new Date();
    }
    
    public LogGestioneMagazzino(String c, String ipc, String ips, String infoAggiuntive) {
        this(c, ipc, ips);
        
        if(c.equalsIgnoreCase("pressione")) {
            nomePulsante = infoAggiuntive;
        }
        else if (c.equalsIgnoreCase("ricerca")) {
            elementoRicercato = infoAggiuntive;
        }
    }
    
    @Override
    public String toString() {
        XStream xs = new XStream();
        xs.registerConverter(new DateConverter("yyyy-MM-dd – HH:mm:ss", null));
        return xs.toXML(this);
    }

}