package IHM;


import IHM.Controleur.ControleurHome;
import IHM.Controleur.ControleurPanier;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FenetreMagasinAdmin extends Application{
    
    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonPanier;
    private Button boutonRetour;
 
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

    private Pane infoMag(){
        BorderPane entier = new BorderPane();
        entier.setStyle("-fx-background-color:rgb(255, 255, 255); -fx-background-radius: 10px;");

        Text titre = new Text("La Librairie Parisienne");
        titre.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");
        BorderPane.setMargin(titre, new Insets(20));
        entier.setTop(titre);

        BorderPane centre = new BorderPane();
        ImageView image = new ImageView(new Image("file:./img/mag1.jpeg"));
        image.setFitHeight(400);
        image.setPreserveRatio(true);
        Pane conteneurImage = new Pane(image);
        centre.setLeft(conteneurImage);
        BorderPane.setMargin(conteneurImage, new Insets(7, 16, 7, 7));
        centre.setLeft(conteneurImage);

        VBox DescriptionMagasin = new VBox();
        DescriptionMagasin.setAlignment(Pos.CENTER_LEFT);

        HBox num = new HBox();
        Text numMagasin = new Text("n°1");
        numMagasin.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        ImageView maison = new ImageView(new Image("file:./img/phone_icon.jpg"));
        maison.setFitHeight(20);
        maison.setPreserveRatio(true);
        HBox.setMargin(maison, new Insets(5, 0, 0, 0));
        num.getChildren().addAll(maison, numMagasin);

        HBox boxNote = new HBox();
        Text note = new Text("4.6");
        note.setStyle("-fx-font-size: 15px;");
        ImageView etoile = new ImageView(new Image("file:./img/star_icon.png"));
        etoile.setFitHeight(16);
        etoile.setPreserveRatio(true);
        boxNote.getChildren().addAll(note, etoile);

        HBox boxTel = new HBox();
        Text tel = new Text("06 73 69 21 41");
        tel.setStyle("-fx-font-size: 15px;");
        ImageView telephone = new ImageView(new Image("file:./img/phone_icon.jpg"));
        telephone.setFitHeight(20);
        telephone.setPreserveRatio(true);
        boxTel.getChildren().addAll(telephone, tel);

        DescriptionMagasin.setAlignment(Pos.TOP_LEFT);
        DescriptionMagasin.setSpacing(17);
        DescriptionMagasin.getChildren().addAll(num, boxNote, boxTel);
        centre.setRight(DescriptionMagasin);

        HBox boxadresse = new HBox();
        Text adresse = new Text("12 rue Dolou, 75014 Paris");
        tel.setStyle("-fx-font-size: 15px;");
        ImageView pointeur = new ImageView(new Image("file:./img/phone_icon.jpg"));
        pointeur.setFitHeight(20);
        pointeur.setPreserveRatio(true);
        boxadresse.getChildren().addAll(pointeur, adresse);
        centre.setBottom(boxadresse);

        entier.setCenter(centre);

        HBox lesBoutons = new HBox();
        Button modifierStock = new Button("Modifier les stocks");
        modifierStock.setStyle("-fx-background-color: #f28c28; -fx-text-fill: white; -fx-background-radius: 10px;");
        Button supprimerMagasin = new Button("Supprimer Magasin");
        supprimerMagasin.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-background-radius: 10px;");
        lesBoutons.setAlignment(Pos.CENTER_LEFT);
        lesBoutons.getChildren().addAll(modifierStock, supprimerMagasin);

        entier.setBottom(lesBoutons);

        return entier;
    }

    private Pane compteVendeur(){
       HBox compteVendeur = new HBox(10);
        compteVendeur.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        compteVendeur.setPadding(new Insets(15));

        VBox gauche = new VBox();
        gauche.setPadding(new Insets(5));
        Text titre = new Text("Créer un compte Vendeur");
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField nom = new TextField();
        nom.setPromptText("Nom");
        nom.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10px;");
        TextField prenom = new TextField();
        prenom.setPromptText("Prénom");
        prenom.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10px;");
        gauche.getChildren().addAll(titre, nom, prenom);
        VBox.setMargin(prenom, new Insets(5));
        VBox.setMargin(nom, new Insets(5));
        VBox.setMargin(titre, new Insets(5));

        VBox droit = new VBox();
        ImageView image = new ImageView(new Image("file:./img/user_icon.png"));
        image.setFitHeight(100);
        image.setPreserveRatio(true);
        Button button = new Button("Créer");
        button.setStyle("-fx-background-color: #f28c28; -fx-text-fill: white; -fx-background-radius: 10px;");
        button.setPrefWidth(100);

        droit.setAlignment(Pos.CENTER);
        droit.getChildren().addAll(image, button);
        VBox.setMargin(image, new Insets(10));
        VBox.setMargin(button, new Insets(5));

        compteVendeur.getChildren().addAll(gauche, droit);

        return compteVendeur;
    }

    private Pane statistique(){
        VBox stat = new VBox(15);
        stat.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        stat.setPadding(new Insets(15));

        Text titreText = new Text("Statistiques");
        titreText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        ImageView titreImage = new ImageView(new Image("file:./img/phone_icon.jpg"));
        titreImage.setFitHeight(20);
        titreImage.setPreserveRatio(true);
        HBox titre = new HBox(titreText, titreImage);
        HBox.setMargin(titreImage, new Insets(0, 0, 0, 5));
        titre.setAlignment(Pos.CENTER);

        ImageView livre = new ImageView(new Image("file:./img/phone_icon.jpg"));
        livre.setFitHeight(20);
        livre.setPreserveRatio(true);
        HBox vente = new HBox(new Text("Nombre de livres venus pour un magasin"), livre);
        HBox.setMargin(livre, new Insets(0, 0, 0, 5));
        vente.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 10px;");
        vente.setPadding(new Insets(10));

        ImageView pinceau = new ImageView(new Image("file:./img/phone_icon.jpg"));
        pinceau.setFitHeight(20);
        pinceau.setPreserveRatio(true);
        HBox theme = new HBox(new Text("Chiffre d'affaire par thème en année"), pinceau);
        HBox.setMargin(pinceau, new Insets(0, 0, 0, 5));
        theme.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 10px;");
        theme.setPadding(new Insets(10));

        ImageView mag = new ImageView(new Image("file:./img/phone_icon.jpg"));
        mag.setFitHeight(20);
        mag.setPreserveRatio(true);
        HBox ca = new HBox(new Text("Chiffre d'affaire par magasin et par mois en une année"), mag);
        HBox.setMargin(mag, new Insets(0, 0, 0, 5));
        ca.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 10px;");
        ca.setPadding(new Insets(10));

        stat.getChildren().addAll(titre, vente, theme, ca);
        return stat;
    }

        

    @Override
    public void start(Stage primaryStage){
        BorderPane root = new BorderPane();

        Pane banniere = titre();
        root.setTop(banniere);

        HBox cadre = new HBox();
        cadre.setStyle("-fx-background-color: #206db8;");
        cadre.setPadding(new Insets(20));
        root.setCenter(cadre);

        Pane mag = infoMag();
        HBox.setMargin(mag, new Insets(20));
        Pane compte = compteVendeur();
        Pane stat = statistique();
        VBox compteStat = new VBox();
        compteStat.getChildren().addAll(compte, stat);
        VBox.setMargin(compte, new Insets(20));
        VBox.setMargin(stat, new Insets(20));
        cadre.getChildren().addAll(mag, compteStat);
        HBox.setHgrow(mag, Priority.ALWAYS);
         


        Scene scene = new Scene(root, 1200, 750);
        primaryStage.setTitle("Fenêtre des magasins");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

