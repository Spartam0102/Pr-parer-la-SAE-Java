package IHM;

import IHM.Controleur.ControleurHome;

import java.sql.SQLException;
import java.util.Map;

import BD.*;
import Java.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class FenetreStock extends Application {

    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonPanier;
    private Button boutonRetour;
    private MagasinBD magasinBD;
    private Magasin magasin;

    public FenetreStock(ConnexionMySQL connexionMySQL, Magasin magasin){
        this.magasinBD = new MagasinBD(connexionMySQL);
        this.magasin = magasin;
    }


    private Pane titre() {
        ImageView logo = new ImageView(new Image("file:img/ChatGPT Image 17 juin 2025, 08_55_03.png"));
        logo.setFitHeight(110);
        logo.setPreserveRatio(true);

        ImageView homeView = new ImageView(new Image("file:img/house.png"));
        ImageView settingsView = new ImageView(new Image("file:img/settings.png"));
        ImageView panierView = new ImageView(new Image("file:img/panier.png"));
        ImageView retourView = new ImageView(new Image("file:img/retour.png"));

        for (ImageView iv : new ImageView[] { homeView, settingsView, panierView, retourView }) {
            iv.setFitHeight(30);
            iv.setFitWidth(30);
        }

        this.boutonHome = new Button("", homeView);
        this.boutonSettings = new Button("", settingsView);
        this.boutonPanier = new Button("", panierView);
        this.boutonRetour = new Button("", retourView);

        String styleBouton = "-fx-background-color: #206db8;" +
                "-fx-border-radius: 18; -fx-background-radius: 18;";
        boutonHome.setStyle(styleBouton);
        boutonSettings.setStyle(styleBouton);
        boutonPanier.setStyle(styleBouton);
        boutonRetour.setStyle(styleBouton);

        HBox boutons = new HBox(10, boutonHome, boutonSettings, boutonPanier, boutonRetour);
        boutons.setPadding(new Insets(10));
        boutons.setAlignment(Pos.CENTER);

        boutonHome.setOnAction(e -> {
            Stage stage = (Stage) boutonHome.getScene().getWindow();
            ControleurHome.allerAccueil(stage);
        });

        VBox conteneurDroit = new VBox(boutons);
        conteneurDroit.setAlignment(Pos.CENTER);
        conteneurDroit.setPadding(new Insets(10));

        BorderPane banniere = new BorderPane();
        banniere.setLeft(logo);
        banniere.setRight(conteneurDroit);
        banniere.setStyle("-fx-background-color: white;");

        return banniere;
    }

    @Override
    public void start(Stage primaryStage) throws SQLException{

        BorderPane root = new BorderPane();

        Pane banniere = titre();
        root.setTop(banniere);

        GridPane cadre = new GridPane();
        cadre.setStyle("-fx-background-color: #206db8;");
        cadre.setPadding(new Insets(20));
        cadre.setHgap(20);
        cadre.setVgap(20);
        cadre.setPadding(new Insets(30));
        root.setCenter(cadre);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHgrow(Priority.ALWAYS);
            colConst.setFillWidth(true);
            colConst.setPercentWidth(33.33);
            cadre.getColumnConstraints().add(colConst);
        }

        Map<Livre, Integer> listeLivres = magasin.getStockLivre();
        int i = -1;
        for(Map.Entry<Livre, Integer> entry : listeLivres.entrySet()){
            i++;
            Livre livre = entry.getKey();
            Integer quantité = entry.getValue();
            VBox caseInfo = new VBox();

            Text nomLivre = new Text(livre.getNomLivre());
            HBox detail = new HBox();

            VBox gauche = new VBox();
            Text nomAuteur = new Text("Claire Dubois");
            HBox stock = new HBox();
            if (quantité <= 0){
                ImageView croix = new ImageView(new Image("file:./img/croix.jpeg"));
                Text indisponible = new Text("livre non disponible");
                stock.getChildren().addAll(croix, indisponible); 
            } else {
                ImageView boite = new ImageView(new Image("file:./img/stock_icon.jpeg"));
                Text nbStock = new Text(String.valueOf(quantité) + " en stock");
                stock.getChildren().addAll(boite, nbStock); 
            }
            gauche.getChildren().addAll(nomAuteur, stock);

            VBox droite = new VBox();
            Text prix = new Text(String.valueOf(livre.getPrix()) + " €");
            Button bouton = new Button("Ajouter au panier");
            droite.getChildren().addAll(prix, bouton);

            detail.getChildren().addAll(gauche, droite);
            caseInfo.getChildren().addAll(nomLivre, detail);

            int col = i % 3;
            int row = i / 3;
            cadre.add(caseInfo, col, row);
            
            root.setCenter(cadre);
        }

        Scene scene = new Scene(root, 1200, 750);
        primaryStage.setTitle("Fenêtre Magasin client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
