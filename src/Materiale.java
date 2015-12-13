
import java.io.Serializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Materiale implements Serializable {
    private int id;
    private String nominativo;
    private Categoria categoria;
    private int disponibilita;
    private ObservableList<IstanzaMateriale> istanze;
    
    private boolean cached;
    private boolean modificato;

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

    public void setCategoria(Categoria c) {
        this.categoria = c;
    }

    public int getId() {
        return id;
    }

    public String getNominativo() {
        return nominativo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public ObservableList<IstanzaMateriale> getIstanze() {
        return istanze;
    }

    public void setIstanze(ObservableList<IstanzaMateriale> istanze) {
        this.istanze = istanze;
        //cached = false;
    }
    public void caricaIstanzeDB() { //(1)
        caricaIstanzeDB(false);
    }
    
    public void caricaIstanzeDB(boolean clearCache) { //(2)
        if(clearCache || cached) return;
        cached = true;
        
        if(this.getId() == 1) {
            istanze = FXCollections.observableArrayList(
                            new IstanzaMateriale("TEL-CTL-M0002", "Mela SPA", "Funzionante"),
                            new IstanzaMateriale("TEL-CTL-M0012", "Banana SPA", "Funzionante")
                        );            
        }
        else {
            istanze = FXCollections.observableArrayList(
                            new IstanzaMateriale("TEL-CTL-M0020", "Apple SPA", "Funzionante"),
                            new IstanzaMateriale("TEL-CTL-M0004", "Apple SPA", "Funzionante"),
                            new IstanzaMateriale("TEL-CTL-M0008", "Società delle Banane", "Funzionante"),
                            new IstanzaMateriale("TEL-CTL-M0003", "Società delle Banane", "In riparazione"),
                            new IstanzaMateriale("TEL-CTL-M0001", "", "Danneggiato"),
                            new IstanzaMateriale("TEL-CTL-M0026", "Da Pino", "Funzionante")
                        );
        }
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
    
    public Materiale(int i, String n, Categoria c, int d) {
        id = i;
        nominativo = n;
        categoria = c;
        disponibilita = d;
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