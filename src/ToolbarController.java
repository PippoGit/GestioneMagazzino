//import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ToolbarController extends BorderPane {
    private final Label labelTitolo;
    private final Button add;
    private final Button remove;
    private final Button addOrdine;
    private final ApplicationController bind;
    
    public String getTitoloTxt() {
        return labelTitolo.getText();
    }
    
    public void setTitoloTxt(String t) {
        labelTitolo.setText(t);
    }
    
    private void configuraStileToolbar() {
        add.setId("tbb-add");
        remove.setId("tbb-remove");
        addOrdine.setId("tbb-addOrdine");
        this.setId("toolBar");

        this.setMinHeight(55);        
        this.setMaxHeight(55);        
        //this.setPrefHeight(55);        
        add.setMinSize(60, 45);
        add.getStyleClass().add("toolBar-button");
        remove.setMinSize(60, 45);
        remove.getStyleClass().add("toolBar-button");
        addOrdine.setMinSize(60, 45);
        addOrdine.getStyleClass().add("toolBar-button");    
        
        labelTitolo.getStyleClass().add("titolo");
    }
    
    private void posizionaComponenti() {
        HBox left = new HBox();
        HBox right = new HBox(8);
        
        this.setCenter(labelTitolo);
        left.setPadding(new Insets(0, 0, 0, 16));
        left.getChildren().add(add);
        left.setAlignment(Pos.CENTER);
        this.setLeft(left);    
        right.setPadding(new Insets(0, 16, 0, 0));
        right.setAlignment(Pos.CENTER);
        right.getChildren().addAll(addOrdine);//, remove);
        this.setRight(right);        
    }
    
    private void configuraPulsanti() {
        addOrdine.setOnAction((ActionEvent e) -> {
            //salva
            if(bind.getCurrent().isModificato()) {
                bind.getCurrent().setModificato(false);
                bind.modificaSalvataElementoCorrenteListaMateriali();
                setTitoloTxt(getTitoloTxt().substring(0, getTitoloTxt().length()-2));
                bind.aggiornaSchedaMateriale();
            }
        });
    }
    
    public ToolbarController(String titolo){
        super();
        this.add = new Button();
        this.remove = new Button();
        this.addOrdine = new Button();
        this.labelTitolo = new Label(titolo);
        bind = ApplicationController.getDelegationLink();      
        
        configuraStileToolbar();
        posizionaComponenti();
        configuraPulsanti();
    }
}
