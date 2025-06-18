package IHM;

import BD.ConnexionMySQL;
import BD.MagasinBD;
import BD.LivreBD;
import BD.StatistiqueBD;
import BD.ClientBD;
import IHM.Controleur.ControleurSeConnecter;
import Java.Client;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.sql.SQLException;

public class FenetreConnexion extends Application {
    private ConnexionMySQL connexionMySQL;
    private boolean connexionEtablie;
    private ClientBD clientBD;

    private void initialiserConnexion() {
        try {
            this.connexionMySQL = new ConnexionMySQL();
            this.connexionMySQL.connecter();
            this.clientBD = new ClientBD(this.connexionMySQL);
            this.connexionEtablie = true;
            System.out.println("Connexion à la base de données établie avec succès !");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver MySQL non trouvé !");
            this.connexionEtablie = false;
        } catch (Exception e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
            this.connexionEtablie = false;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        initialiserConnexion();

        BorderPane root = new BorderPane();

        // Barre de sélection des rôles en haut à droite
        HBox boxRoles = new HBox(20);
        boxRoles.setAlignment(Pos.TOP_RIGHT);
        boxRoles.setPadding(new Insets(20, 30, 0, 0));

        ToggleGroup groupeRoles = new ToggleGroup();

        ToggleButton btnClient = new ToggleButton("Client");
        ToggleButton btnVendeur = new ToggleButton("Vendeur");
        ToggleButton btnAdmin = new ToggleButton("Administrateur");

        btnClient.setToggleGroup(groupeRoles);
        btnVendeur.setToggleGroup(groupeRoles);
        btnAdmin.setToggleGroup(groupeRoles);

        // Ajout des icônes aux boutons rôles
        ImageView iconeC = new ImageView(new Image("file:./img/shopping-32.png"));
        iconeC.setFitHeight(30);
        iconeC.setFitWidth(30);
        btnClient.setGraphic(iconeC);
        btnClient.setContentDisplay(ContentDisplay.TOP);

        ImageView iconeV = new ImageView(new Image("file:./img/seller.png"));
        iconeV.setFitHeight(30);
        iconeV.setFitWidth(30);
        btnVendeur.setGraphic(iconeV);
        btnVendeur.setContentDisplay(ContentDisplay.TOP);

        ImageView iconeA = new ImageView(new Image("file:./img/admin.png"));
        iconeA.setFitHeight(30);
        iconeA.setFitWidth(30);
        btnAdmin.setGraphic(iconeA);
        btnAdmin.setContentDisplay(ContentDisplay.TOP);

        boxRoles.getChildren().addAll(btnClient, btnVendeur, btnAdmin);

        // Sélection par défaut sur Client
        btnClient.setSelected(true);
        updateButtonStyles(btnClient, btnClient, btnVendeur, btnAdmin);

        groupeRoles.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                ToggleButton selectedBtn = (ToggleButton) newToggle;
                updateButtonStyles(selectedBtn, btnClient, btnVendeur, btnAdmin);
            }
        });

        // Logo à gauche
        ImageView logo = new ImageView(new Image("file:./img/ChatGPT Image 17 juin 2025, 08_55_03.png"));
        logo.setFitHeight(200);
        logo.setPreserveRatio(true);
        Pane conteneurLogo = new Pane(logo);
        BorderPane.setMargin(conteneurLogo, new Insets(10, 0, 0, 15));
        root.setTop(new BorderPane(null, null, boxRoles, null, conteneurLogo)); // top = logo gauche + rôles droite
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
        userfield.setPromptText("ID Utilisateur");
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

        TextField mdpVisibleField = new TextField();
        mdpVisibleField.setPromptText("Mot de passe");
        mdpVisibleField.setPrefWidth(450);
        mdpVisibleField.setPrefHeight(50);
        mdpVisibleField.setStyle("-fx-background-radius: 18; -fx-border-radius: 18;" +
                "-fx-border-color: black; -fx-border-width: 2;");
        mdpVisibleField.setVisible(false);
        mdpVisibleField.setManaged(false);

        StackPane stackMdp = new StackPane();
        stackMdp.getChildren().addAll(mdpfield, mdpVisibleField);

        grid.add(userIcon, 0, 3);
        grid.add(userfield, 1, 3);
        grid.add(mdpIcon, 0, 6);
        grid.add(stackMdp, 1, 6);

        cadre.getChildren().add(grid);

        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);

        VBox checkbox = new VBox();
        checkbox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(checkbox, new Insets(10, 0, 0, 0));

        CheckBox voirMdp = new CheckBox("Afficher mot de passe");
        voirMdp.setStyle("-fx-text-fill: white;");

        // Gestion affichage / masquage mot de passe
        voirMdp.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                mdpVisibleField.setText(mdpfield.getText());
                mdpVisibleField.setVisible(true);
                mdpVisibleField.setManaged(true);
                mdpfield.setVisible(false);
                mdpfield.setManaged(false);
            } else {
                mdpfield.setText(mdpVisibleField.getText());
                mdpfield.setVisible(true);
                mdpfield.setManaged(true);
                mdpVisibleField.setVisible(false);
                mdpVisibleField.setManaged(false);
            }
        });

        mdpfield.textProperty().addListener((obs, oldText, newText) -> {
            if (!voirMdp.isSelected()) {
                mdpVisibleField.setText(newText);
            }
        });

        mdpVisibleField.textProperty().addListener((obs, oldText, newText) -> {
            if (voirMdp.isSelected()) {
                mdpfield.setText(newText);
            }
        });

        Text texteQuestion = new Text("Vous n’avez pas encore de compte ? ");
        texteQuestion.setStyle("-fx-fill: white;");

        Text texteInscription = new Text("Inscription");
        texteInscription.setStyle("-fx-fill: white; -fx-underline: true;");
        texteInscription.setCursor(Cursor.HAND);

        texteInscription.setOnMouseEntered(e -> texteInscription.setStyle("-fx-fill: orange; -fx-underline: true;"));
        texteInscription.setOnMouseExited(e -> texteInscription.setStyle("-fx-fill: white; -fx-underline: true;"));
        texteInscription.setOnMouseClicked(e -> afficherPopupInscription());

        TextFlow inscriptionFlow = new TextFlow(texteQuestion, texteInscription);
        inscriptionFlow.setStyle("-fx-padding: 10 0 10 0;");

        checkbox.getChildren().addAll(voirMdp, inscriptionFlow);
        VBox.setMargin(voirMdp, new Insets(10, 0, 10, 0));

        Button boutonConnexion = new Button("Se connecter");
        boutonConnexion.setStyle("-fx-background-color: #ff7d0f; -fx-text-fill: white; -fx-font-size: 18px;" +
                "-fx-font-weight: bold; -fx-border-radius: 18; -fx-background-radius: 18;");
        boutonConnexion.setPrefHeight(45);
        boutonConnexion.setPrefWidth(180);

        VBox boxConnection = new VBox();
        boxConnection.setAlignment(Pos.CENTER_RIGHT);
        boxConnection.getChildren().add(boutonConnexion);

        bottomBox.getChildren().addAll(checkbox, boxConnection);
        cadre.getChildren().add(bottomBox);

        root.setCenter(cadre);

        ControleurSeConnecter controleur = new ControleurSeConnecter(connexionMySQL);
        controleur.gererConnexion(boutonConnexion, userfield, mdpfield, groupeRoles);

        Scene scene = new Scene(root, 1200, 750);
        primaryStage.setTitle("Fenêtre connexion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour mettre à jour le style selon la sélection
    private void updateButtonStyles(ToggleButton selected, ToggleButton... allButtons) {
        for (ToggleButton btn : allButtons) {
            if (btn == selected) {
                btn.setStyle("-fx-background-color: #104a8c; -fx-text-fill: white; -fx-font-weight: bold;" +
                        " -fx-border-radius: 20; -fx-background-radius: 20;");
            } else {
                btn.setStyle("-fx-background-color: #dbe9f7; -fx-text-fill: #206db8; -fx-font-weight: normal;" +
                        " -fx-border-radius: 20; -fx-background-radius: 20;");
            }
        }
    }

    private void afficherPopupInscription(){
        Stage popup = new Stage();
        popup.setTitle("Inscription nouveau client");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        nomField.setPrefWidth(300);

        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom");
        nomField.setPrefWidth(300);;

        TextField adresseField = new TextField();
        adresseField.setPromptText("Adresse complète (ex: 12 rue X 75000 Paris)");
        nomField.setPrefWidth(300);

        PasswordField mdpField = new PasswordField();
        mdpField.setPromptText("Mot de passe");
        nomField.setPrefWidth(300);

        Button btnValider = new Button("Créer le compte");
        Label message = new Label();
        message.setStyle("-fx-text-fill: red;");
        nomField.setPrefWidth(300);

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(new Label("Adresse:"), 0, 2);
        grid.add(adresseField, 1, 2);
        grid.add(new Label("Mot de passe:"), 0, 3);
        grid.add(mdpField, 1, 3);
        grid.add(btnValider, 1, 4);
        grid.add(message, 1, 5);

        btnValider.setOnAction(ev -> {
            try{
                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();
                String adresse = adresseField.getText().trim();
                String mdp = mdpField.getText();
                int id = clientBD.maxIdClient() + 1;

                if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || mdp.isEmpty()) {
                    message.setText("Tous les champs doivent être remplis !");
                    return;
                }

                try {
                    Client nouveauClient = new Client(nom, prenom, null, id, adresse, mdp);
                    clientBD.creerClient(nouveauClient);
                    message.setStyle("-fx-text-fill: green;");
                    message.setText("Compte créé avec succès !");
                    message.setText("Compte créé avec succès ! Votre identifiant est : " + nouveauClient.getIdCli());
                    new Thread(() -> {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        popup.close();
                    }).start();

                } catch (SQLException e) {
                    message.setText(e.getMessage());
                }
            }
            catch(SQLException e){
                message.setText("Erreur lors de la création du compte : " + e.getMessage());
            }
        });

        Scene scene = new Scene(grid, 600, 300);
        popup.setScene(scene);
        popup.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
