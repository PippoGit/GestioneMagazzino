import java.sql.SQLException;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.control.ListView;

public class ListController extends ListView {
    private ObservableList<Materiale> materiali;   
    private final ApplicationController appConBind;

    private void scambiaCurrentInLista() { //(1)
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
    public void caricaMateriali(int c) { //(2.1)
        ArchivioMagazzino am = new ArchivioMagazzino(6);
        materiali.clear();
        try {
            materiali.addAll(am.caricaListaMateriali("", c));
            scambiaCurrentInLista();       
        } catch (SQLException ex) {
            appConBind.mostraErroreToolbar("Errore nel collegamento al DB");
        }
    }
    
    public void caricaMateriali(String txt) { //(2.2)
        ArchivioMagazzino am = new ArchivioMagazzino(6);
        materiali.clear();
        try {
            materiali.addAll(am.caricaListaMateriali(txt, -1));
            scambiaCurrentInLista();       
        } catch (SQLException ex) {
            appConBind.mostraErroreToolbar("Errore nel collegamento al DB");
        }
    }
    
    public void caricaMateriali(String txt, int c) { //(2.2)
        ArchivioMagazzino am = new ArchivioMagazzino(6);
        materiali.clear();
        try {
            materiali.addAll(am.caricaListaMateriali(txt, c));      
            scambiaCurrentInLista();
        } catch (SQLException ex) {
            appConBind.mostraErroreToolbar("Errore nel collegamento al DB");
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
        this.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> { //(3)
                if(newValue == null || ((Materiale) newValue).getId() == appConBind.getCurrent().getId()) return;
                appConBind.setCurrent((Materiale) newValue);
                appConBind.setTitoloTxtToolbar("Scheda materiale – " + ((Materiale) newValue).getNominativo());
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

1) Metodo utilizzato per "selezionare" nella lista il materiale corrente dopo aver effettuato una ricerca
si tratta di un'operazione superflua che però migliora l'esperienza per l'utente.

2) Metodo utilizzato per popolare la lista con dei materiali, viene utilizzata la tecnica dell'overloading
per effettuare la query giusta in base ai parametri scelti dall'utente. In particolare si ha:
 2.1 Carico tutti i materiali con idCategoria uguale a c
 2.2 Carico tutti i materiali aventi nominativo "simile" a txt
 2.3 Carico tutti i materiali aventi nominativo "simile" a txt e idCategoria uguale a c

3) Viene selezionato un elemento della lista, per tanto comunico all'application controller
di cambiare l'oggetto current e di avvisare il PannelloPrincipale di aggiornarsi per 
caricare il nuovo Materiale. Nel frattempo invia al Toolbar il nuovo titolo da impostare.
Se l'oggettoo selezionato è lo stesso che è al momento visualizzato è inutile ricaricarlo, si fa una
semplice return senza effettuare alcuna modifica.
*/