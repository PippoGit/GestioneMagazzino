import java.sql.SQLException;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.control.ListView;

public class ListController extends ListView {
    private ObservableList<Materiale> materiali;   
    private final ApplicationController appConBind;

    private void scambiaCurrentInLista() {
        int i=0;
        for(Materiale m: materiali) {
            if(m.getId() == appConBind.getCurrent().getId()) {
                materiali.set(i, appConBind.getCurrent());
                this.getSelectionModel().select(i);
                return;
            }
            i++;
        }           
    }
    public void caricaMateriali(int c) {
        ArchivioMagazzino am = new ArchivioMagazzino(6);
        materiali.clear();
        try {
            materiali.addAll(am.caricaListaMateriali("", c));
            scambiaCurrentInLista();       
        } catch (SQLException ex) {
            appConBind.mostraErroreMenu("Errore nel collegamento al DB");
        }
    }
    
    public void caricaMateriali(String txt) {
        ArchivioMagazzino am = new ArchivioMagazzino(6);
        materiali.clear();
        try {
            materiali.addAll(am.caricaListaMateriali(txt, -1));
            scambiaCurrentInLista();       
        } catch (SQLException ex) {
            appConBind.mostraErroreMenu("Errore nel collegamento al DB");
        }
    }
    
    public void caricaMateriali(String txt, int c) {
        ArchivioMagazzino am = new ArchivioMagazzino(6);
        materiali.clear();
        try {
            materiali.addAll(am.caricaListaMateriali(txt, c));      
            scambiaCurrentInLista();
        } catch (SQLException ex) {
            appConBind.mostraErroreMenu("Errore nel collegamento al DB");
        }
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
                if(newValue == null || ((Materiale) newValue).getId() == appConBind.getCurrent().getId())
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