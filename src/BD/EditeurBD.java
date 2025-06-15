package BD;

import Java.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditeurBD {
	private ConnexionMySQL laConnexion;
	private Statement st;

	public EditeurBD(ConnexionMySQL laConnexion) {
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