import java.io.Serializable;
import javafx.collections.ObservableList;

public class Materiale implements Serializable {
    protected int id;
    protected String nominativo;
    protected int categoria;
    protected int disponibilita;
    private ObservableList<IstanzaMateriale> istanze;
    
    private boolean cached;
    private boolean modificato;
    private int disponibilitaConsistente;
    
    public int getDisponibilitaConsistente() {
        return disponibilitaConsistente;
    }
    
    public void salvaModificheDB() {
        ArchivioMagazzino am = new ArchivioMagazzino(1);
        am.salvaMateriale(this);
        disponibilitaConsistente = disponibilita;
    }
    
    public boolean isModificato() {
        return modificato;
    }

    public void setModificato(boolean modificato) {
        this.modificato = modificato;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setNominativo(String nominativo) {
        this.nominativo = nominativo;
    }

    public void setCategoria(int c) {
        this.categoria = c;
    }

    public int getId() {
        return id;
    }

    public String getNominativo() {
        return nominativo;
    }

    public int getCategoria() {
        return categoria;
    }

    public final ObservableList<IstanzaMateriale> getIstanze() {
        return istanze;
    }

    public final void setIstanze(ObservableList<IstanzaMateriale> istanze) {
        this.istanze = istanze;
        //cached = false;
    }
    public final void caricaIstanzeDB() { //(1)
        caricaIstanzeDB(false);
    }
    
    public final void caricaIstanzeDB(boolean clearCache) { //(2)
        if(clearCache || cached) return;
        cached = true;
        
        ArchivioMagazzino am = new ArchivioMagazzino(1);
        this.istanze = am.caricaMateriale(id);
    }

    public int getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }
    
    public void aumentaDisponibilita() {
        disponibilita++;
    }
    
    public void diminuisciDisponibilita() {
        disponibilita--;
    }
    
    public Materiale(int i, String n, int c, int d) {
        id = i;
        nominativo = n;
        categoria = c;
        disponibilita = d;
        disponibilitaConsistente = d;
    }
    
    public Materiale(int i, String n, int d) {
        id = i;
        nominativo = n;
        disponibilita = d;
        disponibilitaConsistente = d;
    }
    
    public Materiale() {
        id = -1;
        nominativo = "";
    }
    
    @Override 
    public String toString() {
        return nominativo;
    }
}

/*
Commenti
Classe che si occupa di realizzare il concetto di Materiale previsto nelle specifiche. 
Per ogni materiale è previsto una lista di IstanzeMateriale come previsto nelle specifiche.
Implementa l'interfaccia serializable perchè verrà serializzata nel file binario di cache.

1) Carico le istanze da un DB senza pulire la cache, ovvero se ho già caricato da db queste 
informazioni evito di ricaricarle perchè tanto non sono state modificate (quando modifico le
informazioni relative ad un materiale setto a true il valore di cached).

2) Carico le istanze di materiale dal Database utilizzando l'oggetto ArchivioMateriale.
*/