import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.control.ListView;

public class ListController extends ListView {
    private final ObservableList<Materiale> materiali;   
    private final ApplicationController bind;
    
    public void ottieniDatiMySQL() {
        materiali.addAll(new Materiale(1, "Telecamera CCTV", bind.getCategoria(1), 0), 
                         new Materiale(2, "Centralino Uno", bind.getCategoria(2), 1), 
                         new Materiale(3, "Centralino Uno", bind.getCategoria(2), 1), 
                         new Materiale(4, "Router Netgear 1xaa", bind.getCategoria(0), 1));        
    }
    
    /*
    public void aggiornaLista() {
        materiali.remove(bind.getCurrent());    
        materiali.add(bind.getCurrent());
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
   */
    
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
        super.setMinSize(300, 183);
        super.setMaxSize(300, 183);

        this.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                bind.setCurrent((Materiale) newValue);
                bind.setTitoloTxtMenu("Scheda materiale – " + ((Materiale) newValue).getNominativo());
                bind.aggiornaSchedaMateriale();
            }
        );
    }
   
}
