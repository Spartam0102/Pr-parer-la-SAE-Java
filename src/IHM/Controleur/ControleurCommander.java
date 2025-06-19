package IHM.Controleur;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import BD.*;
import Java.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ControleurCommander implements EventHandler<ActionEvent> {

    private Magasin magasin;
    private Client client;
    private CommandeBD commandeBD;
    private String modeDeReception;
    private LivreBD livreBD;
    private ClientBD clientBD;

    public ControleurCommander(ConnexionMySQL connexion, Magasin magasin, Client client, String modeDeReception) {
        this.magasin = magasin;
        this.client = client;
        this.commandeBD = new CommandeBD(connexion);
        this.modeDeReception = modeDeReception;
        this.livreBD = new LivreBD(connexion);
        this.clientBD = new ClientBD(connexion);
    }

    @Override
    public void handle(ActionEvent event) {
        
        Map<Livre, Integer> panier = client.getPanier();
        double prixTotal = 0;
        for (Map.Entry<Livre, Integer> entry : panier.entrySet()) {
            Livre livre = entry.getKey();
            int quantite = entry.getValue();
            prixTotal += livre.getPrix() * quantite;
        }

        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de commande");
        confirmation.setHeaderText("Êtes-vous sûr de vouloir passer la commande ?");
        confirmation.setContentText("Prix total : " + String.format("%.2f", prixTotal) + " €");

        if (confirmation.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            
            try {
                System.out.println(magasin);
                int idCommande = commandeBD.genererNouvelIdCommande();
                String dateStr = LocalDate.now().toString();

                char modeRecepChar = modeDeReception.equals("Domicile") ? 'C' : 'M';

                Commande commande = new Commande(idCommande, dateStr, modeRecepChar, client, magasin);

                for (Map.Entry<Livre, Integer> entry : panier.entrySet()) {
                    commande.ajouterLivre(entry.getKey(), entry.getValue());
                }

                commandeBD.enregistrerCommande(commande);
                livreBD.majQteApresCommande(client, magasin);
                client.reinitialiserPanier();
                clientBD.sauvegardePanierBD(client);

                Alert good = new Alert(AlertType.INFORMATION);
                good.setTitle("Commande validée");
                good.setHeaderText(null);
                good.setContentText("Votre commande a bien été enregistrée !");
                good.showAndWait();
            }
            catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }
        }    
    }
}
