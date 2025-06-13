package BD; 
import Java.*; 
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class LivreBD {
	ConnexionMySQL laConnexion;
	Statement st;

	public LivreBD(ConnexionMySQL laConnexion) {
		this.laConnexion = laConnexion;
	}

	ResultSet VerificationIsbn(String isbn) throws SQLException {
		this.st = this.laConnexion.createStatement();
		ResultSet resultat = st.executeQuery("SELECT * FROM LIVRE WHERE isbn='" + isbn + "';");


		if (!resultat.next()) {
			System.out.println("Aucun livre trouvé avec l'ISBN : " + isbn);
			return null;
		}
		resultat.beforeFirst();

		return resultat;
	}

	public void modifierStock(long isbn, int idMagasin, int nouvelleQte) throws SQLException {
    String updateQte = "UPDATE POSSEDER SET qte = ? WHERE isbn = ? AND idmag = ?";
    try (PreparedStatement ps = laConnexion.prepareStatement(updateQte)) {
        ps.setInt(1, nouvelleQte);
        ps.setLong(2, isbn);
        ps.setInt(3, idMagasin);
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated == 0) {
            throw new SQLException("Aucune ligne modifiée : association livre-magasin introuvable.");
        }
    }
}

	
	public String insererLivre(Livre l) throws SQLException {
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into LIVRE values (?,?,?,?,?)");
		PreparedStatement id = this.laConnexion.prepareStatement("insert into ECRIRE values (?,?)");
		PreparedStatement it = this.laConnexion.prepareStatement("insert into THEMES values (?,?)");
		PreparedStatement iq = this.laConnexion.prepareStatement("insert into EDITER values (?,?)");
		String res = "livre pas ajouter";

		if (VerificationIsbn(l.getIdLivre() + "") == null) {
			ps.setLong(1, l.getIdLivre());
			ps.setString(2, l.getNomLivre());
			ps.setInt(3, l.getNbPage());
			ps.setString(4, l.getDateDePublication());
			ps.setDouble(5, l.getPrix());
			ps.executeUpdate();
			res = "livre bien ajouter";

		

		for(String aut:l.getAuteur()){
			id.setString(1, l.getIdLivre()+"");
			id.setString(2, aut);
			id.executeUpdate();

		}

		for(String the:l.getClassification()){
			it.setString(1, l.getIdLivre()+"");
			it.setString(2, the);
			it.executeUpdate();

		}

		for(int edi:l.getEditeur()){
			iq.setString(1, l.getIdLivre()+"");
			iq.setInt(2, edi);
			iq.executeUpdate();

		}
		}
		return res;

	}

	public int getStockLivreMagasin(long isbn, int idMagasin) throws SQLException {
    String query = "SELECT qte FROM POSSEDER WHERE isbn = ? AND idmag = ?";
    try (PreparedStatement ps = laConnexion.prepareStatement(query)) {
        ps.setLong(1, isbn);
        ps.setInt(2, idMagasin);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("qte");
            } else {
                return 0; // ou -1 si tu préfères indiquer que c'est introuvable
            }
        }
    }
}

public ArrayList<Livre> listeDesLivres(int idMagasin) throws SQLException {
    ArrayList<Livre> livres = new ArrayList<>();

    String query = """
        SELECT l.*
        FROM LIVRE l
        JOIN POSSEDER p ON l.isbn = p.isbn
        WHERE p.idmag = ?
    """;

    try (PreparedStatement ps = laConnexion.prepareStatement(query)) {
        ps.setInt(1, idMagasin);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Livre l = new Livre(
                    rs.getLong("isbn"),
                    rs.getString("titre"),
                    rs.getString("datepubli"),
                    rs.getDouble("prix"),
                    rs.getInt("nbpages"),
                    new ArrayList<>(), // classifications
                    new ArrayList<>(), // éditeurs
                    new ArrayList<>()  // auteurs
                );
                livres.add(l);
            }
        }
    }

    return livres;
}




	/*
	 * void effacerJoueur(int num) throws SQLException {
	 * PreparedStatement ps =
	 * this.laConnexion.prepareStatement("DELETE FROM JOUEUR WHERE numJoueur = ?");
	 * ps.setInt(1, num);
	 * ps.executeUpdate();
	 * }
	 * 
	 * void majJoueur(Joueur j)throws SQLException{
	 * throw new SQLException("méthode majJoueur à implémenter");
	 * }
	 * 
	 * Joueur rechercherJoueurParNum(int num) throws SQLException {
	 * PreparedStatement ps =
	 * this.laConnexion.prepareStatement("SELECT * FROM JOUEUR WHERE numJoueur = ?"
	 * );
	 * ps.setInt(1, num);
	 * ResultSet rs = ps.executeQuery();
	 * 
	 * if (rs.next()) {
	 * String pseudo = rs.getString("pseudo");
	 * String motdepasse = rs.getString("motdepasse");
	 * boolean abonne = rs.getString("abonne").equalsIgnoreCase("O");
	 * String main = rs.getString("main");
	 * int niveau = rs.getInt("niveau");
	 * 
	 * return new Joueur(num, pseudo, motdepasse, abonne, main.charAt(0), niveau);
	 * } else {
	 * return null;
	 * }
	 * }
	 */

	/*
	 * int insererJoueur( Joueur j) throws SQLException{
	 * PreparedStatement ps=this.laConnexion.
	 * prepareStatement("insert into JOUEUR values (?,?,?,?,?,?)");
	 * int numJoueur=this.maxNumJoueur()+1;
	 * ps.setInt(1,numJoueur);
	 * ps.setString(2, j.getPseudo());
	 * ps.setString(3, j.getMotdepasse());
	 * if(j.isAbonne()){
	 * ps.setString(4,"0");
	 * }else{
	 * ps.setString(4, "N");
	 * }
	 * ps.setString(5,""+j.getMain());
	 * ps.setInt(6, j.getNiveau());
	 * 
	 * ps.executeUpdate();
	 * 
	 * return numJoueur;
	 * }
	 * 
	 * 
	 * void effacerJoueur(int num) throws SQLException {
	 * String sql = "DELETE FROM JOUEUR WHERE numJoueur = ?";
	 * PreparedStatement ps = this.laConnexion.prepareStatement(sql);
	 * ps.setInt(1, num);
	 * ps.executeUpdate();
	 * }
	 * void majJoueur(Joueur j) throws SQLException {
	 * String sql =
	 * "UPDATE JOUEUR SET pseudo = ?, motdepasse = ?, abonne = ?, main = ?, niveau = ? WHERE numJoueur = ?"
	 * ;
	 * PreparedStatement ps = this.laConnexion.prepareStatement(sql);
	 * ps.setString(1, j.getPseudo());
	 * ps.setString(2, j.getMotdepasse());
	 * if(j.isAbonne()){
	 * ps.setString(4,"0");
	 * }else{
	 * ps.setString(4, "N");
	 * }
	 * ps.setString(4, String.valueOf(j.getMain()));
	 * ps.setInt(5, j.getNiveau());
	 * ps.setInt(6, j.getNumJoueur());
	 * ps.executeUpdate();
	 * }
	 * 
	 * Joueur rechercherJoueurParNum(int num)throws SQLException{
	 * throw new SQLException("méthode rechercherJoueurParNum à implémenter");
	 * }
	 */
	ArrayList<Magasin> listeDesMagasins() throws SQLException {
		try (PreparedStatement ps = laConnexion.prepareStatement("select * from MAGASIN;")) {
			ResultSet rs = ps.executeQuery();
			ArrayList<Magasin> entreprise = new ArrayList<>();
			while (rs.next()) {
				entreprise.add(new Magasin(rs.getString("nommag"), rs.getString("villemag"), rs.getInt("idmag")));
			}
			return entreprise;
		}
	}

	String rapportMessage() throws SQLException {
		return "rapportMessage A faire";
	}

	String rapportMessageComplet() throws SQLException {
		return "rapportMessageComplet A faire";
	}

	ArrayList<Map.Entry<String, Integer>> nbMsgParJour() throws SQLException {
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String,
		// Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)
		throw new SQLException("méthode nbMsgParJour à implémenter");
	}

	ArrayList<Map.Entry<String, Integer>> nbMain() throws SQLException {
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String,
		// Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)
		throw new SQLException("méthode nbMain à implémenter");
	}

}
