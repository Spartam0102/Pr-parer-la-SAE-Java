import java.sql.SQLException;
import java.sql.ResultSet;
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
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Administrateur          |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| A: Afficher magasins    |");
            System.out.println("| J: Ajouter magasins     |");
            System.out.println("| U: Supprimer magasins   |");
            System.out.println("| C: Créer compte vendeur |");
            System.out.println("| G: Gérer stocks globaux |");
            System.out.println("| S: Consulter stat vente |");
            System.out.println("| P: Menu précédent       |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;

            } else if (commande.equals("a")) {
                afficherMagasins();
            } else if (commande.equals("j")) {
                ajouterMagasin();
            
            } else if (commande.equals("u")) {
                supprimerMagasin();

            } else if (commande.equals("c")) {

            } else if (commande.equals("g")) {

            } else if (commande.equals("s")) {

            } else if (commande.equals("p")) {
                menu3 = true;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    private void afficherMagasins() {

        try {
            List<Magasin> listeMagasins = magasinBD.listeDesMagasins();

            System.out.println("\nListe des magasins :");
            System.out.println("========================");

            if (listeMagasins.isEmpty()) {
                System.out.println("Aucun magasin trouvé dans la base de données.");
            } else {
                for (Magasin magasin : listeMagasins) {
                    System.out.println(magasin.getNom() +
                            " (Ville: " + magasin.getVille() +
                            ", ID: " + magasin.getIdMagasin() + ")");
                }
            }
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des magasins : " + e.getMessage());
        }
    }

    private void ajouterMagasin() {

        System.out.print("Nom du magasin > ");
        String nomR = System.console().readLine();
        System.out.print("Ville du magasin > ");
        String villem = System.console().readLine();
        Magasin nouveau = new Magasin(nomR, villem, 1);
        try {
            int nb = magasinBD.insererMagasin(nouveau);
            System.out.println("magasin bien inserer" + nb);
        } catch (SQLException ex) {
            System.out.println("erreur");

        }
    }

    private void supprimerMagasin() {

        System.out.print("id du magasin > ");
        String idStr = System.console().readLine();
        
        try {
            int id = Integer.parseInt(idStr);
            magasinBD.effacerMagasin(id);
            System.out.println("magasin bien supprimé");
        } catch (SQLException ex) {
            System.out.println("erreur");

        }
    }

    private String lireCommande() {
        System.out.print("Commande > ");
        return scanner.nextLine().strip().toLowerCase();
    }
}