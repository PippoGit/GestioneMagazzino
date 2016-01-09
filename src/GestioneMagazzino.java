import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GestioneMagazzino extends Application {    
    private final ApplicationController controller;
    private Scene scene;

    public GestioneMagazzino() {
        controller = ApplicationController.getDelegationLink();
    }
    
    public void caricaPreferenzeXML() throws Exception {
        final ConfigurazioneXML config = ConfigurazioneXML.getDelegationLink();
        Font.loadFont(GestioneMagazzino.class.getResource(config.getParams().getRegularFont()).toExternalForm(), 15);        
        Font.loadFont(GestioneMagazzino.class.getResource(config.getParams().getMediumFont()).toExternalForm(), 15);
        scene.getStylesheets().add(config.getParams().getCss());   
    }
    
    private void caricaBin() throws IOException {
        final AppCache cache = new AppCache();
        controller.setCurrent(cache.carica());            
        controller.aggiornaPannelloPrincipale();            
        controller.setTitoloTxtToolbar("Scheda materiale – " + controller.getCurrent().getNominativo());
    }
    
    private void conservaBin() {
        final AppCache cache = new AppCache();
        try {
            if (controller.getCurrent().getId() != -1) 
                cache.salva(controller.getCurrent());          
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        final VBox root = new VBox();
        scene = new Scene(root, 900, 640); 
        controller.preparaElementiGrafici(root);        
        
        try {
            caricaPreferenzeXML();
            controller.caricaCategorie();
            caricaBin();
            controller.ottieniDatiListaMaterialiDB();
        }
        catch (IOException e) {
            controller.mostraErroreToolbar("Si è verificato un errore nell'apertura del file di cache");
            controller.cambiaVisibilitaFigliPannelloPrincipale(false);
        }
        catch (Exception e) {
            if(!(e instanceof SQLException))
                controller.mostraErroreToolbar(e.getMessage());
            controller.cambiaVisibilitaFigliPannelloPrincipale(false);
        }
        primaryStage.setOnCloseRequest((WindowEvent ev) -> { conservaBin(); });
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
