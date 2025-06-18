package IHM.Controleur;

import BD.ConnexionMySQL;
import IHM.FenetreMagasinAdmin;
import Java.Magasin;
import javafx.stage.Stage;

public class ControleurCarteMagasinAdmin {
    private ConnexionMySQL connexionMySQL;
    private Magasin magasin;

    public ControleurCarteMagasinAdmin(ConnexionMySQL connexionMySQL, Magasin magasin) {
        this.connexionMySQL = connexionMySQL;
        this.magasin = magasin;
    }

    public void allerDansFenetreMAgasin(Stage stage) {
        try {
            FenetreMagasinAdmin fenetreMagasinAdmin = new FenetreMagasinAdmin(connexionMySQL, magasin);
            fenetreMagasinAdmin.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
