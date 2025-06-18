package IHM.Controleur;

import IHM.FenetreStock;
import Java.Magasin;
import BD.ConnexionMySQL;
import javafx.stage.Stage;

public class ControleurCarteMagasin {

    private ConnexionMySQL connexionMySQL;
    private Magasin magasin;

    public ControleurCarteMagasin(ConnexionMySQL connexionMySQL, Magasin magasin) {
        this.connexionMySQL = connexionMySQL;
        this.magasin = magasin;
    }

    public void allerStockMagasin(Stage stage) {
        try {
            FenetreStock fenetreStock = new FenetreStock(connexionMySQL, magasin);
            fenetreStock.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
