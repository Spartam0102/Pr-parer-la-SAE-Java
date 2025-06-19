package IHM.Controleur;

import BD.*;
import Java.Client;
import Java.Livre;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControleurAjouterLivre implements EventHandler<ActionEvent> {

    private Client client;
    private Livre livre;
    private ClientBD clientBD;

    public ControleurAjouterLivre(Client client, Livre livre, ConnexionMySQL connexionMySQL) {
        this.client = client;
        this.livre = livre;
        this.clientBD = new ClientBD(connexionMySQL);
    }

    @Override
    public void handle(ActionEvent event) {
        client.ajouterLivrePanier(livre);
        try {
            clientBD.sauvegardePanierBD(client);
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
