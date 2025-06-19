package IHM.Controleur;

import IHM.FenetreStock;
import Java.Client;
import Java.Magasin;
import BD.ConnexionMySQL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControleurStock  implements EventHandler<MouseEvent> {

    private ConnexionMySQL connexionMySQL;
    private Magasin magasin;
    private Client client;
    private Stage stage;

    public ControleurStock(ConnexionMySQL connexionMySQL, Magasin magasin, Client client, Stage stage) {
        this.connexionMySQL = connexionMySQL;
        this.magasin = magasin;
        this.client = client;
        this.stage = stage;
    }

    public void handle(MouseEvent event) {
        try {
            FenetreStock fenetreStock = new FenetreStock(connexionMySQL, magasin, client);
            fenetreStock.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}