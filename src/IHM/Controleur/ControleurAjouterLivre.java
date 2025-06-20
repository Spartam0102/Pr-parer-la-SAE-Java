package IHM.Controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import Java.Auteur;
import Java.Client;
import Java.Livre;
import Java.Vendeur;

import java.sql.SQLException;

import BD.MagasinBD;
import BD.ClientBD;
import BD.ConnexionMySQL;

public class ControleurAjouterLivre implements EventHandler<ActionEvent> {

    private Client client;
    private Livre livre;
    private ClientBD clientBD;
    private Label labelCompteur; 

    public ControleurAjouterLivre(Client client, Livre livre, ConnexionMySQL connexionMySQL, Label labelCompteur) {
        this.client = client;
        this.livre = livre;
        this.clientBD = new ClientBD(connexionMySQL);
        this.labelCompteur = labelCompteur;
    }

    private ConnexionMySQL connexion;
    private Vendeur vendeur;
    private TextField champTitre, champAuteur, champPages, champPrix;
    private TextField champAnnee;

    public ControleurAjouterLivre(ConnexionMySQL connexion, Vendeur vendeur,
            TextField champTitre, TextField champAuteur,
            TextField champPages, TextField champPrix,
            TextField champAnnee) {
        this.connexion = connexion;
        this.vendeur = vendeur;
        this.champTitre = champTitre;
        this.champAuteur = champAuteur;
        this.champPages = champPages;
        this.champPrix = champPrix;
        this.champAnnee = champAnnee;

    }

    @Override
    public void handle(ActionEvent event) {

        try {
          
            if (champTitre.getText().trim().isEmpty()) {
                afficherAlerte("Champ manquant", "Le titre ne peut pas être vide.", AlertType.WARNING);
                return;
            }

            if (champAuteur.getText().trim().isEmpty()) {
                afficherAlerte("Champ manquant", "Le nom de l'auteur ne peut pas être vide.", AlertType.WARNING);
                return;
            }

            String titre = champTitre.getText().trim();
            String auteurInfo = champAuteur.getText().trim(); 

           
            int pages;
            try {
                pages = Integer.parseInt(champPages.getText().trim());
                if (pages <= 0) {
                    afficherAlerte("Valeur invalide", "Le nombre de pages doit être un nombre positif.",
                            AlertType.WARNING);
                    return;
                }
            } catch (NumberFormatException e) {
                afficherAlerte("Format invalide", "Le nombre de pages doit être un nombre entier valide.",
                        AlertType.ERROR);
                return;
            }

            double prix;
            try {
                prix = Double.parseDouble(champPrix.getText().trim());
                if (prix < 0) {
                    afficherAlerte("Valeur invalide", "Le prix ne peut pas être négatif.", AlertType.WARNING);
                    return;
                }
            } catch (NumberFormatException e) {
                afficherAlerte("Format invalide", "Le prix doit être un nombre décimal valide.", AlertType.ERROR);
                return;
            }

           
            Auteur auteur = creerAuteur(auteurInfo);
            if (auteur == null) {
                afficherAlerte("Format auteur invalide",
                        "Format attendu: 'Nom Prénom' ou 'Nom'\nExemple: 'Dupont Jean' ou 'Dupont'",
                        AlertType.WARNING);
                return;
            }

            String annee = champAnnee.getText().trim();
            if (annee.isEmpty()) {
                afficherAlerte("Champ manquant", "L'année ne peut pas être vide.", AlertType.WARNING);
                return;
            }

          
            Livre livre = new Livre(titre, auteur, pages, prix, annee);

           
            MagasinBD magasinBD = new MagasinBD(connexion);
            magasinBD.ajouterLivreDansMagasin(vendeur.getMagasin().getIdMagasin(), livre, 1);

            afficherAlerte("Ajout réussi", "Le livre \"" + titre + "\" a été ajouté avec succès !",
                    AlertType.INFORMATION);

            
            viderChamps();

        } catch (SQLException e) {
            e.printStackTrace();
            String message = "Impossible d'ajouter le livre en base de données.";
            if (e.getMessage().contains("Duplicate")) {
                message = "Ce livre existe déjà.";
            }
            afficherAlerte("Erreur de base de données", message, AlertType.ERROR);

        } catch (Exception e) {
            e.printStackTrace();
            afficherAlerte("Erreur inattendue", "Une erreur inattendue s'est produite : " + e.getMessage(),
                    AlertType.ERROR);
        }
    }

    private Auteur creerAuteur(String auteurInfo) {
        if (auteurInfo == null || auteurInfo.trim().isEmpty()) {
            return null;
        }

        String[] parties = auteurInfo.trim().split("\\s+", 2);
        String nom = parties[0];
        String prenom = parties.length > 1 ? parties[1] : "";

        String dateDeNaissance = "1900-01-01";
        int idEditeur = 1;

        return new Auteur(nom, prenom, dateDeNaissance, idEditeur);
    }

    private void viderChamps() {
        champTitre.clear();
        champAuteur.clear();
        champPages.clear();
        champPrix.clear();
    }

    private void afficherAlerte(String titre, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}