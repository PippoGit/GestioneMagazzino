import javafx.geometry.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ChartController extends VBox {
    private Categoria[] categorie;
    private final BarChart<String, Number> istogramma;
    XYChart.Series dati;
    
    public void aggiornaDati() { //(1)
        for (int i=0; i < categorie.length; i++) {
            Categoria c = categorie[i];
            dati.getData().set(i, new XYChart.Data<>(c.getDescrizione(), c.getDisponibilita()));
        }
    }
   
    private void inizializzaDati() { //(2)
        dati = new XYChart.Series();
        dati.setName("Disponibilita");
  
        for (Categoria c : categorie) {
            dati.getData().add(new XYChart.Data<>(c.getDescrizione(), c.getDisponibilita()));
        }
        
        istogramma.getData().addAll(dati);        
    }
    
    public void setCategorie(Categoria[] c) {
        categorie = c;
        inizializzaDati();
    }
    
    public ChartController() {
        super(8);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        Label titolo = new Label("Grafico disponibilità");
        istogramma = new BarChart<>(xAxis,yAxis);
        istogramma.setAnimated(false);
        
        titolo.getStyleClass().add("titolo");

        getStyleClass().add("pannello");
        setPadding(new Insets(16));
        super.setMinSize(300, 215);
        super.setMaxSize(300, 215);

        super.getChildren().addAll(titolo, istogramma);
    } 
}

/*
Commenti
Classe che si occupa di realizzare il pannello contenente l'istogramma della disponibilità in
magazzino delle varie categorie.

1) Aggiorna i dati a seguito di una modifica alla disponibilità di una categoria.

2) Inizializza i dati del grafico. Viene chiamato dopo che vengono settate le categorie di 
materiale (all'apertura del programma) prima che l'utente possa fare modifiche ai dati. 
*/