package BD;

import Java.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MagasinBD {
	private ConnexionMySQL laConnexion;
	private Statement st;

	public MagasinBD(ConnexionMySQL laConnexion) {
		this.laConnexion = laConnexion;
	}

	public int getQuantiteLivreDansMagasin(long isbn, int idMagasin) throws SQLException {
		String sql = "SELECT qte FROM POSSEDER WHERE isbn = ? AND idmag = ?";
		try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {
			ps.setLong(1, isbn);
			ps.setInt(2, idMagasin);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("qte");
				} else {
					return 0;
				}
			}
		}
	}

	public Long getIsbnSiExiste(Livre livre) throws SQLException {
		String sql = "SELECT isbn FROM LIVRE WHERE titre = ?";
		try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {
			ps.setString(1, livre.getNomLivre());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("isbn");
				} else {
					return null;
				}
			}
		}
	}

	public int maxNumMagasin() throws SQLException {
		int maxNum = 0;
		this.st = this.laConnexion.createStatement();
		ResultSet resultat = st.executeQuery("SELECT MAX(idmag) maxn FROM MAGASIN;");

		if (resultat.next()) {
			maxNum = resultat.getInt("maxn");
		}
		return maxNum;
	}

	public Magasin trouverMagasinParId(int idMagasin) throws SQLException {
		String sql = "SELECT * FROM MAGASIN WHERE idmag = ?";

		try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {

			ps.setInt(1, idMagasin);
			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					return new Magasin(rs.getString("nommag"), rs.getString("adressemag"), rs.getInt("idmag"), rs.getDouble("note"), rs.getString("telmag"));
				} else {
					return null;
				}
			}
		}
	}

	public void ajouterLivreDansMagasin(int idMagasin, Livre livre, int quantite) throws SQLException {
		Long isbn = getIsbnSiExiste(livre);

		if (isbn == null) {
			isbn = insererLivre(livre);
		}
		if (existeDansStock(isbn, idMagasin)) {
			modifierStock(isbn, idMagasin, getQuantiteLivreDansMagasin(isbn, idMagasin) + quantite);
		} else {
			String sql = "INSERT INTO POSSEDER (isbn, idmag, qte) VALUES (?, ?, ?)";
			try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {

				ps.setLong(1, isbn);
				ps.setInt(2, idMagasin);
				ps.setInt(3, quantite);
				ps.executeUpdate();
			}
		}
	}

	public boolean existeDansStock(long isbn, int idMagasin) throws SQLException {
		String sql = "SELECT * FROM POSSEDER WHERE isbn = ? AND idmag = ?";
		try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {

			ps.setLong(1, isbn);
			ps.setInt(2, idMagasin);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	public ConnexionMySQL getConnexion() {
    return this.laConnexion; 
}


	public long insererLivre(Livre livre) throws SQLException {
		long isbnGenere = System.currentTimeMillis();
		int anneePublication;

		try {
			anneePublication = Integer.parseInt(livre.getDateDePublication());
		} catch (NumberFormatException e) {
			System.out.println("⚠️ Erreur : année de publication invalide (« " + livre.getDateDePublication() + " »).");
			throw new SQLException("Année invalide : " + livre.getDateDePublication(), e);
		}

		String sql = "INSERT INTO LIVRE (isbn, titre, datepubli, prix, nbpages) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {

			ps.setLong(1, isbnGenere);
			ps.setString(2, livre.getNomLivre());
			ps.setInt(3, anneePublication);
			ps.setDouble(4, livre.getPrix());
			ps.setInt(5, livre.getNbPage());
			ps.executeUpdate();
		}
		return isbnGenere;
	}

	public int insererMagasin(Magasin m) throws SQLException {
	PreparedStatement ps = this.laConnexion.prepareStatement("insert into MAGASIN values (?,?,?,?,?)");

	int numMagasin = this.maxNumMagasin() + 1;
	ps.setInt(1, numMagasin);
	ps.setString(2, m.getNom());
	ps.setString(3, m.getAdresse());
	ps.setString(4, m.getTel());
	ps.setDouble(5, m.getNote());
	ps.executeUpdate();

	return numMagasin;
	}

	public void effacerMagasin(int num) throws SQLException {
		PreparedStatement ps = this.laConnexion.prepareStatement("DELETE FROM MAGASIN WHERE idmag = ?");
		ps.setInt(1, num);
		ps.executeUpdate();
	}

	public List<Magasin> listeDesMagasins() throws SQLException {
		try (PreparedStatement ps = laConnexion.prepareStatement("select * from MAGASIN order by idmag;")) {

			ResultSet rs = ps.executeQuery();
			List<Magasin> entreprise = new ArrayList<>();

			while (rs.next()) {

				Magasin magasin = new Magasin(rs.getString("nommag"), rs.getString("adressemag"), rs.getInt("idmag"), rs.getDouble("note"), rs.getString("telmag"));
				entreprise.add(magasin);
			}
			return entreprise;
		}
	}

	public Map<Livre, Integer> listeLivreUnMagasin(int id) throws SQLException {
		String requete = "SELECT DISTINCT isbn, titre, datepubli, prix, nbpages, qte, idmag FROM LIVRE NATURAL JOIN POSSEDER WHERE idmag = ?";

		try (PreparedStatement ps = laConnexion.prepareStatement(requete)) {

			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			Map<Livre, Integer> livresUnMagasin = new HashMap<>();

			while (rs.next()) {
				int quantite = rs.getInt("qte");
				if (!(quantite == 0)) {

					Livre livre = new Livre(rs.getLong("isbn"),
							rs.getString("titre"),
							rs.getString("datepubli"),
							rs.getDouble("prix"),
							rs.getInt("nbpages"),
							null,
							null,
							null);
					livresUnMagasin.put(livre, quantite);
				}
			}
			return livresUnMagasin;
		}
	}

	public void modifierStock(long isbn, int idMagasin, int nouvelleQuantite) throws SQLException {
		String updateQte = "UPDATE POSSEDER SET qte = ? WHERE isbn = ? AND idmag = ?";

		try (PreparedStatement psQte = laConnexion.prepareStatement(updateQte)) {
			psQte.setInt(1, nouvelleQuantite);
			psQte.setLong(2, isbn);
			psQte.setInt(3, idMagasin);

			int rowsUpdated = psQte.executeUpdate();
			if (rowsUpdated == 0) {
				throw new SQLException("Aucune ligne modifiée : l'association livre-magasin est introuvable.");
			}
		}
	}

	public void supprimerLivreDuMagasin(long isbn, int idMagasin) throws SQLException {
    String sql = "DELETE FROM POSSEDER WHERE isbn = ? AND idmag = ?";
    try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {
        ps.setLong(1, isbn);
        ps.setInt(2, idMagasin);
        ps.executeUpdate();
    }
}

}