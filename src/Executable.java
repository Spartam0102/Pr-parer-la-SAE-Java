import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Executable{

    public static void main(String[] args) {
    
    // Création de l'entreprise
    Entreprise entreprise = new Entreprise("Livre Express", "Paris");

    // Création de magasins
    Magasin magasin1 = new Magasin("Librairie Centrale", "Paris", 1);
    Magasin magasin2 = new Magasin("Le Coin Lecture", "Lyon", 2);
    Magasin magasin3 = new Magasin("Pages et Plumes", "Marseille", 3);
    Magasin magasin4 = new Magasin("Livres & Co", "Toulouse", 4);
    Magasin magasin5 = new Magasin("Univers Livres", "Bordeaux", 5);
    Magasin magasin6 = new Magasin("Lecture Passion", "Nice", 6);
    Magasin magasin7 = new Magasin("Le Livre Bleu", "Nantes", 7);

    // Ajout des magasins dans entreprise
    entreprise.ajouterMagasin(magasin1);
    entreprise.ajouterMagasin(magasin2);
    entreprise.ajouterMagasin(magasin3);
    entreprise.ajouterMagasin(magasin4);
    entreprise.ajouterMagasin(magasin5);
    entreprise.ajouterMagasin(magasin6);
    entreprise.ajouterMagasin(magasin7);

    // Création d'auteurs
    Auteur auteur1 = new Auteur("Hugo", "Victor", "1802-02-26", 1);
    Auteur auteur2 = new Auteur("Orwell", "George", "1903-06-25", 2);
    Auteur auteur3 = new Auteur("Rowling", "J.K.", "1965-07-31", 3);

    // Création d'éditeurs
    Editeur editeur1 = new Editeur(1, "Éditions Classiques");
    Editeur editeur2 = new Editeur(2, "Dystopia Press");
    Editeur editeur3 = new Editeur(3, "Magical Books");

    // Création de classifications
    Classification fiction = new Classification(100, "Fiction");
    Classification classique = new Classification(200, "Classique");
    Classification fantastique = new Classification(300, "Fantastique");
    Classification politique = new Classification(400, "Politique");

    // Création de listes de classifications
    List<Classification> classifications1 = new ArrayList<>();
    classifications1.add(classique);
    classifications1.add(fiction);

    List<Classification> classifications2 = new ArrayList<>();
    classifications2.add(politique);
    classifications2.add(fiction);

    List<Classification> classifications3 = new ArrayList<>();
    classifications3.add(fantastique);

    // Création de livres
    Livre livre1 = new Livre(1, "Les Misérables", "1862-01-01", 20, 1232, classifications1, editeur1, auteur1);
    Livre livre2 = new Livre(2, "1984", "1949-06-08", 15, 328, classifications2, editeur2, auteur2);
    Livre livre3 = new Livre(3, "Harry Potter à l'école des sorciers", "1997-06-26", 25, 309, classifications3, editeur3, auteur3);

    // Ajout des livres dans les magasins
    Map<Livre, Integer> livresAAjouter1 = new HashMap<>(Map.of(livre1, 2, livre2, 4, livre3, 3));
    Map<Livre, Integer> livresAAjouter2 = new HashMap<>(Map.of(livre1, 5, livre3, 2));
    Map<Livre, Integer> livresAAjouter3 = new HashMap<>(Map.of(livre1, 1, livre2, 3, livre3, 5));
    magasin1.ajouterLivres(livresAAjouter1);
    magasin2.ajouterLivres(livresAAjouter2);
    magasin3.ajouterLivres(livresAAjouter3);


    // Création des vendeurs
    Vendeur vendeur1 = new Vendeur("Durand", "Paul", "1985-03-14", 101, magasin3);
    Vendeur vendeur2 = new Vendeur("Martin", "Claire", "1990-07-22", 102, magasin2);
    Vendeur vendeur3 = new Vendeur("Petit", "Julien", "1978-11-02", 103, magasin5);

    // Création des clients
    Client client1 = new Client("Leclerc", "Marie", "1992-03-12", 201, "10 rue des Lilas, Paris");
    Client client2 = new Client("Dupont", "Jean", "1980-07-01", 202, "5 avenue Victor Hugo, Lyon");
    Client client3 = new Client("Moreau", "Alice", "1995-10-25", 203, "33 boulevard Haussmann, Marseille");

    // Création des administrateurs
    Administrateur admin1 = new Administrateur("Lemoine", "Caroline", "1970-05-10", 301);
    Administrateur admin2 = new Administrateur("Bertrand", "Olivier", "1965-11-22", 302);
    Administrateur admin3 = new Administrateur("Fernandez", "Julie", "1983-08-03", 303);

    // Création des commandes
    // Commande 1
    Commande commande1 = new Commande(1001, "2025-05-05", 'L', client1, magasin1);
    commande1.ajouterLivre(livre1, 2);
    commande1.ajouterLivre(livre2, 1);
    // Commande 2
    Commande commande2 = new Commande(1002, "2025-05-06", 'M', client2, magasin2);
    commande2.ajouterLivre(livre3, 1);
    commande2.ajouterLivre(livre2, 3);
    // Commande 3
    Commande commande3 = new Commande(1003, "2025-05-07", 'C', client1, magasin3);
    commande3.ajouterLivre(livre1, 1);

    // Ajout des livres à des magasins
    livre1.addMagasins(magasin1);
    livre1.addMagasins(magasin2);

    livre2.addMagasins(magasin3);
    livre2.addMagasins(magasin4);
    livre2.addMagasins(magasin5);

    livre3.addMagasins(magasin6);
    livre3.addMagasins(magasin7);

    // // Affichage de l'entreprise;
    // System.out.println(entreprise);
    
    // // Affichage des auteurs;
    // System.out.println(auteur1);
    // System.out.println(auteur2);
    // System.out.println(auteur3 + "\n");

    // // Affichage des éditeurs;
    // System.out.println(editeur1);
    // System.out.println(editeur2);
    // System.out.println(editeur3 + "\n");

    // // Afichage des clients;
    // System.out.println(client1);
    // System.out.println(client2);
    // System.out.println(client3 + "\n");

    // // Affichage des administrateurs;
    // System.out.println(admin1);
    // System.out.println(admin2);
    // System.out.println(admin3 + "\n");

    // // Affichage des vendeurs;
    // System.out.println(vendeur1);
    // System.out.println(vendeur2);
    // System.out.println(vendeur3 + "\n");

    // // Affichage des classifications;
    // System.out.println(fiction);
    // System.out.println(classique);
    // System.out.println(fantastique);
    // System.out.println(politique + "\n");

    // // Affichage des livres
    // System.out.println(livre1);
    // System.out.println(livre2);
    // System.out.println(livre3 + "\n");
    
    // // Affichage des commandes
    // System.out.println(commande1);
    // System.out.println(commande2);
    // System.out.println(commande3);

    AppLibrairie app = new AppLibrairie(entreprise);
    app.run();
    }
}