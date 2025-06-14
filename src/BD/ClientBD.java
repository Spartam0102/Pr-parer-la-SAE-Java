package BD; 
import Java.*; 
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ClientBD {
	ConnexionMySQL laConnexion;
	Statement st;
	public ClientBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	public Map<Livre, Integer> recupererPanier(int id) throws SQLException {
		PreparedStatement ps = laConnexion.prepareStatement("select * from LIVRE natural join PANIER where idCli=" + id + ";");
		ResultSet rs = ps.executeQuery();
		Map<Livre, Integer> panier = new HashMap<>();
		while (rs.next()) {

			long isbn = Long.parseLong(rs.getString("isbn"));
			int quantite = Integer.parseInt(rs.getString("quantite"));

			String titre = rs.getString("titre");

			String datepubli = rs.getString("datepubli");

			double prix = 0.0;
			String prixStr = rs.getString("prix");
			if (prixStr != null)
				prix = Double.parseDouble(prixStr);

				int nbpages = 0;
				String nbpagesStr = rs.getString("nbpages");
			if (nbpagesStr != null)
				nbpages = Integer.parseInt(nbpagesStr);

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

		for (Map.Entry<Livre, Integer> entree : client.getPanier().entrySet()) {
			Livre livre = entree.getKey();
			int quantite = entree.getValue();

			ps2.setInt(1, idCli);
			ps2.setString(2, Long.toString(livre.getIdLivre()));
			ps2.setInt(3, quantite);

			ps2.executeUpdate();
		}
		ps2.close();
	}

	public Client recupererClient(int id) throws SQLException{
		PreparedStatement ps = laConnexion.prepareStatement("select * from CLIENT where idcli = ?;");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()){
			Client client = new Client(
				rs.getString("nomcli"),
				rs.getString("prenomCli"),
				null,
				id,
				rs.getString("adressecli") + " " + rs.getString("codepostal") + " " + rs.getString("villecli"));
				return client;		
		}
		return null;
	}


	public List<Livre> recupererToutLivreClient(int id) throws SQLException{
		List<Livre> res = new ArrayList<>();
		PreparedStatement commandesDuCLient = laConnexion.prepareStatement("select * from COMMANDE natural join DETAILCOMMANDE where idcli = ?;");
		commandesDuCLient.setInt(1,id);
		ResultSet rsCommande = commandesDuCLient.executeQuery();
		while(rsCommande.next()){
			PreparedStatement livreDeLaCommande = laConnexion.prepareStatement("select * from LIVRE where isbn = ?;");
			livreDeLaCommande.setLong(1, rsCommande.getLong("isbn"));
			ResultSet rsLivre = livreDeLaCommande.executeQuery();
			while (rsLivre.next()) {
				Livre livre = new Livre(rsLivre.getLong("isbn"), 
										rsLivre.getString("titre"), 
										rsLivre.getString("datepubli"), 
										rsLivre.getDouble("prix"),
										rsLivre.getInt("nbpages"),
										null,
										null,
										null);
				res.add(livre);	
			}
		}
		return res;
	}

	public List<Client> recuperToutClient() throws SQLException{
		List<Client> res = new ArrayList<>();
		PreparedStatement toutlesClients = laConnexion.prepareStatement("select * from CLIENT;");
		ResultSet rs = toutlesClients.executeQuery();
		while(rs.next()){
			Client client = new Client(
				rs.getString("nomcli"),
				rs.getString("prenomCli"),
				null,
				rs.getInt("idcli"),
				rs.getString("adressecli") + " " + rs.getString("codepostal") + " " + rs.getString("villecli"));
			res.add(client);
		}
		return res;
	}
}