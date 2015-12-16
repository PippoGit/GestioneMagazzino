import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Questa classe fa pietà. VA RISCRITTSA.

public class ArchivioMagazzino {
    private final static String LOCATION = "localhost";
    private final static String DB_NAME = "ArchivioMagazzino";
    private final static String USR = "root";
    private final static String PWD = "";
    private static int MAX_RES;
    
    public ArchivioMagazzino(int max) {
        MAX_RES = max;
    }

    public ObservableList<Materiale> cercaMateriale(String txt, int c) {
       
        ObservableList<Materiale> ol;
        ol = FXCollections.observableArrayList();        
        c = (c<0)?0:c;
        
        try ( Connection co = DriverManager.getConnection("jdbc:mysql://"+LOCATION +"/"+ DB_NAME, USR, PWD);   //9
            PreparedStatement  ps = co.prepareStatement("SELECT idMateriale, nominativo, categoria, COALESCE(disponibilita, 0) as disponbilita\n" +
                                                        "FROM Materiale AS T1 LEFT JOIN \n" +
                                                        "(\n" +
                                                        "	SELECT count(1) as disponibilita, materiale\n" +
                                                        "	FROM IstanzeMateriale\n" +
                                                        "	WHERE CHAR_LENGTH(cliente)=0\n AND stato = 'Funzionante'" +
                                                        "	GROUP BY materiale\n" +
                                                        ") AS T ON T1.idMateriale=T.materiale "+
                                                        " WHERE nominativo LIKE ? AND categoria = ? LIMIT "+ MAX_RES);
            ) { 
            ps.setString(1, "%"+txt+"%");
            ps.setInt(2, c);
            ResultSet rs = ps.executeQuery(); //11   
            while (rs.next()){ //12 {
                ol.add(new Materiale(rs.getInt("idMateriale"),
                                    rs.getString("nominativo"), 
                                    rs.getInt("categoria"),
                                    rs.getInt("disponbilita")));
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}    
        
        return ol;
    }
    
    public ObservableList<Materiale> cercaMateriale(int c) {
       
        ObservableList<Materiale> ol;
        ol = FXCollections.observableArrayList();        
        c = (c<0)?0:c;
        
        try ( Connection co = DriverManager.getConnection("jdbc:mysql://"+LOCATION +"/"+ DB_NAME, USR, PWD);   //9
            PreparedStatement  ps = co.prepareStatement("SELECT idMateriale, nominativo, categoria, COALESCE(disponibilita, 0) as disponbilita\n" +
                                                        "FROM Materiale AS T1 LEFT JOIN \n" +
                                                        "(\n" +
                                                        "	SELECT count(1) as disponibilita, materiale\n" +
                                                        "	FROM IstanzeMateriale\n" +
                                                        "	WHERE CHAR_LENGTH(cliente)=0\n AND stato = 'Funzionante'" +
                                                        "	GROUP BY materiale\n" +
                                                        ") AS T ON T1.idMateriale=T.materiale "
                                                        + "WHERE categoria =? LIMIT "+ MAX_RES);
        ) { 
            //ps.setString(1, txt);
            ps.setInt(1, c);
            
            ResultSet rs = ps.executeQuery(); //11   
            while (rs.next()){ //12 {
                ol.add(new Materiale(rs.getInt("idMateriale"),
                                                    rs.getString("nominativo"), 
                                                    rs.getInt("categoria"),
                                                    rs.getInt("disponbilita")));
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}    
        
        return ol;
    }
    
    public ObservableList<Materiale> cercaMateriale(String txt) {
        ObservableList<Materiale> ol;
        ol = FXCollections.observableArrayList();        
        
        try ( Connection co = DriverManager.getConnection("jdbc:mysql://"+LOCATION +"/"+ DB_NAME, USR, PWD);   //9
            PreparedStatement  ps = co.prepareStatement("SELECT idMateriale, nominativo, categoria, COALESCE(disponibilita, 0) as disponbilita\n" +
                                                        "FROM Materiale AS T1 LEFT JOIN \n" +
                                                        "(\n" +
                                                        "	SELECT count(1) as disponibilita, materiale\n" +
                                                        "	FROM IstanzeMateriale\n" +
                                                        "	WHERE CHAR_LENGTH(cliente)=0 AND stato = 'Funzionante'\n" +
                                                        "	GROUP BY materiale\n" +
                                                        ") AS T ON T1.idMateriale=T.materiale "+                    
                                                        "WHERE nominativo LIKE ? LIMIT "+ MAX_RES);
            ) { 
            ps.setString(1, "%"+txt+"%");
            ResultSet rs = ps.executeQuery(); //11   
            while (rs.next()){ //12 {
                ol.add(new Materiale(rs.getInt("idMateriale"),
                                    rs.getString("nominativo"), 
                                    rs.getInt("categoria"),
                                    rs.getInt("disponbilita")));
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}    
        
        return ol;
    }
    
    public ObservableList<IstanzaMateriale> caricaIstanzeMateriali(int id) {
        ObservableList<IstanzaMateriale> ol;
        ol = FXCollections.observableArrayList();        
        
        try ( Connection co = DriverManager.getConnection("jdbc:mysql://"+LOCATION +"/"+ DB_NAME, USR, PWD);   //9
            PreparedStatement  ps = co.prepareStatement("SELECT * FROM Materiale LEFT JOIN IstanzeMateriale ON idMateriale=materiale WHERE materiale = ?");
            ) { 
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); //11   
            while (rs.next()){ //12 {
                ol.add(new IstanzaMateriale(rs.getString("codice_materiale"),
                                            rs.getString("cliente"), 
                                            rs.getString("stato")));
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}    
        
        return ol;
    }
    
    public void salvaMateriale(Materiale m) {
        
        m.getIstanze().stream().forEach((i) -> {
            try ( Connection co = DriverManager.getConnection("jdbc:mysql://"+LOCATION +"/"+ DB_NAME, USR, PWD);   //9
                    PreparedStatement  ps = co.prepareStatement("UPDATE IstanzeMateriale SET cliente =?, stato=? WHERE codice_materiale= ? ");
                    ) { 
                ps.setString(1,i.getCliente());
                ps.setString(2, i.getStato());
                ps.setString(3, i.getCodiceMateriale());
                System.out.println("rows affected: " + ps.executeUpdate()); //11
            } catch (SQLException e) {System.err.println(e.getMessage());}
        });
    }
    
}
