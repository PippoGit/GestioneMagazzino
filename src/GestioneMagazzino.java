import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GestioneMagazzino extends Application {    
    private final GUIGestioneMagazzino GUIGestioneMagBind;
    private Scene scene;

    public GestioneMagazzino() {
        GUIGestioneMagBind = GUIGestioneMagazzino.getDelegationLink();
    }
    
    public void caricaPreferenzeXML() throws Exception {
        final ConfigurazioneXML config = ConfigurazioneXML.getDelegationLink();
        Font.loadFont(GestioneMagazzino.class.getResource(config.getParams().getRegularFont()).toExternalForm(), 15); //(6)        
        Font.loadFont(GestioneMagazzino.class.getResource(config.getParams().getMediumFont()).toExternalForm(), 15);
        scene.getStylesheets().add(config.getParams().getCss());   //(6)
    }
    
    private void caricaBin() throws IOException {
        final AppCache cache = new AppCache();
        GUIGestioneMagBind.setCurrent(cache.carica());            
        GUIGestioneMagBind.aggiornaPannelloPrincipale();            
        GUIGestioneMagBind.setTitoloTxtToolbar("Scheda materiale – " + GUIGestioneMagBind.getCurrent().getNominativo());
    }
    
    private void conservaBin() {
        final AppCache cache = new AppCache();
        try {
            if (GUIGestioneMagBind.getCurrent().getId() != -1) 
                cache.salva(GUIGestioneMagBind.getCurrent());          
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void caricaApp() { //(1)
        try {
            caricaPreferenzeXML();
            GUIGestioneMagBind.caricaCategorie();
            caricaBin();
            GUIGestioneMagBind.ottieniDatiListaMaterialiDB();
        }
        catch (IOException e) {
            GUIGestioneMagBind.mostraErroreToolbar("#1002 Si è verificato un errore nell'apertura del file di cache");
            GUIGestioneMagBind.cambiaVisibilitaFigliPannelloPrincipale(false); //(2)
        }
        catch (Exception e) {
            if(!(e instanceof SQLException))
                GUIGestioneMagBind.mostraErroreToolbar("#1001 Si è verificato un errore nell'apertura del file di configurazione");
        }
    }
    
    private void inviaLog(boolean avvio) {
        try {
            ConfigurazioneXMLParametri params = ConfigurazioneXML.getDelegationLink().getParams();
            if(avvio)
                LoggerXML.logAvvio(params.getPort(), params.getIpClient(), params.getIpServer());
            else 
                LoggerXML.logTermine(params.getPort(), params.getIpClient(), params.getIpServer());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            GUIGestioneMagBind.mostraErroreToolbar("#4001 Errore nell'invio log");
        }
    }
    
    private void inviaLogTermine() {
        try {
            ConfigurazioneXMLParametri params = ConfigurazioneXML.getDelegationLink().getParams();
            LoggerXML.logTermine(params.getPort(), params.getIpClient(), params.getIpServer());
        } catch (Exception ex) {
            GUIGestioneMagBind.mostraErroreToolbar("#4001 Errore nell'invio log");
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        final VBox root = new VBox();
        scene = new Scene(root, 900, 640); 
        GUIGestioneMagBind.preparaElementiGrafici(root);    //(5)    
        
        caricaApp();
        inviaLog(true); //(3)
        
        primaryStage.setOnCloseRequest((WindowEvent ev) -> { conservaBin(); inviaLog(false); }); //(4)
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

/*
Classe principale dell'applicazione.

1) Il metodo viene utilizzato per caricare tutte le informazioni principali dell'app,
le preferenze XML, le categorie ed il file di cache. In caso di errore nasconde il pannello principale (2)

3) Invia un messaggio di log che comunica l'avio dell'app.

4) Alla chiusura conserva su file binario ed invia un log.

5) Metodo chiamato per costruire l'interfaccia grafica.

6) Carico dalle preferenze il nome del font e lo apro nell'app. Carico anche il file di CSS contenente lo stile.

*/