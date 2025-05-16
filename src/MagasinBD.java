import java.sql.*;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Map;

public class MagasinBD {
	ConnexionMySQL laConnexion;
	Statement st;
	MagasinBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	int maxNumMagasin() throws SQLException{
		int maxNum=0;
		this.st=this.laConnexion.createStatement();
		ResultSet resultat=st.executeQuery("SELECT MAX(idMagasin) maxn FROM magasin;");
		if(resultat.next()){
			maxNum=resultat.getInt("maxn");

		}
		return maxNum;
/* 		Statement s=c.createStatement();
		int i= s.executeUpdate("select max(numJoueur) from JOUEUR");
		executeUpdate(String);
*/
	}


	int insererJoueur( Magasin j) throws  SQLException{
		PreparedStatement ps=this.laConnexion.prepareStatement("insert into MAGASIN values (?,?,?,?,?,?)");
		int numJoueur=this.maxNumMagasin()+1;
		ps.setInt(1,numJoueur);
        System.out.print("Commande > ");
        String mess = System.console().readLine().strip().toLowerCase();
		ps.setString(2, j.getPseudo());
		ps.setString(3, j.getMotdepasse());
		if(j.isAbonne()){
			ps.setString(4,"0");
		}else{
			ps.setString(4, "N");
		}
		ps.setString(5,""+j.getMain());
		ps.setInt(6, j.getNiveau());

		ps.executeUpdate();

		return numJoueur;
	}


void effacerJoueur(int num) throws SQLException {
    String sql = "DELETE FROM JOUEUR WHERE numJoueur = ?";
    PreparedStatement ps = this.laConnexion.prepareStatement(sql);
    ps.setInt(1, num);
    ps.executeUpdate();
}
void majJoueur(Joueur j) throws SQLException {
    String sql = "UPDATE JOUEUR SET pseudo = ?, motdepasse = ?, abonne = ?, main = ?, niveau = ? WHERE numJoueur = ?";
    PreparedStatement ps = this.laConnexion.prepareStatement(sql);
    ps.setString(1, j.getPseudo());
    ps.setString(2, j.getMotdepasse());
		if(j.isAbonne()){
			ps.setString(4,"0");
		}else{
			ps.setString(4, "N");
		}
    ps.setString(4, String.valueOf(j.getMain()));
    ps.setInt(5, j.getNiveau());
    ps.setInt(6, j.getNumJoueur());
    ps.executeUpdate();
}

    Joueur rechercherJoueurParNum(int num)throws SQLException{
    	throw new SQLException("méthode rechercherJoueurParNum à implémenter");
    }

	ArrayList<Joueur> listeDesJoueurs() throws SQLException{
		throw new SQLException("méthode listeDesJoueurs à implémenter");
	}
	
	String rapportMessage() throws SQLException{
		return "rapportMessage A faire";
	}
	
	String rapportMessageComplet() throws SQLException{
		return "rapportMessageComplet A faire";	
	}

	ArrayList<Map.Entry<String, Integer>> nbMsgParJour() throws SQLException{
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String, Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)
		throw new SQLException("méthode nbMsgParJour à implémenter");
	}
	ArrayList<Map.Entry<String, Integer>> nbMain() throws SQLException{
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String, Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)
		throw new SQLException("méthode nbMain à implémenter");
	}	
}