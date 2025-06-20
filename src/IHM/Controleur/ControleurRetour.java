package IHM.Controleur;

import IHM.Client.*;
import IHM.Admin.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import BD.*;
import Java.*;

public class ControleurRetour implements EventHandler<ActionEvent> {

    private Stage stage;
    private String destination;
    private ConnexionMySQL connexionMySQL;
    private Client client;

    public ControleurRetour(ConnexionMySQL connexionMySQL, Stage stage, Client client,String destination) {
        this.stage = stage;
        this.destination = destination;
        this.connexionMySQL = connexionMySQL;
        this.client = client;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            switch(destination) {
                case "fenetreMagasinsClient":
                    FenetreMagasinsClient fenetreMagasinsClient = new FenetreMagasinsClient(connexionMySQL, client);
                    fenetreMagasinsClient.start(this.stage);
                    break;
                case "fenetreMagasinsAdmin":
                    FenetreMagasinsAdmin fenetreMagasinsAdmin = new FenetreMagasinsAdmin(connexionMySQL);
                    fenetreMagasinsAdmin.start(this.stage);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
