package App;

import BD.*;
import Java.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AppStatistiques {

    private MagasinBD magasinBD;
    private LivreBD livreBD;
    private StatistiqueBD statistiqueBD;
    private boolean quitterApp = false;
    private Scanner scanner = new Scanner(System.in);

    private ConnexionMySQL connexionMySQL;

    public AppStatistiques(MagasinBD magasinBD, LivreBD livreBD, StatistiqueBD statistiqueBD,
            ConnexionMySQL connexionMySQL) {
        this.magasinBD = magasinBD;
        this.livreBD = livreBD;
        this.connexionMySQL = connexionMySQL;
        this.statistiqueBD = statistiqueBD;
    }

    public void menuStatistiques() {
        boolean menuActif = true;
        int largeurConsole = getLargeurConsole();

        while (menuActif && !quitterApp) {
            clearConsole();

            String[] titre = {
                    "╔════════════════════════════════════════════════════════════════╗",
                    "║                        Statistiques                            ║",
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
                    centrerTexte("║     📊  Nombre de livres venus pour un magasins magasin..............[1] ║",
                            largeurConsole),
                    centrerTexte("║     📊  Chiffre d'affaire par thème en année.........................[2] ║",
                            largeurConsole),
                    centrerTexte("║     📊  chiffre d'affaire par magasin et par mois en une année.......[3] ║",
                            largeurConsole),
                    centrerTexte("║     ↩️   menu précédent.........................................[Q] ║",
                            largeurConsole),
                    centrerTexte("║                                                                        ║",
                            largeurConsole),
                    centrerTexte("╚════════════════════════════════════════════════════════════════════════╝",
                            largeurConsole)
            };

            try {
                machineAEcrireLigneParLigne(menu, 10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.print("\nEntrez votre choix : ");
            String commande = lireCommande();

            switch (commande) {
                case "q":
                    quitterApp = true;
                    menuActif = false;
                    break;
                case "1":
                    premierStat();
                    pause();
                    break;
                case "2":
                    deuxiemeStat();
                    pause();
                    break;
                case "3":
                    troisiemeStat();
                    pause();
                    break;
                default:
                    System.out.println("\n>>> Commande invalide. Veuillez réessayer.");
                    attendre(1500);
                    break;
            }
        }
    }

    private void premierStat() {
        try {
            System.out.print("📝 id du magasin > ");
            String id = scanner.nextLine().strip();
            List<List<String>> tableau = statistiqueBD.premier(id);
            clearConsole();

            System.out.println("\n📋 nombre de livre vendu par année :");
            System.out.println("============================");

            if (tableau.isEmpty()) {
                System.out.println("❌ Aucune donnée trouvée pour ce magasin.");
                return;
            }

            String nomMagasin = tableau.get(0).get(0);
            System.out.println("🏪 Magasin : " + nomMagasin);
            System.out.println();

            System.out.printf("%-6s │ %-10s%n", "Année", "Ventes");
            System.out.println("───────┼───────────");

            for (List<String> ligne : tableau) {
                String annee = ligne.get(1);
                String ventes = ligne.get(2);
                System.out.printf("%-6s │ %-10s%n", annee, ventes);

            }

            System.out.println();

        } catch (SQLException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }

private void deuxiemeStat() {
    try {
        System.out.print("📝 Année > ");
        String annee = scanner.nextLine().strip();
        List<List<String>> tableau = statistiqueBD.deuxieme(annee);
        clearConsole();

        System.out.println("\n📋 Chiffre d'affaire par thème en " + annee + " :");
        System.out.println("============================");

        if (tableau.isEmpty()) {
            System.out.println("❌ Aucune donnée trouvée pour cette année.");
            return;
        }

        System.out.printf("%-15s │ %-15s%n", "Thème", "Montant (€)");
        System.out.println("────────────────┼────────────────");

        for (List<String> ligne : tableau) {
            String theme = ligne.get(0);
            String montant = ligne.get(1);
            System.out.printf("%-15s │ %-15s%n", theme, montant);
        }

        System.out.println();

    } catch (SQLException e) {
        System.out.println("❌ Erreur : " + e.getMessage());
    }
}

private void troisiemeStat() {
    try {
        System.out.print("📝 Année > ");
        String annee = scanner.nextLine().strip();
        List<List<String>> tableau = statistiqueBD.troisieme(annee);
        clearConsole();

        System.out.println("\n📋 Chiffre d'affaire par magasin et par mois en " + annee + " :");
        System.out.println("============================");

        if (tableau.isEmpty()) {
            System.out.println("❌ Aucune donnée trouvée pour cette année.");
            return;
        }

        System.out.printf("%-4s │ %-20s │ %-15s%n", "Mois", "Magasin", "CA (€)");
        System.out.println("─────┼──────────────────────┼────────────────");

        for (List<String> ligne : tableau) {
            String mois = ligne.get(0);
            String magasin = ligne.get(1);
            String ca = ligne.get(2);
            System.out.printf("%-4s │ %-20s │ %-15s%n", mois, magasin, ca);
        }

        System.out.println();

    } catch (SQLException e) {
        System.out.println("❌ Erreur : " + e.getMessage());
    }
}







    // ======================= OUTILS =============================

    private void machineAEcrireLigneParLigne(String[] lignes, int delayMillis) throws InterruptedException {
        for (String ligne : lignes) {
            System.out.println(ligne);
            Thread.sleep(delayMillis);
        }
    }

    private String lireCommande() {
        String input = scanner.nextLine().strip().toLowerCase();
        effacerDerniereLigne();
        return input;
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
            for (int i = 0; i < 50; ++i)
                System.out.println();
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
}
