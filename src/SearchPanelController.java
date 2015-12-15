import java.util.*;
import javafx.beans.value.*;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

public class SearchPanelController extends VBox {
    private final ArrayList<ToggleButton> toggleCategorie;
    private final ToggleGroup gruppo;
    private final HBox containerCategoria;
    private final TextField barraRicerca;
    private final ApplicationController appConBind;
    
    public TextField getBarraRicerca(){
        return barraRicerca;
    }    
    
    private void caricaCategorieXML() {
        int num_categorie = 3;
        Categoria[] c = new Categoria[num_categorie];        

        c[0] = new Categoria(0, "Networking");
        c[0].setDisponibilita(1);
        c[1] = new Categoria(1, "CCTV");
        c[1].setDisponibilita(0);      
        c[2] = new Categoria(2, "Telefonia");
        c[2].setDisponibilita(2);              
        appConBind.setListaCategorie(c);

        for(int i=0; i<num_categorie; i++) {
            toggleCategorie.add(new ToggleButton());

            toggleCategorie.get(i).setToggleGroup(gruppo);
            
            toggleCategorie.get(i).getStyleClass().add("categoria");
            toggleCategorie.get(i).setId("categoria"+i);            
            toggleCategorie.get(i).setMaxSize(300/num_categorie,53);
            toggleCategorie.get(i).setMinSize(300/num_categorie,53);    
            
            toggleCategorie.get(i).setUserData(c[i].getId());
        }
        
        containerCategoria.getChildren().addAll(toggleCategorie);
    }
    
    public SearchPanelController() {
        super(20);
        this.barraRicerca = new TextField();
        this.gruppo = new ToggleGroup();
        this.containerCategoria = new HBox();
        this.appConBind = ApplicationController.getDelegationLink();      
        this.toggleCategorie = new ArrayList();
        
        super.setMinSize(300, 123);
        super.setMaxSize(300, 123);
        this.setAlignment(Pos.TOP_CENTER);
        this.getStyleClass().add("pannello");
        
        this.caricaCategorieXML();
        gruppo.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                //Selezionato
                if(newValue == null) {
                    barraRicerca.setPromptText("Ricerca nel database...");
                }
                else
                    barraRicerca.setPromptText("Ricerca " + appConBind.getCategoria((int)newValue.getUserData()).getDescrizione() + " nel database...");
            }

        });
        
        barraRicerca.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String text = barraRicerca.getText();
                    if(text.length() == 0) {
                        return;
                    }
                    int categoria =-1;
                    
                    if(gruppo.getSelectedToggle() != null)
                        categoria = (int) gruppo.getSelectedToggle().getUserData();
                    
                    appConBind.ottieniDatiListaMaterialiDB(text, categoria);
                }
            }
        });
        
        barraRicerca.setMinSize(260, 30);
        barraRicerca.setMaxSize(260, 30);
        barraRicerca.setPromptText("Ricerca nel database...");
        
        super.getChildren().addAll(containerCategoria, barraRicerca);
    }
}

/*
    NOTE SULLE QUERY:

Ottenere disponibilita di un materiale
SELECT COUNT(*) AS disponibilita, materiale
FROM IstanzeMateriale
WHERE cliente = "" AND materiale = 1 AND stato = "funzionante"
GROUP BY materiale;

Ottenere monitor di un materiale
SELECT *
FROM IstanzeMateriale
WHERE materiale = 1;

Ottieni disponibilita per categoria
SELECT COUNT(*) AS disponibilita, categoria
FROM IstanzeMateriale INNER JOIN materiale ON idMateriale = materiale
WHERE cliente = "" AND stato = "funzionante"
GROUP BY categoria;




*/