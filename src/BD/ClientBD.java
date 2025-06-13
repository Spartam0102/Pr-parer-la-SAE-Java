package BD; 
import Java.*; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ClientBD {
	ConnexionMySQL laConnexion;
	Statement st;
	public ClientBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
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
			commandesDuCLient.setLong(1, rsCommande.getLong("isbn"));
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
		PreparedStatement toutlesClients = laConnexion.prepareStatement("select * from Client;");
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

