import java.io.IOException;
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
    
    public void caricaPreferenzeXML() {
        final ConfigurazioneXML config = ConfigurazioneXML.getDelegationLink();

        Font.loadFont(GestioneMagazzino.class.getResource("font/Roboto/Roboto-Regular.ttf").toExternalForm(), 15);        
        Font.loadFont(GestioneMagazzino.class.getResource("font/Roboto/Roboto-Medium.ttf").toExternalForm(), 15);
        //scene.getStylesheets().add(config.getCss());                
    }
    
    private void caricaBin() {
        AppCache cache = new AppCache();
        try {
            controller.setCurrent(cache.carica());            
            controller.aggiornaPannelloPrincipale();            
            controller.setTitoloTxtToolbar("Scheda materiale – " + controller.getCurrent().getNominativo());
        }
        catch (IOException ex) {
            controller.mostraErroreToolbar("Si è verificato un errore nell'apertura del file di cache");
            controller.cambiaVisibilitaFigliPannelloPrincipale(false);
        }
    }
    
    private void conservaBin() {
        AppCache cache = new AppCache();
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
        
        caricaPreferenzeXML();
        controller.preparaElementiGrafici(root);
        controller.setCategorieGraficoDisponibilita();  
        caricaBin();
        controller.ottieniDatiListaMaterialiDB();
        
        primaryStage.setOnCloseRequest((WindowEvent ev) -> { conservaBin(); });
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
