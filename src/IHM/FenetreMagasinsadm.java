package IHM;

import IHM.Controleur.ControleurCarteMagasin;
import IHM.Controleur.ControleurHome;
import IHM.Controleur.ControleurPanier;
import IHM.Controleur.ControleurPlusMagasin;
import IHM.Controleur.ControleurCarteMagasinAdmin;

import java.sql.SQLException;
import java.util.List;

import BD.*;
import Java.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FenetreMagasinsadm extends Application {

    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonPanier;
    private Button boutonRetour;
    private MagasinBD magasinBD;

    public FenetreMagasinsadm(ConnexionMySQL connexionMySQL) {
        this.magasinBD = new MagasinBD(connexionMySQL);
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
    public void start(Stage primaryStage) throws SQLException {

        BorderPane root = new BorderPane();

        Pane banniere = titre();
        root.setTop(banniere);

        GridPane cadre = new GridPane();
        cadre.setStyle("-fx-background-color: #206db8;");
        cadre.setPadding(new Insets(20));
        cadre.setHgap(20);
        cadre.setVgap(20);
        cadre.setPadding(new Insets(30));
        ScrollPane scrollPane = new ScrollPane(cadre);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: transparent;");
        scrollPane.prefHeightProperty().bind(root.heightProperty().multiply(1));
        scrollPane.maxHeightProperty().bind(root.heightProperty().multiply(1));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 

        root.setCenter(scrollPane);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHgrow(Priority.ALWAYS);
            colConst.setFillWidth(true);
            colConst.setPercentWidth(33.33);
            cadre.getColumnConstraints().add(colConst);
        }

        List<Magasin> listeMagasins = magasinBD.listeDesMagasins();
        for (int i = 0; i < listeMagasins.size(); i++) {
            BorderPane carte = new BorderPane();

            ImageView image = new ImageView(new Image("file:./img/mag" + (i + 1) + ".jpeg"));
            image.setFitHeight(130);
            image.setPreserveRatio(true);
            if(i>6){
            image = new ImageView(new Image("file:./img/image.png"));
            image.setFitHeight(130);
            image.setPreserveRatio(true);

            }
            VBox boxImage = new VBox(image);
            boxImage.setAlignment(Pos.CENTER_LEFT);
            boxImage.setMinWidth(150);
            carte.setLeft(boxImage);
            BorderPane.setMargin(boxImage, new Insets(7, 16, 7, 7));

            VBox description = new VBox();
            description.setAlignment(Pos.CENTER_LEFT);
            description.setSpacing(17);

            Text nom = new Text(listeMagasins.get(i).getNom());
            nom.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            VBox.setMargin(nom, new Insets(5, 0, 0, 0));

            HBox noteBox = new HBox();
            noteBox.setSpacing(5);
            Text note = new Text(String.valueOf(listeMagasins.get(i).getNote()));
            note.setStyle("-fx-font-size: 15px;");
            ImageView etoile = new ImageView(new Image("file:./img/star_icon.png"));
            etoile.setFitHeight(16);
            etoile.setPreserveRatio(true);
            noteBox.getChildren().addAll(note, etoile);

            HBox telBox = new HBox();
            telBox.setSpacing(5);
            Text tel = new Text(listeMagasins.get(i).getTel());
            tel.setStyle("-fx-font-size: 15px;");
            ImageView phone = new ImageView(new Image("file:./img/phone.png"));
            phone.setFitHeight(20);
            phone.setPreserveRatio(true);
            telBox.getChildren().addAll(phone, tel);

            description.getChildren().addAll(nom, noteBox, telBox);
            carte.setCenter(description);

            HBox adresseBox = new HBox();
            adresseBox.setSpacing(5);
            Text adresse = new Text(listeMagasins.get(i).getAdresse());
            adresse.setStyle("-fx-font-size: 15px;");
            ImageView map = new ImageView(new Image("file:./img/map.png"));
            map.setFitHeight(20);
            map.setPreserveRatio(true);
            adresseBox.getChildren().addAll(map, adresse);
            carte.setBottom(adresseBox);

            VBox conteneurCarte = new VBox();
            conteneurCarte.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
            conteneurCarte.setPadding(new Insets(10));
            conteneurCarte.setSpacing(10);
            conteneurCarte.getChildren().add(carte);

            int col = i % 3;
            int row = i / 3;

            cadre.add(conteneurCarte, col, row);
            conteneurCarte.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(conteneurCarte, Priority.ALWAYS);

             Magasin magasinSelectionne = listeMagasins.get(i);

            ControleurCarteMagasinAdmin controleur = new ControleurCarteMagasinAdmin(magasinBD.getConnexion(),
                    magasinSelectionne);

            conteneurCarte.setOnMouseClicked(event -> {

                System.out.println("Magasin sélectionné : " + magasinSelectionne.getNom());
                controleur.allerDansFenetreMAgasin(primaryStage);
            });
        }

        // Création du bouton "Ajouter un magasin" avec l'icône plus
        VBox magasinPlus = new VBox();
        magasinPlus.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        magasinPlus.setPadding(new Insets(10));
        magasinPlus.setSpacing(10);
        magasinPlus.setPrefHeight(180);
        magasinPlus.setAlignment(Pos.CENTER);

        ImageView plusIcon = new ImageView(new Image("file:./img/plus.png"));
        plusIcon.setPreserveRatio(true);
        plusIcon.setFitWidth(100);

        Text texteAjouter = new Text("Ajouter un magasin");
        texteAjouter.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #206db8;");

        magasinPlus.getChildren().addAll(plusIcon, texteAjouter);

/*         // Ajout de l'effet hover pour le bouton plus
        magasinPlus.setOnMouseEntered(e -> {
            magasinPlus.setStyle("-fx-background-color: #f0f8ff; -fx-background-radius: 15px; " +
                               "-fx-border-color: #206db8; -fx-border-width: 2px; -fx-border-radius: 15px; " +
                               "-fx-cursor: hand;");
        });

        magasinPlus.setOnMouseExited(e -> {
            magasinPlus.setStyle("-fx-background-color: white; -fx-background-radius: 15px; " +
                               "-fx-border-color: #206db8; -fx-border-width: 2px; -fx-border-radius: 15px;");
        });
*/
        // Action pour ouvrir la popup d'ajout de magasin
        magasinPlus.setOnMouseClicked(e -> {
            ControleurPlusMagasin.afficherPopupAjouterMagasin(primaryStage, magasinBD);
        });

        int i = listeMagasins.size();
        int col = i % 3;
        int row = i / 3;

        cadre.add(magasinPlus, col, row);
        GridPane.setHgrow(magasinPlus, Priority.ALWAYS);

        Scene scene = new Scene(root, 1200, 750);
        primaryStage.setTitle("Fenêtre des magasins");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void afficher(Stage stage, ConnexionMySQL connexionMySQL) {
        try {
            FenetreMagasinsadm fm = new FenetreMagasinsadm(connexionMySQL);
            fm.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}