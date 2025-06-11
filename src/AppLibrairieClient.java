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

    public Client menuClientConexion(){
        System.out.println("\nConnexion :");
        System.out.println("========================");
        System.out.print("Votre id > ");
        try{
            String idStr = System.console().readLine();
            int id = Integer.parseInt(idStr);
            try{
                Client client = clientBD.recupererClient(id);
                if (client == null){
                    System.out.println("Cet id n'existe pas");
                }
                return client;
            } catch(SQLException e){
                System.out.println("Erreur lors de la récupération du client : " + e.getMessage());
                return null;
            }
        }
        catch(NumberFormatException e){System.out.println("Veuillez rentrer un entier");}
        return null; 
    }


    public void menuClient() {
        Client client = this.menuClientConexion();
        while (client == null){
            client = this.menuClientConexion();
        }
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Client " + client.getIdCli() + "               |");
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
            } else if (commande.equals("a")) {
                menuMagasins(client);
            } else if (commande.equals("v")) {
                if (client.getPanier().isEmpty()) {
                    System.out.println("Panier vide.");
                    attendreEntree();
                } else {
                    for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()) {
                        String livre = coupleLivre.getKey().getNomLivre();
                        int quantite = coupleLivre.getValue();
                        System.out.println(livre + " (" + quantite + ")\n");
                    }
                    attendreEntree();
                }
            } else if (commande.equals("m")) {
                menu3 = true;
            } else if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else {
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
            try {
                List<Magasin> listeMagasins = magasinBD.listeDesMagasins();
                int num = 0;
                for (Magasin magasin : listeMagasins) {
                    String nomMagasin = magasin.getNom();
                    if (nomMagasin.length() >= 22) {
                    nomMagasin = nomMagasin.substring(0, 20 - 3) + "...";
                    }
                    int longueurRestante = 21 - nomMagasin.length();
                    for (int y = 0; y < longueurRestante; y++) {
                        nomMagasin += " ";
                    }
                    num += 1;
                    System.out.println("| " + num + ": " + nomMagasin + "|");
                }
                System.out.println("| V: Voir mon panier      |");
                System.out.println("| M: Menu précédent       |");
                System.out.println("| Q: Quitter              |");
                System.out.println("+-------------------------+");

                String commande = lireCommande();

                if (commande.matches("[1-" + listeMagasins.size() + "]")) {
                    int commandeInt = Integer.parseInt(commande);
                    menuUnMagasin(listeMagasins.get(commandeInt - 1), client);
                } else if (commande.equals("v")) {
                    if (client.getPanier().isEmpty()) {
                        System.out.println("Panier vide.");
                        attendreEntree();
                    } else {
                        for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()) {
                            String livre = coupleLivre.getKey().getNomLivre();
                            int quantite = coupleLivre.getValue();
                            System.out.println(livre + " (" + quantite + ")\n");
                        }
                        attendreEntree();
                    }
                } else if (commande.equals("m")) {

                    menu3 = true;
                } else if (commande.equals("q")) {
                    quitterApp = true;
                    menu3 = true;
                } else {
                    System.out.println("Commande invalide.");
                }
            } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des magasins : " + e.getMessage());
            }
        }
    }

    public void menuUnMagasin(Magasin magasin, Client client) {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            String nomMagasin = magasin.getNom();
            int longueurRestante = 24 - nomMagasin.length();
            for (int y = 0; y < longueurRestante; y++) {
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
            } else if (commande.equals("s")) {
                try{
                    Map<Livre, Integer> stock = magasinBD.listeLivreUnMagasin(magasin.getIdMagasin());
                    menuStock(stock, client);
                }
                catch (SQLException e) {System.out.println("Erreur lors de la récupération des magasins : " + e.getMessage());
            }
            } else if (commande.equals("v")) {
                if (client.getPanier().isEmpty()) {
                    System.out.println("Panier vide.");
                    attendreEntree();
                } else {
                    for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()) {
                        String livre = coupleLivre.getKey().getNomLivre();
                        int quantite = coupleLivre.getValue();
                        System.out.println(livre + " (" + quantite + ")\n");
                    }
                    attendreEntree();
                }
            } else if (commande.equals("m")) {
                menu3 = true;
            } else if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else {
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
            System.out.println("| V: Voir mon panier      |");
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
                        } else {
                            client.ajouterLivrePanier(livre);
                        }
                    } else {
                        client.ajouterLivrePanier(livre);
                    }
                }
                else{
                    System.out.println("Le numéro de livre que vous avez saisie n'existe pas");
                    attendreEntree();
                }
            }
            catch(NumberFormatException e){
                if (commande.equals("v")) {
                    if (client.getPanier().isEmpty()) {
                        System.out.println("Panier vide.");
                        attendreEntree();
                    } else {
                        for (Map.Entry<Livre, Integer> coupleLivre : client.getPanier().entrySet()) {
                            String livre = coupleLivre.getKey().getNomLivre();
                            int quantite = coupleLivre.getValue();
                            System.out.println(livre + " (" + quantite + ")\n");
                        }
                        attendreEntree();
                    }
                } else if (commande.equals("m")) {
                    menu3 = true;
                } else if (commande.equals("q")) {
                    quitterApp = true;
                    menu3 = true;
                } else {
                    System.out.println("Commande invalide.");
                }
            }
        }
    }

    private String lireCommande() {
        System.out.print("Commande > ");
        return scanner.nextLine().strip().toLowerCase();
    }

    private void attendreEntree(){
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}