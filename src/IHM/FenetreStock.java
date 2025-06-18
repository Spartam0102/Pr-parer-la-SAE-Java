package IHM;

import IHM.Controleur.ControleurHome;

import java.sql.SQLException;
import java.util.List;
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

    // Champs statiques pour transmettre connexion et magasin
    private static ConnexionMySQL connexionStatic;
    private static Magasin magasinStatic;

    // Constructeur sans argument requis par JavaFX
    public FenetreStock(ConnexionMySQL connexion, Magasin magasin) {
        connexionStatic = connexion;
        magasinStatic = magasin;
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

        boutonHome = new Button("", homeView);
        boutonSettings = new Button("", settingsView);
        boutonPanier = new Button("", panierView);
        boutonRetour = new Button("", retourView);

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

        // TODO : ajouter action boutonSettings, boutonPanier, boutonRetour

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
    public void start(Stage primaryStage) throws SQLException {
        magasinBD = new MagasinBD(connexionStatic);

        BorderPane root = new BorderPane();

        Pane banniere = titre();
        root.setTop(banniere);

        GridPane cadre = new GridPane();
        cadre.setStyle("-fx-background-color: #206db8;");
        cadre.setPadding(new Insets(30));
        cadre.setHgap(20);
        cadre.setVgap(20);
        root.setCenter(cadre);

        // Colonnes de largeur égale (3 colonnes)
        for (int i = 0; i < 3; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHgrow(Priority.ALWAYS);
            colConst.setFillWidth(true);
            colConst.setPercentWidth(33.33);
            cadre.getColumnConstraints().add(colConst);
        }

        Map<Livre, Integer> listeLivres = magasinStatic.getStockLivre();
        int i = -1;
        for (Map.Entry<Livre, Integer> entry : listeLivres.entrySet()) {
            i++;
            Livre livre = entry.getKey();
            Integer quantite = entry.getValue();

            VBox caseInfo = new VBox(5);
            caseInfo.setPadding(new Insets(10));
            caseInfo.setStyle(
                    "-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 5; -fx-background-radius: 5;");

            Text nomLivre = new Text(livre.getNomLivre());
            HBox detail = new HBox(20);
            detail.setAlignment(Pos.CENTER_LEFT);

            VBox gauche = new VBox(5);
            Text texteAuteur = new Text(); 

            List<String> auteurs = livre.getAuteur();
            String auteursString = String.join(", ", auteurs);
            texteAuteur.setText(auteursString);

            HBox stock = new HBox(5);
            if (quantite <= 0) {
                ImageView croix = new ImageView(new Image("file:./img/croix.jpeg"));
                croix.setFitHeight(20);
                croix.setPreserveRatio(true);
                Text indisponible = new Text("Livre non disponible");
                stock.getChildren().addAll(croix, indisponible);
            } else {
                ImageView boite = new ImageView(new Image("file:./img/stock_icon.jpeg"));
                boite.setFitHeight(20);
                boite.setPreserveRatio(true);
                Text nbStock = new Text(quantite + " en stock");
                stock.getChildren().addAll(boite, nbStock);
            }
            gauche.getChildren().addAll(texteAuteur, stock);

            VBox droite = new VBox(5);
            Text prix = new Text(livre.getPrix() + " €");
            Button bouton = new Button("Ajouter au panier");
            droite.getChildren().addAll(prix, bouton);

            bouton.setOnAction(e -> {
                System.out.println("Ajout au panier : " + livre.getNomLivre());
                // TODO : ajouter la logique pour ajouter au panier
            });

            detail.getChildren().addAll(gauche, droite);
            caseInfo.getChildren().addAll(nomLivre, detail);

            int col = i % 3;
            int row = i / 3;
            cadre.add(caseInfo, col, row);
        }

        Scene scene = new Scene(root, 1200, 750);
        primaryStage.setTitle("Fenêtre Magasin client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Méthode statique pour afficher la fenêtre, en initialisant les variables
     * nécessaires.
     * 
     * @throws SQLException
     */
    public static void afficher(Stage stage, Magasin magasinSelectionne, ConnexionMySQL connexion) throws SQLException {
        connexionStatic = connexion;
        magasinStatic = magasinSelectionne;
        FenetreStock fenetre = new FenetreStock(connexion, magasinSelectionne);
        fenetre.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
