package IHM.Controleur;

import BD.ConnexionMySQL;
import IHM.FenetreUnMagasinAdmin;
import Java.Magasin;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControleurCarteMagasinAdmin implements EventHandler<MouseEvent> {

    private ConnexionMySQL connexionMySQL;
    private Magasin magasin;

    public ControleurCarteMagasinAdmin(ConnexionMySQL connexionMySQL, Magasin magasin) {
        this.connexionMySQL = connexionMySQL;
        this.magasin = magasin;
    }

    @Override
    public void handle(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        try {
            FenetreUnMagasinAdmin fenetreMagasinAdmin = new FenetreUnMagasinAdmin(connexionMySQL, magasin);
            fenetreMagasinAdmin.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
