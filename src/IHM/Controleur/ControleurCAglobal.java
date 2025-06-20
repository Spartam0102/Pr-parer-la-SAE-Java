package IHM.Controleur;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import BD.ConnexionMySQL;
import BD.StatistiqueBD;
import IHM.Admin.*;
import Java.Magasin;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControleurCAglobal implements EventHandler<MouseEvent> {

    private StatistiqueBD statistiqueBD;
    private Magasin magasin;
    private Stage stage;

    public ControleurCAglobal(ConnexionMySQL connexionMySQL, Magasin magasin, Stage stage) {
        this.magasin = magasin;
        this.statistiqueBD = new StatistiqueBD(connexionMySQL);
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        try {
            String annee = "2024";
            List<List<String>> data = statistiqueBD.troisieme(annee);

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();

            xAxis.setLabel("Mois");
            yAxis.setLabel("Chiffre d'affaires (€)");

            BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
            chart.setTitle("CA mensuel par magasin - Année " + annee);
            chart.setCategoryGap(10);
            chart.setBarGap(3);

            // Map : nommag → série
            Map<String, XYChart.Series<String, Number>> magasinSeries = new HashMap<>();

            // Pour garder l'ordre des mois
            Set<String> moisSet = new TreeSet<>(Comparator.comparingInt(Integer::parseInt));

            for (List<String> ligne : data) {
                String mois = ligne.get(0);
                String magasin = ligne.get(1);
                double ca = Double.parseDouble(ligne.get(2));

                moisSet.add(mois);

                magasinSeries.putIfAbsent(magasin, new XYChart.Series<>());
                XYChart.Series<String, Number> serie = magasinSeries.get(magasin);
                serie.setName("Magasin " + magasin);
                serie.getData().add(new XYChart.Data<>(mois, ca));
            }

            chart.getData().addAll(magasinSeries.values());

            VBox content = new VBox(chart);
            content.setPrefSize(800, 500);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chiffre d'affaires");
            alert.setHeaderText("CA mensuel par magasin - " + annee);
            alert.getDialogPane().setContent(content);
            alert.setResizable(true);
            alert.showAndWait();
            FenetreUnMagasinAdmin fenetreUnMagasinAdmin = new FenetreUnMagasinAdmin(statistiqueBD.getLaConnexion(),
                    magasin);
            fenetreUnMagasinAdmin.start(this.stage);
        } catch (Exception exception) {
            System.out.println("connexion base de donné impossible");
        }
    }
}
