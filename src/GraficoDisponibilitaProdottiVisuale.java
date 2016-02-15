import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GraficoDisponibilitaProdottiVisuale extends VBox {
    private Categoria[] categorie;
    private final BarChart<String, Number> istogrammaDisponibilita;
    private final BarChart<String, Number> istogrammaProdotti;

    private final Label titolo;
    XYChart.Series datiDisponibilita;
    XYChart.Series datiProdotti;
    
    
    public void aggiornaDatiDisponibilita() { //(1)
        for (int i=0; i < categorie.length; i++) {
            Categoria c = categorie[i];
            datiDisponibilita.getData().set(i, new XYChart.Data<>(c.getDescrizione(), c.getDisponibilita()));
        }
    }
   
    private void inizializzaDatiDisponibilita() { //(2)
        datiDisponibilita = new XYChart.Series();
        datiDisponibilita.setName("Disponibilita");
  
        for (Categoria c : categorie)
            datiDisponibilita.getData().add(new XYChart.Data<>(c.getDescrizione(), c.getDisponibilita()));
        
        istogrammaDisponibilita.getData().addAll(datiDisponibilita);        
    }
   
    private void inizializzaDatiProdotti() { //(2)
        ArchivioMagazzino am = new ArchivioMagazzino(5);
        try {
            datiProdotti = am.caricaDatiGraficoProdotti();
            istogrammaProdotti.getData().addAll(datiProdotti);        
        } catch (SQLException ex) {
            GUIGestioneMagazzino.getDelegationLink().mostraErroreToolbar("#6002 Errore caricamento dati prodotti");
            System.out.println(ex.getMessage());
        }

    }
    
    public void setCategorie(Categoria[] c) {
        categorie = c;
        inizializzaDatiDisponibilita();
    }
    
    private void cambiaIstogramma(boolean r) {
        Node rem = (r)?istogrammaDisponibilita:istogrammaProdotti;
        Node add = (r)?istogrammaProdotti:istogrammaDisponibilita;
        if(this.getChildren().contains(add)) return;
        rem.setVisible(false);
        add.setVisible(true);
        this.getChildren().remove(rem);
        this.getChildren().add(add);
        titolo.setText((r)?"Grafico prodotti":"Grafico disponibilita");

    }
    private void inviaLog(boolean r) {
        try {
            ConfigurazioneXMLParametri params = ConfigurazioneXML.getDelegationLink().getParams();
            LoggerXML.logPressionePulsante(params.getPort(), params.getIpClient(), params.getIpServer(), "CambioGrafico: " + ((r)?"Prodotti":"Disponibilita"));
        } catch (Exception ex) {
            GUIGestioneMagazzino.getDelegationLink().mostraErroreToolbar("#4001 Errore nell'invio log");
        }
    }
    
    private void configuraHeader(BorderPane header) {        
        Button headerButton[] = new Button[2];
       
        for(int i=0; i<2; i++) {
            headerButton[i] = new Button((i==0)?">":"<");
            headerButton[i].getStyleClass().add("chartButton");
            headerButton[i].setOnAction((ActionEvent e) -> {
                Button source = (Button) e.getSource();
                this.cambiaIstogramma(source.getText().equalsIgnoreCase(">"));
                this.inviaLog(source.getText().equalsIgnoreCase(">"));
            });
        }
        
        header.setRight(headerButton[0]);
        header.setCenter(titolo);
        header.setLeft(headerButton[1]);    
    }
    
    private void inizializzaComponenti(BorderPane header) {
        istogrammaDisponibilita.setAnimated(false);
        istogrammaProdotti.setAnimated(false);

        titolo.getStyleClass().add("titolo");
        getStyleClass().add("pannello");
        setPadding(new Insets(16));
        super.setMinSize(300, 215);
        super.setMaxSize(300, 215);
    }
    
    public GraficoDisponibilitaProdottiVisuale() {
        super(8);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        final BorderPane header = new BorderPane();
        
        titolo = new Label("Grafico disponibilità");
        istogrammaDisponibilita = new BarChart<>(xAxis,yAxis);        
        istogrammaProdotti = new BarChart<>(xAxis2, yAxis2);
        
        configuraHeader(header);
        inizializzaComponenti(header);
        inizializzaDatiProdotti();

        super.getChildren().addAll(header, istogrammaDisponibilita);
    } 
}

/*
Commenti
Classe che si occupa di realizzare il pannello contenente l'istogrammaDisponibilita della disponibilità in
magazzino delle varie categorie.

1) Aggiorna i datiDisponibilita a seguito di una modifica alla disponibilità di una categoria.

2) Inizializza i datiDisponibilita del grafico. Viene chiamato dopo che vengono settate le categorie di 
materiale (all'apertura del programma) prima che l'utente possa fare modifiche ai datiDisponibilita. 
*/