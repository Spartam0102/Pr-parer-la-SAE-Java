package BD; 
import Java.*; 
import java.sql.*;
public class CommandeBD {
	ConnexionMySQL laConnexion;
	Statement st;
	public CommandeBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	public int genererNouvelIdCommande() throws SQLException {
        String sql = "select MAX(numcom) as maxId from COMMANDE";
        try (Statement stmt = this.laConnexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()){
                int maxId = rs.getInt("maxId");
                return maxId + 1;
            }
            else{
                return 1;
            }
        }
    }
}