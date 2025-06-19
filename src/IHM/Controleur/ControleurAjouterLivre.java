package IHM.Controleur;

import BD.*;
import Java.Client;
import Java.Livre;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class ControleurAjouterLivre implements EventHandler<ActionEvent> {

    private Client client;
    private Livre livre;
    private ClientBD clientBD;
    private Label labelCompteur; // Référence au label au lieu d'un nombre fixe

    public ControleurAjouterLivre(Client client, Livre livre, ConnexionMySQL connexionMySQL, Label labelCompteur) {
        this.client = client;
        this.livre = livre;
        this.clientBD = new ClientBD(connexionMySQL);
        this.labelCompteur = labelCompteur;
    }

    @Override
    public void handle(ActionEvent event) {
        // Récupérer le nombre actuel depuis le label
        int nombre = Integer.parseInt(labelCompteur.getText());
        
        // Ajouter le nombre de livres spécifié au panier
        for (int i = 0; i < nombre; i++) {
            client.ajouterLivrePanier(livre);
        }
        
        try {
            clientBD.sauvegardePanierBD(client);
            
            // Message de confirmation adapté au nombre
            String message = nombre + " exemplaires du livre \"" + livre.getNomLivre() + "\" ont bien été ajoutés au panier.";
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
            
            // Optionnel : remettre le compteur à 1 après ajout
            labelCompteur.setText("1");
            
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible d'ajouter le livre au panier");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}