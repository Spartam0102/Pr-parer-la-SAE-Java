package App;

import BD.*;
import Java.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AppLibrairieAdmin {

    private MagasinBD magasinBD;
    private LivreBD livreBD;
    private boolean quitterApp = false;
    private Scanner scanner = new Scanner(System.in);

    public AppLibrairieAdmin(MagasinBD magasinBD, LivreBD livreBD) {
        this.magasinBD = magasinBD;
        this.livreBD = livreBD;
    }

    public void menuAdministrateur() {
        boolean menuActif = true;
        int largeurConsole = getLargeurConsole();

        while (menuActif && !quitterApp) {
            clearConsole();

            String[] titre = {
                "╔════════════════════════════════════════════════════════════════╗",
                "║                        ADMINISTRATEUR                         ║",
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
                centrerTexte("║     🏪  Afficher les magasins........................................[A] ║", largeurConsole),
                centrerTexte("║     ➕  Ajouter un magasin............................................[J] ║", largeurConsole),
                centrerTexte("║     ❌  Supprimer un magasin.........................................[U] ║", largeurConsole),
                centrerTexte("║     👤  Créer un compte vendeur......................................[C] ║", largeurConsole),
                centrerTexte("║     📦  Gérer les stocks globaux.....................................[G] ║", largeurConsole),
                centrerTexte("║     📊  Consulter les statistiques de vente..........................[S] ║", largeurConsole),
                centrerTexte("║     ↩️  Retour au menu précédent......................................[P] ║", largeurConsole),
                centrerTexte("║     ❎  Quitter l'application.........................................[Q] ║", largeurConsole),
                centrerTexte("║                                                                        ║", largeurConsole),
                centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole)
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
                case "a":
                    afficherMagasins();
                    pause();
                    break;
                case "j":
                    ajouterMagasin();
                    pause();
                    break;
                case "u":
                    supprimerMagasin();
                    pause();
                    break;
                case "c":
                    creerCompteVendeur();
                    pause();
                    break;
                case "g":
                    gererStocksGlobaux();
                    pause();
                    break;
                case "s":
                    consulterStatistiques();
                    pause();
                    break;
                case "p":
                    menuActif = false;
                    break;
                default:
                    System.out.println("\n>>> Commande invalide. Veuillez réessayer.");
                    attendre(1500);
                    break;
            }
        }
    }

    private void afficherMagasins() {
        try {
            List<Magasin> listeMagasins = magasinBD.listeDesMagasins();

            System.out.println("\n📋 Liste des magasins :");
            System.out.println("==========================");

            if (listeMagasins.isEmpty()) {
                System.out.println("Aucun magasin trouvé.");
            } else {
                for (Magasin magasin : listeMagasins) {
                    System.out.println("- " + magasin.getNom() +
                            " (Ville : " + magasin.getVille() +
                            ", ID : " + magasin.getIdMagasin() + ")");
                }
            }
            System.out.println();

        } catch (SQLException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }

    private void ajouterMagasin() {
        System.out.print("📝 Nom du magasin > ");
        String nom = scanner.nextLine().strip();

        System.out.print("📍 Ville du magasin > ");
        String ville = scanner.nextLine().strip();

        Magasin nouveau = new Magasin(nom, ville, 1);
        try {
            int id = magasinBD.insererMagasin(nouveau);
            System.out.println("✅ Magasin ajouté avec l'ID : " + id);
        } catch (SQLException ex) {
            System.out.println("❌ Erreur lors de l'insertion : " + ex.getMessage());
        }
    }

    private void supprimerMagasin() {
        System.out.print("🗑️ ID du magasin à supprimer > ");
        String idStr = scanner.nextLine().strip();

        try {
            int id = Integer.parseInt(idStr);
            magasinBD.effacerMagasin(id);
            System.out.println("✅ Magasin supprimé.");
        } catch (NumberFormatException e) {
            System.out.println("⚠️ ID invalide.");
        } catch (SQLException ex) {
            System.out.println("❌ Erreur : " + ex.getMessage());
        }
    }

    private void creerCompteVendeur() {
        System.out.println("🧑‍💼 Création d'un compte vendeur...");
        System.out.print("Nom d'utilisateur > ");
        String nom = scanner.nextLine().strip();

        System.out.print("Mot de passe > ");
        String mdp = scanner.nextLine().strip();

        // TODO: enregistrement réel en base
        System.out.println("✅ Compte vendeur \"" + nom + "\" créé avec succès. (simulation)");
    }

    private void gererStocksGlobaux() {
        System.out.println("📦 Gestion des stocks globaux (à implémenter)");
        // TODO: Implémenter affichage + modification stock pour chaque magasin/livre
    }

    private void consulterStatistiques() {
        System.out.println("📊 Consultation des statistiques de vente (à implémenter)");
        // TODO: Afficher top livres, nombre de ventes, CA par magasin, etc.
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
            for (int i = 0; i < 50; ++i) System.out.println();
        }
    }

    private String centrerTexte(String texte, int largeurTotale) {
        if (texte.length() >= largeurTotale) return texte;
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
