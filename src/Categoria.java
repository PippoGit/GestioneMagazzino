public class Categoria {
    final private int id;
    final private String descrizione;
    private int disponibilita;

    public int getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }
    
    public int getDisponibilita() {
        return disponibilita;
    }
    
    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }
    
    public void aumentaDisponibilita() {
        this.disponibilita++;
    }

    public void diminuisciDisponibilita() {
        this.disponibilita--;
    } 
    
    public Categoria(int i, String d, int di) {
        id = i;
        descrizione = d;
        disponibilita = di;
    }
    
    public Categoria(int i, String d) {
        id = i;
        descrizione = d;
    }
}

/*
Commenti
La classe Categoria realizza il concetto di Categoria di materiale.
I metodi che esegue sono piuttosto semplici e si occupano semplicemente di mantenere
aggiornata la struttura.
*/