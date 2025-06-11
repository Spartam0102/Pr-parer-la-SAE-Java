package BD; 
import Java.*; 
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class ClientBD {
	ConnexionMySQL laConnexion;
	Statement st;
	public ClientBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	public Client recupererClient(int id) throws SQLException{
		PreparedStatement ps = laConnexion.prepareStatement("select * from CLIENT;");
		ResultSet rs = ps.executeQuery();
		while (rs.next()){
			
			if (rs.getInt(1) == id){
				Client client = new Client(
					rs.getString("nomcli"),
					rs.getString("prenomCli"),
					null,
					id,
					rs.getString("adressecli") + " " + rs.getString("codepostal") + " " + rs.getString("villecli"));
					return client;
			}				
		}
		return null;
	}

	/*
	int insererJoueur( Joueur j) throws  SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into JOUEUR values (?,?,?,?,?,?)"); 
		
		int numJoueur = this.maxNumJoueur()+1; 
		ps.setInt(1,numJoueur);
		ps.setString(2, j.getPseudo());
		ps.setString(3, j.getMotdepasse());	
		if (j.isAbonne())
			ps.setString(4, "O");
		else 
			ps.setString(4, "N");
			
		ps.setString(5, ""+j.getMain());
		ps.setInt(6, j.getNiveau());

		ps.executeUpdate(); 
		return numJoueur; 

	}


	void effacerJoueur(int num) throws SQLException {
    PreparedStatement ps = this.laConnexion.prepareStatement("DELETE FROM JOUEUR WHERE numJoueur = ?");
    ps.setInt(1, num);
    ps.executeUpdate();
}

    void majJoueur(Joueur j)throws SQLException{
		throw new SQLException("méthode majJoueur à implémenter");
    }

    Joueur rechercherJoueurParNum(int num) throws SQLException {
    PreparedStatement ps = this.laConnexion.prepareStatement("SELECT * FROM JOUEUR WHERE numJoueur = ?");
    ps.setInt(1, num);
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
        String pseudo = rs.getString("pseudo");
        String motdepasse = rs.getString("motdepasse");
        boolean abonne = rs.getString("abonne").equalsIgnoreCase("O");
        String main = rs.getString("main");
        int niveau = rs.getInt("niveau");

        return new Joueur(num, pseudo, motdepasse, abonne, main.charAt(0), niveau);
    } else {
        return null; 
    }
}
	 */

/* 
	int insererJoueur( Joueur j) throws  SQLException{
		PreparedStatement ps=this.laConnexion.prepareStatement("insert into JOUEUR values (?,?,?,?,?,?)");
		int numJoueur=this.maxNumJoueur()+1;
		ps.setInt(1,numJoueur);
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
*/
	ArrayList<Magasin> listeDesMagasins() throws SQLException{
		try(PreparedStatement ps = laConnexion.prepareStatement("select * from MAGASIN;")){
			ResultSet rs = ps.executeQuery();
			ArrayList<Magasin> entreprise=new ArrayList<>();
			while (rs.next()) {
				entreprise.add(new Magasin(rs.getString("nommag"),rs.getString("villemag"),rs.getInt("idmag")));				
			}
			return entreprise;
		}
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