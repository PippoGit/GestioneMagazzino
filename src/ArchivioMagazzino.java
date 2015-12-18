import java.sql.*;
import javafx.collections.*;

public class ArchivioMagazzino {
    private final static String LOCATION = "localhost";
    private final static String DB_NAME = "ArchivioMagazzino";
    private final static String USR = "root";
    private final static String PWD = "";
    private static int MAX_ROWS; //(1)
    
    public ArchivioMagazzino(int max) {
        MAX_ROWS = max;
    }
    
    public ArchivioMagazzino() { //(2)
        this(1);
    }

    public ObservableList<Materiale> caricaListaMateriali(String txt, int c) throws SQLException { //(3)
        ObservableList<Materiale> ol;
        ol = FXCollections.observableArrayList();        
        String q = (c==-1)?"WHERE nominativo LIKE ?":"WHERE nominativo LIKE ? AND categoria = ?"; 
        try (Connection co = DriverManager.getConnection("jdbc:mysql://" + LOCATION + "/" + DB_NAME, USR, PWD);
             PreparedStatement  ps = co.prepareStatement("SELECT idMateriale, nominativo, categoria, COALESCE(disponibilita, 0) as disponbilita\n" +
                                                        "FROM Materiale AS T1 LEFT JOIN \n" +
                                                        "(  SELECT count(1) as disponibilita, materiale\n" +
                                                        "   FROM IstanzaMateriale\n" +
                                                        "   WHERE CHAR_LENGTH(cliente)=0\n AND stato = 'Funzionante'" +
                                                        "   GROUP BY materiale  ) AS T ON T1.idMateriale=T.materiale " +
                                                        q +  " LIMIT " + MAX_ROWS);
            ) {
            ps.setString(1, "%"+txt+"%");
            if(c !=- 1) ps.setInt(2, c);
            ResultSet rs = ps.executeQuery(); 
            while (rs.next()) ol.add(new Materiale(rs.getInt("idMateriale"), rs.getString("nominativo"),   rs.getInt("categoria"), rs.getInt("disponbilita")));
        }
        return ol;
    }
    
    public ObservableList<IstanzaMateriale> caricaMateriale(int id) throws SQLException { //(4)
        ObservableList<IstanzaMateriale> ol;
        ol = FXCollections.observableArrayList();        
        try (Connection co = DriverManager.getConnection("jdbc:mysql://" + LOCATION + "/" + DB_NAME, USR, PWD);
             PreparedStatement  ps = co.prepareStatement("SELECT * FROM Materiale LEFT JOIN IstanzaMateriale ON idMateriale=materiale WHERE materiale = ?");
            ) { 
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); 
            while (rs.next()) ol.add(new IstanzaMateriale(rs.getString("codice_materiale"), rs.getString("cliente"), rs.getString("stato")));
        }         
        return ol;
    }
    
    public void salvaMateriale(Materiale m) throws SQLException { //(5)
        for(IstanzaMateriale im: m.getIstanze()) {
            try (Connection co = DriverManager.getConnection("jdbc:mysql://" + LOCATION + "/" + DB_NAME, USR, PWD);
                 PreparedStatement  ps = co.prepareStatement("UPDATE IstanzaMateriale SET cliente =?, stato=? WHERE codice_materiale= ? ");
                ) { 
                ps.setString(1, im.getCliente());
                ps.setString(2, im.getStato());
                ps.setString(3, im.getCodiceMateriale());
                System.out.println("rows affected: " + ps.executeUpdate());
            }
        }
    }
    
    public int caricaDisponibilitaCategorie(int c) throws SQLException { //(6)
        int disponibilita = 0;
        try (Connection co = DriverManager.getConnection("jdbc:mysql://" + LOCATION + "/" + DB_NAME, USR, PWD);  
             PreparedStatement  ps = co.prepareStatement("SELECT count(*) as disponibilita, categoria\n" +
                                                        "FROM IstanzaMateriale INNER JOIN Materiale on idMateriale=materiale\n" +
                                                        "WHERE CHAR_LENGTH(cliente)=0 AND stato=\"Funzionante\" AND categoria = ?\n" +
                                                        "GROUP BY categoria");
            ) { 
            ps.setInt(1, c);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) disponibilita = rs.getInt("disponibilita");
        }
        return disponibilita;
    }
}

/*
Commenti
Classe che si occupa di realizzare tutte le query (di ricerca e update) alla base di dati utilizzata
dall'applicazione.
Tutti i metodi che effettuano accessi al database sollevano eccezioni di tipo SQLException così da 
mostrare sull'interfaccia che c'è stato un problema in fase di ricerca/salvataggio.

1) Le query di ricerca possono mostrare al massimo MAX_ROWS righe, dove il valore di MAX_ROWS è definito
in fase di caricamento delle preferenze locali.

2) Se non viene specificata una dimensione massima di righe allora questa verrà settata a 1

3) Il metodo carica la lista dei materiali che verrà poi usata per popolare la listview. 
La query seleziona informazioni relative all'id del materiale, il nominativo, id della categoria e 
disponibilita (calcolata guardando quali istanze sono funzionanti e in magazzino)

4) Carica le informazioni di tutte le istanze di materiale (cliente, stato e codice)

5) Per ogni IstanzaMateriale del materiale m effettua una query di aggiornamento settando il nuovo
cliente ed il nuovo stato.

6) Carica la disponibilità totale di ogni categoria. Il metodo viene usato per sapere con quali valori
inizializzare il grafico.
*/