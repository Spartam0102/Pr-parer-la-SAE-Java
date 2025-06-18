package BD;

import Java.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientBD {
	private ConnexionMySQL laConnexion;
	private ClientBD clientBD;

	public ClientBD(ConnexionMySQL laConnexion) {
    this.laConnexion = laConnexion;
    this.clientBD = this; 
}


	public Client recupererClient(int id) throws SQLException {
		PreparedStatement ps = laConnexion.prepareStatement("select * from CLIENT where idcli = ?;");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			Client client = new Client(
					rs.getString("nomcli"),
					rs.getString("prenomCli"),
					null,
					id,
					rs.getString("adressecli") + " " + rs.getString("codepostal") + " " + rs.getString("villecli"),
					rs.getString("mdpC"));
			return client;
		}
		return null;
	}

	public List<Client> recuperToutClient() throws SQLException {
		List<Client> res = new ArrayList<>();
		PreparedStatement toutlesClients = laConnexion.prepareStatement("select * from CLIENT;");
		ResultSet rs = toutlesClients.executeQuery();

		while (rs.next()) {

			Client client = new Client(
					rs.getString("nomcli"),
					rs.getString("prenomCli"),
					null,
					rs.getInt("idcli"),
					rs.getString("adressecli") + " " + rs.getString("codepostal") + " " + rs.getString("villecli"),
					rs.getString("mdpC"));
			res.add(client);
		}
		return res;
	}

	public Map<Livre, Integer> recupererPanier(int id) throws SQLException {
		PreparedStatement ps = laConnexion
				.prepareStatement("select * from LIVRE natural join PANIER where idCli=" + id + ";");
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

	public boolean verifierConnexion(int idCli, String mdp) throws SQLException {
    String sql = "SELECT mdpC FROM CLIENT WHERE idcli = ?";
    PreparedStatement ps = laConnexion.prepareStatement(sql);
    ps.setInt(1, idCli);
    ResultSet rs = ps.executeQuery();

    boolean isValid = false;
    if (rs.next()) {
        String mdpBD = rs.getString("mdpC");
        isValid = mdpBD != null && mdpBD.equals(mdp);
    }

    rs.close();
    ps.close();

    return isValid;
}


	public Map<String, String> recupererIdEtMotDePasse(int idCli) throws SQLException {
		Map<String, String> result = new HashMap<>();

		String sql = "SELECT idcli, mdpC FROM CLIENT WHERE idcli = ?";
		PreparedStatement ps = laConnexion.prepareStatement(sql);
		ps.setInt(1, idCli);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			result.put("idcli", String.valueOf(rs.getInt("idcli")));
			result.put("mdpC", rs.getString("mdpC"));
		}

		rs.close();
		ps.close();

		return result;
	}

	public void creerClient(Client client) throws SQLException {
    String sql = "INSERT INTO CLIENT (idcli, nomcli, prenomCli, adressecli, codepostal, villecli, mdpC) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement ps = laConnexion.prepareStatement(sql);

    
    String adresseComplete = client.getAdresse(); 
    String[] adresseSplit = adresseComplete.split(" ");

    
    String codePostal = adresseSplit[adresseSplit.length - 2];
    String ville = adresseSplit[adresseSplit.length - 1];
    StringBuilder adresse = new StringBuilder();
    for (int i = 0; i < adresseSplit.length - 2; i++) {
        adresse.append(adresseSplit[i]).append(" ");
    }

	ps.setInt(1, client.getIdCli());
    ps.setString(2, client.getNom());
    ps.setString(3, client.getPrenom());
    ps.setString(4, adresse.toString().trim());
    ps.setString(5, codePostal);
    ps.setString(6, ville);
    ps.setString(7, client.getMotDePasseCli());

    ps.executeUpdate();
    ps.close();
}




	public List<Livre> recupererToutLivreClient(int id) throws SQLException {
		List<Livre> res = new ArrayList<>();

		PreparedStatement commandesDuCLient = laConnexion
				.prepareStatement("select * from COMMANDE natural join DETAILCOMMANDE where idcli = ?;");
		commandesDuCLient.setInt(1, id);
		ResultSet rsCommande = commandesDuCLient.executeQuery();

		while (rsCommande.next()) {

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

	public int maxIdClient() throws SQLException {
	Integer maxId = null;
		PreparedStatement ps = laConnexion
				.prepareStatement("SELECT MAX(idcli) maxid FROM CLIENT;");
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			maxId =  rs.getInt("maxid");
		}
		return maxId;
	}

	public List<Livre> livreRecommander(int id) throws SQLException {
    List<Livre> livresDuClient = this.recupererToutLivreClient(id);
    List<Client> tousLesClients = this.recuperToutClient();
    List<Livre> meilleureListe = maxLivreEnCommun(livresDuClient, tousLesClients, id);
    return differenceDeLivre(livresDuClient, meilleureListe);
}


    public List<Livre> differenceDeLivre(List<Livre> liste1, List<Livre> liste2) {
        List<Livre> res = new ArrayList<>();
        for (Livre livre : liste2) {
            if (!liste1.contains(livre)) {
                res.add(livre);
            }
        }
        return res;
    }

    public List<Livre> maxLivreEnCommun(List<Livre> livresDuClient, List<Client> listeClients, int idClientCourant)
            throws SQLException {
        List<Livre> res = new ArrayList<>();
        int max = 0;

        for (Client client : listeClients) {
            if (client.getIdCli() == idClientCourant)
                continue;

            List<Livre> livresAutreClient = this.recupererToutLivreClient(client.getIdCli());

            int commun = livreEnCommun(livresDuClient, livresAutreClient);

            if (commun > max) {
                max = commun;
                res = livresAutreClient;
            }
        }
        return res;
    }

    public int livreEnCommun(List<Livre> liste1, List<Livre> liste2) {
        int res = 0;
        for (Livre livre : liste2) {
            if (liste1.contains(livre)) {
                res++;
            }
        }
        return res;
    }
}