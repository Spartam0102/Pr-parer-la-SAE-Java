package IHM;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class FenetrePanier extends Application {

    private BorderPane racine;
    private VBox panelCentral;

    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonRetour;
    private Button boutonPanier;

    @Override
    public void init() {
        // Initialisation si nécessaire
    }

    private Scene laScene() {
        this.racine = new BorderPane();
        racine.setTop(this.titre()); // Bannière en haut
        this.panelCentral = fenetrePanier(); // Contenu principal au centre
        racine.setCenter(this.panelCentral);
        Scene scene = new Scene(racine, 1200, 750);
        return scene;
    }

    private Pane titre() {
    ImageView logo = new ImageView(new Image("file:img/ChatGPT Image 17 juin 2025, 08_55_03.png"));
    logo.setFitHeight(110);
    logo.setPreserveRatio(true);

    ImageView homeView = new ImageView(new Image("file:img/house.png"));
    ImageView settingsView = new ImageView(new Image("file:img/settings.png"));
    ImageView panierView = new ImageView(new Image("file:img/panier.png"));
    ImageView retourView = new ImageView(new Image("file:img/retour.png"));

    for (ImageView iv : new ImageView[]{homeView, settingsView, panierView, retourView}) {
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
    boutons.setAlignment(Pos.CENTER); // centre les boutons dans la HBox horizontalement

    // Conteneur pour centrer la HBox verticalement dans la partie droite
    VBox conteneurDroit = new VBox(boutons);
    conteneurDroit.setAlignment(Pos.CENTER); // centre verticalement
    conteneurDroit.setPadding(new Insets(10));

    BorderPane banniere = new BorderPane();
    banniere.setLeft(logo);
    banniere.setRight(conteneurDroit); // la VBox est placée à droite
    banniere.setStyle("-fx-background-color: white;");

    return banniere;
}


    private VBox fenetrePanier() {
        VBox containerVertical = new VBox();
        containerVertical.setPadding(new Insets(20));
        containerVertical.setSpacing(10);

        HBox conteneur = new HBox(20);
        conteneur.setPadding(new Insets(20));
        conteneur.setStyle("-fx-background-color: #2073c4;");
        HBox.setHgrow(conteneur, Priority.ALWAYS);

        // Liste des livres
        VBox listeLivres = new VBox(10);
        listeLivres.setPadding(new Insets(10));
        listeLivres.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        listeLivres.setPrefWidth(400);

        for (int i = 0; i < 20; i++) {
            VBox livre = new VBox(5);
            Label titre = new Label("L'Égypte des pharaons au Musée du Caire");
            titre.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            Label auteur = new Label("Clatire Dubois");
            Label prix = new Label("8.63 €");
            prix.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            livre.getChildren().addAll(titre, auteur, prix);
            livre.setPadding(new Insets(10));
            livre.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");
            listeLivres.getChildren().add(livre);
        }

        ScrollPane scrollPane = new ScrollPane(listeLivres);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent;");

        // Liaison dynamique de la hauteur du ScrollPane à 70% de la hauteur de la
        // fenêtre (racine)
        scrollPane.prefHeightProperty().bind(racine.heightProperty().multiply(0.7));
        scrollPane.maxHeightProperty().bind(racine.heightProperty().multiply(0.7));

        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        // Partie récapitulatif
        VBox recap = new VBox(20);
        recap.setPrefWidth(300);
        recap.setMaxHeight(Double.MAX_VALUE);
        recap.setPadding(new Insets(15));
        recap.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        Label nbProduits = new Label("Nombre de produit : 4");
        Label livraison = new Label("Livraison : Domicile");
        Label total = new Label("Total : 25,90 €");
        total.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Button commander = new Button("Commander");
        commander.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white; -fx-font-weight: bold;");
        recap.getChildren().addAll(nbProduits, livraison, total, commander);

        // Mode de livraison
        VBox modeLivraison = new VBox(10);
        modeLivraison.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        modeLivraison.setPadding(new Insets(15));
        Label modeLabel = new Label("Mode de livraison");
        Button domicile = new Button("Domicile");
        Button magasin = new Button("Magasin");

        // Style initial : domicile sélectionné, magasin non sélectionné
        domicile.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white;");
        magasin.setStyle("-fx-background-color: #fbbd8d; -fx-text-fill: black;");

        // Gestion des clics pour basculer la sélection
        domicile.setOnAction(e -> {
            domicile.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white;");
            magasin.setStyle("-fx-background-color: #fbbd8d; -fx-text-fill: black;");
        });

        magasin.setOnAction(e -> {
            magasin.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white;");
            domicile.setStyle("-fx-background-color: #fbbd8d; -fx-text-fill: black;");
        });

        HBox boutonsLivraison = new HBox(10, domicile, magasin);
        boutonsLivraison.setAlignment(Pos.CENTER);
        modeLivraison.getChildren().addAll(modeLabel, boutonsLivraison);
        modeLivraison.setAlignment(Pos.CENTER);

        // Regroupement total
        VBox recapWrapper = new VBox(20, recap, modeLivraison);
        recapWrapper.setPrefWidth(300);
        HBox.setHgrow(recapWrapper, Priority.ALWAYS);

        // Synchronisation de la hauteur (optionnel, si besoin)
        recapWrapper.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPane.setPrefHeight(newVal.doubleValue());
        });

        // Ajout au conteneur horizontal
        conteneur.getChildren().addAll(scrollPane, recapWrapper);

        // Ajout au conteneur vertical principal
        containerVertical.getChildren().add(conteneur);

        VBox.setVgrow(conteneur, Priority.ALWAYS);

        return containerVertical;
    }

    @Override
    public void start(Stage stage) {
        Scene scene = laScene();
        stage.setScene(scene);
        stage.setTitle("Livre Express - Panier");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
