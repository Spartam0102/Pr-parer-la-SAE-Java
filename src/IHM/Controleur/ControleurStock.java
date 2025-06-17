package IHM.Controleur;

import IHM.FenetrePanier;
import javafx.stage.Stage;

public class ControleurStock {

    public static void allerStock(Stage stage) {
        try {
            FenetrePanier fenetrePanier = new FenetrePanier();
            fenetrePanier.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
