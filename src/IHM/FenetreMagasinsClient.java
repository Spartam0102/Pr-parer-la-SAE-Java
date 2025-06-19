package IHM;

import IHM.Controleur.ControleurStock;
import IHM.Controleur.ControleurCarteMagasinAdmin;
import IHM.Controleur.ControleurHome;
import IHM.Controleur.ControleurMagasinVendeur;
import IHM.Controleur.ControleurPanier;
import IHM.Controleur.ControleurParametre;

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

public class FenetreMagasinsClient extends Application {

    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonPanier;
    private Button boutonRetour;
    private MagasinBD magasinBD;
    private Client client;
    private Stage stage;

    public FenetreMagasinsClient(ConnexionMySQL connexionMySQL, Client client) {
        this.magasinBD = new MagasinBD(connexionMySQL);
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
        boutonRetour.setStyle("-fx-background-color:rgb(150, 175, 201);" +
                "-fx-border-radius: 18; -fx-background-radius: 18;");
        boutonRetour.setDisable(true);

        HBox boutons = new HBox(10, boutonHome, boutonSettings, boutonPanier, boutonRetour);
        boutons.setPadding(new Insets(10));
        boutons.setAlignment(Pos.CENTER);

        boutonHome.setOnAction(new ControleurHome(this.stage));
        boutonPanier.setOnAction(new ControleurPanier(magasinBD.getConnexion(), this.client, this.stage));

        VBox conteneurDroit = new VBox(boutons);
        conteneurDroit.setAlignment(Pos.CENTER);
        conteneurDroit.setPadding(new Insets(10));

        Text nomClient = new Text("Bienvenue " + this.client.getPrenom());
        nomClient.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

        BorderPane banniere = new BorderPane();
        banniere.setLeft(logo);
        banniere.setCenter(nomClient);
        banniere.setRight(conteneurDroit);
        banniere.setStyle("-fx-background-color: white;");

        return banniere;
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
         this.stage = primaryStage;

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
            BorderPane PaneMagasin = new BorderPane();

            ImageView image = new ImageView(new Image("file:./img/mag" + (i + 1) + ".jpeg"));
            image.setFitHeight(130);
            image.setPreserveRatio(true);

            VBox conteneurImage = new VBox(image);
            conteneurImage.setAlignment(Pos.CENTER_LEFT);
            conteneurImage.setMinWidth(150);
            conteneurImage.setMaxWidth(150);
            PaneMagasin.setLeft(conteneurImage);
            BorderPane.setMargin(conteneurImage, new Insets(7, 16, 7, 7));

            VBox DescriptionMagasin = new VBox();
            DescriptionMagasin.setAlignment(Pos.CENTER_LEFT);
            DescriptionMagasin.setPadding(new Insets(0, 10, 0, 10));
            DescriptionMagasin.setMaxWidth(Double.MAX_VALUE);

            image.setFitHeight(130);
            image.setPreserveRatio(true);
            image.setSmooth(true);

            Text nomMagasin = new Text(listeMagasins.get(i).getNom());
            nomMagasin.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            VBox.setMargin(nomMagasin, new Insets(5, 0, 0, 0));

            HBox boxNote = new HBox();
            Text note = new Text(Double.toString(listeMagasins.get(i).getNote()));
            note.setStyle("-fx-font-size: 15px;");
            ImageView etoile = new ImageView(new Image("file:./img/star_icon.png"));
            etoile.setFitHeight(16);
            etoile.setPreserveRatio(true);
            boxNote.getChildren().addAll(note, etoile);

            HBox boxTel = new HBox();
            Text tel = new Text(listeMagasins.get(i).getTel());
            tel.setStyle("-fx-font-size: 15px;");
            ImageView telephone = new ImageView(new Image("file:./img/phone.png"));
            telephone.setFitHeight(20);
            telephone.setPreserveRatio(true);
            boxTel.getChildren().addAll(telephone, tel);

            DescriptionMagasin.setAlignment(Pos.TOP_LEFT);
            DescriptionMagasin.setSpacing(17);
            DescriptionMagasin.getChildren().addAll(nomMagasin, boxNote, boxTel);
            PaneMagasin.setCenter(DescriptionMagasin);

            HBox boxMap = new HBox();
            Text adresse = new Text(listeMagasins.get(i).getAdresse());
            adresse.setStyle("-fx-font-size: 15px;");
            ImageView map = new ImageView(new Image("file:./img/map.png"));
            map.setFitHeight(20);
            map.setPreserveRatio(true);
            boxMap.getChildren().addAll(map, adresse);

            PaneMagasin.setBottom(boxMap);

            VBox carteMagasin = new VBox();
            carteMagasin.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
            carteMagasin.setPadding(new Insets(10));
            carteMagasin.setSpacing(10);

            carteMagasin.getChildren().add(PaneMagasin);
            int col = i % 3;
            int row = i / 3;

            cadre.add(carteMagasin, col, row);

            carteMagasin.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(carteMagasin, Priority.ALWAYS);
            Magasin magasinSelectionne = listeMagasins.get(i);

            carteMagasin.setOnMouseClicked(new ControleurStock(this.magasinBD.getConnexion(), magasinSelectionne, client, primaryStage));
        }

        Scene scene = new Scene(root, 1500, 750);
        primaryStage.setTitle("Fenêtre des magasins");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void afficher(Stage stage, ConnexionMySQL connexionMySQL, Client client) {
        try {
            FenetreMagasinsClient fm = new FenetreMagasinsClient(connexionMySQL, client);
            fm.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}