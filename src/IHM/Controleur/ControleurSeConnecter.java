package IHM.Controleur;

import BD.ConnexionMySQL;
import BD.VendeurBD;
import BD.AdministrateurBD;
import BD.ClientBD;
import IHM.FenetreMagasins;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ControleurSeConnecter {

    private ConnexionMySQL connexionMySQL;
    private ClientBD clientBD;
    private VendeurBD vendeurBD;
    private AdministrateurBD adminBD;

    public ControleurSeConnecter(ConnexionMySQL connexionMySQL) {
        this.connexionMySQL = connexionMySQL;
        this.clientBD = new ClientBD(connexionMySQL);
        this.vendeurBD = new VendeurBD(connexionMySQL);
        this.adminBD = new AdministrateurBD(connexionMySQL);
    }

    public void gererConnexion(Button boutonConnexion, TextField userfield, PasswordField mdpfield,
            ToggleGroup groupeRoles) {
        boutonConnexion.setOnAction(e -> {
            String idTexte = userfield.getText().trim();
            String mdp = mdpfield.getText();

            Toggle selectedToggle = groupeRoles.getSelectedToggle();
            if (idTexte.isEmpty() || mdp.isEmpty() || selectedToggle == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.show();
                return;
            }

            String role = ((ToggleButton) selectedToggle).getText();

            int id;
            try {
                id = Integer.parseInt(idTexte);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("L'identifiant doit être un nombre.");
                alert.show();
                return;
            }

            boolean authOk = false;
            try {
                switch (role) {
                    case "Client":
                        authOk = clientBD.verifierConnexion(id, mdp);
                        break;
                    case "Vendeur":
                        authOk = vendeurBD.verifierConnexion(id, mdp);
                        break;
                    case "Administrateur":
                        authOk = adminBD.verifierConnexion(id, mdp);
                        break;
                    default:
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Rôle inconnu.");
                        alert.show();
                        return;
                }
            } catch (SQLException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Erreur lors de la connexion à la base de données.");
                alert.show();
                ex.printStackTrace();
                return;
            }

            if (authOk) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Connexion réussie !");
                alert.show();

                // Récupérer la fenêtre actuelle
                Stage currentStage = (Stage) boutonConnexion.getScene().getWindow();

                // Ouvrir la nouvelle fenêtre FenetreMagasins
                FenetreMagasins fenetreMagasins = new FenetreMagasins(connexionMySQL);
                Stage newStage = new Stage();
                try {
                    fenetreMagasins.start(newStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // Fermer la fenêtre actuelle
                currentStage.close();
            }

        });
    }
}
