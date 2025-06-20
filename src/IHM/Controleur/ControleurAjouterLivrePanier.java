package IHM.Controleur;

import BD.*;

import java.util.Map;
import Java.Client;
import Java.Livre;
import Java.Magasin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class ControleurAjouterLivrePanier implements EventHandler<ActionEvent> {

    private Client client;
    private Livre livre;
    private ClientBD clientBD;
    private Label labelCompteur;
    private Magasin magasin;
    private MagasinBD magasinBD;

    public ControleurAjouterLivrePanier(Client client, Livre livre, ConnexionMySQL connexionMySQL, Label labelCompteur,
            Magasin magasin) {
        this.client = client;
        this.livre = livre;
        this.clientBD = new ClientBD(connexionMySQL);
        this.labelCompteur = labelCompteur;
        this.magasin = magasin;
        this.magasinBD = new MagasinBD(connexionMySQL);
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            int nombre = Integer.parseInt(labelCompteur.getText());

            Map<Livre, Integer> stockMagasin = magasinBD.listeLivreUnMagasin(magasin.getIdMagasin());
            Integer quantiteDisponible = stockMagasin.get(livre);

            if (quantiteDisponible == null || quantiteDisponible <= 0) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Stock insuffisant");
                alert.setHeaderText(null);
                alert.setContentText("Ce livre n'est plus disponible en stock.");
                alert.showAndWait();
                return;
            }

            Integer quantiteDejaDansPanier = client.getPanier().get(livre);
            if (quantiteDejaDansPanier == null) {
                quantiteDejaDansPanier = 0;
            }

            int quantiteMaxAjoutPossible = quantiteDisponible - quantiteDejaDansPanier;

            if (quantiteMaxAjoutPossible <= 0) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Stock insuffisant");
                alert.setHeaderText(null);
                alert.setContentText("Vous avez déjà le maximum de ce livre dans votre panier ("
                        + quantiteDejaDansPanier + "/" + quantiteDisponible + ").");
                alert.showAndWait();
                return;
            }

            if (nombre > quantiteMaxAjoutPossible) {
                nombre = quantiteMaxAjoutPossible;

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Quantité ajustée");
                alert.setHeaderText(null);
                alert.setContentText("Seuls " + nombre + " exemplaire(s) ont été ajoutés car le stock est limité.");
                alert.showAndWait();
            }

            for (int i = 0; i < nombre; i++) {
                client.ajouterLivrePanier(livre);
            }

            clientBD.sauvegardePanierBD(client);

            String message = nombre + " exemplaire(s) du livre \"" + livre.getNomLivre()
                    + "\" ont bien été ajoutés au panier.";

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();

            labelCompteur.setText("1");

        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Valeur invalide");
            alert.setContentText("La quantité saisie n'est pas valide.");
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