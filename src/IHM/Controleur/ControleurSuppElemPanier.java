package IHM.Controleur;

import java.util.Optional;

import BD.*;
import IHM.FenetrePanier;
import Java.*;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ControleurSuppElemPanier implements EventHandler<MouseEvent> {

    private Client client;
    private Livre livreASupprimer;
    private ClientBD clientBD;
    private ConnexionMySQL connexionMySQL;
    private Stage stage;

    public ControleurSuppElemPanier(Client client, Livre livreASupprimer, ConnexionMySQL connexionMySQL, Stage stage) {
        this.client = client;
        this.livreASupprimer = livreASupprimer;
        this.clientBD = new ClientBD(connexionMySQL);
        this.connexionMySQL = connexionMySQL;
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression d'un article");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce livre du panier ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            client.supprimerLivrePanier(livreASupprimer);

            try {
                clientBD.sauvegardePanierBD(client);
                FenetrePanier fenetrePanier = new FenetrePanier(connexionMySQL, client);
                fenetrePanier.start(this.stage);
            } catch (Exception e) {
                System.out.println("Impossible de sauvegarder dans la base de données.");
                e.printStackTrace();
            }
        }
    }
}
