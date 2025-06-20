package IHM.Controleur;

import java.util.List;
import IHM.Admin.*;
import BD.ConnexionMySQL;
import BD.StatistiqueBD;
import Java.Magasin;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControleurNbCommande implements EventHandler<MouseEvent>  {
    private StatistiqueBD statistiqueBD;
    private Magasin magasin;
    private Stage stage;

    public ControleurNbCommande(ConnexionMySQL connexionMySQL, Magasin magasin, Stage stage) {
        this.magasin = magasin;
        this.statistiqueBD = new StatistiqueBD(connexionMySQL);
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        try {
            List<List<String>> data = statistiqueBD.premier(String.valueOf(magasin.getIdMagasin()));
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

            barChart.setTitle("Commandes par année");
            xAxis.setLabel("Année");
            yAxis.setLabel("Quantité");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            if (!data.isEmpty()) {
                series.setName(data.get(0).get(0));
            }

            for (List<String> ligne : data) {
                String annee = ligne.get(1);
                int quantite = Integer.parseInt(ligne.get(2));
                series.getData().add(new XYChart.Data<>(annee, quantite));
            }

            barChart.getData().add(series);

            VBox content = new VBox(barChart);
            content.setPrefSize(600, 300);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Graphique");
            alert.setHeaderText("Nombre de commandes par année");
            alert.getDialogPane().setContent(content);
            alert.setResizable(true);
            alert.showAndWait();
            FenetreUnMagasinAdmin fenetreUnMagasinAdmin = new FenetreUnMagasinAdmin(statistiqueBD.getLaConnexion(), magasin);
            fenetreUnMagasinAdmin.start(this.stage);
        } catch (Exception exception) {
            System.out.println("connexion base de donné impossible");
        }
    }
}
