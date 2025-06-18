package IHM;

import IHM.Controleur.ControleurHome;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import BD.*;
import Java.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class FenetreStock extends Application {

    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonPanier;
    private Button boutonRetour;
    private MagasinBD magasinBD;
    private Magasin magasin;
    private Timeline timelineDefilante;
    private int indexLigne = 0; 

    public FenetreStock(ConnexionMySQL connexionMySQL, Magasin magasin) {
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

    private List<String> getRecommandations() throws SQLException {
        // Récupère les titres des livres disponibles dans le magasin
        Map<Livre, Integer> livres = magasinBD.listeLivreUnMagasin(magasin.getIdMagasin());
        List<String> titres = new ArrayList<>();
        for (Livre livre : livres.keySet()) {
            titres.add(livre.getNomLivre());
        }
        return titres;
    }

    private void setupBanniereDefilanteLigneParLigne(VBox container, List<String> recommandations, double largeur) {
    container.getChildren().clear();
    container.setPrefHeight(40);
    container.setMinHeight(40);
    container.setMaxHeight(40);
    container.setPrefWidth(largeur);
    container.setMaxWidth(largeur);
    container.setMinWidth(largeur);

    // Clip pour empêcher le dépassement visible hors de la zone
    Rectangle clip = new Rectangle(largeur, 40);
    container.setClip(clip);

    HBox hbox = new HBox(50);
    hbox.setAlignment(Pos.CENTER_LEFT);

    // Ajout 2 fois des titres pour boucle fluide
    for (int i = 0; i < 2; i++) {
        for (String texteStr : recommandations) {
            Text texte = new Text(texteStr);
            texte.setStyle("-fx-font-size: 20px; -fx-fill: white; -fx-font-weight: bold;");
            hbox.getChildren().add(texte);
        }
    }

    container.getChildren().add(hbox);

    // Timeline pour gérer le défilement infini
    if (timelineDefilante != null) {
        timelineDefilante.stop();
    }

    // Timeline d’attente que la largeur soit calculée
    Timeline[] attenteLayout = new Timeline[1];

    attenteLayout[0] = new Timeline(new KeyFrame(Duration.millis(50), e -> {
        double width = hbox.getWidth();
        // System.out.println("Lecture largeur HBox : " + width);

        if (width > 0) {
            double totalWidth = width / 2; // largeur d'une répétition

            hbox.setTranslateX(0);

            timelineDefilante = new Timeline(new KeyFrame(Duration.millis(20), ev -> {
                double x = hbox.getTranslateX();
                x -= 2; // vitesse de défilement

                if (x <= -totalWidth) {
                    x = 0;
                }
                hbox.setTranslateX(x);
            }));

            timelineDefilante.setCycleCount(Timeline.INDEFINITE);
            timelineDefilante.play();

            // Stopper la timeline d’attente
            attenteLayout[0].stop();
        } else {
            // Si largeur = 0, relancer la timeline d’attente
            attenteLayout[0].playFromStart();
        }
    }));

    attenteLayout[0].setCycleCount(1);
    attenteLayout[0].play();
}


    private void ajusterLargeur(Scene scene, VBox test, List<String> recommandations, GridPane grilleLivres,
        VBox cadreGrand) {
        double largeurScene = scene.getWidth();

        test.setPrefWidth(largeurScene);
        // test.setPrefHeight(200);  // ligne supprimée ou commentée
        test.setClip(new Rectangle(largeurScene, 40));

        if (timelineDefilante != null) {
            timelineDefilante.stop();
        }
        setupBanniereDefilanteLigneParLigne(test, recommandations, largeurScene);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1500, 750);

        Pane banniere = titre();
        root.setTop(banniere);

        VBox cadreGrand = new VBox();
        cadreGrand.setStyle(" -fx-background-color: #206db8;");
        cadreGrand.setSpacing(20);
        cadreGrand.setAlignment(Pos.CENTER);

        VBox ensemble = new VBox();
        

        VBox test = new VBox();
        test.setStyle("-fx-background-color: rgb(236,18,225);");
        
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

        // Récupération des recommandations
        List<String> recommandations = getRecommandations();

        // Initialisation de la bannière défilante avec la largeur initiale de la scène
        setupBanniereDefilanteLigneParLigne(test, recommandations, scene.getWidth());
        // Construction de la grille des livres
        GridPane grilleLivres = new GridPane();
        grilleLivres.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20px 20px 0 0; -fx-border-radius: 20px 20px 0 0;");
        cadreGrand.setPadding(new Insets(20, 50, 0, 50)); // un padding plus léger
        grilleLivres.setHgap(30);
        grilleLivres.setVgap(30);
        grilleLivres.setAlignment(Pos.CENTER);
        cadreGrand.getChildren().addAll(grilleLivres);
        grilleLivres.setPadding(new Insets(20));

        Text titreMag = new Text(magasin.getNom());
        titreMag.setStyle("-fx-font-size: 35px; -fx-font-weight: bold;");
        titreMag.setWrappingWidth(400);
        grilleLivres.add(titreMag, 1, 0);
        GridPane.setHalignment(titreMag, HPos.CENTER);

        Map<Livre, Integer> listeLivres = magasinBD.listeLivreUnMagasin(this.magasin.getIdMagasin());

        final int nbColonnes = 3;
        int i = 0;
        for (Map.Entry<Livre, Integer> entry : listeLivres.entrySet()) {
            Livre livre = entry.getKey();
            Integer quantite = entry.getValue();

            VBox carte = new VBox(10);
            carte.setPadding(new Insets(10));
            carte.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 20px; -fx-border-radius: 20px;");
            carte.setAlignment(Pos.TOP_LEFT);
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

            droite.getChildren().addAll(prix, bouton);

            BorderPane ligne = new BorderPane();
            ligne.setLeft(infos);
            ligne.setRight(droite);

            carte.getChildren().addAll(titre, ligne);

            int col = i % nbColonnes;
            int row = i / nbColonnes + 1; // +1 pour sauter la ligne du titre
            grilleLivres.add(carte, col, row);
            i++;
        }

        // Ajustement dynamique de la largeur des cartes et bannière défilante
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double largeurScene = newVal.doubleValue();

            // Bannière défilante
            test.setPrefWidth(largeurScene);
            test.setClip(new Rectangle(largeurScene, 40));

            if (timelineDefilante != null) {
                timelineDefilante.stop();
            }
            setupBanniereDefilanteLigneParLigne(test, recommandations, largeurScene);

            // Largeur carte
            double largeurCarte = (largeurScene - (nbColonnes - 1) * grilleLivres.getHgap()
                    - cadreGrand.getPadding().getLeft() - cadreGrand.getPadding().getRight()) / nbColonnes;
            largeurCarte = Math.min(Math.max(largeurCarte, 200), 350);

            for (javafx.scene.Node node : grilleLivres.getChildren()) {
                if (node instanceof VBox) {
                    ((VBox) node).setPrefWidth(largeurCarte);
                }
            }
        });

        // Déclenche le listener une première fois pour ajuster la taille au démarrage
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            ajusterLargeur(scene, test, recommandations, grilleLivres, cadreGrand);
        });

        primaryStage.show();
    }

    public static void afficher(Stage stage, ConnexionMySQL connexionMySQL, Magasin magasin) {
        try {
            FenetreStock fs = new FenetreStock(connexionMySQL, magasin);
            fs.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
