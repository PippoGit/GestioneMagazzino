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

        this.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> { //(1)
                if(((Materiale) newValue).getId() == appConBind.getCurrent().getId())
                    return;
                appConBind.setCurrent((Materiale) newValue);
                appConBind.setTitoloTxtMenu("Scheda materiale – " + ((Materiale) newValue).getNominativo());
                appConBind.aggiornaPannelloPrincipale();
            }
        );
    }
   
}

/*
Commenti
Classe che si occupa di realizzare la lista dei risultati di ricerca.
Viene popolata dal pannello di ricerca che invoca tramite l'ApplicationController
il metodo caricaMateriali() dopo aver eseguito la query al database.

1) Viene selezionato un elemento della lista, per tanto comunico all'application controller
di cambiare l'oggetto current e di avvisare il PannelloPrincipale di aggiornarsi per 
caricare il nuovo Materiale. Nel frattempo invia al Toolbar il nuovo titolo da impostare.
*/