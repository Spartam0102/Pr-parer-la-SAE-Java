package IHM;


import BD.ConnexionMySQL;
import BD.MagasinBD;
import IHM.Controleur.ControleurAjouterVendeur;
import IHM.Controleur.ControleurAllerModifierStock;
import IHM.Controleur.ControleurCompteur;
import IHM.Controleur.ControleurHome;
import IHM.Controleur.ControleurParametre;
import IHM.Controleur.ControleurRetour;

import IHM.Controleur.ControleurStock;
import IHM.Controleur.ControleurSuppElemPanier;
import IHM.Controleur.ControleurSuppMagasin;

import Java.Magasin;
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

public class FenetreUnMagasinAdmin extends Application{
    
    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonRetour;
    private MagasinBD magasinBD;
    private Magasin magasin;
    private Stage stage;

    public FenetreUnMagasinAdmin(ConnexionMySQL connexionMySQL, Magasin magasin) {
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
        this.boutonRetour = new Button("", retourView);
        boutonSettings.setOnAction(new ControleurParametre(this.stage));

        String styleBouton = "-fx-background-color: #206db8;" +
                "-fx-border-radius: 18; -fx-background-radius: 18;";
        boutonHome.setStyle(styleBouton);
        boutonSettings.setStyle(styleBouton);
        boutonRetour.setStyle(styleBouton);

        HBox boutons = new HBox(10, boutonHome, boutonSettings, boutonRetour);
        boutons.setPadding(new Insets(10));
        boutons.setAlignment(Pos.CENTER);

        boutonHome.setOnAction(new ControleurHome(this.stage));
        boutonRetour.setOnAction(new ControleurRetour(this.magasinBD.getConnexion(), stage, null, "fenetreMagasinsAdmin"));

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

        Text titre = new Text(magasin.getNom());
        titre.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");
        BorderPane.setMargin(titre, new Insets(0, 20, 20, 20));
        entier.setTop(titre);

        BorderPane centre = new BorderPane();
        ImageView image = new ImageView(new Image("file:./img/mag"+ magasin.getIdMagasin() + ".jpeg"));
        image.setFitHeight(300);
        image.setStyle("-fx-background-radius: 10px;");
        image.setPreserveRatio(true);
        centre.setLeft(image);

        HBox num = new HBox();
        Text numMagasin = new Text("n°"+ magasin.getIdMagasin());
        numMagasin.setStyle("-fx-font-size: 20px;");
        ImageView maison = new ImageView(new Image("file:./img/image.png"));
        maison.setFitHeight(20);
        maison.setPreserveRatio(true);
        HBox.setMargin(maison, new Insets(0, 5, 0, 0));
        num.getChildren().addAll(maison, numMagasin);

        HBox boxNote = new HBox();
        Text note = new Text("" + magasin.getNote());
        note.setStyle("-fx-font-size: 15px;");
        ImageView etoile = new ImageView(new Image("file:./img/star_icon.png"));
        etoile.setFitHeight(16);
        etoile.setPreserveRatio(true);
        HBox.setMargin(etoile, new Insets(0, 0, 0, 5));
        boxNote.getChildren().addAll(note, etoile);

        HBox boxTel = new HBox();
        Text tel = new Text(magasin.getTel());
        tel.setStyle("-fx-font-size: 15px;");
        ImageView telephone = new ImageView(new Image("file:./img/phone.png"));
        telephone.setFitHeight(20);
        telephone.setPreserveRatio(true);
        HBox.setMargin(telephone, new Insets(0, 5, 0, 0));
        boxTel.getChildren().addAll(telephone, tel);

        VBox descriptionMagasin = new VBox();
        descriptionMagasin.setAlignment(Pos.CENTER_RIGHT);
        descriptionMagasin.setSpacing(17);
        VBox.setMargin(num, new Insets(0, 0 , 20, 0));
        VBox.setMargin(boxNote, new Insets(0, 0 , 20, 0));
        VBox.setMargin(boxTel, new Insets(0, 0 , 20, 0));
        descriptionMagasin.getChildren().addAll(num, boxNote, boxTel);
        centre.setRight(descriptionMagasin);

        HBox boxadresse = new HBox();
        Text adresse = new Text(magasin.getAdresse());
        tel.setStyle("-fx-font-size: 15px;");
        ImageView pointeur = new ImageView(new Image("file:./img/map.png"));
        pointeur.setFitHeight(20);
        pointeur.setPreserveRatio(true);
        boxadresse.setAlignment(Pos.CENTER);
        boxadresse.getChildren().addAll(pointeur, adresse);
        centre.setBottom(boxadresse);

        entier.setCenter(centre);

        HBox lesBoutons = new HBox();
        Button modifierStock = new Button("Modifier les stocks");
        modifierStock.setStyle("-fx-background-color: #f28c28; -fx-text-fill: white; -fx-background-radius: 10px; -fx-font-weight: bold;");
        HBox.setMargin(modifierStock, new Insets(0, 50, 0, 0));
        modifierStock.setPrefHeight(40);
        modifierStock.setPrefWidth(200);
        Button supprimerMagasin = new Button("Supprimer Magasin");
        supprimerMagasin.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-background-radius: 10px; -fx-font-weight: bold;");
        HBox.setMargin(supprimerMagasin, new Insets(0, 0, 0, 50));
        supprimerMagasin.setPrefHeight(40);
        supprimerMagasin.setPrefWidth(200);
        lesBoutons.setAlignment(Pos.CENTER);
        BorderPane.setMargin(lesBoutons, new Insets(20));
        lesBoutons.getChildren().addAll(modifierStock, supprimerMagasin);

        modifierStock.setOnMouseClicked(new ControleurAllerModifierStock(this.magasinBD.getConnexion(), magasin, stage));
        supprimerMagasin.setOnMouseClicked(new ControleurSuppMagasin(magasin, this.magasinBD.getConnexion(), stage));

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
        TextField mdp = new TextField();
        mdp.setPromptText("mdp");
        mdp.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10px;");
        TextField date = new TextField();
        date.setPromptText("date de naissance");
        mdp.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10px;");
        gauche.getChildren().addAll(titre, nom, prenom,mdp,date);
        VBox.setMargin(prenom, new Insets(5));
        VBox.setMargin(nom, new Insets(5));
        VBox.setMargin(date, new Insets(5));
        VBox.setMargin(mdp, new Insets(5));
        VBox.setMargin(titre, new Insets(5));

        VBox droit = new VBox();
        ImageView image = new ImageView(new Image("file:./img/user_icon.png"));
        image.setFitHeight(100);
        image.setPreserveRatio(true);
        Button button = new Button("Créer");
        button.setStyle("-fx-background-color: #f28c28; -fx-text-fill: white; -fx-background-radius: 10px; -fx-font-weight: bold;");
        button.setPrefWidth(100);


    ControleurAjouterVendeur controleurAjouterVendeur = new ControleurAjouterVendeur(nom, prenom, date, mdp, magasin, magasinBD.getConnexion());
    button.setOnAction(controleurAjouterVendeur);

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
        this.stage = primaryStage;
        BorderPane root = new BorderPane();

        Pane banniere = titre();
        root.setTop(banniere);

        HBox cadre = new HBox();
        cadre.setStyle("-fx-background-color: #206db8;");
        cadre.setPadding(new Insets(20));
        root.setCenter(cadre);

        Pane mag = infoMag();
        mag.setPadding(new Insets(30));
        HBox.setMargin(mag, new Insets(20));
        Pane compte = compteVendeur();
        compte.setMinWidth(400);
        compte.setMinHeight(200);
        Pane stat = statistique();
        stat.setMinWidth(400);
        stat.setMinWidth(200);
        VBox compteStat = new VBox();
        compteStat.getChildren().addAll(compte, stat);
        VBox.setMargin(compte, new Insets(20));
        VBox.setMargin(stat, new Insets(20));
        cadre.getChildren().addAll(mag, compteStat);
        HBox.setHgrow(mag, Priority.ALWAYS);


        Scene scene = new Scene(root, 1500, 750);
        primaryStage.setTitle("Fenêtre des magasins");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


     public static void afficher(Stage stage, ConnexionMySQL connexionMySQL, Magasin magasin) {
        try {
            FenetreStock fs = new FenetreStock(connexionMySQL, magasin, null);
            fs.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}

