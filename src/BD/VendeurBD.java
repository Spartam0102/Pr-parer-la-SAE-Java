package BD;

import Java.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendeurBD {
	private ConnexionMySQL laConnexion;
	private Statement st;

	public VendeurBD(ConnexionMySQL laConnexion) {
		this.laConnexion = laConnexion;
	}

	public int maxMagasin() throws SQLException {
		int maxNum = 0;
		this.st = this.laConnexion.createStatement();
		ResultSet resultat = st.executeQuery("SELECT MAX(idmag) maxn FROM MAGASIN;");

		if (resultat.next()) {
			maxNum = resultat.getInt("maxn");
		}
		return maxNum;
	}

	public void creerVendeur(Vendeur vendeur) throws SQLException {
		int nextId = getNextIdVendeur();
		String sql = "INSERT INTO VENDEUR (idVen, nomVen, prenomVen, idmag) VALUES (?, ?, ?, ?)";

		try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {

			ps.setInt(1, nextId);
			ps.setString(2, vendeur.getNom());
			ps.setString(3, vendeur.getPrenom());

			if (vendeur.getMagasin() != null) {
				ps.setInt(4, vendeur.getMagasin().getIdMagasin());
			} else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.executeUpdate();
		}
	}

	public int getNextIdVendeur() throws SQLException {
		String sql = "SELECT MAX(idVen) AS maxId FROM VENDEUR";

		try (PreparedStatement ps = laConnexion.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return rs.getInt("maxId") + 1;
			}
		}
		return 1;
	}

	public Vendeur recupererVendeur(int id) throws SQLException {

		String sql = "SELECT * FROM VENDEUR WHERE idVen = ?";
		try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				int idMagasin = rs.getInt("idmag");
				Magasin magasin = null;

				if (idMagasin > 0) {
					magasin = new Magasin(null, null, idMagasin);
				}
				return new Vendeur(
						rs.getString("nomVen"),
						rs.getString("prenomVen"),
						null,
						id,
						magasin);
			}
		}
		return null;
	}

	public List<Magasin> listeDesMagasins() throws SQLException {
		try (PreparedStatement ps = laConnexion.prepareStatement("select * from MAGASIN;")) {

			ResultSet rs = ps.executeQuery();
			List<Magasin> entreprise = new ArrayList<>();

			while (rs.next()) {
				entreprise.add(new Magasin(rs.getString("nommag"), rs.getString("villemag"), rs.getInt("idmag")));
			}
			return entreprise;
		}
	}
}