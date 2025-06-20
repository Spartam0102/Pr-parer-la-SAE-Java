package IHM;

import IHM.Controleur.ControleurHome;
import IHM.Controleur.ControleurParametre;

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

public class FenetreLivres extends Application {

    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonPanier;
    private Button boutonRetour;
    private Stage stage;

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

        boutonHome.setOnAction(new ControleurHome(this.stage));

        HBox boutons = new HBox(10, boutonHome, boutonSettings, boutonPanier, boutonRetour);
        boutons.setPadding(new Insets(10));
        boutons.setAlignment(Pos.CENTER);

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
    public void start(Stage primaryStage) {
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
        root.setCenter(cadre);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHgrow(Priority.ALWAYS);
            colConst.setFillWidth(true);
            colConst.setPercentWidth(33.33);
            cadre.getColumnConstraints().add(colConst);
        }

        for (int i = 0; i < 9; i++) {
            BorderPane PaneMagasin = new BorderPane();

            ImageView image = new ImageView(new Image("file:./img/mag1.jpeg"));
            image.setFitHeight(130);
            image.setPreserveRatio(true);
            Pane conteneurImage = new Pane(image);
            PaneMagasin.setLeft(conteneurImage);
            BorderPane.setMargin(conteneurImage, new Insets(7, 16, 7, 7));

            VBox DescriptionMagasin = new VBox();
            DescriptionMagasin.setAlignment(Pos.CENTER_LEFT);

            Text nomMagasin = new Text("Magasin 1");
            nomMagasin.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            VBox.setMargin(nomMagasin, new Insets(5, 0, 0, 0));

            HBox boxNote = new HBox();
            Text note = new Text("4.4");
            note.setStyle("-fx-font-size: 15px;");
            ImageView etoile = new ImageView(new Image("file:./img/star_icon.png"));
            etoile.setFitHeight(16);
            etoile.setPreserveRatio(true);
            boxNote.getChildren().addAll(note, etoile);

            HBox boxTel = new HBox();
            Text tel = new Text("06 64 65 48 05");
            tel.setStyle("-fx-font-size: 15px;");
            ImageView telephone = new ImageView(new Image("file:./img/phone_icon.jpg"));
            telephone.setFitHeight(20);
            telephone.setPreserveRatio(true);
            boxTel.getChildren().addAll(telephone, tel);

            DescriptionMagasin.setAlignment(Pos.TOP_LEFT);
            DescriptionMagasin.setSpacing(17);
            DescriptionMagasin.getChildren().addAll(nomMagasin, boxNote, boxTel);
            PaneMagasin.setCenter(DescriptionMagasin);

            HBox boxMap = new HBox();
            Text adresse = new Text("12 rue de bernard, 27321 Agde, France");
            adresse.setStyle("-fx-font-size: 15px;");
            ImageView map = new ImageView(new Image("file:./img/map_icon.jpg"));
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
        }

        Scene scene = new Scene(root, 1200, 750);
        primaryStage.setTitle("Fenêtre un magasin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}