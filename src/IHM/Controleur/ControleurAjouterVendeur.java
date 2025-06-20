package IHM.Controleur;

import BD.ConnexionMySQL;
import BD.VendeurBD;

import Java.Magasin;
import Java.Vendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurAjouterVendeur implements EventHandler<ActionEvent> {
    private Magasin magasin;
    private TextField nom;
    private TextField prenom;
    private TextField dateDeNaissance;
    private TextField motDePasse;
    private ConnexionMySQL connexion;

    public ControleurAjouterVendeur(TextField nom, TextField prenom, TextField dateDeNaissance,
            TextField motDePasse, Magasin magasin, ConnexionMySQL connexion) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.motDePasse = motDePasse;
        this.magasin = magasin;
        this.connexion = connexion;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            String nomText = nom.getText().trim();
            String prenomText = prenom.getText().trim();
            String dateText = dateDeNaissance.getText().trim();
            String mdpText = motDePasse.getText().trim();

            if (nomText.isEmpty() || prenomText.isEmpty() || dateText.isEmpty() || mdpText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Champs manquants");
                alert.setHeaderText("Erreur de saisie");
                alert.setContentText("Tous les champs doivent être remplis");
                alert.showAndWait();
                return;
            }

            Vendeur nouveau = new Vendeur(nomText, prenomText, dateText, 0, magasin, mdpText);

            VendeurBD vendeurBD = new VendeurBD(connexion);
            int id = vendeurBD.creerVendeur(nouveau);

            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
            alertSuccess.setTitle("Vendeur créé");
            alertSuccess.setHeaderText("Création réussie !");
            alertSuccess.setContentText("Le vendeur " + nomText + " " + prenomText +
                    " a été créé avec succès.\n\n" +
                    "ID du vendeur : " + id);
            alertSuccess.showAndWait();

            System.out.println("Vendeur créé avec succès ! ID: " + id);

            nom.clear();
            prenom.clear();
            dateDeNaissance.clear();
            motDePasse.clear();

        } catch (Exception e) {

            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Erreur");
            alertError.setHeaderText("Erreur lors de la création du vendeur");
            alertError.setContentText("Une erreur s'est produite lors de la création du vendeur :\n\n" +
                    e.getMessage());
            alertError.showAndWait();

            System.err.println("Erreur lors de la création du vendeur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}