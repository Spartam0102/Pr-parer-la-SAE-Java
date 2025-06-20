package IHM.Controleur;

import java.util.Optional;

import BD.*;
import IHM.FenetrePanier;
import Java.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ControleurReinitialiserPanier implements EventHandler<ActionEvent> {

    private Client client;
    private ClientBD clientBD;
    private ConnexionMySQL connexionMySQL;
    private Stage stage;

    public ControleurReinitialiserPanier(Client client, ConnexionMySQL connexionMySQL, Stage stage) {
        this.client = client;
        this.clientBD = new ClientBD(connexionMySQL);
        this.stage = stage;
        this.connexionMySQL = connexionMySQL;
    }

    @Override
    public void handle(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Réinitialisation du panier");
        alert.setContentText("Êtes-vous sûr de vouloir vider votre panier ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            client.reinitialiserPanier();

            try {
                clientBD.sauvegardePanierBD(client);
                FenetrePanier fenetrePanier = new FenetrePanier(connexionMySQL, client);
                fenetrePanier.start(this.stage);
            } catch (Exception e) {
                System.out.println("Impossible de sauvegarder dans la base de données.");
            }
        }
    }
}