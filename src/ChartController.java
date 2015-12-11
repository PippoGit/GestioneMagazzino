
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author filipposcotto
 */
public class ChartController extends VBox {
    private Categoria[] categorie;
    private final BarChart<String,Number> bc;
    XYChart.Series series;
    
    public void aggiornaDati() {
        series.getData().clear();
        for (Categoria c : categorie) {
            series.getData().add(new XYChart.Data(c.getDescrizione(), c.getDisponibilita()));
        }
        bc.getData().setAll(series);
    }
   
    public void setCategorie(Categoria[] c) {
        categorie = c;
    }
    
    public ChartController() {
        super(8);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        Label titolo = new Label("Grafico disponibilità");
        series = new XYChart.Series();
        bc = new BarChart<>(xAxis,yAxis);
        
        titolo.getStyleClass().add("titolo");

        series.setName("Disponibilita");
        getStyleClass().add("pannello");
        setPadding(new Insets(16));
        super.setMinSize(300, 215);
        super.setMaxSize(300, 215);

        super.getChildren().addAll(titolo, bc);
    } 
}
