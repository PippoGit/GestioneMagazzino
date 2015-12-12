//import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ToolbarController extends BorderPane {
    private final Label labelTitolo;

    private final Button save;
    private final ApplicationController appConBind;
    
    public String getTitoloTxt() {
        return labelTitolo.getText();
    }
    
    public void setTitoloTxt(String t) {
        labelTitolo.setText(t);
    }
    
    private void configuraStileToolbar() {

        save.setId("tbb-save");
        this.setId("toolBar");

        this.setMinHeight(55);        
        this.setMaxHeight(55);        

        save.setMinSize(60, 45);
        save.getStyleClass().add("toolBar-button");    
        
        labelTitolo.getStyleClass().add("titolo");
    }
    
    private void posizionaComponenti() {
        HBox left = new HBox();
        HBox right = new HBox(8);
        
        this.setCenter(labelTitolo);
        right.setPadding(new Insets(0, 16, 0, 0));
        right.setAlignment(Pos.CENTER);
        right.getChildren().addAll(save);
        left.setMinWidth(60);
        left.setMaxWidth(60);
        this.setRight(right);
        this.setLeft(left);         
    }
    
    private void configuraPulsanti() {
        save.setOnAction((ActionEvent e) -> {
            //salva
            if(appConBind.getCurrent().isModificato()) {
                appConBind.getCurrent().setModificato(false);
                //bind.modificaSalvataElementoCorrenteListaMateriali();
                //setTitoloTxt(getTitoloTxt().substring(0, getTitoloTxt().length()-2));
                appConBind.aggiornaSchedaMateriale();
            }
        });
    }
    
    public ToolbarController(String titolo){
        super();

        this.save = new Button();
        this.labelTitolo = new Label(titolo);
        appConBind = ApplicationController.getDelegationLink();      
        
        configuraStileToolbar();
        posizionaComponenti();
        configuraPulsanti();
    }
}
