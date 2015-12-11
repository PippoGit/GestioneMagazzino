import javafx.geometry.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ChartController extends VBox {
    private Categoria[] categorie;
    private final BarChart<String, Number> bc;
    XYChart.Series series;
    
    public void aggiornaDati() {
        for (int i=0; i<categorie.length; i++) {
            Categoria c = categorie[i];
            series.getData().set(i, new XYChart.Data<>(c.getDescrizione(), c.getDisponibilita()));
        }
    }
   
    private void inizializzaDati() {
        series = new XYChart.Series();
        series.setName("Disponibilita");
  
        for (int i=0; i<categorie.length; i++) {
            Categoria c = categorie[i];
            series.getData().add(new XYChart.Data<>(c.getDescrizione(), c.getDisponibilita()));
        }
        
        bc.getData().addAll(series);        
    }
    
    public void setCategorie(Categoria[] c) {
        categorie = c;
        inizializzaDati();
        System.out.println("Porcoddi");
    }
    
    public ChartController() {
        super(8);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        Label titolo = new Label("Grafico disponibilità");
        bc = new BarChart<>(xAxis,yAxis);
        bc.setAnimated(false);
        
        titolo.getStyleClass().add("titolo");

        getStyleClass().add("pannello");
        setPadding(new Insets(16));
        super.setMinSize(300, 215);
        super.setMaxSize(300, 215);

        super.getChildren().addAll(titolo, bc);
    } 
}

/*

public class ChartController extends VBox {
    private Categoria[] categorie;
    private final BarChart<String,Number> bc;
    XYChart.Series series;
    ObservableList<XYChart.Data<XAxes, YAz>> datiGrafico;
    
    public void aggiornaDati() {
        for (int i =0; i<categorie.length; i++) {
            datiGrafico.get(i).setYValue(categorie[i].getDisponibilita());
        }
    }
    
    public void setCategorie(Categoria[] c) {
        categorie = c;
        series.set(datiGrafico);
    }
    
    public ChartController() {
        super(8);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        Label titolo = new Label("Grafico disponibilità");
        series = new XYChart.Series();
        
        bc = new BarChart<>(xAxis,yAxis);
        bc.getData().add(series);
        
        titolo.getStyleClass().add("titolo");

        series.setName("Disponibilita");
        getStyleClass().add("pannello");
        setPadding(new Insets(16));
        super.setMinSize(300, 215);
        super.setMaxSize(300, 215);

        super.getChildren().addAll(titolo, bc);
    } 
}

*/