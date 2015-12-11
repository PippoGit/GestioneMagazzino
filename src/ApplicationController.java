import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class ApplicationController {  
    private final VBox root = new VBox();
    
    private ToolbarController menu;
    private SearchPanelController pannelloRicerca;
    private ListController listaMateriali;
    private MainPanelController schedaMateriale;
    private ChartController graficoDisponibilita;
    
    private Materiale current;
    private Categoria[] listaCategorie;
    
    private static final ApplicationController DELEGATIONLINK = new ApplicationController(); //Singleton
    
    private ApplicationController() {        
        this.current = new Materiale();
    }    
    
    public static ApplicationController getDelegationLink() {
        return DELEGATIONLINK;
    }
    
    public Pane getRoot() {
        return root;
    }
    
    public Materiale getCurrent() {
        return current;
    }
    public void setCurrent(Materiale m) {
        current = m;
    }
    
    public void setListaCategorie (Categoria[] c) {
        listaCategorie = c;
    }
    
    public Categoria getCategoria(int i){
        return listaCategorie[i];
    }
      
    public void aumentaDisponibilitaCurrent() {
        current.aumentaDisponibilita();
    }
    
    public void diminuisciDisponibilitaCurrent() {
        current.diminuisciDisponibilita();
    }
    
    public void aggiornaSchedaMateriale() {
        schedaMateriale.caricaMateriale(current);
        schedaMateriale.cambiaVisibilitaFigli(true);           
    }
    
    public void setTitoloTxtMenu(String txt) {
        menu.setTitoloTxt(txt);
    }
    
    public String getTitoloTxtMenu(String txt) {
        return menu.getTitoloTxt();
    }
    
    /*
    public void mostraModifiche() {
        if(!current.isModificato()) {
            current.setModificato(true);
            listaMateriali.segnalaModifica(listaMateriali.getSelectionModel().getSelectedIndex());
            menu.setTitoloTxt(menu.getTitoloTxt() + " *");
        }
        aggiornaSchedaMateriale();        
    }
    
    public void modificaSalvataElementoCorrenteListaMateriali(){
        listaMateriali.modificaSalvata(listaMateriali.getSelectionModel().getSelectedIndex());
    }
    */
    
    public void ottieniDatiMySQLListaMateriali() {
        listaMateriali.ottieniDatiMySQL();
    }
    
    public void cambiaVisibilitaFigliSchedaMateriale(boolean b) {
        schedaMateriale.cambiaVisibilitaFigli(b);
    }

    public void setCategorieGraficoDisponibilita() {
        graficoDisponibilita.setCategorie(this.listaCategorie);
        graficoDisponibilita.aggiornaDati();
    }
    
    public void preparaElementiGrafici(Scene s) {
        VBox pannelloSx = new VBox(16);
        VBox pannelloDx = new VBox(16);        
        HBox center = new HBox(16);
        
        this.menu = new ToolbarController("");
        this.pannelloRicerca = new SearchPanelController();
        this.schedaMateriale = new MainPanelController();
        this.listaMateriali = new ListController();
        this.graficoDisponibilita = new ChartController();
        
        Font.loadFont(GestioneMagazzino.class.getResource("font/Roboto/Roboto-Regular.ttf").toExternalForm(), 15);        
        Font.loadFont(GestioneMagazzino.class.getResource("font/Roboto/Roboto-Medium.ttf").toExternalForm(), 15);        
        s.getStylesheets().add("style/StyleGestioneMagazzino.css");
        
        pannelloSx.setPadding(new Insets(16, 0, 0, 16));
        pannelloSx.getChildren().addAll(pannelloRicerca, listaMateriali, graficoDisponibilita);
        pannelloDx.setPadding(new Insets(16, 16, 16, 0));
        pannelloDx.getChildren().add(schedaMateriale);
        center.getChildren().addAll(pannelloSx, pannelloDx);
        
        root.getChildren().addAll(menu, center);
    }
    
}
