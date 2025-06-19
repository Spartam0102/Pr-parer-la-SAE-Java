package IHM.Controleur;

import java.util.Optional;

import BD.*;
import IHM.FenetreModifierStock;
import Java.Livre;
import Java.Magasin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ControleurModifierStock implements EventHandler<ActionEvent> {

    private MagasinBD magasinBD;
    private Livre livre;
    private Magasin magasin;
    private Stage stage;

    public ControleurModifierStock(Magasin magasin, Livre livre, ConnexionMySQL connexionMySQL, Stage stage) {
        this.magasin = magasin;
        this.livre = livre;
        this.magasinBD = new MagasinBD(connexionMySQL);
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Modifier le stock");
        alert.setContentText("Nouvelle Quantité ?");
        TextField textField = new TextField();
        textField.setPromptText("Quantité");
        VBox content = new VBox();
        content.setSpacing(10);
        content.getChildren().add(textField);
        alert.getDialogPane().setContent(content);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            magasin.getStockLivre().put(livre, Integer.parseInt(textField.getText())); 
            try {
                magasinBD.modifierStock(livre.getIdLivre(), magasin.getIdMagasin(), Integer.parseInt(textField.getText()));;
                FenetreModifierStock fms = new FenetreModifierStock(this.magasinBD.getConnexion(), magasin);
                fms.start(this.stage);
            }
            catch (Exception e) {
                System.out.println("Impossible de supprimer dans la base de donnée");
                e.printStackTrace();
            }
        }
    }
}