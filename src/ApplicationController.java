
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.scene.control.ListView;

public class ListController extends ListView {
    private final ObservableList<Materiale> materiali;   
    private final ApplicationController bind;
    
    public void ottieniDatiMySQL() {
        materiali.addAll(new Materiale(1, "Telecamera CCTV", "Videosorveglianza", 0), 
                         new Materiale(2, "Centralino Uno", "Telefonia", 1), 
                         new Materiale(3, "Router Netgear 1xaa", "Networking", 1));        
    }
    
    public void aggiornaLista() {
        Materiale t = new Materiale();
        materiali.add(t);
        materiali.remove(t);    
    }
    
    public void segnalaModifica(int i) {
        Materiale m = materiali.get(i);     
        m.setNominativo(m.getNominativo() + " *");
        aggiornaLista();
    }
    
    public void modificaSalvata(int i) {
        Materiale m = materiali.get(i);
        m.setNominativo(m.getNominativo().substring(0, m.getNominativo().length()-2));
        aggiornaLista();
    }
    
    public ObservableList<Materiale> getMateriali() {
        return materiali;
    }
    
    public ListController() {
        super();
        bind = ApplicationController.getDelegationLink();      
        
        materiali  = FXCollections.observableArrayList();

        this.setId("listaMateriali");
        super.getStyleClass().add("pannello");
                
        this.setItems(materiali);
        super.setPrefSize(250, 414);
        
        this.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Materiale>() {
            @Override
            public void changed(ObservableValue<? extends Materiale> observable, Materiale oldValue, Materiale newValue) {
                bind.setCurrent(newValue);
                bind.setTitoloTxtMenu("Scheda materiale – " + newValue.getNominativo());
                bind.aggiornaSchedaMateriale();
            }
    });
    }
   
}
