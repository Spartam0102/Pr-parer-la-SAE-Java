package IHM.Général;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupParametres {

    public static void afficher(Stage parentStage) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(parentStage);
        popup.setTitle("Paramètres");

        VBox contenu = new VBox(20);
        contenu.setPadding(new Insets(20));
        contenu.setAlignment(Pos.CENTER);

        Button boutonDeconnexion = new Button("Se déconnecter");
        boutonDeconnexion.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
        boutonDeconnexion.setOnAction(e -> {
            popup.close();
            parentStage.close();
            
        });

        Button fermer = new Button("Fermer");
        fermer.setOnAction(e -> popup.close());

        contenu.getChildren().addAll(boutonDeconnexion, fermer);

        Scene scene = new Scene(contenu, 250, 150);
        popup.setScene(scene);
        popup.setResizable(false);
        popup.showAndWait();
    }
}
