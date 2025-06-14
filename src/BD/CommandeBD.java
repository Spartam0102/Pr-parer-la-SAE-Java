package BD; 
import Java.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class CommandeBD {
	ConnexionMySQL laConnexion;
	Statement st;
    ClientBD clientBD;
    MagasinBD magasinBD;
    LivreBD livreBD;
	public CommandeBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
        this.clientBD = new ClientBD(laConnexion);
        this.magasinBD = new MagasinBD(laConnexion);
        this.livreBD = new LivreBD(laConnexion);
	}

	public int genererNouvelIdCommande() throws SQLException {
        String sql = "select MAX(numcom) as maxId from COMMANDE";
        try (Statement stmt = this.laConnexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()){
                int maxId = rs.getInt("maxId");
                return maxId + 1;
            }
            else{
                return 1;
            }
        }
    }

    public  void enregistrerCommande(Commande commande) throws SQLException {
            String sqlCommande = "INSERT INTO COMMANDE (numcom, datecom, enligne, livraison, idcli, idmag) VALUES (?, ?, ?, ?, ?, ?)";
             try (PreparedStatement psCommande = this.laConnexion.prepareStatement(sqlCommande)){
            psCommande.setInt(1, commande.getIdCommande());
            psCommande.setDate(2, java.sql.Date.valueOf(commande.getDateDeCommande()));
            psCommande.setString(3, "O");
            psCommande.setString(4, String.valueOf(commande.getModeDeReception()));
            psCommande.setInt(5, commande.getClient().getIdCli());
            psCommande.setInt(6, commande.getMagasin().getIdMagasin());
            psCommande.executeUpdate();

            // 2. Insertion dans DETAILCOMMANDE
            String sqlDetail = "INSERT INTO DETAILCOMMANDE (numcom, numlig, qte, prixvente, isbn) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psDetail = this.laConnexion.prepareStatement(sqlDetail);

            int numLig = 1;
            for (Map.Entry<Livre, Integer> entry : commande.getLivresCommande().entrySet()) {
                Livre livre = entry.getKey();
                int qte = entry.getValue();

                psDetail.setInt(1, commande.getIdCommande());
                psDetail.setInt(2, numLig++);
                psDetail.setInt(3, qte);
                psDetail.setInt(4, (int) livre.getPrix());
                psDetail.setString(5, String.valueOf(livre.getIdLivre()));
                psDetail.executeUpdate();
            }
        }
    }

    public List<Commande> getCommandesParClient(int idClient) throws SQLException {
        List<Commande> commandes = new ArrayList<>();

        String sqlCommande = "SELECT * FROM COMMANDE WHERE idcli = ? ORDER BY datecom DESC";
        String sqlDetail = "SELECT * FROM DETAILCOMMANDE WHERE numcom = ? ORDER BY numlig";

        try (PreparedStatement psCommande = this.laConnexion.prepareStatement(sqlCommande)) {
            psCommande.setInt(1, idClient);

            try (ResultSet rsCommande = psCommande.executeQuery()) {
                while (rsCommande.next()) {
                    int numcom = rsCommande.getInt("numcom");
                    LocalDate datecom = rsCommande.getDate("datecom").toLocalDate();
                    char modeReception = rsCommande.getString("livraison").charAt(0);
                    int idcli = rsCommande.getInt("idcli");
                    int idmag = rsCommande.getInt("idmag"); 
                    Client client = clientBD.recupererClient(idcli);
                    Magasin magasin = magasinBD.trouverMagasinParId(idmag); 

                    Commande commande = new Commande(numcom, datecom.toString(), modeReception, client, magasin);

                    try (PreparedStatement psDetail = this.laConnexion.prepareStatement(sqlDetail)) {
                        psDetail.setInt(1, numcom);
                        try (ResultSet rsDetail = psDetail.executeQuery()) {
                            while (rsDetail.next()) {
                                int qte = rsDetail.getInt("qte");
                                double prix = rsDetail.getDouble("prixvente");
                                String isbnStr = rsDetail.getString("isbn");
                                long isbn = Long.parseLong(isbnStr);

                                Livre livre = livreBD.trouverLivreParIsbn(isbn);
                                if (livre != null) {
                                    livre.setPrix(prix); // actualiser le prix au moment de la commande
                                    commande.ajouterLivre(livre, qte);
                                }
                            }
                        }
                    }

                    commandes.add(commande);
                }
            }
        }
        return commandes;
    }
}