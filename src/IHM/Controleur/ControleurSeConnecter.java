package IHM.Controleur;

import BD.ConnexionMySQL;
import BD.ClientBD;
import IHM.FenetreMagasins;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Map;

public class ControleurSeConnecter {

    private ConnexionMySQL connexionMySQL;
    private ClientBD clientBD;

    public ControleurSeConnecter(ConnexionMySQL connexionMySQL) {
        this.connexionMySQL = connexionMySQL;
        this.clientBD = new ClientBD(connexionMySQL);
    }

    public void gererConnexion(Button boutonConnexion, javafx.scene.control.TextField userfield, javafx.scene.control.PasswordField mdpfield) {
        boutonConnexion.setOnAction(e -> {
            try {
                int idCli = Integer.parseInt(userfield.getText());
                String mdpEntre = mdpfield.getText();

                Map<String, String> infos = clientBD.recupererIdEtMotDePasse(idCli);

                if (!infos.isEmpty() && infos.get("mdpC").equals(mdpEntre)) {
                    Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                    FenetreMagasins.afficher(stage, connexionMySQL);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de connexion");
                    alert.setHeaderText("Identifiants incorrects");
                    alert.setContentText("Le mot de passe ou l'identifiant est incorrect.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Format incorrect");
                alert.setHeaderText("Identifiant invalide");
                alert.setContentText("L'identifiant doit être un nombre.");
                alert.showAndWait();
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur base de données");
                alert.setHeaderText("Problème lors de la récupération des données");
                alert.setContentText("Veuillez réessayer plus tard.");
                alert.showAndWait();
            }
        });
    }
}
