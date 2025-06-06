
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
                AppLibrairieClient clientMenu = new AppLibrairieClient(magasinBD, LivreBD, connexionMySQL);
                clientMenu.menuClient();
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
                System.err.println("caca");

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
        long isbnLI = Long.parseLong(isbnL);

        System.out.print("titre du livre > ");
        String titreL = System.console().readLine();
        System.out.print("nbpages du livre > ");
        String nbpagesL = System.console().readLine();
        int nbpagesLI = Integer.parseInt(nbpagesL);
        System.out.print("datepubli du livre > ");
        String datepubliL = System.console().readLine();
        System.out.print("prix du livre > ");
        String prixL = System.console().readLine();
        double prixLI = Double.parseDouble(prixL);

        List<String> classification = new ArrayList<>();
        boolean passer = true;
        while (passer) {
            System.out.print("classifications du livre sinon P pour passer> ");
            String commande = lireCommande();
            if (commande.equals("p")) {
                passer = false;
            } else {
                classification.add(commande);

            }
        }

        List<String> auteur = new ArrayList<>();
        passer = true;
        while (passer) {
            System.out.print("auteur du livre sinon P pour passer> ");
            String commande = lireCommande();
            if (commande.equals("p")) {
                passer = false;
            } else {
                auteur.add(commande);

            }
        }

        List<Integer> editeur = new ArrayList<>();
        passer = true;
        while (passer) {
            System.out.print("editeur du livre sinon P pour passer> ");
            String commande = lireCommande();
            if (commande.equals("p")) {
                passer = false;
            } else {
                int number = Integer.parseInt(commande);
                editeur.add(number);

            }
        }
        Livre nouveau = new Livre(isbnLI, titreL, datepubliL, prixLI, nbpagesLI, classification, editeur,auteur);

        try {
            String nb = LivreBD.insererLivre(nouveau);
            System.out.println("cool" + nb);
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