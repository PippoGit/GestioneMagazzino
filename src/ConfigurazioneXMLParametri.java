public class ConfigurazioneXMLParametri implements Serializable {
    final private int MAX_QUERY_RESULT;
    final private String regularFont;
    final private String mediumFont;
    final private String css;
    //final private Categoria[] categorie;
    final private String ipClient;
    final private String ipServer;
    final private int port;

    public ConfigurazioneXMLParametri(int MAX_QUERY_RESULT, String regularFont, String mediumFont, String style,/* Categoria[] c,*/ String ipClient, String ipServer, int port) {
        this.MAX_QUERY_RESULT = MAX_QUERY_RESULT;
        this.regularFont = regularFont;
        this.mediumFont = mediumFont;
        this.css = style;
        //this.c = c;
        this.ipClient = ipClient;
        this.ipServer = ipServer;
        this.port = port;
    }

    public int getMAX_QUERY_RESULT() {
        return MAX_QUERY_RESULT;
    }

    public String getRegularFont() {
        return regularFont;
    }

    public String getMediumFont() {
        return mediumFont;
    }

    public String getCss() {
        return css;
    }
/*
    public Categoria[] getCategorie() {
        return c;
    }
*/
    public String getIpClient() {
        return ipClient;
    }

    public String getIpServer() {
        return ipServer;
    }

    public int getPort() {
        return port;
    }
    
}
