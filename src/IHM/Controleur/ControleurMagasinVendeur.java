package IHM.Controleur;

import BD.ConnexionMySQL;
import Java.*;
import IHM.FenetreUnMagasinVendeur;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControleurMagasinVendeur implements EventHandler<MouseEvent> {

    private ConnexionMySQL connexion;
    private Vendeur vendeur;
    private Stage stage;

    public ControleurMagasinVendeur(ConnexionMySQL connexion, Vendeur vendeur, Stage stage) {
        this.connexion = connexion;
        this.vendeur = vendeur;
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            FenetreUnMagasinVendeur fs = new FenetreUnMagasinVendeur(connexion, vendeur);
            fs.start(stage);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ouverture de la fenêtre du stock du magasin.");
            e.printStackTrace();
        }
    }
}
