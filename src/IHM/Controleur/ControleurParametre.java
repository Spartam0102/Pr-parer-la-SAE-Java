package IHM.Controleur;

import IHM.Général.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ControleurParametre implements EventHandler<ActionEvent> {

    private Stage parentStage;

    public ControleurParametre(Stage parentStage) {
        this.parentStage = parentStage;
    }

    @Override
    public void handle(ActionEvent event) {
        PopupParametres.afficher(parentStage);
    }
}
