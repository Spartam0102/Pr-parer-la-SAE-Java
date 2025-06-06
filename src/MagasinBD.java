import java.sql.*;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Map;
import java.util.HashMap;

public class MagasinBD {
	ConnexionMySQL laConnexion;
	Statement st;
	MagasinBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	int maxNumMagasin() throws SQLException{
		int maxNum=0;
		this.st=this.laConnexion.createStatement();
		ResultSet resultat=st.executeQuery("SELECT MAX(idmag) maxn FROM MAGASIN;");
		if(resultat.next()){
			maxNum=resultat.getInt("maxn");

		}
		return maxNum;

	}


	int insererMagasin( Magasin m) throws  SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into MAGASIN values (?,?,?)"); 
		
		int numJoueur = this.maxNumMagasin()+1; 
		ps.setInt(1,numJoueur);
		ps.setString(2, m.getNom());
		ps.setString(3, m.getVille());
		ps.executeUpdate();	
 
		return numJoueur; 

	}

	
	void effacerMagasin(int num) throws SQLException {
    PreparedStatement ps = this.laConnexion.prepareStatement("DELETE FROM MAGASIN WHERE idmag = ?");
    ps.setInt(1, num);
    ps.executeUpdate();
}

/*
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
				Magasin magasin = new Magasin(rs.getString("nommag"),rs.getString("villemag"),rs.getInt("idmag"));
				entreprise.add(magasin);		
			}
			return entreprise;
		}
	}
	
	Map<Livre, Integer> listeLivreUnMagasin(long id) throws SQLException {
    String requete = "SELECT DISTINCT isbn, titre, datepubli, prix, nbpages, qte FROM LIVRE NATURAL JOIN POSSEDER WHERE idmag = ?";
    
    try (PreparedStatement ps = laConnexion.prepareStatement(requete)){
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        Map<Livre, Integer> livresUnMagasin = new HashMap<>();

        while (rs.next()){
            long isbn = 0L;
            String isbnStr = rs.getString("isbn");
            if (isbnStr != null) isbn = Long.parseLong(isbnStr);

            String titre = rs.getString("titre");

            String datepubli = rs.getString("datepubli");

            double prix = 0.0;
            String prixStr = rs.getString("prix");
            if (prixStr != null) prix = Double.parseDouble(prixStr);

            int nbpages = 0;
            String nbpagesStr = rs.getString("nbpages");
            if (nbpagesStr != null) nbpages = Integer.parseInt(nbpagesStr);

            int quantite = 0;
            String qteStr = rs.getString("qte");
            if (qteStr != null) quantite = Integer.parseInt(qteStr);

			if (!(quantite == 0)){
				Livre livre = new Livre(isbn, titre, datepubli, prix, nbpages, null, null, null);
            livresUnMagasin.put(livre, quantite);
			} 
        }

        return livresUnMagasin;
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