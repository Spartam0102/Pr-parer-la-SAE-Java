package BD;

import Java.*;
import java.sql.*;

public class VendeurBD {
	private ConnexionMySQL laConnexion;

	public VendeurBD(ConnexionMySQL laConnexion) {
		this.laConnexion = laConnexion;
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
					magasin = new Magasin(null, null, idMagasin, 0.0, null);
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
}