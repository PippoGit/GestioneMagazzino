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
    
    public void caricaMateriale(Materiale current) {
        //ObservableList<Node> childrens = informazioni.getChildren();
        informazioniTxt[0].setText(current.getNominativo());
        informazioniTxt[1].setText(current.getCategoria().getDescrizione());
        informazioniTxt[2].setText(Integer.toString(current.getDisponibilita()));
        
        if(current.getDisponibilita() <= 0) {
            informazioniTxt[2].setStyle("-fx-text-fill: #E0082A;");
            informazioniTxt[2].setText("Non disponibile");
        }
        else if (current.getDisponibilita() <= 5) {
            informazioniTxt[2].setStyle("-fx-text-fill: #E0B508;");
        }
        else {
            informazioniTxt[2].setStyle("-fx-text-fill: #088C74;");
        }
        
        current.caricaIstanzeDB();
        listaOrdini = current.getIstanze();
        monitor.setItems(listaOrdini);
     }
    
    private void costruisciTabellaMonitor() {
        TableColumn statoColumn;
        statoColumn = new TableColumn("STATO");
        statoColumn.setResizable(false);
        statoColumn.setMinWidth(120);
        statoColumn.setSortable(false);
        statoColumn.setCellValueFactory(
            new PropertyValueFactory<>("stato")        
        );
        

        statoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        statoColumn.setOnEditCommit(new EventHandler<CellEditEvent<IstanzaMateriale, String>>() {
            @Override
            public void handle(CellEditEvent<IstanzaMateriale, String> event) {
                ((IstanzaMateriale) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                        ).setStato(event.getNewValue());
                appConBind.mostraModifiche();
            }
        });
    
        
        TableColumn clienteColumn;
        clienteColumn = new TableColumn("CLIENTE");
        clienteColumn.setResizable(false);
        clienteColumn.setSortable(false);        
        clienteColumn.setMinWidth(260);
        
        clienteColumn.setCellValueFactory(
            new PropertyValueFactory<>("cliente")        
        );
        
        clienteColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        clienteColumn.setOnEditCommit(new EventHandler<CellEditEvent<IstanzaMateriale, String>>() {
            @Override
            public void handle(CellEditEvent<IstanzaMateriale, String> event) { 
               IstanzaMateriale selezionato;
                selezionato = ((IstanzaMateriale) event.getTableView().getItems().get(
                        event.getTablePosition().getRow()));
                
                String ov = selezionato.getCliente();
                selezionato.setCliente(event.getNewValue());
                
                System.out.println(event.getNewValue());

                if(event.getNewValue().equals("") &&
                   ov.length() != 0) {
                    appConBind.aumentaDisponibilitaCurrent();
                }
                else if (event.getNewValue().length() > 0  && 
                         ov.length() == 0) {
                    appConBind.diminuisciDisponibilitaCurrent();
                }
                
                appConBind.mostraModifiche();
            }
        });
        
        TableColumn codiceMaterialeColumn;
        codiceMaterialeColumn = new TableColumn("CODICE MATERIALE");
        codiceMaterialeColumn.setResizable(false);
        codiceMaterialeColumn.setEditable(false);
        codiceMaterialeColumn.setMinWidth(138);
        codiceMaterialeColumn.setSortable(false);
        
        codiceMaterialeColumn.setCellValueFactory(
            new PropertyValueFactory<>("codiceMateriale")        
        );
                
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
