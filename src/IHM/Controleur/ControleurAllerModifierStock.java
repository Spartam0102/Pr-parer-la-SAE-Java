package IHM.Controleur;

import BD.ConnexionMySQL;
import Java.*;
import IHM.Admin.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControleurAllerModifierStock implements EventHandler<MouseEvent> {

    private ConnexionMySQL connexion;
    private Magasin magasin;
    private Stage stage;

    public ControleurAllerModifierStock(ConnexionMySQL connexion, Magasin magasin, Stage stage) {
        this.connexion = connexion;
        this.magasin =magasin;
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            FenetreModifierStock fs = new FenetreModifierStock(connexion, magasin);
            fs.start(stage);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ouverture de la fenêtre du stock du magasin.");
            e.printStackTrace();
        }
    }
}