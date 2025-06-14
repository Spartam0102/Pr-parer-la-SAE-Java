package BD; 
import Java.*; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

	public List<Magasin> listeDesMagasins() throws SQLException {
		try (PreparedStatement ps = laConnexion.prepareStatement("select * from MAGASIN;")) {
			ResultSet rs = ps.executeQuery();
			ArrayList<Magasin> entreprise = new ArrayList<>();
			while (rs.next()) {
				entreprise.add(new Magasin(rs.getString("nommag"), rs.getString("villemag"), rs.getInt("idmag")));
			}
			return entreprise;
		}
	}

	public Livre trouverLivreParIsbn(long isbn) throws SQLException {
		String query = "SELECT * FROM LIVRE WHERE isbn = ?";

		try (PreparedStatement ps = laConnexion.prepareStatement(query)) {
			ps.setLong(1, isbn);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Livre(
						rs.getLong("isbn"),
						rs.getString("titre"),
						rs.getString("datepubli"),
						rs.getDouble("prix"),
						rs.getInt("nbpages"),
						new ArrayList<>(), // classifications
						new ArrayList<>(), // éditeurs
						new ArrayList<>()  // auteurs
					);
				} else {
					return null;
				}
			}
		}
	}
}