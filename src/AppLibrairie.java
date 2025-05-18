import java.util.Map;
import java.util.HashMap;

public class AppLibrairie {
    private Entreprise entreprise;

    private boolean quitterApp = false;

    public AppLibrairie(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public void run() {
        bienvenue();
        while (!quitterApp) {
            menuPrincipal();
        }
        auRevoir();
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
                menuActif = false; // revient au menu principal après connexion
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
        Client client = new Client("Leclerc", "Marie", "1992-03-12", 201, "10 rue des Lilas, Paris");
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Client                  |");
            System.out.println("+-------------------------+");
            System.out.println("| I: Infos Personelles    |");
            System.out.println("| A: Afficher magasins    |");
            System.out.println("| V: Voir mon panier      |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("i")) {
                System.out.println(client.toString());
            }
            else if (commande.equals("a")) {
                menuMagasins(client);
            } 
            else if (commande.equals("v")) {
                if (client.getPanier().isEmpty()){
                    System.out.println("Panier vide");
                }
                else{
                    for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()){
                        String livre = coupleLivre.getKey().getNomLivre();
                        int quantite = coupleLivre.getValue();
                        System.out.println(livre + " (" + quantite + ")\n");
                    }
                }
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

    public void menuMagasins(Client client) {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Magasins                |");
            System.out.println("+-------------------------+");
            for (int i = 0 ; i < this.entreprise.getListeMagasins().size() ; i++){
                String nomMagasin = this.entreprise.getListeMagasins().get(i).getNom();
                int longueurRestante = 21 - nomMagasin.length();
                for (int y = 0 ; y < longueurRestante ; y++){
                    nomMagasin += " ";
                }
                int num = i + 1;
                System.out.println("| " + num + ": " + nomMagasin + "|");
            }
            System.out.println("| V: Voir mon panier      |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.matches("[1-7]")) {
                int commandeInt = Integer.parseInt(commande);
                menuUnMagasin(this.entreprise.getListeMagasins().get(commandeInt - 1), client);
            }
             else if (commande.equals("v")) {
                if (client.getPanier().isEmpty()){
                    System.out.println("Panier vide");
                }
                else{
                    for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()){
                        String livre = coupleLivre.getKey().getNomLivre();
                        int quantite = coupleLivre.getValue();
                        System.out.println(livre + " (" + quantite + ")\n");
                    }
                }
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

    public void menuUnMagasin(Magasin magasin, Client client) {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            String nomMagasin = magasin.getNom();
                int longueurRestante = 24 - nomMagasin.length();
                for (int y = 0 ; y < longueurRestante ; y++){
                    nomMagasin += " ";
                }
            System.out.println("| " + nomMagasin + "|");
            System.out.println("+-------------------------+");
            System.out.println("| I: Infos Magasin        |");
            System.out.println("| S: Voir stock           |");
            System.out.println("| V: Voir mon panier      |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

           
            if (commande.equals("i")) {
                System.out.println(magasin.toString());
            }
            else if (commande.equals("s")) {
                Map<Livre, Integer> stock = magasin.getStockLivre();
                menuStock(stock, client);
            }
            else if (commande.equals("v")) {
                if (client.getPanier().isEmpty()){
                    System.out.println("Panier vide");
                }
                else{
                    for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()){
                        String livre = coupleLivre.getKey().getNomLivre();
                        int quantite = coupleLivre.getValue();
                        System.out.println(livre + " (" + quantite + ")\n");
                    }
                }
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

    public void menuStock(Map<Livre, Integer> stock, Client client) {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Stock                   |");
            System.out.println("+-------------------------+");
            System.out.println("| Pour ajouter un livre à |");
            System.out.println("| votre panier, entrez le |");
            System.out.println("| numéro correspondant au |");
            System.out.println("| livre                   |");
            System.out.println("+-------------------------+");
            int num = 1;
            for (Map.Entry<Livre, Integer> coupleLivre : stock.entrySet()){
                String livre = coupleLivre.getKey().getNomLivre();
                if (livre.length() >= 17) {
                    livre = livre.substring(0, 17 - 3) + "...";
                }
                int quantite = coupleLivre.getValue();
                int longueurRestante = 17 - livre.length();
                livre += " (" + quantite +")";
                for (int y = 0 ; y < longueurRestante ; y++){
                    livre += " ";
                }
                System.out.println("| " + num + ": " + livre + "|");
                num += 1;
            }
            System.out.println("| V: Voir mon panier      |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            int nbLivreDiff = stock.size();

            if (commande.matches("[1-" + nbLivreDiff + "]")) {
                int commandeInt = Integer.parseInt(commande);
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
                if (client.getPanier().get(livre)!= null){
                    if (quantiteRestante <= client.getPanier().get(livre)){
                        System.out.println("Stock insuffisant");
                    }
                    else{
                        client.ajouterLivrePanier(livre);
                    }
                }
                else{
                    client.ajouterLivrePanier(livre);
                }
            }
            else if (commande.equals("v")) {
                if (client.getPanier().isEmpty()){
                    System.out.println("Panier vide");
                }
                else{
                    for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()){
                        String livre = coupleLivre.getKey().getNomLivre();
                        int quantite = coupleLivre.getValue();
                        System.out.println(livre + " (" + quantite + ")\n");
                    }
                }
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
                System.out.println(entreprise.toString());
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
            System.out.println("| J: ajouter magasins     |");
            System.out.println("| P: Menu précédent       |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else if (commande.equals("a")) {
                System.out.println(entreprise.toString());
            } else if (commande.equals("j")) {
                System.out.print("nom > ");
                String nomR = System.console().readLine();
                System.out.print("ville > ");
                String villeR = System.console().readLine();
                System.out.print("id > ");
                int idR = Integer.parseInt(System.console().readLine());

                Magasin magasin = new Magasin(nomR, villeR,idR);
                entreprise.ajouterMagasin(magasin);
            } else if (commande.equals("p")) {
                menu3 = true;
            } else {
                System.out.println("Commande invalide.");
            }
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
