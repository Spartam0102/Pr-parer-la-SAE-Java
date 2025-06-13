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

            try{
                Client client = clientBD.recupererClient(id);
                if (client == null){
                    System.out.println("Cet id n'existe pas");
                }
                else{
                    client.setPanier(clientBD.recupererPanier(id));
                }
                return client;
            } catch(SQLException e){
                System.out.println("Erreur lors de la récupération du client : " + e.getMessage());

                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println(centrerTexte("Veuillez rentrer un entier.", largeur));
            return null;
        }
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
                centrerTexte(String.format("║     🧑  Infos personnelles.........................................[I] ║"), largeurConsole),
                centrerTexte(String.format("║     🏬  Afficher magasins..........................................[A] ║"), largeurConsole),
                centrerTexte(String.format("║     🛒  Commander..................................................[C] ║"), largeurConsole),
                centrerTexte(String.format("║     🛒  Voir mon panier............................................[P] ║"), largeurConsole),
                centrerTexte(String.format(" ║     ↩️   Retour.....................................................[R] ║"), largeurConsole),
                centrerTexte(String.format("║     ❌  Quitter....................................................[Q] ║"), largeurConsole),
                centrerTexte("║                                                                        ║", largeurConsole),
                centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole)
            };

            try {
                machineAEcrireLigneParLigne(menu, 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.print("\n" + centrerTexte("Entrez votre choix : ", largeurConsole));
            String cmd = lireCommande();

            switch (cmd) {
                case "i" -> {
                    System.out.println(client.toString());
                    attendreEntree();
                }
                case "a" -> menuMagasins(client);
                case "p" -> afficherPanier(client);
                case "r" -> menuActif = false;
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

    public void menuMagasins(Client client) {
        boolean menuActif = true;
        int largeurConsole = getLargeurConsole();

        while (menuActif && !quitterApp) {
            clearConsole();

            String[] titre = {
                "╔════════════════════════════════════════════════════════════════╗",
                "║                           MAGASINS                             ║",
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
                System.out.println(centrerTexte("╔════════════════════════════════════════════════════════════════════════╗", largeurConsole));
                List<Magasin> listeMagasins = magasinBD.listeDesMagasins();
                for (int i = 0; i < listeMagasins.size(); i++) {
                    String nom = listeMagasins.get(i).getNom();
                    if (nom.length() > 50) {
                        nom = nom.substring(0, 47) + "...";
                    }
                    String ligne = "   ║     🛍️   " + nom;
                    int esp = largeurConsole - ligne.length() - 6;
                    ligne += ".".repeat(Math.max(0, esp)) + "[" + (i+1) + "] ║";
                    System.out.println(centrerTexte(ligne, largeurConsole));
                    pause(100);
                }

                String[] menu = {
                    
                    centrerTexte(" ║     ↩️   Retour ....................................................[M] ║", largeurConsole),
                    centrerTexte("║     🛒  Voir mon panier ...........................................[P] ║", largeurConsole),
                    centrerTexte("║     ❌  Quitter ...................................................[Q] ║", largeurConsole),
                    centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole)
                };
        
                try {
                    machineAEcrireLigneParLigne(menu, 100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
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
                pause(1500);
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

            String texteCentre = String.format("%-62s", String.format("%" + (29 + nom.length()/2) + "s", nom));

            String ligneMagasin = "║  " + texteCentre + "║";

            ligneMagasin = centrerTexte(ligneMagasin, largeurConsole);

            String[] titre = {
                centrerTexte("╔════════════════════════════════════════════════════════════════╗", largeurConsole),
                ligneMagasin,
                centrerTexte("╚════════════════════════════════════════════════════════════════╝", largeurConsole),
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
            System.out.println(centrerTexte("║     🏪  Infos Magasin..............................................[I] ║", largeurConsole));
            System.out.println(centrerTexte("║     📦  Voir stock.................................................[S] ║", largeurConsole));
            System.out.println(centrerTexte("║     ↩️  Menu précédent.............................................[M] ║", largeurConsole));
            System.out.println(centrerTexte("║     ❌  Quitter....................................................[Q] ║", largeurConsole));
            System.out.println(centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole));
            System.out.print("\n" + centrerTexte("Entrez votre choix : ", largeurConsole));
            String cmd = lireCommande().toLowerCase();

            switch (cmd) {
                case "i" -> {
                    System.out.println(centrerTexte(magasin.toString(), largeurConsole));
                    pause(1500);
                }

                case "s" -> {
                    try {

                        Map<Livre, Integer> stock = magasinBD.listeLivreUnMagasin(magasin.getIdMagasin());
                        afficherStock(stock);
                    } catch (SQLException e) {
                        System.out.println(centrerTexte("Erreur lors de la récupération du stock : " + e.getMessage(), largeurConsole));
                        pause(1500);
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
        pause(1500);
    }


public void afficherPanier(Client client) {
    clearConsole();
    int largeurConsole = getLargeurConsole();

    System.out.println(centrerTexte("╔════════════════════════════════════════════════════════════════╗", largeurConsole));
    System.out.println(centrerTexte("║                          PANIER CLIENT                         ║", largeurConsole));
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
    pause(1500);
}


    
    
    

    public void menuStock(Map<Livre, Integer> stock, Client client) {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Stock                   |");
            System.out.println("+-------------------------+");
            int num = 1;
            for (Map.Entry<Livre, Integer> coupleLivre : stock.entrySet()) {
                if (!(coupleLivre.getValue() == 0)){
                    String livre = coupleLivre.getKey().getNomLivre();
                    if (livre.length() >= 17){
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
            }
            System.out.println("+-------------------------+");
            System.out.println("| Pour ajouter un livre à |");
            System.out.println("| votre panier, entrez le |");
            System.out.println("| numéro correspondant au |");
            System.out.println("| livre                   |");
            System.out.println("+-------------------------+");
            System.out.println("| P: Panier               |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            try{
                int commandeInt = Integer.parseInt(commande);
                int nbLivreDiff = stock.size();
                if ((commandeInt > 0) && (commandeInt <= nbLivreDiff)) {
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
                            attendreEntree();
                        }
                        else {
                            client.ajouterLivrePanier(livre);
                        }
                    }
                    else {
                        client.ajouterLivrePanier(livre);
                        try{
                            clientBD.sauvegardePanierBD(client);
                        }
                        catch(Exception e){
                            System.out.println("Impossible de sauvegarder dans la bd");
                            attendreEntree();}
                    }
                }
                else{
                    System.out.println("Le numéro de livre que vous avez saisie n'existe pas");
                    attendreEntree();
                }
            }
            catch(NumberFormatException e){
                if (commande.equals("p")) {
                    menuPanier(client);
                } 
                else if (commande.equals("m")) {
                    menu3 = true;
                }
                else if (commande.equals("q")) {
                    quitterApp = true;
                    menu3 = true;
                }   
                else {
                    System.out.println("Commande invalide.");
                }
            }
        }
    }

    public void menuPanier(Client client) {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Client " + client.getIdCli() + "               |");
            System.out.println("+-------------------------+");
            System.out.println("| V: Voir mon panier      |");
            System.out.println("| S: Supprimer Panier     |");
            System.out.println("| C: Commander            |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("v")) {
                voirPanier(client);
            }
            else if (commande.equals("s")) {
                client.reunitialiserPanier();
                try{
                    clientBD.sauvegardePanierBD(client);
                }
                catch(Exception e){
                    System.out.println("Impossible de sauvegarder dans la bd");
                    attendreEntree();}
            }
            else if (commande.equals("c")) {
            } 
            else if (commande.equals("m")) {
                menu3 = true;
            }
            else if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            }
            else {
                System.out.println("Commande invalide.");
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


    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void voirPanier(Client client){
        if (client.getPanier().isEmpty()) {
            System.out.println("Panier vide.");
            attendreEntree();
        }
        else {
            for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()) {
                String livre = coupleLivre.getKey().getNomLivre();
                int quantite = coupleLivre.getValue();
                System.out.println(livre + " (" + quantite + ")\n");
            }
            attendreEntree();
        }
    }

    private void attendreEntree(){

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
