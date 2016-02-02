import com.thoughtworks.xstream.XStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogGestioneMagazzino {
    private final String categoria;
    private final String ipClient;
    private final String ipServer;
    private final String nomeApplicativo = "GestioneMagazzino";
    private final String timestamp;
    private String nomePulsante;

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

    public String getTimestamp() {
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
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd – HH:mm:ss");
        Date d = new Date();
        timestamp = d.toString();
    }
    
    public LogGestioneMagazzino(String c, String ipc, String ips, String infoAggiuntive) {
        this(c, ipc, ips);
        
        if(c.equalsIgnoreCase("pressione")) {
            nomePulsante = infoAggiuntive;
        }
    }
    
    @Override
    public String toString() {
        XStream xs = new XStream();
        return xs.toXML(this);
    }

}