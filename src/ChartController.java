import javafx.geometry.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ChartController extends VBox {
    private Categoria[] categorie;
    private final BarChart<String, Number> istogramma;
    XYChart.Series dati;
    
    public void aggiornaDati() {
        for (int i=0; i<categorie.length; i++) {
            Categoria c = categorie[i];
            dati.getData().set(i, new XYChart.Data<>(c.getDescrizione(), c.getDisponibilita()));
        }
    }
   
    private void inizializzaDati() {
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