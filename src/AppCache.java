import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AppCache implements Serializable {
    private static final String FILE_PATH = "cache.bin";
    
    class SerializableMateriale extends Materiale implements Serializable{ //(1)
        
        public SerializableMateriale() {
            super();
        }

        public SerializableMateriale(Materiale m) { //(2)
            this.categoria = m.getCategoria();
            this.disponibilita = m.getDisponibilitaConsistente();
            this.id = m.getId();
            this.nominativo = m.getNominativo();
        }
    }
    
    public Materiale carica() throws IOException { //(3)
        SerializableMateriale cachedObject = new SerializableMateriale();
        Materiale m;
        
        try (
            FileInputStream fin = new FileInputStream(FILE_PATH);
            ObjectInputStream oin = new ObjectInputStream(fin);
        ){
            cachedObject = (SerializableMateriale) oin.readObject();
            System.out.println(cachedObject);
        }
        catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
        
        m = new Materiale(cachedObject.id, cachedObject.nominativo, cachedObject.categoria, cachedObject.disponibilita);
        return m;
    }
    
    public void salva(Materiale m) throws IOException { //(4)
        SerializableMateriale cachedObject = new SerializableMateriale(m);
        try (
            FileOutputStream fout = new FileOutputStream(FILE_PATH);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
        ){
            oout.writeObject(cachedObject);
        } 
    } 
}

/*
Commenti
Classe che si occupa di realizzare la cache su file binario.

1) Avendo l'oggetto Materiale un attributo non serializzabile (la ObservableList) è necessario
definire una classe interna che estende Materiale senza però ereditare gli attributi non serializzabili.
Tale classe, SerializableMateriale verrà utilizzata per memorizzare le informazioni che poi permetteranno
la realizzazione della cache.

2) Metodo per costruire un oggetto SerializableMateriale a partire da un Materiale.

3) Metodo che si occupa di caricare un SerializableMateriale dal file di cache ed utilizzarlo per creare
un oggetto Materiale che verrà poi ritornato al chiamante.

4) Metodo che costruice un SerializableMateriale a partide da un Materiale m e lo memorizza sul file di 
cache. Questo metodo verrà chiamato al momento della chiusura dell'applicazione.
*/