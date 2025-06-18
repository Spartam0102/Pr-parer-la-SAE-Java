package BD;

import Java.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class VendeurBD {
	private ConnexionMySQL laConnexion;

	public VendeurBD(ConnexionMySQL laConnexion) {
		this.laConnexion = laConnexion;
	}

	public void creerVendeur(Vendeur vendeur) throws SQLException {
		int nextId = getNextIdVendeur();
		String sql = "INSERT INTO VENDEUR (idVen, nomVen, prenomVen, idmag, mdpV) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement ps = laConnexion.prepareStatement(sql)) {

			ps.setInt(1, nextId);
			ps.setString(2, vendeur.getNom());
			ps.setString(3, vendeur.getPrenom());

			if (vendeur.getMagasin() != null) {
				ps.setInt(4, vendeur.getMagasin().getIdMagasin());
			} else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.setString(5, vendeur.getMotDePasseVendeur());
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
						magasin,
						rs.getString("mdpV"));
			}
		}
		return null;
	}

	public boolean verifierConnexion(int idVen, String mdp) throws SQLException {
    String sql = "SELECT mdpV FROM VENDEUR WHERE idven = ?";
    PreparedStatement ps = laConnexion.prepareStatement(sql);
    ps.setInt(1, idVen);
    ResultSet rs = ps.executeQuery();

    boolean isValid = false;
    if (rs.next()) {
        String mdpBD = rs.getString("mdpV");
        isValid = mdpBD != null && mdpBD.equals(mdp);
    }

    rs.close();
    ps.close();

    return isValid;
}


	public Map<String, String> recupererIdEtMotDePasse(int idVen) throws SQLException {
    Map<String, String> result = new HashMap<>();

    String sql = "SELECT idVen, mdpV FROM VENDEUR WHERE idVen = ?";
    PreparedStatement ps = laConnexion.prepareStatement(sql);
    ps.setInt(1, idVen);
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
        result.put("idVen", String.valueOf(rs.getInt("idVen")));
        result.put("mdpV", rs.getString("mdpV"));
    }

    rs.close();
    ps.close();

    return result;
}
}