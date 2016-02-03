import java.sql.SQLException;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.control.ListView;

public class ListaMaterialiVisuale extends ListView {
    private ObservableList<Materiale> materiali;   
    private final GUIGestioneMagazzino GUIGestioneMagBind;
    private int MAX_ROWS;

    private void scambiaCurrentInLista() { //(1)
        int i=0;
        for(Materiale m: materiali) {
            if(m.getId() == GUIGestioneMagBind.getCurrent().getId()) {
                materiali.set(i, GUIGestioneMagBind.getCurrent());
                this.getSelectionModel().select(i);
                return;
            }
            i++;
        }           
    }
    public void caricaMateriali(int c) { //(2.1)
        ArchivioMagazzino am = new ArchivioMagazzino(MAX_ROWS);
        materiali.clear();
        try {
            materiali.addAll(am.caricaListaMateriali("", c));
            scambiaCurrentInLista();       
        } catch (SQLException ex) {
            GUIGestioneMagBind.mostraErroreToolbar("Errore nel collegamento al DB");
        }
    }
    
    public void caricaMateriali(String txt) { //(2.2)
        ArchivioMagazzino am = new ArchivioMagazzino(MAX_ROWS);
        materiali.clear();
        try {
            materiali.addAll(am.caricaListaMateriali(txt, -1));
            scambiaCurrentInLista();       
        } catch (SQLException ex) {
            GUIGestioneMagBind.mostraErroreToolbar("Errore nel collegamento al DB");
        }
    }
    
    public void caricaMateriali(String txt, int c) { //(2.2)
        ArchivioMagazzino am = new ArchivioMagazzino(MAX_ROWS);
        materiali.clear();
        try {
            materiali.addAll(am.caricaListaMateriali(txt, c));      
            scambiaCurrentInLista();
        } catch (SQLException ex) {
            GUIGestioneMagBind.mostraErroreToolbar("Errore nel collegamento al DB");
        }
    }
    
    public ObservableList<Materiale> getMateriali() {
        return materiali;
    }
    
    private void inviaLog() {
        try {
            ConfigurazioneXMLParametri params = ConfigurazioneXML.getDelegationLink().getParams();
            LoggerXML.logListaMaterialiVisuale(params.getPort(), params.getIpClient(), params.getIpServer());
        } catch (Exception ex) {
            GUIGestioneMagBind.mostraErroreToolbar("Errore nell'invio log");
        }
    }
    
    public ListaMaterialiVisuale() {
        super();
        GUIGestioneMagBind = GUIGestioneMagazzino.getDelegationLink();      
        materiali  = FXCollections.observableArrayList();
        
        ConfigurazioneXML config = ConfigurazioneXML.getDelegationLink();
        try {
            MAX_ROWS = config.getParams().getMAX_QUERY_RESULT();
        }
        catch (Exception e) {
            GUIGestioneMagBind.mostraErroreToolbar(e.getMessage());
        }
        
        this.setId("listaMateriali");
        super.getStyleClass().add("pannello");
        this.setItems(materiali);
        super.setMinSize(300, 183);
        super.setMaxSize(300, 183);
        this.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> { //(3)
            if(newValue == null || ((Materiale) newValue).getId() == GUIGestioneMagBind.getCurrent().getId()) return;
                GUIGestioneMagBind.setCurrent((Materiale) newValue);
                GUIGestioneMagBind.setTitoloTxtToolbar("Scheda materiale – " + ((Materiale) newValue).getNominativo());
                GUIGestioneMagBind.aggiornaPannelloPrincipale();
                inviaLog();
            }
        );
    }
   
}

/*
Commenti
Classe che si occupa di realizzare la lista dei risultati di ricerca.
Viene popolata dal pannello di ricerca che invoca tramite l'GUIGestioneMagazzino
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