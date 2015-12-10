
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Materiale {
    private int id;
    private String nominativo;
    private String categoria;
    private int disponibilita;
    private ObservableList<Ordine> ordini;
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

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getNominativo() {
        return nominativo;
    }

    public String getCategoria() {
        return categoria;
    }

    public ObservableList<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(ObservableList<Ordine> ordini) {
        this.ordini = ordini;
        //cached = false;
    }
    public void caricaOrdiniDB() {
        caricaOrdiniDB(false);
    }
    
    public void caricaOrdiniDB(boolean clearCache) {
        if(clearCache || cached) return;
        cached = true;
        
        if(this.getId() == 1) {
            ordini = FXCollections.observableArrayList(
                            new Ordine("TEL-CTL-M0002", "Mela SPA", "Funzionante"),
                            new Ordine("TEL-CTL-M0012", "Banana SPA", "Funzionante")
                        );            
        }
        else {
            ordini = FXCollections.observableArrayList(
                            new Ordine("TEL-CTL-M0020", "Apple SPA", "Funzionante"),
                            new Ordine("TEL-CTL-M0004", "Apple SPA", "Funzionante"),
                            new Ordine("TEL-CTL-M0008", "Società delle Banane", "Funzionante"),
                            new Ordine("TEL-CTL-M0003", "Società delle Banane", "In riparazione"),
                            new Ordine("TEL-CTL-M0001", "", "Danneggiato"),
                            new Ordine("TEL-CTL-M0026", "Da Pino", "Funzionante")
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
    
    public Materiale(int i, String n, String c, int d) {
        id = i;
        nominativo = n;
        categoria = c;
        disponibilita = d;
    }
    
    public Materiale() {
        id = -1;
        nominativo = "";
        categoria = "";
    }
    
    @Override 
    public String toString() {
        return nominativo;
    }
}
