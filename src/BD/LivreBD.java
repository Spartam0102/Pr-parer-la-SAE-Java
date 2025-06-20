package BD;

import Java.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LivreBD {
	private ConnexionMySQL laConnexion;
	private Statement st;

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

	public int getStockLivreMagasin(long isbn, int idMagasin) throws SQLException {
		String query = "SELECT qte FROM POSSEDER WHERE isbn = ? AND idmag = ?";

		try (PreparedStatement ps = laConnexion.prepareStatement(query)) {
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

	public List<Livre> listeDesLivres(int idMagasin) throws SQLException {
		List<Livre> livres = new ArrayList<>();

		String query = "SELECT l.* FROM LIVRE l JOIN POSSEDER p ON l.isbn = p.isbn WHERE p.idmag = ?";
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
							new ArrayList<>(),
							new ArrayList<>(),
							null);
					livres.add(l);
				}
			}
		}
		return livres;
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
							new ArrayList<>(),
							new ArrayList<>(),
							null);
				} else {
					return null;
				}
			}
		}
	}

	public void majQteApresCommande(Client client, Magasin magasinDestination) throws SQLException {
		Map<Livre, Integer> panier = client.getPanier();

		for (Map.Entry<Livre, Integer> entry : panier.entrySet()) {

			Livre livre = entry.getKey();
			int qteDemandee = entry.getValue();
			long isbn = livre.getIdLivre();
			int idMagDest = magasinDestination.getIdMagasin();
			int qteDisponible = getQteActuelleDansMagasin(isbn, idMagDest);

			if (qteDisponible >= qteDemandee) {
				modifierStock(isbn, idMagDest, qteDisponible - qteDemandee);
			} else {
				int qteManquante = qteDemandee - qteDisponible;

				if (qteDisponible > 0) {
					modifierStock(isbn, idMagDest, 0);
				}
				transfererDepuisAutreMagasin(isbn, idMagDest, qteManquante);
			}
		}
	}

	public int getQteActuelleDansMagasin(long isbn, int idMagasin) throws SQLException {
		String query = "SELECT qte FROM POSSEDER WHERE isbn = ? AND idmag = ?";

		try (PreparedStatement ps = laConnexion.prepareStatement(query)) {

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

	public void transfererDepuisAutreMagasin(long isbn, int idMagDest, int qteATransferer) throws SQLException {
		String query = "SELECT idmag, qte FROM POSSEDER WHERE isbn = ? AND idmag <> ? ORDER BY qte DESC";

		try (PreparedStatement ps = laConnexion.prepareStatement(query)) {
			ps.setLong(1, isbn);
			ps.setInt(2, idMagDest);

			try (ResultSet rs = ps.executeQuery()) {
				int qteRestante = qteATransferer;

				while (rs.next() && qteRestante > 0) {
					int idMagSource = rs.getInt("idmag");
					int qteSource = rs.getInt("qte");

					if (qteSource > 0) {
						int qtePrelevee = Math.min(qteSource, qteRestante);
						modifierStock(isbn, idMagSource, qteSource - qtePrelevee);
						ajouterOuMajStock(isbn, idMagDest, qtePrelevee);
						qteRestante -= qtePrelevee;
					}
				}

				if (qteRestante > 0) {
					throw new SQLException(
							"Stock insuffisant pour transférer " + qteATransferer + " exemplaires du livre " + isbn);
				}
			}
		}
	}

	public void ajouterOuMajStock(long isbn, int idMag, int qteAjoutee) throws SQLException {
		String select = "SELECT qte FROM POSSEDER WHERE isbn = ? AND idmag = ?";

		try (PreparedStatement ps = laConnexion.prepareStatement(select)) {

			ps.setLong(1, isbn);
			ps.setInt(2, idMag);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					int qteActuelle = rs.getInt("qte");
					modifierStock(isbn, idMag, qteActuelle + qteAjoutee);

				} else {
					String insert = "INSERT INTO POSSEDER (isbn, idmag, qte) VALUES (?, ?, ?)";

					try (PreparedStatement insertPs = laConnexion.prepareStatement(insert)) {

						insertPs.setLong(1, isbn);
						insertPs.setInt(2, idMag);
						insertPs.setInt(3, qteAjoutee);
						insertPs.executeUpdate();
					}
				}
			}
		}
	}
}