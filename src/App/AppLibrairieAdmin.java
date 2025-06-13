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

    private ConnexionMySQL connexionMySQL; 

public AppLibrairieAdmin(MagasinBD magasinBD, LivreBD livreBD, ConnexionMySQL connexionMySQL) {
    this.magasinBD = magasinBD;
    this.livreBD = livreBD;
    this.connexionMySQL = connexionMySQL;
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

    System.out.print("Nom > ");
    String nom = scanner.nextLine().strip();

    System.out.print("Prénom > ");
    String prenom = scanner.nextLine().strip();

    System.out.print("ID du magasin > ");
    String idMagStr = scanner.nextLine().strip();

    int idMagasin;
    try {
        idMagasin = Integer.parseInt(idMagStr);
    } catch (NumberFormatException e) {
        System.out.println("⚠️ ID de magasin invalide.");
        return;
    }

    try {
        List<Magasin> magasins = magasinBD.listeDesMagasins();
        Magasin magasinAssocie = magasins.stream()
                .filter(m -> m.getIdMagasin() == idMagasin)
                .findFirst()
                .orElse(null);

        if (magasinAssocie == null) {
            System.out.println("❌ Aucun magasin trouvé avec l'ID fourni.");
            return;
        }

        Vendeur vendeur = new Vendeur(nom, prenom, null, 0, magasinAssocie);
        VendeurBD vendeurBD = new VendeurBD(connexionMySQL);
        vendeurBD.creerVendeur(vendeur);

        System.out.println("✅ Compte vendeur créé avec succès !");
    } catch (SQLException e) {
        System.out.println("❌ Erreur lors de la création du vendeur : " + e.getMessage());
    }
}


    private void gererStocksGlobaux() {
    try {
        List<Magasin> magasins = magasinBD.listeDesMagasins();

        if (magasins.isEmpty()) {
            System.out.println("❌ Aucun magasin trouvé.");
            return;
        }

        // Afficher les magasins
        System.out.println("\n📍 Magasins disponibles :");
        for (Magasin m : magasins) {
            System.out.printf("- ID %d : %s (%s)\n", m.getIdMagasin(), m.getNom(), m.getVille());
        }

        // Demander le magasin ciblé
        System.out.print("\nEntrez l'ID du magasin > ");
        String idMagStr = scanner.nextLine().strip();
        int idMagasin;
        try {
            idMagasin = Integer.parseInt(idMagStr);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ ID magasin invalide.");
            return;
        }

        // Vérifier que le magasin existe
        Magasin magasinChoisi = magasins.stream()
            .filter(m -> m.getIdMagasin() == idMagasin)
            .findFirst()
            .orElse(null);
        if (magasinChoisi == null) {
            System.out.println("❌ Magasin introuvable.");
            return;
        }

        // Obtenir les livres de ce magasin
        List<Livre> livres = livreBD.listeDesLivres(idMagasin);

        if (livres.isEmpty()) {
            System.out.println("📭 Aucun livre dans ce magasin.");
            return;
        }

        System.out.println("\n📚 Livres disponibles dans le magasin " + magasinChoisi.getNom() + " :");
        for (Livre livre : livres) {
            int stock = livreBD.getStockLivreMagasin(livre.getIdLivre(), idMagasin);
            System.out.printf("- %s (ISBN : %d) : %d en stock\n", livre.getNomLivre(), livre.getIdLivre(), stock);
        }

        // Choisir le livre
        System.out.print("\nEntrez l'ISBN du livre à modifier > ");
        String isbnStr = scanner.nextLine().strip();
        long isbn;
        try {
            isbn = Long.parseLong(isbnStr);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ ISBN invalide.");
            return;
        }

        Livre livreChoisi = livres.stream()
            .filter(l -> l.getIdLivre() == isbn)
            .findFirst()
            .orElse(null);
        if (livreChoisi == null) {
            System.out.println("❌ Livre introuvable dans ce magasin.");
            return;
        }

        // Nouvelle quantité
        System.out.print("Entrez la nouvelle quantité en stock > ");
        String qteStr = scanner.nextLine().strip();
        int nouvelleQte;
        try {
            nouvelleQte = Integer.parseInt(qteStr);
            if (nouvelleQte < 0) {
                System.out.println("⚠️ La quantité ne peut pas être négative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Quantité invalide.");
            return;
        }

        // Mise à jour
        livreBD.modifierStock(isbn, idMagasin, nouvelleQte);
        System.out.println("✅ Stock mis à jour avec succès.");

    } catch (SQLException e) {
        System.out.println("❌ Erreur lors de la gestion des stocks : " + e.getMessage());
    }
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
