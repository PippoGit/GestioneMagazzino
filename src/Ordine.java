import javafx.beans.property.*;

public class Ordine {
    private  final SimpleStringProperty codiceMateriale;
    private  SimpleStringProperty cliente;
    private  SimpleStringProperty stato; //-1 nok 1ok 0rip

    public String getCodiceMateriale() {
        return codiceMateriale.get();
    }

    public String getCliente() {
        return cliente.get();
    }

    public String getStato() {
        return stato.get();
    }
    
    public Ordine(String m, String l, String s) {
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
