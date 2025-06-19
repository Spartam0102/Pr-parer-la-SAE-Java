package IHM.Controleur;

import Java.Client;
import BD.ConnexionMySQL;
import IHM.FenetrePanier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ControleurPanier implements EventHandler<ActionEvent> {

    private ConnexionMySQL connexionMySQL;
    private Client client;
    private Stage stage;

    public ControleurPanier(ConnexionMySQL connexionMySQL, Client client, Stage stage) {
        this.connexionMySQL = connexionMySQL;
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            FenetrePanier fenetrePanier = new FenetrePanier(connexionMySQL, client);
            fenetrePanier.start(this.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
