import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GestioneMagazzino extends Application {    
    private final ApplicationController controller;
    private final VBox root = new VBox();

    public GestioneMagazzino() {
        controller = ApplicationController.getDelegationLink();
    }
    
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(controller.getRoot(), 900, 640); 
                
        controller.preparaElementiGrafici(scene);
        controller.ottieniDatiMySQLListaMateriali();
        controller.setTitoloTxtMenu("Gestione Magazzino");
        controller.cambiaVisibilitaFigliSchedaMateriale(false);
        
        primaryStage.setTitle("Magazzino");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
