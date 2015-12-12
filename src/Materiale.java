
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Materiale {
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
    public void caricaIstanzeDB() {
        caricaIstanzeDB(false);
    }
    
    public void caricaIstanzeDB(boolean clearCache) {
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
