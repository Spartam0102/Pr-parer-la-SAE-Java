package App;

import BD.*;
import Java.*;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;

public class AppLibrairieClient {

    private MagasinBD magasinBD;
    private CommandeBD commandeBD;
    private ClientBD clientBD;
    private boolean quitterApp = false;
    private Scanner scanner = new Scanner(System.in);

    public AppLibrairieClient(MagasinBD magasinBD, LivreBD livreBD, ConnexionMySQL connexionMySQL) {
        this.magasinBD = magasinBD;
        this.clientBD = new ClientBD(connexionMySQL);
        this.commandeBD = new CommandeBD(connexionMySQL);
    }

    public Client menuClientConnexion() {
        clearConsole();
        int largeur = getLargeurConsole();
        String[] titre = {
            "╔════════════════════════════════════════════════════════════════╗",
            "║                       CONNEXION CLIENT                         ║",
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
                "║                         MENU CLIENT                            ║",
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
                centrerTexte(String.format("║    🧑  Infos personnelles..........................................[I] ║"), largeurConsole),
                centrerTexte(String.format("║    🏬  Afficher magasins...........................................[A] ║"), largeurConsole),
                centrerTexte(String.format("║    🛒  Commander...................................................[C] ║"), largeurConsole),
                centrerTexte(String.format("║    🛒  Panier .....................................................[P] ║"), largeurConsole),
                centrerTexte(String.format("║    💡  Recommandations.............................................[R] ║"), largeurConsole),
                centrerTexte(String.format("║    ❌  Quitter.....................................................[Q] ║"), largeurConsole),
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
                case "r" -> menuRecommandations(client);
                case "c" -> commander(client);
                case "p" -> menuPanier(client);
                case "q" -> {
                    quitterApp = true;
                    menuActif = false;
                }
                default -> {
                    System.out.println("\n>>> Commande invalide.");
                    pause(1500);
                }
            }
        }
    }

    public void menuRecommandations(Client client) {
    clearConsole();
    int largeurConsole = getLargeurConsole();

    String[] titre = {
        "╔════════════════════════════════════════════════════════════════╗",
        "║                      RECOMMANDATIONS                           ║",
        "╚════════════════════════════════════════════════════════════════╝",
        ""
    };

    for (int i = 0; i < titre.length; i++) {
        titre[i] = centrerTexte(titre[i], largeurConsole);
    }

    try {
        machineAEcrireLigneParLigne(titre, 100);
        
        List<Livre> recommandations = livreRecommander(client.getIdCli());
        
        if (recommandations.isEmpty()) {
            System.out.println(centrerTexte("╔════════════════════════════════════════════════════════════════════════╗", largeurConsole));
            System.out.println(centrerTexte("║                                                                        ║", largeurConsole));
            System.out.println(centrerTexte("║    💡  Aucune recommandation disponible pour le moment               ║", largeurConsole));
            System.out.println(centrerTexte("║        Essayez d'acheter quelques livres pour obtenir des suggestions ║", largeurConsole));
            System.out.println(centrerTexte("║                                                                        ║", largeurConsole));
            System.out.println(centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole));
        } else {
            System.out.println(centrerTexte("╔════════════════════════════════════════════════════════════════════════╗", largeurConsole));
            System.out.println(centrerTexte("║                                                                        ║", largeurConsole));
            System.out.println(centrerTexte("║    💡  Livres recommandés pour vous :                                ║", largeurConsole));
            System.out.println(centrerTexte("║        (Basés sur les goûts de clients similaires)                   ║", largeurConsole));
            System.out.println(centrerTexte("║                                                                        ║", largeurConsole));
            
            for (int i = 0; i < Math.min(recommandations.size(), 10); i++) {
                Livre livre = recommandations.get(i);
                String nom = livre.getNomLivre();
                if (nom.length() > 50) {
                    nom = nom.substring(0, 47) + "...";
                }
                String ligne = "║    📚  " + nom;
                int esp = largeurConsole - ligne.length() - 3;
                ligne += " ".repeat(Math.max(0, esp)) + "║";
                System.out.println(centrerTexte(ligne, largeurConsole));
                pause(200);
            }
            
            System.out.println(centrerTexte("║                                                                        ║", largeurConsole));
            System.out.println(centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole));
        }
        
    } catch (SQLException e) {
        System.out.println(centrerTexte("╔════════════════════════════════════════════════════════════════════════╗", largeurConsole));
        System.out.println(centrerTexte("║                                                                        ║", largeurConsole));
        System.out.println(centrerTexte("║    ❌  Erreur lors de la récupération des recommandations            ║", largeurConsole));
        System.out.println(centrerTexte("║        " + e.getMessage(), largeurConsole));
        System.out.println(centrerTexte("║                                                                        ║", largeurConsole));
        System.out.println(centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole));
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    
    attendreEntree();
}
    
    public void menuPanier(Client client) {
        boolean menuActif = true;
        int largeurConsole = getLargeurConsole();

        while (menuActif && !quitterApp) {
            clearConsole();

            String[] menu = {
                centrerTexte("╔════════════════════════════════════════════════════════════════╗", largeurConsole),
                centrerTexte(String.format("║                        🧑 Client %-30s║", client.getIdCli()), largeurConsole),
                centrerTexte("╚════════════════════════════════════════════════════════════════╝", largeurConsole),
                centrerTexte("", largeurConsole),
                centrerTexte("╔════════════════════════════════════════════════════════════════════════╗", largeurConsole),
                centrerTexte("║                                                                        ║", largeurConsole),
                centrerTexte("║    🛒  Voir mon panier.............................................[V] ║", largeurConsole),
                centrerTexte("   ║    🗑️   Supprimer panier............................................[S] ║", largeurConsole),
                centrerTexte("║    🛒  Commander...................................................[C] ║", largeurConsole),
                centrerTexte("║    🛒  Voir anciennes commandes....................................[A] ║", largeurConsole),
                centrerTexte("  ║    ↩️   Retour......................................................[R] ║", largeurConsole),
                centrerTexte("║    ❌  Quitter.....................................................[Q] ║", largeurConsole),
                centrerTexte("║                                                                        ║", largeurConsole),
                centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole)
            };

            try {
                machineAEcrireLigneParLigne(menu, 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.print("\n" + centrerTexte("Entrez votre choix : ", largeurConsole));
            String commande = lireCommande().toLowerCase();

            switch (commande) {
                case "v" -> {
                    afficherPanier(client);
                }
                case "s" ->  {
                    client.reunitialiserPanier();
                    try {
                        clientBD.sauvegardePanierBD(client);
                        System.out.println(centrerTexte("✔ Panier supprimé et sauvegardé avec succès.", largeurConsole));
                        pause(2500);
                    } catch (Exception e) {
                        System.out.println(centrerTexte("✖ Impossible de sauvegarder dans la base de données.", largeurConsole));
                        attendreEntree();
                    }
                }
                case "c" -> commander(client);
                case "a" -> {
                    try{
                        List<Commande> lstCommandes = commandeBD.getCommandesParClient(client.getIdCli());
                        if (lstCommandes.isEmpty()){
                            System.out.println(centrerTexte("Aucune ancienne commande", largeurConsole));
                            attendreEntree();
                        }
                        else{
                            for (Commande cmmd : lstCommandes){
                                System.out.println(centrerTexte(cmmd.toString(), largeurConsole));
                            }
                            attendreEntree();
                        }
                    }
                    catch (SQLException e){
                        System.out.println("Erreur lors de la recupération des anciennes commandes d'un client");
                    }
                }
                case "r" -> menuActif = false;
                case "q" -> {
                    quitterApp = true;
                    menuActif = false;
                }
                default -> {
                    System.out.println("\n>>> Commande invalide.");
                    pause(1500);
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
                System.out.println(centrerTexte("║                                                                        ║", largeurConsole));
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
                    
                    centrerTexte("║     🛒  Panier ....................................................[P] ║", largeurConsole),
                    centrerTexte(" ║     ↩️   Retour ....................................................[R] ║", largeurConsole),
                    centrerTexte("║     ❌  Quitter ...................................................[Q] ║", largeurConsole),
                    centrerTexte("║                                                                        ║", largeurConsole),
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
                        case "r" -> menuActif = false;
                        case "p" -> menuPanier(client);
                        case "q" -> {
                            quitterApp = true;
                            menuActif = false;
                        }
                        default -> {
                            System.out.println("\n>>> Commande invalide.");
                            pause(1500);
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

            String ligneMagasin = "║  " + texteCentre + "║    ";

            ligneMagasin = centrerTexte(ligneMagasin, largeurConsole);

            String[] titre = {
                centrerTexte("╔════════════════════════════════════════════════════════════════╗    ", largeurConsole),
                ligneMagasin,
                centrerTexte("╚════════════════════════════════════════════════════════════════╝    ", largeurConsole),
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

            String[] menuAnime = {
            centrerTexte("╔════════════════════════════════════════════════════════════════════════╗", largeurConsole),
            centrerTexte("║                                                                        ║", largeurConsole),
            centrerTexte("║     🏪  Infos Magasin..............................................[I] ║", largeurConsole),
            centrerTexte("║     📦  Voir stock.................................................[S] ║", largeurConsole),
            centrerTexte("║     🛒  Panier ....................................................[P] ║", largeurConsole),
            centrerTexte(" ║     ↩️   Retour ....................................................[R] ║", largeurConsole),
            centrerTexte("║     ❌  Quitter....................................................[Q] ║", largeurConsole),
            centrerTexte("║                                                                        ║", largeurConsole),
            centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole)
            };

            try {
                machineAEcrireLigneParLigne(menuAnime, 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.print("\n" + centrerTexte("Entrez votre choix : ", largeurConsole));
            String cmd = lireCommande().toLowerCase();

            switch (cmd) {
                case "i" -> {
                    System.out.println(centrerTexte(magasin.toString(), largeurConsole));
                    attendreEntree();
                }

                case "s" -> {
                    try {

                        Map<Livre, Integer> stock = magasinBD.listeLivreUnMagasin(magasin.getIdMagasin());
                        menuStock(stock, client);
                    } catch (SQLException e) {
                        System.out.println(centrerTexte("Erreur lors de la récupération du stock : " + e.getMessage(), largeurConsole));
                        pause(1500);
                    }

                }
                case "p" -> menuPanier(client);
                case "r" -> menuActif = false;
                case "q" -> {
                    quitterApp = true;
                    menuActif = false;
                }
                default -> {
                    System.out.println("\n>>> Commande invalide.");
                    pause(1500);

                }
            }
        }
    }

    public void menuStock(Map<Livre, Integer> stock, Client client) {
        boolean menu3 = true;
        while (menu3 && !quitterApp) {
            clearConsole();
            int largeurConsole = getLargeurConsole();
            
            String[] titre = {
                centrerTexte("╔════════════════════════════════════════════════════════════════╗", largeurConsole),
                centrerTexte("║                            STOCK                               ║", largeurConsole),
                centrerTexte("╚════════════════════════════════════════════════════════════════╝", largeurConsole),
                ""
            };
            try {
                machineAEcrireLigneParLigne(titre, 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            
            int largeurLigne = 70;
            int num = 1;
            System.out.println(centrerTexte("╔════════════════════════════════════════════════════════════════════════╗", largeurConsole));
            for (Map.Entry<Livre, Integer> coupleLivre : stock.entrySet()) {
                if (coupleLivre.getValue() > 0) {
                    String nomLivre = coupleLivre.getKey().getNomLivre();
                    int quantite = coupleLivre.getValue();

                    if (nomLivre.length() > 40) {
                        nomLivre = nomLivre.substring(0, 37) + "...";
                    }

                    String numero = String.format("%2d.", num);
                    String quantiteStr = String.format("(x%d)", quantite);

                    int espaces = largeurLigne - (numero.length() + 1 + nomLivre.length() + quantiteStr.length());
                    if (espaces < 0) espaces = 0;

                    String ligneLivre = String.format("║ %s %s%s%s ║",
                        numero,
                        nomLivre,
                        " ".repeat(espaces),
                        quantiteStr);

                    System.out.println(centrerTexte(ligneLivre, largeurConsole));
                    System.out.flush();
                    pause(15);
                    num++;
                }
            }

            String[] menuAnime = {
                centrerTexte("║════════════════════════════════════════════════════════════════════════║", largeurConsole),
                centrerTexte("║ Entrez le numéro du livre pour l’ajouter à votre panier.               ║", largeurConsole),
                centrerTexte("║════════════════════════════════════════════════════════════════════════║", largeurConsole),
                centrerTexte("║     🛒  Panier ....................................................[P] ║", largeurConsole),
                centrerTexte(" ║     ↩️   Retour ....................................................[R] ║", largeurConsole),
                centrerTexte("║     ❌  Quitter....................................................[Q] ║", largeurConsole),
                centrerTexte("║                                                                        ║", largeurConsole),
                centrerTexte("╚════════════════════════════════════════════════════════════════════════╝", largeurConsole)
            };

            try {
                machineAEcrireLigneParLigne(menuAnime, 15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.print("\n" + centrerTexte("Entrez votre choix : ", largeurConsole));
            String commande = lireCommande().toLowerCase();

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
                            try{
                            clientBD.sauvegardePanierBD(client);
                            System.out.println(centrerTexte("✔ Livre ajouté à votre panier avec succès.", largeurConsole));
                            pause(1500);
                            }
                            catch(Exception e){
                                System.out.println(centrerTexte("✖ Impossible de sauvegarder dans la base de données.", largeurConsole));
                                attendreEntree();}
                        }
                    }
                    else {
                        client.ajouterLivrePanier(livre);
                        try{
                            clientBD.sauvegardePanierBD(client);
                            System.out.println(centrerTexte("✔ Livre ajouté à votre panier avec succès.", largeurConsole));
                            pause(1500);
                        }
                        catch(Exception e){
                            System.out.println(centrerTexte("✖ Impossible de sauvegarder dans la base de données.", largeurConsole));
                            attendreEntree();}
                    }
                }
                else{
                    System.out.println("Le numéro de livre que vous avez saisie n'existe pas");
                    attendreEntree();
                }
            }
            catch(NumberFormatException e){
                switch (commande) {
                    case "p":
                        menuPanier(client);
                        break;
                    case "m":
                        menu3 = true;
                        break;
                    case "q":
                        quitterApp = true;
                        menu3 = true;
                        break;
                    default:
                        System.out.println("Commande invalide.");
                        break;
                }
            }
        }
    }

    public void commander(Client client) {
        int largeurConsole = getLargeurConsole();

        if (client.getPanier().isEmpty()) {
            System.out.println(centrerTexte("Veuillez d'abord ajouter des livres à votre panier", largeurConsole));
        }

        try{
            int idCommande = commandeBD.genererNouvelIdCommande();

            LocalDate dateDeCommande = LocalDate.now();
            String dateStr = dateDeCommande.toString();

            System.out.print("\n" + centrerTexte("Quelle est le mode de réception (Livraison : C / en Magasin : M) ? : ", largeurConsole));
            String commande1 = lireCommande().toLowerCase();
            char modeDeReception;
            while (!(commande1.equals("c") || commande1.equals("m"))) {
                System.out.print("\n" + centrerTexte("Entrée invalide. Veuillez entrer 'C' ou 'M' : ", largeurConsole));
                commande1 = lireCommande().toLowerCase();
            }
            modeDeReception = commande1.toUpperCase().charAt(0);

            List<Magasin> magasins = magasinBD.listeDesMagasins();
            Magasin bonMagasin = null;
            for (int i = 0 ; i < magasins.size() ; i++){
                System.out.println(centrerTexte(magasins.get(i).getNom() + " : [" + (i + 1) + "]", largeurConsole));
            }
            System.out.print("\n" + centrerTexte("Dans quel magasin voulez-vous commander (entrer un entier) ? : ", largeurConsole));
            String commande2 = lireCommande().toLowerCase().trim();

            while (bonMagasin == null) {
                try{
                    for (Magasin mag : magasins) {
                        if (mag.getIdMagasin() == Integer.parseInt(commande2)) {
                            bonMagasin = new Magasin(mag.getNom(), mag.getVille(), mag.getIdMagasin());
                            break;
                        }
                    }

                    if (bonMagasin == null) {
                        System.out.println(centrerTexte("Magasin non trouvé. Veuillez réessayer.", largeurConsole));
                        System.out.print("\n" + centrerTexte("Dans quel magasin voulez-vous commander ? : ", largeurConsole));
                        commande2 = lireCommande().toLowerCase().trim();
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println(centrerTexte("Entrée invalide. Vous devez saisir un nombre entier.", largeurConsole));
                    System.out.print("\n" + centrerTexte("Dans quel magasin voulez-vous commander ? : ", largeurConsole));
                    commande2 = lireCommande().toLowerCase().trim();
                }
            }

            Commande commande = new Commande(idCommande, dateStr, modeDeReception, client, bonMagasin);

            Map<Livre, Integer> panier = client.getPanier();
            for (Map.Entry<Livre, Integer> entry : panier.entrySet()) {
                commande.ajouterLivre(entry.getKey(), entry.getValue());
            }

            System.out.println(centrerTexte("Etes vous sur de vouloir passer commande ? (O/N)", largeurConsole));
            String commandesur = lireCommande().toLowerCase();
            while (!(commandesur.equals("o")) && !(commandesur.equals("n"))) {
                System.out.println(centrerTexte("Veuillez rentrer soit O soit N", largeurConsole));
                System.out.println(centrerTexte("Etes vous sur de vouloir passer commande ? (O/N)", largeurConsole));
                commandesur = lireCommande().toLowerCase();
            }

            if (commandesur.equals("n")) {
                System.out.println(centrerTexte("Commande annulée", largeurConsole));
                pause(3000);
                return;
            }

            commandeBD.enregistrerCommande(commande);
            System.out.println("1");
            client.reunitialiserPanier();

        }
        catch(SQLException e){
            System.out.println("Erreur sql" + e.getMessage());}
            attendreEntree();
    }

    public void afficherPanier(Client client) {
        clearConsole();
        int largeurConsole = getLargeurConsole();

        System.out.println(centrerTexte("╔════════════════════════════════════════════════════════════════╗", largeurConsole));
        System.out.println(centrerTexte("║                         PANIER CLIENT                          ║", largeurConsole));
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
        attendreEntree();
    }

    private String centrerTexte(String texte, int largeurConsole) {
        int espacement = Math.max(0, (largeurConsole - texte.length()) / 2);
        return " ".repeat(espacement) + texte;
    }

    private int getLargeurConsole() {
        return 80;
    }

    private void machineAEcrireLigneParLigne(String[] lignes, int delai) throws InterruptedException {
        for (String ligne : lignes) {
            System.out.println(ligne);
            Thread.sleep(delai);
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

    private void attendreEntree(){

        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    
    public List<Livre> livreRecommander(int id) throws SQLException {
        List<Livre> livresDuClient = clientBD.recupererToutLivreClient(id);
        List<Client> tousLesClients = clientBD.recuperToutClient();
        List<Livre> meilleureListe = maxLivreEnCommun(livresDuClient, tousLesClients, id);
        return differenceDeLivre(livresDuClient, meilleureListe);
    }

    public List<Livre> differenceDeLivre(List<Livre> liste1, List<Livre> liste2) {
        List<Livre> res = new ArrayList<>();
        for (Livre livre : liste2) {
            if (!liste1.contains(livre)) {
                res.add(livre);
            }
        }
        return res;
    }

    public List<Livre> maxLivreEnCommun(List<Livre> livresDuClient, List<Client> listeClients, int idClientCourant) throws SQLException {
        List<Livre> res = new ArrayList<>();
        int max = 0;

        for (Client client : listeClients) {
            if (client.getIdCli() == idClientCourant) continue; // ne pas se comparer à soi-même

            List<Livre> livresAutreClient = clientBD.recupererToutLivreClient(client.getIdCli());
            int commun = livreEnCommun(livresDuClient, livresAutreClient);

            if (commun > max) {
                max = commun;
                res = livresAutreClient;
            }
        }

        return res;
    }

    public int livreEnCommun(List<Livre> liste1, List<Livre> liste2) {
        int res = 0;
        for (Livre livre : liste2) {
            if (liste1.contains(livre)) {
                res++;
            }
        }
        return res;
    } 
}