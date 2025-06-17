package IHM;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FenetrePanier extends Application {

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        ImageView logo = new ImageView(new Image("file:/home/iut45/Etudiants/o22401144/Documents/SAE JAVA/Pr-parer-la-SAE-Java/img/ChatGPT Image 17 juin 2025, 08_55_03.png"));
        logo.setFitHeight(200); 
        logo.setPreserveRatio(true);
        Pane conteneurLogo = new Pane(logo);
        BorderPane.setMargin(conteneurLogo, new Insets(10, 0, 0, 15));
        root.setTop(conteneurLogo);
        BorderPane.setAlignment(conteneurLogo, Pos.CENTER_LEFT);

        VBox cadre = new VBox();
        BorderPane.setMargin(cadre, new Insets(50, 200, 150, 200));
        cadre.setStyle("-fx-background-color: #206db8; -fx-background-radius: 50;");
        cadre.setPadding(new Insets(20));
        cadre.setAlignment(Pos.TOP_CENTER);

        Text titre = new Text("Connexion");
        titre.setStyle("-fx-font-size: 45px; -fx-font-weight: bold; -fx-fill: white;");
        cadre.getChildren().add(titre);

        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        ImageView userIcon = new ImageView(new Image("file:./img/user_icon.png"));
        userIcon.setFitHeight(45);
        userIcon.setFitWidth(45);
        TextField userfield = new TextField();
        userfield.setPromptText("Nom d’utilisateur");
        userfield.setPrefWidth(450);
        userfield.setPrefHeight(50);
        userfield.setStyle("-fx-background-radius: 18; -fx-border-radius: 18;" + 
                                "-fx-border-color: black; -fx-border-width: 2;");

        ImageView mdpIcon = new ImageView(new Image("file:./img/mdp_icon.png")); 
        mdpIcon.setFitHeight(45);
        mdpIcon.setFitWidth(45);
        PasswordField mdpfield = new PasswordField();
        mdpfield.setPromptText("Mot de passe");
        mdpfield.setPrefWidth(450);
        mdpfield.setPrefHeight(50);
        mdpfield.setStyle("-fx-background-radius: 18; -fx-border-radius: 18;" + 
                                "-fx-border-color: black; -fx-border-width: 2;");

        grid.add(userIcon, 0, 3);
        grid.add(userfield, 1, 3);
        grid.add(mdpIcon, 0, 6);
        grid.add(mdpfield, 1, 6);

        cadre.getChildren().add(grid);

        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);

        VBox checkbox = new VBox();
        checkbox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(checkbox, new Insets(10, 0, 0, 0));
        CheckBox voirMdp = new CheckBox("Afficher mot de passe");
        voirMdp.setStyle("-fx-text-fill: white;");
        Text texteInscritpion = new Text("Vous n’avez pas encore de compte ? Inscription");
        texteInscritpion.setStyle("-fx-fill: white;");
        VBox.setMargin(voirMdp, new Insets(10, 0, 10, 0));
        VBox.setMargin(texteInscritpion, new Insets(10, 0, 0, 0));
        checkbox.getChildren().addAll(voirMdp, texteInscritpion);

        VBox boxConnection = new VBox();
        boxConnection.setAlignment(Pos.CENTER_RIGHT);
        Button boutonConnexion = new Button("Se connecter");
        boutonConnexion.setStyle("-fx-background-color: #ff7d0f; -fx-text-fill: white; -fx-font-size: 18px;" + 
                    "-fx-font-weight: bold; -fx-border-radius: 18; -fx-background-radius: 18;");
        boutonConnexion.setPrefHeight(45);
        
        boutonConnexion.setPrefWidth(180);
        boxConnection.getChildren().add(boutonConnexion);

        bottomBox.getChildren().addAll(checkbox, boutonConnexion);
        cadre.getChildren().add(bottomBox);

        root.setCenter(cadre);

        Scene scene = new Scene(root, 1200, 750);
        primaryStage.setTitle("Fenêtre connexion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}