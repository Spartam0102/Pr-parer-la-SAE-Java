package BD; 
import Java.*; 
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ClientBD {
	ConnexionMySQL laConnexion;
	Statement st;
	public ClientBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	public Client recupererClient(int id) throws SQLException{
		PreparedStatement ps = laConnexion.prepareStatement("select * from CLIENT;");
		ResultSet rs = ps.executeQuery();
		while (rs.next()){
			
			if (rs.getInt(1) == id){
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

	public Map<Livre, Integer> recupererPanier(int id) throws SQLException{
		PreparedStatement ps = laConnexion.prepareStatement("select * from LIVRE natural join PANIER where idCli=" + id + ";");
		ResultSet rs = ps.executeQuery();
		Map<Livre, Integer> panier = new HashMap<>();
		while (rs.next()){
			
			long isbn = Long.parseLong(rs.getString("isbn"));
			int quantite = Integer.parseInt(rs.getString("quantite"));

			String titre = rs.getString("titre");

			String datepubli = rs.getString("datepubli");

			double prix = 0.0;
			String prixStr = rs.getString("prix");
			if (prixStr != null) prix = Double.parseDouble(prixStr);

			int nbpages = 0;
			String nbpagesStr = rs.getString("nbpages");
			if (nbpagesStr != null) nbpages = Integer.parseInt(nbpagesStr);

			Livre livre = new Livre(isbn, titre, datepubli, prix, nbpages, null, null, null);
			panier.put(livre, quantite);						
		}
		return panier;
	}
 
	public void sauvegardePanierBD(Client client) throws SQLException {
		int idCli = client.getIdCli();

		String deleteSql = "DELETE FROM PANIER WHERE idCli = ?";
		PreparedStatement ps1 = this.laConnexion.prepareStatement(deleteSql);
		ps1.setInt(1, idCli);
		ps1.executeUpdate();
		ps1.close();

		String insertSql = "INSERT INTO PANIER (idCli, isbn, quantite) VALUES (?, ?, ?)";
		PreparedStatement ps2 = this.laConnexion.prepareStatement(insertSql);

		for (Map.Entry<Livre, Integer> entree : client.getPanier().entrySet()){
			Livre livre = entree.getKey();
			int quantite = entree.getValue();

			ps2.setInt(1, idCli);
			ps2.setString(2, Long.toString(livre.getIdLivre()));
			ps2.setInt(3, quantite);

			ps2.executeUpdate();
		}
		ps2.close();
	}


	ArrayList<Magasin> listeDesMagasins() throws SQLException{
		try(PreparedStatement ps = laConnexion.prepareStatement("select * from MAGASIN;")){
			ResultSet rs = ps.executeQuery();
			ArrayList<Magasin> entreprise=new ArrayList<>();
			while (rs.next()) {
				entreprise.add(new Magasin(rs.getString("nommag"),rs.getString("villemag"),rs.getInt("idmag")));				
			}
			return entreprise;
		}
	}
}