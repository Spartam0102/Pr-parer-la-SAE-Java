package IHM.Controleur;

import java.util.Optional;

import BD.ConnexionMySQL;
import BD.MagasinBD;
import IHM.FenetreMagasinsAdmin;
import Java.Magasin;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControleurSuppMagasin implements EventHandler<MouseEvent> {

    private MagasinBD magasinBD;
    private Magasin magasin;
    private ConnexionMySQL connexionMySQL;
    private Stage stage;

    public ControleurSuppMagasin(Magasin magasinaSupp, ConnexionMySQL connexionMySQL, Stage stage) {
        this.magasinBD = new MagasinBD(connexionMySQL);
        this.connexionMySQL = connexionMySQL;
        this.stage = stage;
        this.magasin = magasinaSupp;
    }

    @Override
    public void handle(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression d'un magasin");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette librairie ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            try {
                magasinBD.effacerMagasin(magasin.getIdMagasin());
                FenetreMagasinsAdmin fma = new FenetreMagasinsAdmin(connexionMySQL);
                fma.start(this.stage);
            } catch (Exception e) {
                System.out.println("Impossible de supprimer dans la base de donnée");
                e.printStackTrace();
            }
        }
    }
}
