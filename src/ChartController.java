
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
    private final int[] disponibilita;
    
    final static String austria = "Telefonia";
    final static String brazil = "CCTV";
    final static String france = "Networking";

    public ChartController(int n) {
        super(8);
        
        Label titolo = new Label("Grafico disponibilità");
        titolo.getStyleClass().add("titolo");
       
        disponibilita = new int[n];
       
        setPadding(new Insets(16));
        getStyleClass().add("pannello");
        
       final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<>(xAxis,yAxis);
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Disponibilita");       
        series1.getData().add(new XYChart.Data(austria, 25601.34));
        series1.getData().add(new XYChart.Data(brazil, 20148.82));
        series1.getData().add(new XYChart.Data(france, 10000));
        
        bc.getStyleClass().add("bar-chart");
        
        super.setMinSize(300, 215);
        super.setMaxSize(300, 215);
        bc.getData().addAll(series1);
        getChildren().addAll(titolo, bc);
    } 
}
