import java.io.Serializable;
import javafx.beans.property.*;

public class IstanzaMateriale implements Serializable {
    private  final SimpleStringProperty codiceMateriale;
    private  SimpleStringProperty cliente;
    private  SimpleStringProperty stato;

    public String getCodiceMateriale() {
        return codiceMateriale.get();
    }

    public String getCliente() {
        return cliente.get();
    }

    public String getStato() {
        return stato.get();
    }
    
    public IstanzaMateriale(String m, String l, String s) {
        codiceMateriale = new SimpleStringProperty(m);
        cliente = new SimpleStringProperty(l);
        stato = new SimpleStringProperty(s);
    }

    public void setCliente(String cliente) {
        this.cliente = new SimpleStringProperty(cliente);
    }

    public void setStato(String stato) {
        this.stato = new SimpleStringProperty(stato);
    }

}

/*
Commenti
Classe che si occupa di realizzare il concetto di IstanzaMateriale previsto nelle specifiche.
Si tratta di una semplice classe il cui unico scopo è mantenere aggiornate le varie istanze.
Sono state utilizzate SimpleStringProperty per permettere alla tabella Monitor che dovrà usarle
di mantenersi aggiornata con le modifiche fatte agli oggetti.
Implementa l'interfaccia serializable perchè verrà serializzata nel file binario di cache.
*/