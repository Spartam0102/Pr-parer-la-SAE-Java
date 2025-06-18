package IHM;

import BD.ConnexionMySQL;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class FenetreMagasinVendeur extends Application {

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
        this.panelCentral = fenetreMagasin(); // Contenu principal au centre
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
    conteneurDroit.setAlignment(Pos.CENTER); 
    conteneurDroit.setPadding(new Insets(10));

    BorderPane banniere = new BorderPane();
    banniere.setLeft(logo);
    banniere.setRight(conteneurDroit); 
    banniere.setStyle("-fx-background-color: white;");

    return banniere;
}


   private VBox fenetreMagasin() {
    VBox container = new VBox();
    container.setPadding(new Insets(20));
    container.setSpacing(10);

    HBox principal = new HBox(20);
    principal.setPadding(new Insets(20));
    principal.setStyle("-fx-background-color: #2073c4;");
    HBox.setHgrow(principal, Priority.ALWAYS);

    // --- Carte librairie à gauche ---
    VBox carte = new VBox(10);
    carte.setPadding(new Insets(15));
    carte.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
    carte.setPrefWidth(500);

    Label nomLibrairie = new Label("La Librairie Parisienne");
    nomLibrairie.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

    ImageView imgLibrairie = new ImageView(new Image("file:img/librairie.png"));
    imgLibrairie.setFitHeight(200);
    imgLibrairie.setPreserveRatio(true);

    HBox infos = new HBox(10);
    VBox gaucheInfos = new VBox(5);
    gaucheInfos.getChildren().addAll(
        new Label("📦 n°1"),
        new Label("⭐ 4.6"),
        new Label("📞 06 48 72 35 19")
    );

    VBox droiteInfos = new VBox(5);
    droiteInfos.getChildren().add(new Label("📍 24 Rue de la Tombe-Issoire, 75014 Paris"));

    infos.getChildren().addAll(gaucheInfos, droiteInfos);

    Button supprimerLivre = new Button("Supprimer un livre");
    supprimerLivre.setStyle("-fx-background-color: red; -fx-text-fill: white;");

    carte.getChildren().addAll(nomLibrairie, imgLibrairie, infos, supprimerLivre);

    // --- Colonne droite avec deux encadrés ---

    VBox panneauDroit = new VBox(20);

    // Voir un panier
    VBox voirPanier = new VBox(10);
    voirPanier.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
    voirPanier.setPadding(new Insets(15));
    Label lblVoir = new Label("Voir un panier");
    TextField champIdPanier = new TextField();
    champIdPanier.setPromptText("ID client");
    Button boutonVoir = new Button("Voir");
    boutonVoir.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white;");
    ImageView iconePanier = new ImageView(new Image("file:img/panier.png"));
    iconePanier.setFitHeight(40);
    iconePanier.setPreserveRatio(true);
    HBox voirHBox = new HBox(10, champIdPanier, boutonVoir);
    voirHBox.setAlignment(Pos.CENTER);
    voirPanier.getChildren().addAll(lblVoir, voirHBox, iconePanier);
    voirPanier.setAlignment(Pos.CENTER);

    // Ajouter un livre
    VBox ajoutLivre = new VBox(10);
    ajoutLivre.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
    ajoutLivre.setPadding(new Insets(15));
    Label lblAjout = new Label("Ajouter un livre 📖");
    TextField champTitre = new TextField();
    champTitre.setPromptText("Titre");
    TextField champAuteur = new TextField();
    champAuteur.setPromptText("Auteur");
    TextField champISBN = new TextField();
    champISBN.setPromptText("ISBN");
    TextField champPages = new TextField();
    champPages.setPromptText("Nombre de pages");
    TextField champPrix = new TextField();
    champPrix.setPromptText("Prix");
    Button boutonAjouter = new Button("Ajouter");
    boutonAjouter.setStyle("-fx-background-color: green; -fx-text-fill: white;");
    ajoutLivre.getChildren().addAll(lblAjout, champTitre, champAuteur, champISBN, champPages, champPrix, boutonAjouter);

    panneauDroit.getChildren().addAll(voirPanier, ajoutLivre);
    panneauDroit.setPrefWidth(400);

    // Ajout au conteneur principal
    principal.getChildren().addAll(carte, panneauDroit);

    container.getChildren().add(principal);
    VBox.setVgrow(principal, Priority.ALWAYS);

    return container;
}


    @Override
    public void start(Stage stage) {
        Scene scene = laScene();
        stage.setScene(scene);
        stage.setTitle("Livre Express - Panier");
        stage.show();
    }

     public static void afficher(Stage stage, ConnexionMySQL connexionMySQL) {
        try {
            
            FenetreMagasinVendeur fm = new FenetreMagasinVendeur();
            fm.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
