package IHM.Général;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

public class NavigationManager {

    private Stage stage;
    private Stack<Scene> historique;

    public NavigationManager(Stage stage) {
        this.stage = stage;
        this.historique = new Stack<>();
    }

    public void naviguerVers(Scene nouvelleScene) {

        Scene sceneActuelle = stage.getScene();
        if (sceneActuelle != null) {
            historique.push(sceneActuelle);
        }

        stage.setScene(nouvelleScene);
    }

    public void revenirEnArriere() {
        if (!historique.isEmpty()) {
            Scene scenePrecedente = historique.pop();
            stage.setScene(scenePrecedente);
        }
    }

    public void reinitialiser() {
        historique.clear();
    }
}
