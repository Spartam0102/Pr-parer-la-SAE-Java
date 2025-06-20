package IHM.Controleur;

import BD.ClientBD;
import BD.ConnexionMySQL;
import Java.Client;

import IHM.Général.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControleurVoirPanier implements EventHandler<ActionEvent> {

    private TextField champIdClient;

    private ConnexionMySQL connexionMySQL;  

    private Stage stage;

    public ControleurVoirPanier(Stage stage, ConnexionMySQL connexionMySQL, TextField champIdClient) {
        this.champIdClient = champIdClient;
        this.connexionMySQL = connexionMySQL;
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            String saisie = champIdClient.getText().trim();
            if (saisie.isEmpty()) {
                System.out.println("Veuillez entrer un ID client.");
                return;
            }

            int idClient = Integer.parseInt(saisie);

            ClientBD clientBD = new ClientBD(connexionMySQL);
            Client client = clientBD.recupererClient(idClient);
            if (client == null) {
                System.out.println("Client introuvable !");
                return;
            }

            client.setPanier(clientBD.recupererPanier(idClient));

            FenetrePanier.afficher(stage, connexionMySQL, client);

        } catch (NumberFormatException e) {
            System.out.println("L'ID doit être un entier.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
