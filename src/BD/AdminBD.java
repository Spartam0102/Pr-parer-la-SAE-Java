package BD;

import Java.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminBD {
	private ConnexionMySQL laConnexion;

	public AdminBD(ConnexionMySQL laConnexion) {
		this.laConnexion = laConnexion;
	}

	public Client recupererClient(int id) throws SQLException {

		PreparedStatement ps = laConnexion.prepareStatement("select * from CLIENT;");
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			if (rs.getInt(1) == id) {
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