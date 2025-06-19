package IHM.Controleur;

import BD.*;
import Java.Client;
import Java.Livre;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class ControleurAjouterLivrePanier implements EventHandler<ActionEvent> {

    private Client client;
    private Livre livre;
    private ClientBD clientBD;
    private Label lblCompteur;

public ControleurAjouterLivrePanier(Client client, Livre livre, ConnexionMySQL connexionMySQL, Label lblCompteur) {
    this.client = client;
    this.livre = livre;
    this.clientBD = new ClientBD(connexionMySQL);
    this.lblCompteur = lblCompteur;
}


    @Override
public void handle(ActionEvent event) {
    client.ajouterLivrePanier(livre);
    try {
        clientBD.sauvegardePanierBD(client);

        // Mise à jour du label compteur
        int nbLivres = client.getPanier().size(); // ou autre logique
        lblCompteur.setText("Panier : " + nbLivres + " livre(s)");

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Le livre \"" + livre.getNomLivre() + "\" a bien été ajouté au panier.");
        alert.showAndWait();
    } catch (Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Impossible d'ajouter le livre au panier");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}

}
