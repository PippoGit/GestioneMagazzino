import com.thoughtworks.xstream.XStream;
import java.io.Serializable;

public class ConfigurazioneXMLParametri implements Serializable {
    
    private class ParametriFunzionali {
        final private int MAX_QUERY_RESULT;
        final private Categoria[] categorie;

        public ParametriFunzionali(int MAX_QUERY_RESULT, Categoria[] c) {
            this.MAX_QUERY_RESULT = MAX_QUERY_RESULT;
            this.categorie = c;
        }
    };
        
    private class ParametriStilistici {
        final private String regularFont;
        final private String mediumFont;
        final private String css; 

        public ParametriStilistici(String regularFont, String mediumFont, String css) {
            this.regularFont = regularFont;
            this.mediumFont = mediumFont;
            this.css = css;
        }
    };
    
    private class ParametriTecnologici {
        final private String ipClient;
        final private String ipServer;
        final private int port;

        public ParametriTecnologici(String ipClient, String ipServer, int port) {
            this.ipClient = ipClient;
            this.ipServer = ipServer;
            this.port = port;
        }
    };

    final private ParametriFunzionali funzionali;
    final private ParametriStilistici stilistici;
    final private ParametriTecnologici tecnologici;

    public ConfigurazioneXMLParametri(int MAX_QUERY_RESULT, String regularFont, String mediumFont, String style, Categoria[] c, String ipClient, String ipServer, int port) {
        stilistici = new ParametriStilistici(regularFont, mediumFont, style);
        funzionali = new ParametriFunzionali(MAX_QUERY_RESULT, c);
        tecnologici = new ParametriTecnologici(ipClient, ipServer, port);
    }
    
    static public void setAlias(XStream x) {
        x.alias("configurazione", ConfigurazioneXMLParametri.class);
        x.alias("funzionali", ConfigurazioneXMLParametri.ParametriFunzionali.class);
        x.alias("stilistici", ConfigurazioneXMLParametri.ParametriStilistici.class);
        x.alias("tecnologici", ConfigurazioneXMLParametri.ParametriTecnologici.class);
        x.alias("categoria", Categoria.class);
    }
    
    public int getMAX_QUERY_RESULT() {
        return funzionali.MAX_QUERY_RESULT;
    }

    public String getRegularFont() {
        return stilistici.regularFont;
    }

    public String getMediumFont() {
        return stilistici.mediumFont;
    }

    public String getCss() {
        return stilistici.css;
    }

    public Categoria[] getCategorie() {
        return funzionali.categorie;
    }
    
    public String getIpClient() {
        return tecnologici.ipClient;
    }

    public String getIpServer() {
        return tecnologici.ipServer;
    }

    public int getPort() {
        return tecnologici.port;
    }
    
}
