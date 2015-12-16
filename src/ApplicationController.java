import javafx.geometry.Insets;
import javafx.scene.layout.*;

public class ApplicationController {      
    private ToolbarController menu;
    private SearchPanelController pannelloRicerca;
    private ListController listaMateriali;
    private MainPanelController pannelloPrincipale;
    private ChartController graficoDisponibilita;
    
    private Materiale current; //(1)
    private Categoria[] listaCategorie; //(2)
    
    private static final ApplicationController DELEGATIONLINK = new ApplicationController(); //(3)
    
    private ApplicationController() {        
        this.current = new Materiale(); 
    }    
    
    public static ApplicationController getDelegationLink() { //(4)
        return DELEGATIONLINK;
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
    
    public Categoria getCategoria(int i){ //(3)
        return listaCategorie[i];
    }
      
    public void aumentaDisponibilitaCurrent() { //(4)
        current.aumentaDisponibilita();
        listaCategorie[current.getCategoria()].aumentaDisponibilita();
        graficoDisponibilita.aggiornaDati();        
    }
    
    public void diminuisciDisponibilitaCurrent() { //(5)
        current.diminuisciDisponibilita();
        listaCategorie[current.getCategoria()].diminuisciDisponibilita();
        graficoDisponibilita.aggiornaDati();
    }

    public void aggiornaPannelloPrincipale() { //(6)
        pannelloPrincipale.caricaMateriale(current);
        pannelloPrincipale.cambiaVisibilitaFigli(true);           
    }
    
    public void setTitoloTxtMenu(String txt) { //(7)
        this.menu.getStyleClass().remove("errore");        
        menu.setTitoloTxt(txt);
    }
    
    public String getTitoloTxtMenu(String txt) { //(8)
        return menu.getTitoloTxt();
    }
    
    
    public void mostraModifiche() { //(9)
        if(!current.isModificato()) {
            current.setModificato(true); //(10)
            //listaMateriali.segnalaModifica(listaMateriali.getSelectionModel().getSelectedIndex());
            //menu.setTitoloTxt(menu.getTitoloTxt() + " *");
        }
        aggiornaPannelloPrincipale();        
    }    
    
    public void ottieniDatiListaMaterialiDB() {  //(11)
        listaMateriali.caricaMateriali(0);
    }
    
    public void ottieniDatiListaMaterialiDB(String txt, int c) {  //(11)
        if(c == -1) 
            listaMateriali.caricaMateriali(txt);
        else
            listaMateriali.caricaMateriali(txt, c);
    }
    
    public void cambiaVisibilitaFigliPannelloPrincipale(boolean b) { //(12)
        pannelloPrincipale.cambiaVisibilitaFigli(b);
    }

    public void setCategorieGraficoDisponibilita() { //(13)
        graficoDisponibilita.setCategorie(this.listaCategorie);
        //graficoDisponibilita.aggiornaDati();
    }
    
    public void mostraErroreMenu(String txt) {
        this.menu.titoloConErrore(txt);
    }
    
    public void preparaElementiGrafici(Pane root) { //(14)
        VBox pannelloSx = new VBox(16);
        VBox pannelloDx = new VBox(16);        
        HBox center = new HBox(16);
        
        this.menu = new ToolbarController("");
        this.pannelloRicerca = new SearchPanelController();
        this.pannelloPrincipale = new MainPanelController();
        this.listaMateriali = new ListController();
        this.graficoDisponibilita = new ChartController();
        
        pannelloSx.setPadding(new Insets(16, 0, 0, 16));
        pannelloSx.getChildren().addAll(pannelloRicerca, listaMateriali, graficoDisponibilita);
        pannelloDx.setPadding(new Insets(16, 16, 16, 0));
        pannelloDx.getChildren().add(pannelloPrincipale);
        center.getChildren().addAll(pannelloSx, pannelloDx);
        
        root.getChildren().addAll(menu, center);
    }
    
}

/*
Commenti
La classe Application Controller si occupa di realizzare un canale di comunicazione che gli altri
componenti dell'interfaccia grafica possono utilizzare per invocare uno i metodi dell'altro, 
permettendo così all'interfaccia grafica di tenersi aggiornata in linea con il modello (dati).
L'Application Controller è una specie di "Observer" che gestisce i vari "Subject" rappresentati 
dai moduli separati della GUI.

1) Oggetto di tipo Materiale che verrà utilizzato per riferirsi al materiale visualizzato "in 
questo momento" nel Pannello Principale.

2) Array di oggetti di tipo Categoria, viene utilizzato come "unica istanza" di ogni possibile 
Categoria, viene utilizzato da tutti gli oggetti che utilizzano le categorie.
Il metodo (3) viene utilizzato per richiedere una particolare categoria specificandone l'id. 
Si usa la convenzione che l'indice nell'array delle categorie sia uguale all'id definito nel DB.

3) Costruisco un'istanza static per l'oggetto ApplicationController secondo il Design Pattern 
Singleton. 

4) Metodo più importante delle classe, realizza il legame di delega tra i componenti grafici
e l'istanza di ApplicationController.

4) 5) I metodi utilizzati per aumentare o diminuire la disponiblità in magazzino dell'oggetto
current. L'operazione non viene mai eseguita "autonomamente" dall'ApplicationController, ma solo
se invocata da un altro elemento dell'interfaccia grafica (come ad esempio il Monitor delle 
istanze di materiale in fase di edit).

6) Comunica al Pannello Principale di caricare un materiale ed in particolare l'oggetto current.
Potrebbe essere necessario cambiare la visibilità degli elementi del pannello principale che 
sono inizialmente nascosti.

7) 8) Metodi per dire alla Toolbar di cambiare il titolo.

9) L'oggetto current ha subito delle modifiche in un altro componente grafico. Chiamando questo 
metodo l'oggetto ApplicationController avvertirà il Pannello Principale di aggiornare la view.
Inoltre viene lasciato un flag che dice che quel Materiale è stato modificato (10).

11) Comunica alla lista dei materiali di aggiornarsi.

12) Comunica al pannelloPrincipale di cambiare visibilità ai propri figli (gli elementi grafici
che contiente).

13) Comunica al grafico l'elenco delle categorie.

14) Il metodo si occupa di costruire tutti i vari componenti dell'interfaccia grafica, ad 
inizializzare il layout e ha mostrare tutto all'utente.
*/