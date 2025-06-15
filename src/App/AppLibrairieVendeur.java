package App;

import BD.*;
import Java.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AppLibrairieVendeur {

    private MagasinBD magasinBD;
    private ClientBD clientBD;
    private VendeurBD vendeurBD;
    private boolean quitterApp = false;
    private Scanner scanner = new Scanner(System.in);

    public AppLibrairieVendeur(MagasinBD magasinBD, ConnexionMySQL connexionMySQL) {
        this.magasinBD = magasinBD;
        this.vendeurBD = new VendeurBD(connexionMySQL);
        this.clientBD = new ClientBD(connexionMySQL);
    }

    public Vendeur menuvendeurConnexion() {
        System.out.println("\nConnexion :");
        System.out.print("Votre id > ");
        try {
            String idStr = System.console() != null ? System.console().readLine() : scanner.nextLine();
            int id = Integer.parseInt(idStr);
            Vendeur vendeur = vendeurBD.recupererVendeur(id);
            if (vendeur == null) {
                System.out.println("Cet id n'existe pas.");
                return null;
            }
            return vendeur;
        } catch (NumberFormatException e) {
            System.out.println("Veuillez rentrer un entier.");
            return null;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du vendeur : " + e.getMessage());
            return null;
        }
    }

    public void menuVendeur() throws NumberFormatException, SQLException {
        Vendeur vendeur = null;
        while (vendeur == null) {
            vendeur = menuvendeurConnexion();
        }

        boolean menuActif = true;
        int largeurConsole = getLargeurConsole();

        while (menuActif && !quitterApp) {
            clearConsole();

            String titre = "Vendeur n°" + vendeur.getIdVendeur();
            String ligneTitre = "║  " + titre;
            if (ligneTitre.length() > 68) {
                ligneTitre = ligneTitre.substring(0, 68);
            } else if (ligneTitre.length() < 73) {
                ligneTitre += " ".repeat(73 - ligneTitre.length());
            }
            ligneTitre += "║";

            String[] header = {
                    "╔════════════════════════════════════════════════════════════════════════╗",
                    ligneTitre,
                    "╚════════════════════════════════════════════════════════════════════════╝",
                    ""
            };
            for (int i = 0; i < header.length; i++) {
                header[i] = centrerTexte(header[i], largeurConsole);
            }
            try {
                machineAEcrireLigneParLigne(header, 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println(centrerTexte(
                    "╔════════════════════════════════════════════════════════════════════════╗", largeurConsole));
            System.out.println(centrerTexte("║      Infos Perso...................................................[1] ║",
                    largeurConsole));
            System.out.println(centrerTexte("║      Afficher magasins.............................................[2] ║",
                    largeurConsole));
            System.out.println(centrerTexte("║      Voir un panier................................................[3] ║",
                    largeurConsole));
            System.out.println(centrerTexte(
                    "║      Ajouter un livre..............................................[5] ║", largeurConsole));
            System.out.println(centrerTexte("║      Transférer un livre...........................................[4] ║",
                    largeurConsole));
            System.out.println(centrerTexte("║      Menu précédent................................................[M] ║",
                    largeurConsole));
            System.out.println(centrerTexte("║      Quitter.......................................................[Q] ║",
                    largeurConsole));
            System.out.println(centrerTexte(
                    "╚════════════════════════════════════════════════════════════════════════╝", largeurConsole));
            System.out.print("\n" + centrerTexte("Entrez votre choix : ", largeurConsole));

            String cmd = lireCommande();

            switch (cmd) {
                case "1" -> {
                    System.out.println(centrerTexte(vendeur.toString(), largeurConsole));
                    pause();
                }
                case "2" -> menuMagasins(vendeur);
                case "3" -> voirPanierClient();
                case "4" -> transfererLivreEntreMagasins(vendeur);
                case "5" -> ajouterLivre();
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

    private void ajouterLivre() {
        clearConsole();
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║       Ajouter un Livre       ║");
        System.out.println("╚══════════════════════════════╝");

        try {

            System.out.println("Sélectionnez un magasin pour ajouter le livre :");
            List<Magasin> magasins = magasinBD.listeDesMagasins();
            for (int i = 0; i < magasins.size(); i++) {
                System.out.println((i + 1) + ". " + magasins.get(i).getNom());
            }
            System.out.print("Numéro du magasin > ");
            int indexMagasin = Integer.parseInt(scanner.nextLine()) - 1;
            if (indexMagasin < 0 || indexMagasin >= magasins.size()) {
                System.out.println("Choix invalide.");
                pause();
                return;
            }
            Magasin magasinChoisi = magasins.get(indexMagasin);

            System.out.print("Nom du livre > ");
            String nom = scanner.nextLine();

            System.out.print("Auteur(s) (séparés par des virgules) > ");
            String auteursStr = scanner.nextLine();
            List<String> auteurs = List.of(auteursStr.split("\\s*,\\s*"));

            System.out.print("Genre(s) (séparés par des virgules) > ");
            String genresStr = scanner.nextLine();
            List<String> genres = List.of(genresStr.split("\\s*,\\s*"));

            System.out.print("ID(s) éditeur(s) (séparés par des virgules) > ");
            String editeursStr = scanner.nextLine();
            List<Integer> editeurs = new ArrayList<>();
            for (String idStr : editeursStr.split("\\s*,\\s*")) {
                editeurs.add(Integer.parseInt(idStr));
            }

            System.out.print("Date de publication (année : AAAA) > ");
            String datePublication = scanner.nextLine();

            System.out.print("Nombre de pages > ");
            int nbPages = Integer.parseInt(scanner.nextLine());

            System.out.print("Prix (€) > ");
            double prix = Double.parseDouble(scanner.nextLine());

            System.out.print("Quantité > ");
            int quantite = Integer.parseInt(scanner.nextLine());

            if (quantite <= 0) {
                System.out.println("Quantité invalide.");
                pause();
                return;
            }

            Livre livre = new Livre(
                    0,
                    nom,
                    datePublication,
                    prix,
                    nbPages,
                    genres,
                    editeurs,
                    auteurs);

            magasinBD.ajouterLivreDansMagasin(magasinChoisi.getIdMagasin(), livre, quantite);

            System.out.println("Livre ajouté avec succès au magasin.");
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide (nombre attendu).");
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        pause();
    }

    private void voirPanierClient() {
        clearConsole();
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║         Voir Panier          ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.print("ID du client > ");
        try {
            int idClient = Integer.parseInt(scanner.nextLine());
            Client client = clientBD.recupererClient(idClient);
            if (client == null) {
                System.out.println("Ce client n'existe pas.");
            } else {
                Map<Livre, Integer> panier = client.getPanier();
                if (panier.isEmpty()) {
                    System.out.println("Le panier du client est vide.");
                } else {
                    System.out.println("\nContenu du panier :");
                    for (Map.Entry<Livre, Integer> entry : panier.entrySet()) {
                        System.out.println(entry.getKey().getNomLivre() + " (" + entry.getValue() + ")");
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("ID client invalide.");
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        pause();
    }

    public void menuMagasins(Vendeur vendeur) throws NumberFormatException, SQLException {
        boolean menuActif = true;
        int largeurConsole = getLargeurConsole();

        while (menuActif && !quitterApp) {
            clearConsole();

            String[] header = {
                    "╔════════════════════════════════════════════════════════════════════════╗",
                    centrerTexte("║                Liste des Magasins                                      ║", 72),
                    "╠════════════════════════════════════════════════════════════════════════╣"
            };

            for (String ligne : header) {
                System.out.println(centrerTexte(ligne, largeurConsole));
            }

            try {
                List<Magasin> listeMagasins = magasinBD.listeDesMagasins();
                for (int i = 0; i < listeMagasins.size(); i++) {
                    String nom = listeMagasins.get(i).getNom();
                    if (nom.length() > 58)
                        nom = nom.substring(0, 55) + "...";
                    String ligneMagasin = String.format("║     %2d : %-58s    ║", i + 1, nom);
                    System.out.println(centrerTexte(ligneMagasin, largeurConsole));
                }
            } catch (SQLException e) {
                System.out.println(centrerTexte("Erreur lors de la récupération des magasins : " + e.getMessage(),
                        largeurConsole));
                pause();
                return;
            }

            String[] footer = {
                    "╠════════════════════════════════════════════════════════════════════════╣",
                    "║     Menu précédent.................................................[M] ║",
                    "║     Quitter........................................................[Q] ║",
                    "╚════════════════════════════════════════════════════════════════════════╝"
            };

            for (String ligne : footer) {
                System.out.println(centrerTexte(ligne, largeurConsole));
            }

            String cmd = lireCommande();

            if (cmd.matches("[1-" + magasinBD.listeDesMagasins().size() + "]")) {
                int choix = Integer.parseInt(cmd);
                try {
                    menuUnMagasin(magasinBD.listeDesMagasins().get(choix - 1), vendeur);
                } catch (SQLException e) {
                    System.out.println(
                            centrerTexte("Erreur lors de l'ouverture du magasin : " + e.getMessage(), largeurConsole));
                    pause();
                }
            } else {
                switch (cmd) {
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
    }

    private void menuUnMagasin(Magasin magasin, Vendeur vendeur) {
        boolean menuActif = true;
        int largeurConsole = getLargeurConsole();

        while (menuActif && !quitterApp) {
            clearConsole();

            String nom = magasin.getNom();
            if (nom.length() > 58)
                nom = nom.substring(0, 55) + "...";

            String ligneDeBase = "║  " + nom;
            int largeurContenu = 68;
            String tronquee = ligneDeBase.substring(0, Math.min(ligneDeBase.length(), largeurContenu));
            String padding = " ".repeat(largeurContenu - tronquee.length());

            String[] titre = {
                    "╔════════════════════════════════════════════════════════════════════════╗",
                    tronquee + padding + "       ║",
                    "╚════════════════════════════════════════════════════════════════════════╝",
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

            System.out.println(centrerTexte(
                    "╔════════════════════════════════════════════════════════════════════════╗", largeurConsole));
            System.out.println(centrerTexte(
                    "║    Infos Magasin...................................................[I] ║", largeurConsole));
            System.out.println(centrerTexte(
                    "║    Voir stock......................................................[S] ║", largeurConsole));
            System.out.println(centrerTexte(
                    "║    Menu précédent..................................................[M] ║", largeurConsole));
            System.out.println(centrerTexte(
                    "║    Quitter.........................................................[Q] ║", largeurConsole));
            System.out.println(centrerTexte(
                    "╚════════════════════════════════════════════════════════════════════════╝", largeurConsole));
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
                        System.out.println(centrerTexte("Erreur lors de la récupération du stock : " + e.getMessage(),
                                largeurConsole));
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

    private String centrerTexte(String texte, int largeurConsole) {
        int espacement = Math.max(0, (largeurConsole - texte.length()) / 2);
        return " ".repeat(espacement) + texte;
    }

    private void machineAEcrireLigneParLigne(String[] lignes, int delai) throws InterruptedException {
        for (String ligne : lignes) {
            System.out.println(ligne);
            Thread.sleep(delai);
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
                if (nom.length() > 20)
                    nom = nom.substring(0, 17) + "...";
                System.out.printf("║ %-20s (%2d)     ║\n", nom, quantite);
            }
        }
        System.out.println("╚══════════════════════════════╝");
        pause();
    }

    private void transfererLivreEntreMagasins(Vendeur vendeur) {
        try {
            clearConsole();
            System.out.println("╔══════════════════════════════╗");
            System.out.println("║      Transfert de Livre      ║");
            System.out.println("╚══════════════════════════════╝");

            System.out.println("Sélectionnez le magasin source :");
            List<Magasin> magasins = magasinBD.listeDesMagasins();
            if (magasins.isEmpty()) {
                System.out.println("Aucun magasin disponible.");
                pause();
                return;
            }
            for (int i = 0; i < magasins.size(); i++) {
                System.out.println((i + 1) + ". " + magasins.get(i).getNom());
            }
            System.out.print("Numéro du magasin source > ");
            int indexMagasinSource = Integer.parseInt(scanner.nextLine()) - 1;
            if (indexMagasinSource < 0 || indexMagasinSource >= magasins.size()) {
                System.out.println("Choix invalide.");
                pause();
                return;
            }
            Magasin magasinSource = magasins.get(indexMagasinSource);

            Map<Livre, Integer> stockSource = magasinBD.listeLivreUnMagasin(magasinSource.getIdMagasin());
            if (stockSource.isEmpty()) {
                System.out.println("Le magasin source n'a aucun livre en stock.");
                pause();
                return;
            }
            Livre[] livresStock = new Livre[stockSource.size()];
            int i = 0;
            System.out.println("Livres disponibles dans le magasin source :");
            for (Map.Entry<Livre, Integer> entry : stockSource.entrySet()) {
                System.out.println((i + 1) + ". " + entry.getKey().getNomLivre() + " (" + entry.getValue() + ")");
                livresStock[i] = entry.getKey();
                i++;
            }
            System.out.print("Numéro du livre à transférer > ");
            int indexLivre = Integer.parseInt(scanner.nextLine()) - 1;
            if (indexLivre < 0 || indexLivre >= livresStock.length) {
                System.out.println("Choix invalide.");
                pause();
                return;
            }
            Livre livreChoisi = livresStock[indexLivre];

            System.out.print("Quantité à transférer > ");
            int quantite = Integer.parseInt(scanner.nextLine());
            if (quantite <= 0 || quantite > stockSource.get(livreChoisi)) {
                System.out.println("Quantité invalide.");
                pause();
                return;
            }

            System.out.println("Sélectionnez le magasin destination :");
            for (int j = 0; j < magasins.size(); j++) {
                if (j != indexMagasinSource) {
                    System.out.println((j + 1) + ". " + magasins.get(j).getNom());
                }
            }
            System.out.print("Numéro du magasin destination > ");
            int indexMagasinDestination = Integer.parseInt(scanner.nextLine()) - 1;
            if (indexMagasinDestination < 0 || indexMagasinDestination >= magasins.size()
                    || indexMagasinDestination == indexMagasinSource) {
                System.out.println("Choix invalide.");
                pause();
                return;
            }
            Magasin magasinDestination = magasins.get(indexMagasinDestination);

            Map<Livre, Integer> livresATransferer = Map.of(livreChoisi, quantite);
            vendeur.transfererLivre(magasinDestination, livresATransferer);
            System.out.println("Transfert effectué avec succès.");
            pause();

        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide.");
            pause();
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            pause();
        }
    }

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
            for (int i = 0; i < 50; ++i)
                System.out.println();
        }
    }

    private int getLargeurConsole() {
        return 100;
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