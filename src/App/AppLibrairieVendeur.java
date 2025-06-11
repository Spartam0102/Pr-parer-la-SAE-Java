package App;

import BD.*;
import Java.*;

import java.sql.SQLException;
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
        System.out.println("========================");
        System.out.print("Votre id > ");
        try {
            String idStr = System.console().readLine();
            int id = Integer.parseInt(idStr);
            Vendeur vendeur = vendeurBD.recupererVendeur(id);
            if (vendeur == null) {
                System.out.println("Cet id n'existe pas");
            }
            return vendeur;
        } catch (NumberFormatException e) {
            System.out.println("Veuillez rentrer un entier");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du vendeur : " + e.getMessage());
        }
        return null;
    }

    public void menuVendeur() {
        Vendeur vendeur = this.menuvendeurConnexion();
        while (vendeur == null) {
            vendeur = this.menuvendeurConnexion();
        }

        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Vendeur " + vendeur.getIdVendeur() + "               |");
            System.out.println("+-------------------------+");
            System.out.println("| I: Infos Personnelles   |");
            System.out.println("| A: Afficher magasins    |");
            System.out.println("| V: Voir un panier       |");
            System.out.println("| T: Transferer un livre  |");
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            switch (commande) {
                case "i":
                    System.out.println(vendeur);
                    break;

                case "a":
                    menuMagasins(vendeur);
                    break;

                case "v":
                    voirPanierClient();
                    break;

                case "t":
                    transfererLivreEntreMagasins(vendeur);
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

    private void voirPanierClient() {
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
        attendreEntree();
    }


    private void menuUnMagasin(Magasin magasin, Vendeur vendeur) {
    boolean quitterMagasin = false;
    while (!quitterMagasin && !quitterApp) {
        System.out.println("+------------------------------+");
        System.out.println("| Magasin : " + magasin.getNom());
        System.out.println("+------------------------------+");
        System.out.println("| M: Menu précédent             |");
        System.out.println("| Q: Quitter                   |");
        System.out.println("+------------------------------+");

        String commande = lireCommande();

        switch (commande) {
            case "m":
                quitterMagasin = true;
                break;

            case "q":
                quitterApp = true;
                quitterMagasin = true;
                break;

            default:
                System.out.println("Commande invalide.");
                break;
        }
    }
}


    
private void transfererLivreEntreMagasins(Vendeur vendeur) {
    try {
        // Étape 1 : Choix du magasin source (doit être le magasin du vendeur ? ou un autre magasin ?)
        System.out.println("Sélectionnez le magasin source :");
        List<Magasin> magasins = magasinBD.listeDesMagasins();
        if (magasins.isEmpty()) {
            System.out.println("Aucun magasin disponible.");
            attendreEntree();
            return;
        }
        for (int i = 0; i < magasins.size(); i++) {
            System.out.println((i + 1) + ". " + magasins.get(i).getNom());
        }
        System.out.print("Numéro du magasin source > ");
        int indexMagasinSource = Integer.parseInt(scanner.nextLine()) - 1;
        if (indexMagasinSource < 0 || indexMagasinSource >= magasins.size()) {
            System.out.println("Choix invalide.");
            attendreEntree();
            return;
        }
        Magasin magasinSource = magasins.get(indexMagasinSource);

        // Étape 2 : Afficher le stock du magasin source
        Map<Livre, Integer> stockSource = magasinBD.listeLivreUnMagasin(magasinSource.getIdMagasin());

        if (stockSource.isEmpty()) {
            System.out.println("Le magasin source n'a aucun livre en stock.");
            attendreEntree();
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
            attendreEntree();
            return;
        }
        Livre livreChoisi = livresStock[indexLivre];

        System.out.print("Quantité à transférer > ");
        int quantite = Integer.parseInt(scanner.nextLine());
        if (quantite <= 0 || quantite > stockSource.get(livreChoisi)) {
            System.out.println("Quantité invalide.");
            attendreEntree();
            return;
        }

        // Étape 3 : Choix du magasin destination
        System.out.println("Sélectionnez le magasin destination :");
        for (int j = 0; j < magasins.size(); j++) {
            if (j != indexMagasinSource) {
                System.out.println((j + 1) + ". " + magasins.get(j).getNom());
            }
        }
        System.out.print("Numéro du magasin destination > ");
        int indexMagasinDestination = Integer.parseInt(scanner.nextLine()) - 1;
        if (indexMagasinDestination < 0 || indexMagasinDestination >= magasins.size() || indexMagasinDestination == indexMagasinSource) {
            System.out.println("Choix invalide.");
            attendreEntree();
            return;
        }
        Magasin magasinDestination = magasins.get(indexMagasinDestination);

        // Étape 4 : Effectuer le transfert via la méthode du vendeur
        Map<Livre, Integer> livresATransferer = Map.of(livreChoisi, quantite);
        vendeur.transfererLivre(magasinDestination, livresATransferer);
        System.out.println("Transfert effectué avec succès.");
        attendreEntree();

    } catch (NumberFormatException e) {
        System.out.println("Entrée invalide.");
        attendreEntree();
    } catch (Exception e) {
        System.out.println("Erreur : " + e.getMessage());
        attendreEntree();
    }
}
    

public void menuMagasins(Vendeur vendeur) {
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
            System.out.println("| M: Menu précédent       |");
            System.out.println("| Q: Quitter              |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.matches("[1-" + listeMagasins.size() + "]")) {
                int commandeInt = Integer.parseInt(commande);
                menuUnMagasin(listeMagasins.get(commandeInt - 1), vendeur);
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


    private String lireCommande() {
        System.out.print("Commande > ");
        return scanner.nextLine().strip().toLowerCase();
    }

    private void attendreEntree() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}
