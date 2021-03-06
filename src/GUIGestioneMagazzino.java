import javafx.geometry.Insets;
import javafx.scene.layout.*;

public class GUIGestioneMagazzino {      
    private ToolbarVisuale toolbar;
    private PannelloRicercaVisuale pannelloRicerca;
    private ListaMaterialiVisuale listaMateriali;
    private PannelloPrincipaleVisuale pannelloPrincipale;
    private GraficoDisponibilitaProdottiVisuale graficoDisponibilita;
    
    private Materiale current; //(1)
    private Categoria[] listaCategorie; //(2)
    
    private static final GUIGestioneMagazzino DELEGATIONLINK = new GUIGestioneMagazzino(); //(3)
    
    private GUIGestioneMagazzino() {        
        this.current = new Materiale(); 
    }    
    
    public static GUIGestioneMagazzino getDelegationLink() { //(4)
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
    
    public Categoria[] getListaCategorie () {
        return listaCategorie;
    }
    
    public Categoria getCategoria(int i){ //(3)
        return listaCategorie[i];
    }
      
    public void aumentaDisponibilitaCurrent() { //(4)
        current.aumentaDisponibilita();
        listaCategorie[current.getCategoria()].aumentaDisponibilita();
        graficoDisponibilita.aggiornaDatiDisponibilita();        
    }
    
    public void diminuisciDisponibilitaCurrent() { //(5)
        current.diminuisciDisponibilita();
        listaCategorie[current.getCategoria()].diminuisciDisponibilita();
        graficoDisponibilita.aggiornaDatiDisponibilita();
    }

    public void aggiornaPannelloPrincipale() { //(6)
        pannelloPrincipale.caricaMateriale(current);
        pannelloPrincipale.cambiaVisibilitaFigli(true);           
    }
    
    public void setTitoloTxtToolbar(String txt) { //(7)
        this.toolbar.getStyleClass().remove("errore");        
        toolbar.setTitoloTxt(txt);
    }
    
    public String getTitoloTxtToolbar() { //(8)
        return toolbar.getTitoloTxt();
    }
    
    
    public void mostraModifiche() { //(9)
        if(!current.isModificato()) {
            current.setModificato(true); //(10)
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
    }
    
    public void mostraErroreToolbar(String txt) {
        this.toolbar.titoloConErrore(txt);
    }
    
    public void mostraOkToolbar(String txt) {
        this.toolbar.titoloConEsitoOk(txt);
    }
    
    public void caricaCategorie() {
        pannelloRicerca.caricaCategorie();
        setCategorieGraficoDisponibilita();  
    }
    
    public void preparaElementiGrafici(Pane root) { //(14)
        VBox pannelloSx = new VBox(16);
        VBox pannelloDx = new VBox(16);        
        HBox center = new HBox(16);
        
        this.toolbar = new ToolbarVisuale("");
        this.pannelloRicerca = new PannelloRicercaVisuale();
        this.pannelloPrincipale = new PannelloPrincipaleVisuale();
        this.listaMateriali = new ListaMaterialiVisuale();
        this.graficoDisponibilita = new GraficoDisponibilitaProdottiVisuale();
        
        pannelloSx.setPadding(new Insets(16, 0, 0, 16));
        pannelloSx.getChildren().addAll(pannelloRicerca, listaMateriali, graficoDisponibilita);
        pannelloDx.setPadding(new Insets(16, 16, 16, 0));
        pannelloDx.getChildren().add(pannelloPrincipale);
        center.getChildren().addAll(pannelloSx, pannelloDx);
        
        root.getChildren().addAll(toolbar, center);
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

3) Costruisco un'istanza static per l'oggetto GUIGestioneMagazzino secondo il Design Pattern 
Singleton. 

4) Metodo più importante delle classe, realizza il legame di delega tra i componenti grafici
e l'istanza di GUIGestioneMagazzino.

4) 5) I metodi utilizzati per aumentare o diminuire la disponiblità in magazzino dell'oggetto
current. L'operazione non viene mai eseguita "autonomamente" dall'GUIGestioneMagazzino, ma solo
se invocata da un altro elemento dell'interfaccia grafica (come ad esempio il Monitor delle 
istanze di materiale in fase di edit).

6) Comunica al Pannello Principale di caricare un materiale ed in particolare l'oggetto current.
Potrebbe essere necessario cambiare la visibilità degli elementi del pannello principale che 
sono inizialmente nascosti.

7) 8) Metodi per dire alla Toolbar di cambiare il titolo.

9) L'oggetto current ha subito delle modifiche in un altro componente grafico. Chiamando questo 
metodo l'oggetto GUIGestioneMagazzino avvertirà il Pannello Principale di aggiornare la view.
Inoltre viene lasciato un flag che dice che quel Materiale è stato modificato (10).

11) Comunica alla lista dei materiali di aggiornarsi.

12) Comunica al pannelloPrincipale di cambiare visibilità ai propri figli (gli elementi grafici
che contiente).

13) Comunica al grafico l'elenco delle categorie.

14) Il metodo si occupa di costruire tutti i vari componenti dell'interfaccia grafica, ad 
inizializzare il layout e ha mostrare tutto all'utente.
*/