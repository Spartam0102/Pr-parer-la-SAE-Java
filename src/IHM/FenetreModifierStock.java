package IHM;

import IHM.Controleur.ControleurHome;
import IHM.Controleur.ControleurModifierStock;
import IHM.Controleur.ControleurParametre;
import BD.*;
import Java.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FenetreModifierStock extends Application {

    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonRetour;
    private MagasinBD magasinBD;
    private Magasin magasin;
    private Stage stage;
    private Client client;

    public FenetreModifierStock(ConnexionMySQL connexionMySQL, Magasin magasin) {
        this.magasinBD = new MagasinBD(connexionMySQL);
        this.magasin = magasin;
    }

    private Pane titre() {
        ImageView logo = new ImageView(new Image("file:img/ChatGPT Image 17 juin 2025, 08_55_03.png"));
        logo.setFitHeight(110);
        logo.setPreserveRatio(true);

        ImageView homeView = new ImageView(new Image("file:img/house.png"));
        ImageView settingsView = new ImageView(new Image("file:img/settings.png"));
        ImageView retourView = new ImageView(new Image("file:img/retour.png"));

        for (ImageView iv : new ImageView[] { homeView, settingsView, retourView }) {
            iv.setFitHeight(30);
            iv.setFitWidth(30);
        }

        this.boutonHome = new Button("", homeView);
        this.boutonSettings = new Button("", settingsView);
        this.boutonRetour = new Button("", retourView);

        String styleBouton = "-fx-background-color: #206db8;" +
                "-fx-border-radius: 18; -fx-background-radius: 18;";
        boutonHome.setStyle(styleBouton);
        boutonSettings.setStyle(styleBouton);
        boutonRetour.setStyle(styleBouton);
        boutonSettings.setOnAction(new ControleurParametre(this.stage));

        HBox boutons = new HBox(10, boutonHome, boutonSettings, boutonRetour);
        boutons.setPadding(new Insets(10));
        boutons.setAlignment(Pos.CENTER);

        boutonHome.setOnAction(new ControleurHome(this.stage));

        VBox conteneurDroit = new VBox(boutons);
        conteneurDroit.setAlignment(Pos.CENTER);
        conteneurDroit.setPadding(new Insets(10));

        BorderPane banniere = new BorderPane();
        banniere.setLeft(logo);
        banniere.setRight(conteneurDroit);
        banniere.setStyle("-fx-background-color: white;");

        return banniere;
    }

    private ImageView creerImageLivre(long isbn) {
        String urlImage = "https://covers.openlibrary.org/b/isbn/" + isbn + "-M.jpg";
        Image imageLivre;
        try {
            imageLivre = new Image(urlImage, 120, 180, true, true, true);
            if (imageLivre.isError())
                throw new Exception("Erreur chargement image");
        } catch (Exception e) {
            imageLivre = new Image("file:img/default_book_cover.png", 120, 180, true, true);
        }
        ImageView imageView = new ImageView(imageLivre);
        imageView.setFitWidth(120);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        this.stage = primaryStage;
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1500, 750);

        Pane banniere = titre();
        root.setTop(banniere);

        VBox cadreGrand = new VBox();
        cadreGrand.setStyle("-fx-background-color: linear-gradient(to bottom, #a2cffe, #74a9f7);");
        cadreGrand.setSpacing(20);
        cadreGrand.setAlignment(Pos.CENTER);

        VBox ensemble = new VBox();

        VBox test = new VBox();
        test.setStyle("-fx-background-color: linear-gradient(to right, #74a9f7, #a2cffe);");
        test.setAlignment(Pos.CENTER_LEFT);
        test.setPrefWidth(scene.getWidth());
        test.setMinWidth(scene.getWidth());
        test.setMaxWidth(scene.getWidth());

        cadreGrand.setMaxWidth(Double.MAX_VALUE);
        ensemble.getChildren().addAll(test, cadreGrand);
        ensemble.setMaxWidth(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(ensemble);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        root.setCenter(scrollPane);

        primaryStage.setTitle("Fenêtre Magasin Client");
        primaryStage.setScene(scene);

        Map<Livre, Integer> listeLivres = magasinBD.listeLivreUnMagasin(this.magasin.getIdMagasin());
        List<Livre> livresPourBanniere = new ArrayList<>();
        int max = 7;
        int cpt = 0;
        for (Livre livre : listeLivres.keySet()) {
            livresPourBanniere.add(livre);
            cpt++;
            if (cpt >= max)
                break;
        }

        TextField champRecherche = new TextField();
        champRecherche.setPromptText("Rechercher un livre...");
        champRecherche.setStyle("-fx-background-radius: 15; -fx-padding: 5 10 5 10;");
        champRecherche.setMaxWidth(300);
        champRecherche.setAlignment(Pos.CENTER_LEFT);

        GridPane grilleLivres = new GridPane();
        grilleLivres.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20px 20px 0 0; -fx-border-radius: 20px 20px 0 0;");
        cadreGrand.setPadding(new Insets(20, 50, 0, 50));

        grilleLivres.setHgap(30);
        grilleLivres.setVgap(30);
        grilleLivres.setAlignment(Pos.CENTER);
        cadreGrand.getChildren().addAll(champRecherche, grilleLivres);
        grilleLivres.setPadding(new Insets(20));

        champRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            String recherche = newValue.toLowerCase();

            List<Livre> livresFiltres = listeLivres.keySet().stream()
                    .filter(livre -> livre.getNomLivre().toLowerCase().contains(recherche))
                    .toList();

            afficherLivresDansGrille(grilleLivres, livresFiltres, listeLivres);
        });

        javafx.scene.text.Text titreMag = new javafx.scene.text.Text(magasin.getNom());
        titreMag.setStyle("-fx-font-size: 35px; -fx-font-weight: bold;");
        titreMag.setWrappingWidth(400);
        grilleLivres.add(titreMag, 1, 0);
        GridPane.setHalignment(titreMag, HPos.CENTER);

        final int nbColonnes = 3;
        int i = 0;
        for (Map.Entry<Livre, Integer> entry : listeLivres.entrySet()) {
            Livre livre = entry.getKey();
            Integer quantite = entry.getValue();

            long isbn = livre.getIdLivre();

            Image imageLivre;
            try {
                String imageUrl = "https://covers.openlibrary.org/b/isbn/" + isbn + "-M.jpg";
                imageLivre = new Image(imageUrl, 120, 180, true, true, true);
                if (imageLivre.isError())
                    throw new Exception("Erreur chargement image");
            } catch (Exception e) {
                imageLivre = new Image("file:img/default_book_cover.png", 120, 180, true, true);
            }

            ImageView imageView = new ImageView(imageLivre);
            imageView.setFitHeight(140);
            imageView.setPreserveRatio(true);

            VBox carte = new VBox(10);
            carte.setPadding(new Insets(10));
            carte.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 20px; -fx-border-radius: 20px;");
            carte.setAlignment(Pos.TOP_LEFT);
            carte.setPrefWidth(300);
            GridPane.setMargin(carte, new Insets(5, 0, 5, 0));

            Text titre = new Text(livre.getNomLivre());
            titre.setWrappingWidth(400);
            titre.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Text auteur = new Text("Claire Dubois");

            HBox stock = new HBox(5);
            ImageView iconeStock = new ImageView(new Image("file:img/stock_icon.png"));
            iconeStock.setFitHeight(22);
            iconeStock.setFitWidth(22);
            Text stockText = new Text(quantite + (quantite <= 1 ? " en stock" : " en stock"));
            stock.getChildren().addAll(iconeStock, stockText);

            VBox infos = new VBox(5, auteur, stock);

            VBox droite = new VBox(8);
            droite.setAlignment(Pos.CENTER_RIGHT);
            Text prix = new Text(String.format("%.2f €", livre.getPrix()));
            prix.setStyle("-fx-font-weight: bold;");
            Button bouton = new Button("Modifier Stock");
            bouton.setStyle("-fx-background-color: #206db8; -fx-text-fill: white; -fx-font-size: 13px;" +
                    " -fx-background-radius: 18; -fx-padding: 6 14 6 14;");

            bouton.setOnAction(new ControleurModifierStock(magasin, livre, magasinBD.getConnexion(), stage));

            droite.getChildren().addAll(prix, bouton);

            BorderPane ligne = new BorderPane();
            ligne.setLeft(infos);
            ligne.setRight(droite);

            HBox ligneComplete = new HBox(15);
            ligneComplete.getChildren().addAll(imageView, ligne);

            carte.getChildren().addAll(titre, ligneComplete);

            grilleLivres.add(carte, i % nbColonnes, 1 + i / nbColonnes);

            i++;
        }

    }

    private void afficherLivresDansGrille(GridPane grilleLivres, List<Livre> livres, Map<Livre, Integer> stockMap) {
        grilleLivres.getChildren().clear();
        final int nbColonnes = 3;
        int i = 0;

        for (Livre livre : livres) {
            Integer quantite = stockMap.get(livre);
            if (quantite == null)
                continue;

            long isbn = livre.getIdLivre();

            Image imageLivre;
            try {
                String imageUrl = "https://covers.openlibrary.org/b/isbn/" + isbn + "-M.jpg";
                imageLivre = new Image(imageUrl, 120, 180, true, true, true);
                if (imageLivre.isError())
                    throw new Exception("Erreur chargement image");
            } catch (Exception e) {
                imageLivre = new Image("file:img/default_book_cover.png", 120, 180, true, true);
            }

            ImageView imageView = new ImageView(imageLivre);
            imageView.setFitHeight(140);
            imageView.setPreserveRatio(true);

            VBox carte = new VBox(10);
            carte.setPadding(new Insets(10));
            carte.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 20px;");
            carte.setAlignment(Pos.TOP_LEFT);
            carte.setPrefWidth(300);

            Text titre = new Text(livre.getNomLivre());
            titre.setWrappingWidth(400);
            titre.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Text auteur = new Text("Claire Dubois");

            HBox stock = new HBox(5);
            ImageView iconeStock = new ImageView(new Image("file:img/stock_icon.png"));
            iconeStock.setFitHeight(22);
            iconeStock.setFitWidth(22);
            Text stockText = new Text(quantite + " en stock");
            stock.getChildren().addAll(iconeStock, stockText);

            VBox infos = new VBox(5, auteur, stock);

            VBox droite = new VBox(8);
            droite.setAlignment(Pos.CENTER_RIGHT);
            Text prix = new Text(String.format("%.2f €", livre.getPrix()));
            prix.setStyle("-fx-font-weight: bold;");
            Button bouton = new Button("Ajouter au panier");
            bouton.setStyle("-fx-background-color: #206db8; -fx-text-fill: white; -fx-font-size: 13px;" +
                    " -fx-background-radius: 18; -fx-padding: 6 14 6 14;");

            BorderPane ligne = new BorderPane();
            ligne.setLeft(infos);
            ligne.setRight(droite);

            HBox ligneComplete = new HBox(15);
            ligneComplete.getChildren().addAll(imageView, ligne);

            carte.getChildren().addAll(titre, ligneComplete);

            grilleLivres.add(carte, i % nbColonnes, i / nbColonnes);
            i++;
        }
    }

    public static void afficher(Stage stage, ConnexionMySQL connexion, Magasin magasin, Client client) {
        try {
            FenetreModifierStock fenetre = new FenetreModifierStock(connexion, magasin);
            fenetre.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
