package IHM.Controleur;

import IHM.FenetreConnexion;
import javafx.stage.Stage;

public class ControleurHome {

    public static void allerAccueil(Stage stage) {
        try {
            FenetreConnexion fenetreConnexion = new FenetreConnexion();
            fenetreConnexion.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
