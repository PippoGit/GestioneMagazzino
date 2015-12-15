
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Questa classe fa pietà.

public class ArchivioMagazzino {
    private final static String LOCATION = "localhost";
    private final static String DB_NAME = "ArchivioMagazzino";
    private final static String USR = "root";
    private final static String PWD = "";
    private final static int MAX_RES = 6;
    
    public ObservableList<Materiale> cercaMateriale(String txt, int c) {
       
        ObservableList<Materiale> ol;
        ol = FXCollections.observableArrayList();        
        c = (c<0)?0:c;
        
        try ( Connection co = DriverManager.getConnection("jdbc:mysql://"+LOCATION +"/"+ DB_NAME, USR, PWD);   //9
            PreparedStatement  ps = co.prepareStatement("SELECT * FROM Materiale INNER JOIN Categoria ON idCategoria=categoria WHERE nominativo LIKE ? AND categoria =? LIMIT "+ MAX_RES);
            ) { 
            ps.setString(1, "%"+txt+"%");
            ps.setInt(2, c);
            ResultSet rs = ps.executeQuery(); //11   
            while (rs.next()){ //12 {
                ol.add(new Materiale(rs.getInt("idMateriale"),
                                    rs.getString("nominativo"), 
                                    new Categoria(rs.getInt("idCategoria"), rs.getString("descrizione")),
                                    0));
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}    
        
        return ol;
    }
    
    public ObservableList<Materiale> cercaMateriale(int c) {
       
        ObservableList<Materiale> ol;
        ol = FXCollections.observableArrayList();        
        c = (c<0)?0:c;
        
        try ( Connection co = DriverManager.getConnection("jdbc:mysql://"+LOCATION +"/"+ DB_NAME, USR, PWD);   //9
            PreparedStatement  ps = co.prepareStatement("SELECT * FROM Materiale INNER JOIN Categoria ON idCategoria=categoria WHERE categoria =? LIMIT "+ MAX_RES);
        ) { 
            //ps.setString(1, txt);
            ps.setInt(1, c);
            
            ResultSet rs = ps.executeQuery(); //11   
            while (rs.next()){ //12 {
                ol.add(new Materiale(rs.getInt("idMateriale"),
                                                    rs.getString("nominativo"), 
                                                    new Categoria(rs.getInt("idCategoria"), rs.getString("descrizione")),
                                                    0));
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}    
        
        return ol;
    }
    
    public ObservableList<Materiale> cercaMateriale(String txt) {
        ObservableList<Materiale> ol;
        ol = FXCollections.observableArrayList();        
        
        try ( Connection co = DriverManager.getConnection("jdbc:mysql://"+LOCATION +"/"+ DB_NAME, USR, PWD);   //9
            PreparedStatement  ps = co.prepareStatement("SELECT * FROM Materiale INNER JOIN Categoria ON idCategoria=categoria WHERE nominativo LIKE ? LIMIT "+ MAX_RES);
            ) { 
            ps.setString(1, "%"+txt+"%");
            ResultSet rs = ps.executeQuery(); //11   
            while (rs.next()){ //12 {
                ol.add(new Materiale(rs.getInt("idMateriale"),
                                    rs.getString("nominativo"), 
                                    new Categoria(rs.getInt("idCategoria"), rs.getString("descrizione")),
                                    0));
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}    
        
        return ol;
    }
}
