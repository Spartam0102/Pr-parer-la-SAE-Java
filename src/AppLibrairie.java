
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class AppLibrairie {

    private ConnexionMySQL connexionMySQL;
    private MagasinBD magasinBD;
    private LivreBD LivreBD;
    private Entreprise entreprise;
    private boolean connexionEtablie = false;

    private boolean quitterApp = false;

    public AppLibrairie() {
        // Initialiser la connexion au démarrage
        initialiserConnexion();
    }

    private void initialiserConnexion() {
        try {
            this.connexionMySQL = new ConnexionMySQL();
            this.connexionMySQL.connecter();
            this.magasinBD = new MagasinBD(this.connexionMySQL);
            this.LivreBD = new LivreBD(this.connexionMySQL);
            this.connexionEtablie = true;
            System.out.println("Connexion à la base de données établie avec succès !");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver MySQL non trouvé !");
            this.connexionEtablie = false;
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
            this.connexionEtablie = false;
        }
    }

    public void run() {
        bienvenue();
        while (!quitterApp) {
            menuPrincipal();
        }
        fermerConnexion();
        auRevoir();
    }

    private void fermerConnexion() {
        if (connexionEtablie && connexionMySQL != null) {
            try {
                connexionMySQL.close();
                System.out.println("Connexion fermée.");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    public void menuPrincipal() {
        boolean menuActif = true;
        while (menuActif && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("|  Menu principal         |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| C: Connexion            |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menuActif = false;
            } else if (commande.equals("c")) {
                menuConnexion();
                menuActif = false;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuConnexion() {
        boolean menu2 = false;
        while (!menu2 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("|  Connexion              |");
            System.out.println("+-------------------------+");
            System.out.println("| R: Retour               |");
            System.out.println("| C: Client               |");
            System.out.println("| V: Vendeur              |");
            System.out.println("| A: Administrateur       |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("r")) {
                menu2 = true;
            } else if (commande.equals("c")) {
                menuClient();
            } else if (commande.equals("v")) {
                menuVendeur();
            } else if (commande.equals("a")) {
                AppLibrairieAdmin adminMenu = new AppLibrairieAdmin(magasinBD, LivreBD);
                adminMenu.menuAdministrateur();
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuClient() {
        Client client = new Client("Leclerc", "Marie", "1992-03-12", 201, "10 rue des Lilas, Paris");
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Client                  |");
            System.out.println("+-------------------------+");
            System.out.println("| I: Infos Personelles    |");
            System.out.println("| A: Afficher magasins    |");
            System.out.println("| V: Voir mon panier      |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("i")) {
                System.out.println(client.toString());
            } else if (commande.equals("a")) {
                menuMagasins(client);
            } else if (commande.equals("v")) {
                if (client.getPanier().isEmpty()) {
                    System.out.println("Panier vide");
                } else {
                    for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()) {
                        String livre = coupleLivre.getKey().getNomLivre();
                        int quantite = coupleLivre.getValue();
                        System.out.println(livre + " (" + quantite + ")\n");
                    }
                }
            } else if (commande.equals("m")) {
                menu3 = true;
            } else if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;

            } else if (commande.equals("a")) {
                afficherMagasins();
            } else if (commande.equals("p")) {

            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuMagasins(Client client) {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Magasins                |");
            System.out.println("+-------------------------+");
            for (int i = 0; i < this.entreprise.getListeMagasins().size(); i++) {
                String nomMagasin = this.entreprise.getListeMagasins().get(i).getNom();
                int longueurRestante = 21 - nomMagasin.length();
                for (int y = 0; y < longueurRestante; y++) {
                    nomMagasin += " ";
                }
                int num = i + 1;
                System.out.println("| " + num + ": " + nomMagasin + "|");
            }
            System.out.println("| V: Voir mon panier      |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.matches("[1-7]")) {
                int commandeInt = Integer.parseInt(commande);
                menuUnMagasin(this.entreprise.getListeMagasins().get(commandeInt - 1), client);
            } else if (commande.equals("v")) {
                if (client.getPanier().isEmpty()) {
                    System.out.println("Panier vide");
                } else {
                    for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()) {
                        String livre = coupleLivre.getKey().getNomLivre();
                        int quantite = coupleLivre.getValue();
                        System.out.println(livre + " (" + quantite + ")\n");
                    }
                }
            } else if (commande.equals("m")) {

                menu3 = true;
            } else if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuUnMagasin(Magasin magasin, Client client) {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            String nomMagasin = magasin.getNom();
            int longueurRestante = 24 - nomMagasin.length();
            for (int y = 0; y < longueurRestante; y++) {
                nomMagasin += " ";
            }
            System.out.println("| " + nomMagasin + "|");
            System.out.println("+-------------------------+");
            System.out.println("| I: Infos Magasin        |");
            System.out.println("| S: Voir stock           |");
            System.out.println("| V: Voir mon panier      |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("i")) {
                System.out.println(magasin.toString());
            } else if (commande.equals("s")) {
                Map<Livre, Integer> stock = magasin.getStockLivre();
                menuStock(stock, client);
            } else if (commande.equals("v")) {
                if (client.getPanier().isEmpty()) {
                    System.out.println("Panier vide");
                } else {
                    for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()) {
                        String livre = coupleLivre.getKey().getNomLivre();
                        int quantite = coupleLivre.getValue();
                        System.out.println(livre + " (" + quantite + ")\n");
                    }
                }
            } else if (commande.equals("m")) {
                menu3 = true;
            } else if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuStock(Map<Livre, Integer> stock, Client client) {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Stock                   |");
            System.out.println("+-------------------------+");
            System.out.println("| Pour ajouter un livre à |");
            System.out.println("| votre panier, entrez le |");
            System.out.println("| numéro correspondant au |");
            System.out.println("| livre                   |");
            System.out.println("+-------------------------+");
            int num = 1;
            for (Map.Entry<Livre, Integer> coupleLivre : stock.entrySet()) {
                String livre = coupleLivre.getKey().getNomLivre();
                if (livre.length() >= 17) {
                    livre = livre.substring(0, 17 - 3) + "...";
                }
                int quantite = coupleLivre.getValue();
                int longueurRestante = 17 - livre.length();
                livre += " (" + quantite + ")";
                for (int y = 0; y < longueurRestante; y++) {
                    livre += " ";
                }
                System.out.println("| " + num + ": " + livre + "|");
                num += 1;
            }
            System.out.println("| V: Voir mon panier      |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            int nbLivreDiff = stock.size();

            if (commande.matches("[1-" + nbLivreDiff + "]")) {
                int commandeInt = Integer.parseInt(commande);
                Livre livre = null;
                int quantiteRestante = 0;
                int i = 0;
                int indexRecherche = commandeInt - 1;
                for (Map.Entry<Livre, Integer> couple : stock.entrySet()) {
                    if (i == indexRecherche) {
                        livre = couple.getKey();
                        quantiteRestante = couple.getValue();
                        break;
                    }
                    i++;
                }
                if (client.getPanier().get(livre) != null) {
                    if (quantiteRestante <= client.getPanier().get(livre)) {
                        System.out.println("Stock insuffisant");
                    } else {
                        client.ajouterLivrePanier(livre);
                    }
                } else {
                    client.ajouterLivrePanier(livre);
                }
            } else if (commande.equals("v")) {
                if (client.getPanier().isEmpty()) {
                    System.out.println("Panier vide");
                } else {
                    for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()) {
                        String livre = coupleLivre.getKey().getNomLivre();
                        int quantite = coupleLivre.getValue();
                        System.out.println(livre + " (" + quantite + ")\n");
                    }
                }
            } else if (commande.equals("m")) {
                menu3 = true;
            } else if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    private void afficherMagasins() {

        try {
            List<Magasin> listeMagasins = magasinBD.listeDesMagasins();

            System.out.println("\nListe des magasins :");
            System.out.println("========================");

            if (listeMagasins.isEmpty()) {
                System.out.println("Aucun magasin trouvé dans la base de données.");
            } else {
                for (Magasin magasin : listeMagasins) {
                    System.out.println(magasin.getNom() +
                            " (Ville: " + magasin.getVille() +
                            ", ID: " + magasin.getIdMagasin() + ")");
                }
            }
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des magasins : " + e.getMessage());
        }
    }

    public void menuVendeur() {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Vendeur                 |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| A: Ajouter un livre     |");
            System.out.println("| S: Changer stock livre  |");
            System.out.println("| V: Voir dispo livre     |");
            System.out.println("| C: Passer une commande  |");
            System.out.println("| T: Transférer livre     |");
            System.out.println("| P: Menu précédent       |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else if (commande.equals("a")) {

                ajouterLivre();

            } else if (commande.equals("s")) {

            } else if (commande.equals("v")) {

            } else if (commande.equals("c")) {

            } else if (commande.equals("t")) {

            } else if (commande.equals("p")) {
                menu3 = true;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    private void ajouterLivre() {

        System.out.print("isbin du livre > ");
        String isbnL = System.console().readLine();

        try {
            ResultSet nb = LivreBD.VerificationIsbn(isbnL);
            System.out.println("cool"+nb);
        } catch (SQLException ex) {
            System.out.println("erreur");

        }
    }

    public void bienvenue() {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Bienvenue ! En week-end comme dans la semaine, les bons comptes font les bons amis.│");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    public void auRevoir() {
        System.out.println("╭─────────────────────────────────────────────────╮");
        System.out.println("│ Au revoir, bonne route et bonne continuation !  │");
        System.out.println("╰─────────────────────────────────────────────────╯");
    }

    private String lireCommande() {
        System.out.print("Commande > ");
        String mess = System.console().readLine().strip().toLowerCase();
        return mess;
    }
}