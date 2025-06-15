package App;

import BD.*;

import java.sql.SQLException;
import java.util.Scanner;

public class AppLibrairie {

    private ConnexionMySQL connexionMySQL;
    private MagasinBD magasinBD;
    private LivreBD livreBD;
    private StatistiqueBD statistiqueBD;

    private boolean connexionEtablie = false;

    private boolean quitterApp = false;
    private Scanner scanner = new Scanner(System.in);

    public AppLibrairie() {
        initialiserConnexion();
    }

    private void initialiserConnexion() {
        try {
            this.connexionMySQL = new ConnexionMySQL();
            this.connexionMySQL.connecter();
            this.magasinBD = new MagasinBD(this.connexionMySQL);
            this.livreBD = new LivreBD(this.connexionMySQL);
            this.statistiqueBD = new StatistiqueBD(this.connexionMySQL);
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

    public void run() throws NumberFormatException, SQLException {
        bienvenue();
        while (!quitterApp) {
            menuPrincipal();
        }
        fermerConnexion();
        auRevoir();
    }

    private void effacerDerniereLigne() {
        System.out.print("\033[1A");
        System.out.print("\033[2K");
    }

    private void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // fallback
            for (int i = 0; i < 50; ++i)
                System.out.println();
        }
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

    public void menuPrincipal() throws NumberFormatException, SQLException {
        boolean menuActif = true;
        while (menuActif && !quitterApp) {
            clearConsole();

            String[] titreAnime = {
                    "  _      _                  ______                              ",
                    " | |    (_)                |  ____|                             ",
                    " | |     ___   ___ __ ___  | |__  __  ___ __  _ __ ___  ___ ___ ",
                    " | |    | \\ \\ / / '__/ _ \\ |  __| \\ \\/ / '_ \\| '__/ _ \\/ __/ __|",
                    " | |____| |\\ V /| | |  __/ | |____ >  <| |_) | | |  __/\\__ \\__ \\",
                    " |______|_| \\_/ |_|  \\___| |______/_/\\_\\ .__/|_|  \\___||___/___/",
                    "                                       | |                      ",
                    "                                       |_|                      "
            };

            int largeurConsole = getLargeurConsole();

            for (int i = 0; i < titreAnime.length; i++) {
                titreAnime[i] = centrerTexte(titreAnime[i], largeurConsole);
            }

            try {
                machineAEcrireLigneParLigne(titreAnime, 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            String[] menuAnime = {
                    centrerTexte("╔════════════════════════════════════════════════════════════════════════════════╗",
                            largeurConsole),
                    centrerTexte("║                                                                                ║",
                            largeurConsole),
                    centrerTexte("║                              MENU PRINCIPAL                                    ║",
                            largeurConsole),
                    centrerTexte("║                                                                                ║",
                            largeurConsole),
                    centrerTexte("║    🔐  Connexion au système..............................................[C]   ║",
                            largeurConsole),
                    centrerTexte("║    ❌  Quitter l'application.............................................[Q]   ║",
                            largeurConsole),
                    centrerTexte("║                                                                                ║",
                            largeurConsole),
                    centrerTexte("╚════════════════════════════════════════════════════════════════════════════════╝",
                            largeurConsole)
            };

            try {
                machineAEcrireLigneParLigne(menuAnime, 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.print("\nEntrez votre choix : ");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menuActif = false;
            } else if (commande.equals("c")) {
                menuConnexion();
                menuActif = false;
            } else {
                System.out.println("\n>>> Commande invalide. Veuillez réessayer.");
                attendre(1500);
            }
        }
    }

    private String centrerTexte(String texte, int largeurTotale) {
        if (texte.length() >= largeurTotale)
            return texte;
        int espaces = (largeurTotale - texte.length()) / 2;
        return " ".repeat(espaces) + texte;
    }

    private int getLargeurConsole() {
        try {
            ProcessBuilder pb = new ProcessBuilder("sh", "-c", "tput cols");
            Process p = pb.start();
            p.waitFor();
            try (java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.InputStreamReader(p.getInputStream()))) {
                String cols = br.readLine();
                return Integer.parseInt(cols);
            }
        } catch (Exception e) {

            return 80;
        }
    }

    public void menuConnexion() throws NumberFormatException, SQLException {
        boolean menuActif = true;
        int largeurConsole = getLargeurConsole();

        while (menuActif && !quitterApp) {
            clearConsole();

            String[] titre = {
                    "╔════════════════════════════════════════════════════════════════╗",
                    "║                            CONNEXION                           ║",
                    "╚════════════════════════════════════════════════════════════════╝",
                    ""
            };

            for (int i = 0; i < titre.length; i++) {
                titre[i] = centrerTexte(titre[i], largeurConsole);
            }

            try {
                machineAEcrireLigneParLigne(titre, 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            String[] menu = {
                    centrerTexte("╔════════════════════════════════════════════════════════════════════════╗",
                            largeurConsole),
                    centrerTexte("║                                                                        ║",
                            largeurConsole),
                    centrerTexte("║          Client....................................................[C] ║",
                            largeurConsole),
                    centrerTexte("║          Vendeur...................................................[V] ║",
                            largeurConsole),
                    centrerTexte("║         Administrateur............................................[A] ║",
                            largeurConsole),
                    centrerTexte("║         Retour....................................................[R] ║",
                            largeurConsole),
                    centrerTexte("║                                                                        ║",
                            largeurConsole),
                    centrerTexte("╚════════════════════════════════════════════════════════════════════════╝",
                            largeurConsole)
            };

            try {
                machineAEcrireLigneParLigne(menu, 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.print("\nEntrez votre choix : ");

            String commande = lireCommande();

            switch (commande) {
                case "r":
                    menuActif = false;
                    break;
                case "c":
                    AppLibrairieClient clientMenu = new AppLibrairieClient(magasinBD, livreBD, connexionMySQL);
                    clientMenu.menuClient();
                    break;
                case "v":
                    AppLibrairieVendeur vendeurMenu = new AppLibrairieVendeur(magasinBD, connexionMySQL);
                    vendeurMenu.menuVendeur();
                    break;
                case "a":
                    AppLibrairieAdmin adminMenu = new AppLibrairieAdmin(magasinBD, livreBD, statistiqueBD,
                            connexionMySQL);

                    adminMenu.menuAdministrateur();
                    break;
                default:
                    System.out.println("\n>>> Commande invalide. Veuillez réessayer.");
                    attendre(1500);
            }
        }
    }

    private void machineAEcrireLigneParLigne(String[] lignes, int delayMillis) throws InterruptedException {
        for (String ligne : lignes) {
            System.out.println(ligne);
            Thread.sleep(delayMillis);
        }
    }

    public void bienvenue() {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│                    Bienvenue sur le site de Livre Express                          │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    public void auRevoir() {
        clearConsole();
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│                                 Bonne journée                                      │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    private String lireCommande() {
        String mess = scanner.nextLine();
        if (mess == null)
            mess = "";
        mess = mess.strip().toLowerCase();
        effacerDerniereLigne();
        return mess;
    }

    private void attendre(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}