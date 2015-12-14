import java.io.Serializable;

public class SerializableMateriale implements Serializable {
    protected int id;
    protected String nominativo;
    protected Categoria categoria;
    protected int disponibilita;

    protected boolean cached;
    protected boolean modificato;
    
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
       
    @Override 
    public String toString() {
        return nominativo;
    }
    
    public SerializableMateriale(int i, String n, Categoria c, int d) {
        id = i;
        nominativo = n;
        categoria = c;
        disponibilita = d;
    }
    
    public SerializableMateriale() {
        id = -1;
        nominativo = "";
    }
}
