package IHM;

import IHM.Controleur.ControleurHome;

import java.sql.SQLException;
import java.util.Map;

import BD.*;
import Java.*;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class FenetreStock extends Application {

    private Button boutonHome;
    private Button boutonSettings;
    private Button boutonPanier;
    private Button boutonRetour;
    private MagasinBD magasinBD;
    private Magasin magasin;

    public FenetreStock(ConnexionMySQL connexionMySQL, Magasin magasin){
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

   @Override
    public void start(Stage primaryStage) throws SQLException {
        BorderPane root = new BorderPane();

        Pane banniere = titre();
        root.setTop(banniere);

        VBox cadreGrand = new VBox();
        cadreGrand.setStyle(" -fx-background-color: #206db8;");
        cadreGrand.setSpacing(20);
        cadreGrand.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(cadreGrand);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        root.setCenter(scrollPane);

        GridPane grilleLivres = new GridPane();
        grilleLivres.setStyle("-fx-background-color: white; -fx-background-radius: 20px 20px 0 0; -fx-border-radius: 20px 20px 0 0;");
        cadreGrand.setPadding(new Insets(70, 70, 0, 70));
        grilleLivres.setHgap(30);
        grilleLivres.setVgap(30);
        grilleLivres.setAlignment(Pos.CENTER);
        cadreGrand.getChildren().add(grilleLivres);
        grilleLivres.setPadding(new Insets(20));

        Text titreMag = new Text(magasin.getNom());
        titreMag.setStyle("-fx-font-size: 35px; -fx-font-weight: bold;");
        titreMag.setWrappingWidth(400);
        grilleLivres.add(titreMag, 1, 0);
        GridPane.setHalignment(titreMag, HPos.CENTER);

        Map<Livre, Integer> listeLivres = magasinBD.listeLivreUnMagasin(this.magasin.getIdMagasin());

        int i = 3;
        for (Map.Entry<Livre, Integer> entry : listeLivres.entrySet()) {
            Livre livre = entry.getKey();
            Integer quantite = entry.getValue();

            VBox carte = new VBox(10);
            carte.setPrefWidth(280);
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

            int col = i % 3;
            int row = i / 3;
            grilleLivres.add(carte, col, row);
            i++;
        }
        Scene scene = new Scene(root, 1500, 750);
        primaryStage.setTitle("Fenêtre Magasin Client");
        primaryStage.setScene(scene);
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
