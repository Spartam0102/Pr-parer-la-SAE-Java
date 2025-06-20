package IHM.Controleur;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JOptionPane;

import IHM.Général.*;
import BD.*;
import Java.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ControleurCommander implements EventHandler<ActionEvent> {

    private ComboBox<Magasin> comboMagasins;
    private Client client;
    private CommandeBD commandeBD;
    private String modeDeReception;
    private LivreBD livreBD;
    private ClientBD clientBD;
    private ConnexionMySQL connexion;
    private Stage stage;

    public ControleurCommander(Stage stage, ConnexionMySQL connexion, ComboBox<Magasin> comboMagasins, Client client, String modeDeReception) {
        this.stage = stage;
        this.connexion = connexion;
        this.comboMagasins = comboMagasins;        
        this.client = client;
        this.commandeBD = new CommandeBD(connexion);
        this.modeDeReception = modeDeReception;
        this.livreBD = new LivreBD(connexion);
        this.clientBD = new ClientBD(connexion);
    }

    @Override
    public void handle(ActionEvent event) {
        if (client.getPanier().isEmpty()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Panier vide");
            alert.setHeaderText(null);
            alert.setContentText("Le panier est vide.");
            alert.showAndWait();
            return;
        }


        Magasin magasin = comboMagasins.getSelectionModel().getSelectedItem();

        if (magasin == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Aucun magasin sélectionné");
            alert.setContentText("Veuillez choisir un magasin de retrait\n avant de commander.");
            alert.showAndWait();
            return;
        }

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

                FenetrePanier fenetrePanier = new FenetrePanier(this.connexion, client);
                fenetrePanier.start(this.stage);
            }
            catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }
        }    
    }
}
