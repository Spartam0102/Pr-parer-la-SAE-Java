import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppLibrairie {

    private ConnexionMySQL connexionMySQL;
    private MagasinBD magasinBD;
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
                menuAdministrateur();
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuClient() {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Client                  |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| A: Afficher magasins    |");
            System.out.println("| P: Menu précédent       |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else if (commande.equals("a")) {
                afficherMagasins();
            } else if (commande.equals("p")) {
                menu3 = true;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuVendeur() {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Vendeur                 |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| A: Afficher magasins    |");
            System.out.println("| P: Menu précédent       |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else if (commande.equals("a")) {
                afficherMagasins();
            } else if (commande.equals("p")) {
                menu3 = true;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuAdministrateur() {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Administrateur          |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| A: Afficher magasins    |");
            System.out.println("| J: Ajouter magasins     |");
            System.out.println("| P: Menu précédent       |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else if (commande.equals("a")) {
                afficherMagasins();
            } else if (commande.equals("j")) {
                ajouterMagasin();
            } else if (commande.equals("p")) {
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

    private void ajouterMagasin() {


        System.out.print("Nom du magasin > ");
        String nomR = System.console().readLine();
        System.out.print("Ville > ");
        String villeR = System.console().readLine();
        System.out.print("ID > ");
        int idR = Integer.parseInt(System.console().readLine());

        Magasin magasin = new Magasin(nomR, villeR, idR);
        System.out.println("Magasin créé : " + magasin.getNom());

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