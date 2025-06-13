package App;

import BD.*;
import Java.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AppLibrairieClient {

    private MagasinBD magasinBD;
    private ClientBD clientBD;
    private boolean quitterApp = false;
    private Scanner scanner = new Scanner(System.in);

    public AppLibrairieClient(MagasinBD magasinBD, LivreBD livreBD, ConnexionMySQL connexionMySQL) {
        this.magasinBD = magasinBD;
        this.clientBD = new ClientBD(connexionMySQL);
    }

    public Client menuClientConnexion() {
        clearConsole();
        int largeur = getLargeurConsole();
        String[] titre = {
            "╔════════════════════════════════════════════════════════════════╗",
            "║                          CONNEXION CLIENT                      ║",
            "╚════════════════════════════════════════════════════════════════╝",
            ""
        };

        for (int i = 0; i < titre.length; i++) {
            titre[i] = centrerTexte(titre[i], largeur);
        }

        try {
            machineAEcrireLigneParLigne(titre, 100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.print(centrerTexte("Votre id > ", largeur));
        try {
            String idStr = System.console() != null ? System.console().readLine() : scanner.nextLine();
            int id = Integer.parseInt(idStr);
            Client client = clientBD.recupererClient(id);
            if (client == null) {
                System.out.println(centrerTexte("Cet id n'existe pas.", largeur));
                return null;
            }
            return client;
        } catch (NumberFormatException e) {
            System.out.println(centrerTexte("Veuillez rentrer un entier.", largeur));
            return null;
        } catch (SQLException e) {
            System.out.println(centrerTexte("Erreur lors de la récupération du client : " + e.getMessage(), largeur));
            return null;
        }
    }

    public void menuMagasins(Client client) {
    boolean menuActif = true;
    int largeurConsole = getLargeurConsole();

    while (menuActif && !quitterApp) {
        clearConsole();

        String[] titre = {
            "╔════════════════════════════════════════════════════════════════╗",
            "║                          MAGASINS                              ║",
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

        try {
            List<Magasin> listeMagasins = magasinBD.listeDesMagasins();
            for (int i = 0; i < listeMagasins.size(); i++) {
                String nom = listeMagasins.get(i).getNom();
                if (nom.length() > 50) nom = nom.substring(0, 47) + "...";
                String ligne = String.format("║     %d : %s", i + 1, nom);
                // Compléter la ligne pour que la bordure droite soit alignée à 64 (80-16 espaces d'indentation), par exemple
                int esp = largeurConsole - ligne.length() - 1;
                ligne += " ".repeat(Math.max(0, esp)) + "║";
                System.out.println(centrerTexte(ligne, largeurConsole));
            }

            String[] menuBas = {
                "",
                "║     ↩️  Retour .................................................[M] ║",
                "║     🛒  Voir mon panier .........................................[P] ║",
                "║     ❌  Quitter ................................................[Q] ║",
                "╚════════════════════════════════════════════════════════════════╝"
            };
            for (String ligne : menuBas) {
                System.out.println(centrerTexte(ligne, largeurConsole));
            }

            System.out.print("\n" + centrerTexte("Entrez votre choix : ", largeurConsole));
            String cmd = lireCommande().toLowerCase();

            if (cmd.matches("[1-" + listeMagasins.size() + "]")) {
                int choix = Integer.parseInt(cmd);
                menuUnMagasin(listeMagasins.get(choix - 1), client);
            } else {
                switch (cmd) {
                    case "m" -> menuActif = false;
                    case "p" -> afficherPanier(client);
                    case "q" -> {
                        quitterApp = true;
                        menuActif = false;
                    }
                    default -> {
                        System.out.println("\n>>> Commande invalide.");
                        attendre(1500);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(centrerTexte("Erreur lors de la récupération des magasins : " + e.getMessage(), largeurConsole));
            pause();
        }
    }
}

    private void menuUnMagasin(Magasin magasin, Client client) {
    boolean menuActif = true;
    int largeurConsole = getLargeurConsole();

    while (menuActif && !quitterApp) {
        clearConsole();

        String nom = magasin.getNom();
        if (nom.length() > 50) nom = nom.substring(0, 47) + "...";

        String[] titre = {
            "╔════════════════════════════════════════════════════════════════╗",
            ("║  " + nom).substring(0, Math.min(nom.length() + 4, 64)) + " ".repeat(64 - nom.length() - 4) + "║",
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

        System.out.println(centrerTexte("╔════════════════════════════════════════════════════════════════════════╗", largeurConsole));
        System.out.println(centrerTexte("║     🏪  Infos Magasin.............................................[I] ║", largeurConsole));
        System.out.println(centrerTexte("║     📦  Voir stock................................................[S] ║", largeurConsole));
        System.out.println(centrerTexte("║     ↩️  Menu précédent...........................................[M] ║", largeurConsole));
        System.out.println(centrerTexte("║     ❌  Quitter...................................................[Q] ║", largeurConsole));
        System.out.println(centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole));
        System.out.print("\n" + centrerTexte("Entrez votre choix : ", largeurConsole));
        String cmd = lireCommande().toLowerCase();

        switch (cmd) {
            case "i" -> {
                System.out.println(centrerTexte(magasin.toString(), largeurConsole));
                pause();
            }
            case "s" -> {
                try {
                    Map<Livre, Integer> stock = magasinBD.listeLivreUnMagasin(magasin.getIdMagasin());
                    afficherStock(stock);
                } catch (SQLException e) {
                    System.out.println(centrerTexte("Erreur lors de la récupération du stock : " + e.getMessage(), largeurConsole));
                    pause();
                }
            }
            case "m" -> menuActif = false;
            case "q" -> {
                quitterApp = true;
                menuActif = false;
            }
            default -> {
                System.out.println("\n>>> Commande invalide.");
                attendre(1500);
            }
        }
    }
}
    
    private void afficherStock(Map<Livre, Integer> stock) {
        clearConsole();
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║           Stock              ║");
        System.out.println("╠══════════════════════════════╣");

        if (stock.isEmpty()) {
            System.out.println("║ (vide)                       ║");
        } else {
            for (Map.Entry<Livre, Integer> entry : stock.entrySet()) {
                String nom = entry.getKey().getNomLivre();
                int quantite = entry.getValue();
                if (nom.length() > 20) nom = nom.substring(0, 17) + "...";
                System.out.printf("║ %-20s (%2d)     ║\n", nom, quantite);
            }
        }
        System.out.println("╚══════════════════════════════╝");
        pause();
    }


public void afficherPanier(Client client) {
    clearConsole();
    int largeurConsole = getLargeurConsole();

    System.out.println(centrerTexte("╔════════════════════════════════════════════════════════════════╗", largeurConsole));
    System.out.println(centrerTexte("║                          PANIER CLIENT                        ║", largeurConsole));
    System.out.println(centrerTexte("╚════════════════════════════════════════════════════════════════╝", largeurConsole));

    Map<Livre, Integer> panier = client.getPanier();
    if (panier.isEmpty()) {
        System.out.println(centrerTexte("Le panier est vide.", largeurConsole));
    } else {
        for (Map.Entry<Livre, Integer> entry : panier.entrySet()) {
            String ligne = entry.getKey().getNomLivre() + " (" + entry.getValue() + ")";
            System.out.println(centrerTexte(ligne, largeurConsole));
        }
    }
    pause();
}


    
    
    public void menuClient() {
        Client client = null;
        while (client == null) {
            client = menuClientConnexion();
        }

        boolean menuActif = true;
        int largeurConsole = getLargeurConsole();

        while (menuActif && !quitterApp) {
            clearConsole();

            String[] titre = {
                "╔════════════════════════════════════════════════════════════════╗",
                "║                          MENU CLIENT                           ║",
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
                centrerTexte("╔════════════════════════════════════════════════════════════════════════╗", largeurConsole),
                centrerTexte("║                                                                        ║", largeurConsole),
                centrerTexte(String.format("║     🧑  Infos personnelles............................................[1] ║"), largeurConsole),
                centrerTexte(String.format("║     🏬  Afficher magasins.............................................[2] ║"), largeurConsole),
                centrerTexte(String.format("║     🛒  Commander...............................................[3] ║"), largeurConsole),
                centrerTexte(String.format("║     🛒  Voir mon panier...............................................[4] ║"), largeurConsole),
                centrerTexte(String.format("║     ↩️  Retour........................................................[M] ║"), largeurConsole),
                centrerTexte(String.format("║     ❌  Quitter.......................................................[Q] ║"), largeurConsole),
                centrerTexte("║                                                                        ║", largeurConsole),
                centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole)
            };

            for (String ligne : menu) {
                System.out.println(ligne);
            }

            System.out.print("\n" + centrerTexte("Entrez votre choix : ", largeurConsole));
            String cmd = lireCommande();

            switch (cmd) {
                case "1" -> {
                    System.out.println(client.toString());
                    pause();
                }
                case "2" -> menuMagasins(client);
                case "3" -> afficherPanier(client);
                case "m" -> menuActif = false;
                case "q" -> {
                    quitterApp = true;
                    menuActif = false;
                }
                default -> {
                    System.out.println("\n>>> Commande invalide.");
                    attendre(1500);
                }
            }
        }
    }

    // Les autres méthodes restent inchangées pour l'instant (menuMagasins, menuUnMagasin, etc.)

    private String centrerTexte(String texte, int largeurConsole) {
        int espacement = Math.max(0, (largeurConsole - texte.length()) / 2);
        return " ".repeat(espacement) + texte;
    }

    private int getLargeurConsole() {
        return 80; // Valeur fixe par défaut, peut être ajustée
    }

    private void machineAEcrireLigneParLigne(String[] lignes, int delai) throws InterruptedException {
        for (String ligne : lignes) {
            System.out.println(ligne);
            Thread.sleep(delai);
        }
    }

    // ... conserver les autres méthodes (menuMagasins, menuUnMagasin, menuStock, afficherPanier, etc.)
    // Elles peuvent aussi être stylisées si tu veux que je continue

    private String lireCommande() {
        String input = scanner.nextLine().strip().toLowerCase();
        effacerDerniereLigne();
        return input;
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
            for (int i = 0; i < 50; ++i) System.out.println();
        }
    }

    private void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void attendre(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
