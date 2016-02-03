import java.sql.SQLException;
import java.util.*;
import javafx.beans.value.*;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

public class PannelloRicercaVisuale extends VBox {
    private ArrayList<ToggleButton> toggleCategorie;
    private ToggleGroup gruppo;
    private HBox containerCategoria;
    private TextField barraRicerca;
    private GUIGestioneMagazzino GUIGestioneMagBind;
    
    public TextField getBarraRicerca(){
        return barraRicerca;
    }
    
    private void inviaLog(boolean ricerca, String info) {
        try {
            ConfigurazioneXMLParametri params = ConfigurazioneXML.getDelegationLink().getParams();
            if(ricerca)
                LoggerXML.logRicerca(params.getPort(), params.getIpClient(), params.getIpServer(), info);
            else 
                LoggerXML.logPressionePulsante(params.getPort(), params.getIpClient(), params.getIpServer(), info);
        } catch (Exception ex) {
            GUIGestioneMagBind.mostraErroreToolbar("Errore nell'invio log");
        }
    }
    
    private void caricaCategorieXML() { //(1)
        Categoria[] c; 
        ConfigurazioneXML config = ConfigurazioneXML.getDelegationLink();
        ArchivioMagazzino am = new ArchivioMagazzino();    

        try {
            c = config.getParams().getCategorie();
            GUIGestioneMagBind.setListaCategorie(c);

            for(int i=0; i<c.length; i++) {
                c[i].setDisponibilita(am.caricaDisponibilitaCategorie(i));
            }
        } catch (Exception ex) {
            if (ex instanceof SQLException)
                GUIGestioneMagBind.mostraErroreToolbar("Errore nel collegamento al DB");
            else {
                GUIGestioneMagBind.mostraErroreToolbar(ex.getMessage());
            }
        }
    }
    
    private void effettuaRicerca() { //(2)
        int categoria =-1;
        String text = barraRicerca.getText();

        if(gruppo.getSelectedToggle() != null) 
            categoria = (int) gruppo.getSelectedToggle().getUserData();
        GUIGestioneMagBind.ottieniDatiListaMaterialiDB(text, categoria);
        
        inviaLog(true, text);
    }
    
    private void configuraToggleCategorie() { //(3)
        Categoria [] c = GUIGestioneMagBind.getListaCategorie();
        
        for(int i=0; i<c.length; i++) {
            toggleCategorie.add(new ToggleButton()); //(2)
            toggleCategorie.get(i).setToggleGroup(gruppo);
            toggleCategorie.get(i).getStyleClass().add("categoria");
            toggleCategorie.get(i).setId("categoria"+i);            
            toggleCategorie.get(i).setMaxSize(300/c.length,53);
            toggleCategorie.get(i).setMinSize(300/c.length,53);    
            toggleCategorie.get(i).setUserData(c[i].getId());
        }
        containerCategoria.getChildren().addAll(toggleCategorie);
    }
    
    private void inizializzaComponenti() { //(4)
        this.barraRicerca = new TextField();
        this.gruppo = new ToggleGroup();
        this.containerCategoria = new HBox();
        this.GUIGestioneMagBind = GUIGestioneMagazzino.getDelegationLink();      
        this.toggleCategorie = new ArrayList();
        
        super.setMinSize(300, 123);
        super.setMaxSize(300, 123);
        this.setAlignment(Pos.TOP_CENTER);
        this.getStyleClass().add("pannello");
        
        barraRicerca.setMinSize(260, 30);
        barraRicerca.setMaxSize(260, 30);
        barraRicerca.setPromptText("Ricerca nel database...");
    }
    
    public void caricaCategorie() {
        caricaCategorieXML();
        configuraToggleCategorie();
    }
    
    public PannelloRicercaVisuale() {
        super(20);
        inizializzaComponenti();
        
        gruppo.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){ //(5)
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue == null) barraRicerca.setPromptText("Ricerca nel database...");
                else 
                {
                    String txt = GUIGestioneMagBind.getCategoria((int)newValue.getUserData()).getDescrizione();
                    barraRicerca.setPromptText("Ricerca " + txt + " nel database...");
                    inviaLog(false, "SelettoreCategoria: " + txt);
                }
            }
        });
        barraRicerca.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) effettuaRicerca();
            }
        });

        super.getChildren().addAll(containerCategoria, barraRicerca);
    }
}

/*
Commenti
Classe utilizzata per realizzare il pannello di ricerca. Estende la classe VBox. 
Si occupa di implementare i meccanismi che permetteranno all'utente di effettuare una ricerca,
inoltre carica dall'oggetto di preferenze (costruito dalla classe GestioneMagazzino effettuando un parse
dal file XML di configurazione) le categorie di materiale.

1) Carica le categorie dall'oggetto delle preferenze locali. Dopo aver caricato le categorie si occupa
di effettuare una query al database per ottenere per ciascuna categoria la disponibilità globale in
magazzino (informazioni che saranno utilizzate per permettere la realizzazione del grafico).

2) Metodo che richiede alla lista dei materiali di aggiornarsi richiedendo al database i materiali 
e le categorie richieste dall'utente.

3) Metodo che si occupa di configurare il toggleCategorie che permette all'utente di scegliere tra una 
categoria e l'altra.

4) Metodo che inizializza i componenti grafici

5) Registro operazioni da fare in caso in cui l'utente cambi la categoria. In particolare vado ad
aggiornare il placeholder sulla barra di ricerca.

6) In caso in cui l'utente prema "invio" mentre sta scrivendo nella barraRicerca -> il sistema deve 
effettuare una ricerca (chiamando il metodo apposito (2))
*/