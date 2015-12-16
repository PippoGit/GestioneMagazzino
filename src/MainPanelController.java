import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;

public class MainPanelController extends VBox {
    private Label titoloInformazioni;
    private Label titoloMonitor;
    private boolean visibilitaFigli;
    
    private final GridPane informazioni;
    private final TextField[] informazioniTxt = new TextField[3];
    
    private ObservableList<IstanzaMateriale> listaOrdini;
    private final TableView<IstanzaMateriale> monitor = new TableView<>();
    
    final private ApplicationController appConBind;
    
    public void caricaMateriale(Materiale m) { //(1)
        
        //m.setCategoria(m.getCategoria());
        informazioniTxt[0].setText(m.getNominativo());
        informazioniTxt[1].setText(appConBind.getCategoria(m.getCategoria()).getDescrizione());
        informazioniTxt[2].setText(Integer.toString(m.getDisponibilita()));
        
        if(m.getDisponibilita() <= 0) {
            informazioniTxt[2].setStyle("-fx-text-fill: #E0082A;");
            informazioniTxt[2].setText("Non disponibile");
        }
        else if (m.getDisponibilita() <= 5) {
            informazioniTxt[2].setStyle("-fx-text-fill: #E0B508;");
        }
        else {
            informazioniTxt[2].setStyle("-fx-text-fill: #088C74;");
        }
        
        m.caricaIstanzeDB();
        listaOrdini = m.getIstanze();
        monitor.setItems(listaOrdini);
     }
    
    private void editStatoColumn(IstanzaMateriale selezionato, String newValue) {
        if(!newValue.equalsIgnoreCase("funzionante") && // a
           selezionato.getCliente().length() == 0 &&  // b
           selezionato.getStato().equalsIgnoreCase("Funzionante")) //c
            appConBind.diminuisciDisponibilitaCurrent(); 
        else if (newValue.equalsIgnoreCase("funzionante") &&  //d
           !selezionato.getStato().equalsIgnoreCase("Funzionante") && //e
           selezionato.getCliente().length() == 0) //f
            appConBind.aumentaDisponibilitaCurrent();

        selezionato.setStato(newValue);                
        appConBind.mostraModifiche();        
    }
    
    private void editClienteColumn(IstanzaMateriale selezionato, String newValue) { //(3)
         if(newValue.equals("") && // a
            selezionato.getCliente().length() != 0 &&  // b
            selezionato.getStato().equalsIgnoreCase("funzionante")) { // c
             appConBind.aumentaDisponibilitaCurrent();
         }
         else if (newValue.length() > 0  && // d
                  selezionato.getCliente().length() == 0  && // e
                  selezionato.getStato().equalsIgnoreCase("funzionante")) { // f
             appConBind.diminuisciDisponibilitaCurrent();
         }

         selezionato.setCliente(newValue);
         appConBind.mostraModifiche();        
    }
    
    private void costruisciColonna(TableColumn c, String n, int w) {
        c.setResizable(false);
        c.setMinWidth(w);
        c.setSortable(false);
        c.setEditable(!n.equalsIgnoreCase("codiceMateriale"));
        c.setCellValueFactory(
            new PropertyValueFactory<>(n)        
        );
        
        c.setCellFactory(TextFieldTableCell.forTableColumn());
        
        c.setOnEditCommit(new EventHandler<CellEditEvent<IstanzaMateriale, String>>() { //(4)
            @Override
            public void handle(CellEditEvent<IstanzaMateriale, String> event) {
                IstanzaMateriale selezionato;
                selezionato = ((IstanzaMateriale) event.getTableView().getItems().get(
                        event.getTablePosition().getRow()));                
                if(n.equalsIgnoreCase("stato"))
                    editStatoColumn(selezionato, event.getNewValue());
                else if(n.equalsIgnoreCase("cliente"))
                    editClienteColumn(selezionato, event.getNewValue());
            }
        });        
    }
    
    private void costruisciTabellaMonitor() { //(5)
        TableColumn clienteColumn = new TableColumn("CLIENTE");
        TableColumn statoColumn = new TableColumn("STATO");
        TableColumn codiceMaterialeColumn = new TableColumn("CODICE MATERIALE");
        
        costruisciColonna(statoColumn, "stato", 120);
        costruisciColonna(clienteColumn, "cliente", 260);
        costruisciColonna(codiceMaterialeColumn, "codiceMateriale", 138);
        
        monitor.setEditable(true);       
        monitor.getColumns().addAll(statoColumn, codiceMaterialeColumn, clienteColumn);             
    }
    
    private void preparaLayout() {
        Label descrizioneLbl = new Label("DESCRIZIONE");
        Label categoriaLbl = new Label("CATEGORIA");
        Label disponibilitaLbl = new Label("DISPONIBILITA");
        
        for(int i=0; i<informazioniTxt.length; i++) {
            informazioniTxt[i] = new TextField("");
            informazioniTxt[i].setEditable(false);
            informazioniTxt[i].getStyleClass().add("informazioniTxt");
            informazioniTxt[i].setId("informazioniTxt"+i);
            informazioniTxt[i].setMinWidth(370);
        }

        informazioni.addColumn(1, descrizioneLbl, categoriaLbl, disponibilitaLbl);
        informazioni.setHgap(24);
        informazioni.setVgap(3);
        informazioni.addColumn(2, informazioniTxt);
        
        this.getStyleClass().add("pannello");        
        super.setPrefSize(560, 600);
        this.setPadding(new Insets(16));
        
        titoloInformazioni = new Label("Informazioni");
        titoloInformazioni.getStyleClass().add("titolo");
        titoloMonitor = new Label("Monitoraggio stato e spostamenti");
        titoloMonitor.getStyleClass().add("titolo");  
    }
    
    public void cambiaVisibilitaFigli(boolean b) { //(6)
        if(visibilitaFigli == b) { return; }
        
        ObservableList<Node> children = this.getChildren();
        
        children.stream().forEach((n) -> {
            n.setVisible(b);
        });
        
        visibilitaFigli = b;
    }
    
    public MainPanelController() {
        super(8);
        appConBind = ApplicationController.getDelegationLink();
        
        visibilitaFigli = true;
        informazioni = new GridPane();
        informazioni.setId("informazioni");

        preparaLayout();
        costruisciTabellaMonitor();
        super.getChildren().addAll(titoloInformazioni, informazioni, titoloMonitor, monitor);
    }
}

/*
Commenti
Classe che si occupa di realizzare il pannello principale dell'interfaccia grafica.

1) Carico un materiale nell'interfaccia grafica. Carico anche tutte le IstanzeMateriale per quel
materiale senza pulire la cache (se sono già state caricate in passato non le riscarico).

2) Metodo che permette allo StatoColumn di venire modificato con successo.
In particolare deve DIMINUIRE la disponibilità di quel materiale se:
a. Il nuovo stato è tale da renderlo inutilizzabile (non è "Funzionante")
b. Se nessun cliente prima aveva installato 
c. Se prima funzionava

Deve invece AUMENTARE la disponibilita del materiale se:
d. Il nuovo stato lo rende utilizzabile (se è Funzionante)
e. Il vecchio stato era diverso da Funzionante
f. Nessun cliente l'aveva installato

3) Metodo che permette alla cella di ClienteColumn di venire modificato con successo.
In particolare deve AUMENTARE la disponibilità di quel materiale se:
a. Il nuovo cliente è vuoto, ovvero lo porto in magazzino
b. Prima non era già in magazzino
c. Se prima funzionava

Deve invece DIMINUIRE la disponibilita del materiale se:
d. Installo il materiale ad un cliente (metto un valore diverso da vuoto)
e. Il vecchio cliente era vuoto (era in magazzino)
f. E prima funzionava

4) Costruisco le colonne della tabella, facendo attenzione a registrare gli eventi giusti
in bsae al nome della "CellValueFactory" per distinguere tra le giuste azioni da fare in fase
di modifica.

5) Costruisco la tabella Monitor

6) Metodo utilizzato per cambiare la visibilità degli elementi grafici contenuti dentro il
pannello principale. Per motivi "stilistici" si è scelto di non visualizzare alcun componente
in fase di PRIMA apertura, ovvero quando ancora non è presente una cache e non sono presenti 
materiali da mostrare. Al posto di mostrare una brutta form vuota si è scelto di non mostrare 
niente, è quindi necessario questo metodo che velocemente cambia la visibilità dei figli quando
necessario.
*/