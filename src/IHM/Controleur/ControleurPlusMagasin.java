package IHM.Controleur;

import BD.*;
import Java.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControleurPlusMagasin {

    public static void afficherPopupAjouterMagasin(Stage parentStage, MagasinBD magasinBD) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(parentStage);
        popup.setTitle("Ajouter un magasin");
        popup.setResizable(false);

        VBox conteneur = new VBox(15);
        conteneur.setPadding(new Insets(20));
        conteneur.setAlignment(Pos.CENTER);
        conteneur.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");

        Text titre = new Text("Ajouter un magasin");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField champNom = new TextField();
        champNom.setPromptText("Nom ex(pomme et orange)");
        champNom.setStyle("-fx-pref-width: 200px; -fx-pref-height: 35px; -fx-background-radius: 5px;");

        TextField champAdresse = new TextField();
        champAdresse.setPromptText("Adresse ex(Rue d'Amboise, 45060)");
        champAdresse.setStyle("-fx-pref-width: 200px; -fx-pref-height: 35px; -fx-background-radius: 5px;");

        TextField champNote = new TextField();
        champNote.setPromptText("Note entre 0-5");
        champNote.setStyle("-fx-pref-width: 200px; -fx-pref-height: 35px; -fx-background-radius: 5px;");

        TextField champTelephone = new TextField();
        champTelephone.setPromptText("Téléphone ex(04 22 52 10 10)");
        champTelephone.setStyle("-fx-pref-width: 200px; -fx-pref-height: 35px; -fx-background-radius: 5px;");

        Button boutonAjouter = new Button("Ajouter");
        boutonAjouter.setStyle(
                "-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20px; -fx-pref-width: 100px; -fx-pref-height: 35px;");

        boutonAjouter.setOnAction(e -> {

            try {

                boolean saisieValide = true;

                if (champNom.getText().isEmpty() || champAdresse.getText().isEmpty() ||
                        champNote.getText().isEmpty() || champTelephone.getText().isEmpty()) {
                    afficherErreur("Champs obligatoires", "Veuillez remplir tous les champs.");
                    saisieValide = false;
                }

                double note = 0;
                try {
                    note = Double.parseDouble(champNote.getText());
                    if (note < 0 || note > 5) {
                        afficherErreur("Note invalide", "La note doit être comprise entre 0 et 5.");
                        saisieValide = false;
                    }
                } catch (NumberFormatException ex) {
                    afficherErreur("Note invalide", "La note doit être un nombre valide.");
                    saisieValide = false;
                }

                if (saisieValide) {
                    Magasin nouveauMagasin = new Magasin(
                            champNom.getText(),
                            champAdresse.getText(),
                            0,
                            note,
                            champTelephone.getText());

                    ajouterMagasin(nouveauMagasin, magasinBD, popup, parentStage);
                }

            } catch (NumberFormatException ex) {
                afficherErreur("Note invalide", "La note doit être un nombre valide.");
            }
        });

        conteneur.getChildren().addAll(titre, champNom, champAdresse, champNote, champTelephone, boutonAjouter);

        Scene scene = new Scene(conteneur, 300, 350);
        popup.setScene(scene);
        popup.showAndWait();

    }

    private static void ajouterMagasin(Magasin nouveauMagasin, MagasinBD magasinBD, Stage popup, Stage parentStage) {

        try {
            magasinBD.insererMagasin(nouveauMagasin);

            popup.close();

            IHM.FenetreMagasinsAdmin.afficher(parentStage, magasinBD.getConnexion());
            } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

}