import java.util.*;
import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SearchPanelController extends VBox {
    private final ArrayList<ToggleButton> categoria;
    private final ToggleGroup gruppo;
    private final HBox containerCategoria;
    private final TextField barraRicerca;
    private final ApplicationController bind;
    
    public TextField getBarraRicerca(){
        return barraRicerca;
    }    
    
    private void parseCategoriaXML() {
        int num_categorie = 3;
        Categoria[] c = new Categoria[num_categorie];        

        c[0] = new Categoria(0, "Networking");
        c[0].setDisponibilita(1);
        c[1] = new Categoria(1, "CCTV");
        c[1].setDisponibilita(0);      
        c[2] = new Categoria(2, "Telefonia");
        c[2].setDisponibilita(2);              
        bind.setListaCategorie(c);

        for(int i=0; i<num_categorie; i++) {
            categoria.add(new ToggleButton());

            categoria.get(i).setToggleGroup(gruppo);
            
            categoria.get(i).getStyleClass().add("categoria");
            categoria.get(i).setId("categoria"+i);            
            categoria.get(i).setMaxSize(300/num_categorie,53);
            categoria.get(i).setMinSize(300/num_categorie,53);    
            
            categoria.get(i).setUserData(c[i].getDescrizione());
        }
        
        containerCategoria.getChildren().addAll(categoria);
    }
    
    public SearchPanelController() {
        super(20);
        this.barraRicerca = new TextField();
        this.gruppo = new ToggleGroup();
        this.containerCategoria = new HBox();
        this.bind = ApplicationController.getDelegationLink();      
        this.categoria = new ArrayList();
        
        super.setMinSize(300, 123);
        super.setMaxSize(300, 123);
        this.setAlignment(Pos.TOP_CENTER);
        this.getStyleClass().add("pannello");
        
        this.parseCategoriaXML();
        gruppo.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                //Selezionato
                if(newValue == null) 
                    barraRicerca.setPromptText("Ricerca nel database...");
                else
                    barraRicerca.setPromptText("Ricerca " + newValue.getUserData() + " nel database...");
            }

        });
        
        barraRicerca.setMinSize(260, 30);
        barraRicerca.setMaxSize(260, 30);
        barraRicerca.setPromptText("Ricerca nel database...");
        
        super.getChildren().addAll(containerCategoria, barraRicerca);
    }
}
