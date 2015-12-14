import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class AppCache implements Serializable {
    private static final String FILE_PATH = "cache.bin";
    
    class SerializableMateriale extends Materiale implements Serializable{
        
        public SerializableMateriale() {
            super();
        }

        public SerializableMateriale(Materiale m) {
            this.categoria = m.getCategoria();
            this.disponibilita = m.getDisponibilitaConsistente();
            this.id = m.getId();
            this.nominativo = m.getNominativo();
        }
    }
    
    public Materiale carica() throws IOException {
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
    
    public void salva(Materiale m) throws IOException {
        SerializableMateriale cachedObject = new SerializableMateriale(m);
        try (
            FileOutputStream fout = new FileOutputStream(FILE_PATH);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
        ){
            oout.writeObject(cachedObject);
        } 
    } 
}
