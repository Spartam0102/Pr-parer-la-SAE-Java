package IHM.Controleur;

import BD.*;
import Java.*;
import BD.ConnexionMySQL;
import IHM.FenetrePanier;
import javafx.stage.Stage;

public class ControleurPanier {

    public static void allerStock(Stage stage, Client client, ConnexionMySQL connexionMySQL) {
        try {
            FenetrePanier fenetrePanier = new FenetrePanier(connexionMySQL, client);
            fenetrePanier.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
