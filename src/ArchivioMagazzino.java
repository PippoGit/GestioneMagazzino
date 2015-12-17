import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


 //dacommentare
public class ArchivioMagazzino {
    private final static String LOCATION = "localhost";
    private final static String DB_NAME = "ArchivioMagazzino";
    private final static String USR = "root";
    private final static String PWD = "";
    private static int MAX_RES;
    
    public ArchivioMagazzino(int max) {
        MAX_RES = max;
    }
    
    public ArchivioMagazzino() {
        this(1);
    }

    public ObservableList<Materiale> caricaListaMateriali(String txt, int c) throws SQLException {
        ObservableList<Materiale> ol;
        ol = FXCollections.observableArrayList();        
        String q = (c==-1)?"WHERE nominativo LIKE ?":"WHERE nominativo LIKE ? AND categoria = ?"; 
         
        try (Connection co = DriverManager.getConnection("jdbc:mysql://" + LOCATION + "/" + DB_NAME, USR, PWD);   //9
             PreparedStatement  ps = co.prepareStatement("SELECT idMateriale, nominativo, categoria, COALESCE(disponibilita, 0) as disponbilita\n" +
                                                        "FROM Materiale AS T1 LEFT JOIN \n" +
                                                        "(\n" +
                                                        "   SELECT count(1) as disponibilita, materiale\n" +
                                                        "   FROM IstanzeMateriale\n" +
                                                        "   WHERE CHAR_LENGTH(cliente)=0\n AND stato = 'Funzionante'" +
                                                        "   GROUP BY materiale\n" +
                                                        ") AS T ON T1.idMateriale=T.materiale "+
                                                        q +  " LIMIT " + MAX_RES);
            ) {
            ps.setString(1, "%"+txt+"%");
            if(c !=- 1)
                ps.setInt(2, c);
            
            ResultSet rs = ps.executeQuery(); //11   
            while (rs.next()){ //12 {
                ol.add(new Materiale(rs.getInt("idMateriale"),
                                    rs.getString("nominativo"), 
                                    rs.getInt("categoria"),
                                    rs.getInt("disponbilita")));
            }
        } catch (SQLException e) {
            throw e;
        }    
        
        return ol;
    }
    
    public ObservableList<IstanzaMateriale> caricaMateriale(int id) throws SQLException {
        ObservableList<IstanzaMateriale> ol;
        ol = FXCollections.observableArrayList();        
        
        try (Connection co = DriverManager.getConnection("jdbc:mysql://"+LOCATION +"/"+ DB_NAME, USR, PWD);   //9
             PreparedStatement  ps = co.prepareStatement("SELECT * FROM Materiale LEFT JOIN IstanzeMateriale ON idMateriale=materiale WHERE materiale = ?");
            ) { 
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); //11   
            while (rs.next()){ //12 {
                ol.add(new IstanzaMateriale(rs.getString("codice_materiale"),
                                            rs.getString("cliente"), 
                                            rs.getString("stato")));
            }
        } catch (SQLException e) {
            throw e;
        }            
        return ol;
    }
    
    public void salvaMateriale(Materiale m) throws SQLException {
        for(IstanzaMateriale im: m.getIstanze()) {
            try (Connection co = DriverManager.getConnection("jdbc:mysql://" + LOCATION + "/" + DB_NAME, USR, PWD);   //9
                 PreparedStatement  ps = co.prepareStatement("UPDATE IstanzeMateriale SET cliente =?, stato=? WHERE codice_materiale= ? ");
                ) { 
                ps.setString(1, im.getCliente());
                ps.setString(2, im.getStato());
                ps.setString(3, im.getCodiceMateriale());
                System.out.println("rows affected: " + ps.executeUpdate()); //11
            } catch (SQLException e) {
                throw e;
            }
        }
    }
    
    public int caricaDisponibilitaCategorie(int c) throws SQLException {
        int disponibilita = 0;
        
        try (Connection co = DriverManager.getConnection("jdbc:mysql://" + LOCATION + "/" + DB_NAME, USR, PWD);   //9
             PreparedStatement  ps = co.prepareStatement("SELECT count(*) as disponibilita, categoria\n" +
                                                        "FROM IstanzeMateriale INNER JOIN Materiale on idMateriale=materiale\n" +
                                                        "WHERE CHAR_LENGTH(cliente)=0 AND stato=\"Funzionante\" AND categoria = ?\n" +
                                                        "GROUP BY categoria");
            ) { 
            ps.setInt(1, c);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                disponibilita = rs.getInt("disponibilita");
        } catch (SQLException e) {
            throw e;
        }
        
        return disponibilita;
    }
    
}
