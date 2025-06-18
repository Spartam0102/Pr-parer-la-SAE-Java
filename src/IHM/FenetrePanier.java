package IHM;

import Java.*;
import BD.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import IHM.Controleur.ControleurHome;
import javafx.scene.control.Separator;

import java.sql.SQLException;
import java.util.Map;

public class FenetrePanier extends Application {

    private BorderPane racine;
    private VBox panelCentral;
    private Map<Livre, Integer> panierClient;
    private ClientBD clientBD;

    public FenetrePanier(ConnexionMySQL connexionMySQL, Client client) {
        this.clientBD = new ClientBD(connexionMySQL);
        try{
            client.setPanier(clientBD.recupererPanier(client.getIdCli()));
        }
        catch(SQLException e){
            System.out.println("Erreur sql : " + e.getMessage());
        }
        this.panierClient = client.getPanier();
    }

    private Scene laScene() {
        this.racine = new BorderPane();
        racine.setTop(this.titre());
        this.panelCentral = fenetrePanier();
        racine.setCenter(this.panelCentral);
        Scene scene = new Scene(racine, 1500, 750);
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

        for (ImageView iv : new ImageView[] { homeView, settingsView, panierView, retourView }) {
            iv.setFitHeight(30);
            iv.setFitWidth(30);
        }

        Button boutonHome = new Button("", homeView);
        Button boutonSettings = new Button("", settingsView);
        Button boutonPanier = new Button("", panierView);
        Button boutonRetour = new Button("", retourView);

        String styleBouton = "-fx-background-color: #206db8;-fx-border-radius: 18; -fx-background-radius: 18;";
        boutonHome.setStyle(styleBouton);
        boutonSettings.setStyle(styleBouton);
        boutonPanier.setStyle(styleBouton);
        boutonRetour.setStyle(styleBouton);

        boutonHome.setOnAction(e -> {
            Stage stage = (Stage) boutonHome.getScene().getWindow();
            ControleurHome.allerAccueil(stage);
        });

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

   private VBox fenetrePanier() {
    VBox containerVertical = new VBox();
    containerVertical.setPadding(new Insets(20));
    containerVertical.setSpacing(10);

    HBox conteneur = new HBox(20);
    conteneur.setPadding(new Insets(20));
    conteneur.setStyle("-fx-background-color: #2073c4;");
    HBox.setHgrow(conteneur, Priority.ALWAYS);

    VBox listeLivres = new VBox(10);
    listeLivres.setPadding(new Insets(10));
    listeLivres.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
    listeLivres.setPrefWidth(400);

    ScrollPane scrollPane = new ScrollPane(listeLivres);
    scrollPane.setFitToWidth(true);
    scrollPane.setStyle("-fx-background: transparent;");
    scrollPane.prefHeightProperty().bind(racine.heightProperty().multiply(0.7));
    scrollPane.maxHeightProperty().bind(racine.heightProperty().multiply(0.7));
    HBox.setHgrow(scrollPane, Priority.ALWAYS);

    VBox ensembleLivresCommand = new VBox();
    ensembleLivresCommand.setStyle("-fx-background-color: white;");
    
    // Image placeholder locale
    Image placeholder = new Image("file:img/placeholder.png", 100, 140, true, true);

    for (Map.Entry<Livre, Integer> couple : this.panierClient.entrySet()) {
        VBox unLivreCommand = new VBox();
        unLivreCommand.setPadding(new Insets(20));

        long isbn = couple.getKey().getIdLivre();
        ImageView imageView = new ImageView(placeholder); // placeholder immédiat

        try {
            String url = "https://covers.openlibrary.org/b/isbn/" + isbn + "-M.jpg";
            Image imageLivre = new Image(url, 100, 140, true, true, true); // chargement en arrière-plan

            // Quand l'image est chargée, on remplace le placeholder
            imageLivre.progressProperty().addListener((obs, oldProgress, newProgress) -> {
                if (newProgress.doubleValue() >= 1.0) {
                    imageView.setImage(imageLivre);
                }
            });

        } catch (Exception e) {
            // Si erreur, garder le placeholder
        }

        // Texte titre + quantité dans un VBox
        Text nomLivre = new Text(couple.getKey().getNomLivre());
        nomLivre.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        Text quantite = new Text("     x" + couple.getValue());
        quantite.setStyle("-fx-font-size: 15px");

        VBox texteVBox = new VBox(nomLivre, quantite);
        texteVBox.setAlignment(Pos.CENTER_LEFT);
        texteVBox.setSpacing(5);

        HBox ligneLivre = new HBox(10, imageView, texteVBox);
        ligneLivre.setAlignment(Pos.CENTER_LEFT);

        Text nomAuteur = new Text("Claude Dubois");
        Text prixText = new Text(String.format("%.2f €", couple.getKey().getPrix()));
        HBox prixBox = new HBox(prixText);
        prixText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        prixBox.setAlignment(Pos.CENTER_RIGHT);

        Separator barre = new Separator();
        barre.setPrefHeight(1);
        barre.setOpacity(0.5);

        ensembleLivresCommand.getChildren().add(barre);

        unLivreCommand.getChildren().addAll(ligneLivre, nomAuteur, prixBox);
        ensembleLivresCommand.getChildren().add(unLivreCommand);
    }

    scrollPane.setContent(ensembleLivresCommand);

        VBox recap = new VBox(20);
        recap.setPrefWidth(300);
        recap.setMaxHeight(Double.MAX_VALUE);
        recap.setPadding(new Insets(15));
        recap.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        int nbProduits = panierClient != null ? panierClient.values().stream().mapToInt(Integer::intValue).sum() : 0;
        double totalPrix = 0;
        if (panierClient != null) {
            for (Map.Entry<Livre, Integer> e : panierClient.entrySet()) {
                totalPrix += e.getKey().getPrix() * e.getValue();
            }
        }

        Label nbProduitsLabel = new Label("Nombre de produit(s) : " + nbProduits);
        Label livraisonLabel = new Label("Livraison : Domicile");
        Label totalLabel = new Label(String.format("Total : %.2f €", totalPrix));
        totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Button commander = new Button("Commander");
        commander.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white; -fx-font-weight: bold;");

        recap.getChildren().addAll(nbProduitsLabel, livraisonLabel, totalLabel, commander);

        VBox modeLivraison = new VBox(10);
        modeLivraison.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        modeLivraison.setPadding(new Insets(15));
        Label modeLabel = new Label("Mode de livraison");
        Button domicile = new Button("Domicile");
        Button magasin = new Button("Magasin");

        domicile.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white;");
        magasin.setStyle("-fx-background-color: #fbbd8d; -fx-text-fill: black;");

        domicile.setOnAction(e -> {
            domicile.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white;");
            magasin.setStyle("-fx-background-color: #fbbd8d; -fx-text-fill: black;");
            livraisonLabel.setText("Livraison : Domicile");
        });

        magasin.setOnAction(e -> {
            magasin.setStyle("-fx-background-color: #ff6600; -fx-text-fill: white;");
            domicile.setStyle("-fx-background-color: #fbbd8d; -fx-text-fill: black;");
            livraisonLabel.setText("Livraison : Magasin");
        });

        HBox boutonsLivraison = new HBox(10, domicile, magasin);
        boutonsLivraison.setAlignment(Pos.CENTER);
        modeLivraison.getChildren().addAll(modeLabel, boutonsLivraison);
        modeLivraison.setAlignment(Pos.CENTER);

        VBox recapWrapper = new VBox(20, recap, modeLivraison);
        recapWrapper.setPrefWidth(300);
        HBox.setHgrow(recapWrapper, Priority.ALWAYS);

        recapWrapper.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPane.prefHeightProperty().unbind(); //
            scrollPane.setPrefHeight(newVal.doubleValue());
        });

        conteneur.getChildren().addAll(scrollPane, recapWrapper);
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