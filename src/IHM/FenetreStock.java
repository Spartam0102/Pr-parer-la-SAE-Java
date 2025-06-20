package IHM;

import IHM.Controleur.ControleurCompteur;
import IHM.Controleur.ControleurAjouterLivrePanier;
import IHM.Controleur.ControleurHome;
import IHM.Controleur.ControleurPanier;
import IHM.Controleur.ControleurParametre;
import IHM.Controleur.ControleurRetour;
import BD.*;
import Java.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FenetreStock extends Application {

    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonPanier;
    private Button boutonRetour;
    private MagasinBD magasinBD;
    private Magasin magasin;
    private Timeline timelineDefilante;
    private Client client;
    private Stage stage;
    private Label lblCompteurPanier;

    public FenetreStock(ConnexionMySQL connexionMySQL, Magasin magasin, Client client) {
        this.magasinBD = new MagasinBD(connexionMySQL);
        this.magasin = magasin;
        this.client = client;
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
        boutonSettings.setOnAction(new ControleurParametre(this.stage));

        String styleBouton = "-fx-background-color: #206db8;" +
                "-fx-border-radius: 18; -fx-background-radius: 18;";
        boutonHome.setStyle(styleBouton);
        boutonSettings.setStyle(styleBouton);
        boutonPanier.setStyle(styleBouton);
        boutonRetour.setStyle(styleBouton);

        HBox boutons = new HBox(10, boutonHome, boutonSettings, boutonPanier, boutonRetour);
        boutons.setPadding(new Insets(10));
        boutons.setAlignment(Pos.CENTER);

        boutonHome.setOnAction(new ControleurHome(this.stage));
        boutonPanier.setOnAction(new ControleurPanier(this.magasinBD.getConnexion(), client, stage));
        boutonRetour.setOnAction(
                new ControleurRetour(this.magasinBD.getConnexion(), stage, client, "fenetreMagasinsClient"));

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

    private void setupBanniereDefilanteImages(VBox container, List<Livre> livres, double largeur) {
        container.getChildren().clear();
        container.setPrefHeight(200);
        container.setMinHeight(200);
        container.setMaxHeight(200);
        container.setPrefWidth(largeur);
        container.setMaxWidth(largeur);
        container.setMinWidth(largeur);

        Rectangle clip = new Rectangle(largeur, 200);
        container.setClip(clip);

        HBox hbox = new HBox(40);
        hbox.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < 2; i++) {
            for (Livre livre : livres) {
                ImageView imageView = creerImageLivre(livre.getIdLivre());
                hbox.getChildren().add(imageView);
            }
        }

        container.getChildren().add(hbox);

        if (timelineDefilante != null) {
            timelineDefilante.stop();
        }

        Timeline[] attenteLayout = new Timeline[1];

        attenteLayout[0] = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            double width = hbox.getWidth();
            if (width > 0) {
                double totalWidth = width / 2;

                hbox.setTranslateX(0);

                timelineDefilante = new Timeline(new KeyFrame(Duration.millis(20), ev -> {
                    double x = hbox.getTranslateX();
                    x -= 2;

                    if (x <= -totalWidth) {
                        x = 0;
                    }
                    hbox.setTranslateX(x);
                }));

                timelineDefilante.setCycleCount(Timeline.INDEFINITE);
                timelineDefilante.play();

                attenteLayout[0].stop();
            } else {
                attenteLayout[0].playFromStart();
            }
        }));

        attenteLayout[0].setCycleCount(1);
        attenteLayout[0].play();
    }

    private void ajusterLargeur(Scene scene, VBox test, List<Livre> livres) {
        double largeurScene = scene.getWidth();

        test.setPrefWidth(largeurScene);
        test.setClip(new Rectangle(largeurScene, 200));

        if (timelineDefilante != null) {
            timelineDefilante.stop();
        }
        setupBanniereDefilanteImages(test, livres, largeurScene);
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

        setupBanniereDefilanteImages(test, livresPourBanniere, scene.getWidth());

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

            Button bouton = new Button("Ajouter au panier");
            bouton.setStyle("-fx-background-color: #206db8; -fx-text-fill: white; -fx-font-size: 13px;" +
                    " -fx-background-radius: 18; -fx-padding: 6 14 6 14;");

            HBox nombre = new HBox();
            Button btnMoins = new Button("-");
            btnMoins.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-border-color: transparent;" +
                            "-fx-text-fill: black;");
            Button btnPlus = new Button("+");
            btnPlus.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-border-color: transparent;" +
                            "-fx-text-fill: black;");

            Label lblCompteur = new Label("1");
            lblCompteur.setStyle("-fx-font-size: 18px;" + "-fx-text-fill: black;");

            ControleurCompteur controleurMoins = new ControleurCompteur(lblCompteur, "moins", 1, quantite);
            ControleurCompteur controleurPlus = new ControleurCompteur(lblCompteur, "plus", 1, quantite);

            btnMoins.setOnAction(controleurMoins);
            btnPlus.setOnAction(controleurPlus);

            nombre.getChildren().addAll(btnMoins, lblCompteur, btnPlus);
            nombre.setAlignment(Pos.CENTER_RIGHT);

            ControleurAjouterLivrePanier controleurAjouterLivrePanier = new ControleurAjouterLivrePanier(this.client,
                    livre, magasinBD.getConnexion(), lblCompteur, magasin);

            bouton.setOnAction(controleurAjouterLivrePanier);

            droite.getChildren().addAll(prix, nombre, bouton);

            BorderPane ligne = new BorderPane();
            ligne.setLeft(infos);
            ligne.setRight(droite);

            HBox ligneComplete = new HBox(15);
            ligneComplete.getChildren().addAll(imageView, ligne);

            carte.getChildren().addAll(titre, ligneComplete);

            grilleLivres.add(carte, i % nbColonnes, 1 + i / nbColonnes);
            i++;

        }

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            ajusterLargeur(scene, test, livresPourBanniere);
        });

        primaryStage.show();
    }

    public static void afficher(Stage stage, ConnexionMySQL connexion, Magasin magasin, Client client) {
        try {
            FenetreStock fenetre = new FenetreStock(connexion, magasin, client);
            fenetre.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
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

            bouton.setOnAction(
                    new ControleurAjouterLivrePanier(client, livre, magasinBD.getConnexion(), lblCompteurPanier,
                            magasin));

            droite.getChildren().addAll(prix, bouton);

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

    public static void main(String[] args) {
        launch(args);
    }
}
