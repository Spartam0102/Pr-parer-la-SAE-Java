package IHM.Controleur;

import IHM.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ControleurHome implements EventHandler<ActionEvent> {

    private Stage stage;

    public ControleurHome(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            FenetreConnexion fenetreConnexion = new FenetreConnexion();
            fenetreConnexion.start(this.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}