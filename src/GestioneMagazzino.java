import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GestioneMagazzino extends Application {    
    private final ApplicationController controller;
    //private Object preferenze;

    public GestioneMagazzino() {
        controller = ApplicationController.getDelegationLink();
    }
    
    public void caricaPreferenzeXML() {
        Font.loadFont(GestioneMagazzino.class.getResource("font/Roboto/Roboto-Regular.ttf").toExternalForm(), 15);        
        Font.loadFont(GestioneMagazzino.class.getResource("font/Roboto/Roboto-Medium.ttf").toExternalForm(), 15);        
    }
    
    @Override
    public void start(Stage primaryStage) {
        final VBox root = new VBox();
        final Scene scene = new Scene(root, 900, 640); 
        
        /*preferenze.*/caricaPreferenzeXML();
        scene.getStylesheets().add("style/StyleGestioneMagazzino.css");        

        controller.preparaElementiGrafici(root);
        
        controller.ottieniDatiMySQLListaMateriali();
        controller.setCategorieGraficoDisponibilita();
        
        controller.setTitoloTxtMenu("Gestione Magazzino");
        controller.cambiaVisibilitaFigliPannelloPrincipale(false);
        
        primaryStage.setTitle("Magazzino");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
