import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.control.ListView;

public class ListController extends ListView {
    private final ObservableList<Materiale> materiali;   
    private final ApplicationController appConBind;
    
    public void caricaMateriali() {
        materiali.addAll(new Materiale(1, "Telecamera CCTV", appConBind.getCategoria(1), 0), 
                         new Materiale(2, "Centralino Uno", appConBind.getCategoria(2), 0), 
                         new Materiale(3, "Centralino Uno", appConBind.getCategoria(2), 0), 
                         new Materiale(4, "Router Netgear 1xaa", appConBind.getCategoria(0), 0));        
    }
    
    public ObservableList<Materiale> getMateriali() {
        return materiali;
    }
    
    public ListController() {
        super();
        appConBind = ApplicationController.getDelegationLink();      
        
        materiali  = FXCollections.observableArrayList();

        this.setId("listaMateriali");
        super.getStyleClass().add("pannello");
                
        this.setItems(materiali);
        super.setMinSize(300, 183);
        super.setMaxSize(300, 183);

        this.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                appConBind.setCurrent((Materiale) newValue);
                appConBind.setTitoloTxtMenu("Scheda materiale – " + ((Materiale) newValue).getNominativo());
                appConBind.aggiornaSchedaMateriale();
            }
        );
    }
   
}
