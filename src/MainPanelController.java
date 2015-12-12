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
    
    public void caricaMateriale(Materiale m) {
        informazioniTxt[0].setText(m.getNominativo());
        informazioniTxt[1].setText(m.getCategoria().getDescrizione());
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
        if(!newValue.equalsIgnoreCase("funzionante") && //se ora non funziona
           selezionato.getCliente().length() == 0 && //e un non cliente l'aveva
           selezionato.getStato().equalsIgnoreCase("Funzionante")) //e se prima funzionava
            appConBind.diminuisciDisponibilitaCurrent(); //allora dimunuisci
        else if (newValue.equalsIgnoreCase("funzionante") &&  //se ora funziona
           !selezionato.getStato().equalsIgnoreCase("Funzionante") && //e prima non funzionava
           selezionato.getCliente().length() == 0) //e nessuno ce l'ha
            appConBind.aumentaDisponibilitaCurrent();

        selezionato.setStato(newValue);                
        appConBind.mostraModifiche();        
    }
    
    private void editClienteColumn(IstanzaMateriale selezionato, String newValue) {
         if(newValue.equals("") && //se lo porto in magazzino
            selezionato.getCliente().length() != 0 &&  //e non c'era già
            selezionato.getStato().equalsIgnoreCase("funzionante")) { //e funziona
             appConBind.aumentaDisponibilitaCurrent();
         }
         else if (newValue.length() > 0  && //se lo do a un cliente
                  selezionato.getCliente().length() == 0  && //e prima era in mag
                  selezionato.getStato().equalsIgnoreCase("funzionante")) { //e funziona
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
        
        c.setOnEditCommit(new EventHandler<CellEditEvent<IstanzaMateriale, String>>() {
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
    
    private void costruisciTabellaMonitor() {     
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
    
    public void cambiaVisibilitaFigli(boolean b) {
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
