import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Vendeur extends Personne {

    private int idVendeur;

    public Vendeur(String nom, String prenom, String dateDeNaissance, int id) {
        super(nom, prenom, dateDeNaissance);
        this.idVendeur = id;
    }

    public int getIdVendeur() {
        return this.idVendeur;
    }

    public void ajouterLivre(Magasin magasin, Livre livre, int qte) {
        magasin.ajouterLivre(List.of(livre), List.of(qte));
    }

    public void mettreAJour() {

    }

    public boolean disponibiliteLivre(Magasin magasin, Livre livre) {
        int index = magasin.getLivres().indexOf(livre);
        if (index != -1) {
            return magasin.stockLivre.get(index) > 0;
        }
        return false;
    }

    public boolean transfererLivre(Magasin magasinDepart, Magasin magasinArrivee, Livre livre, int qte) {
        int indexDepart = magasinDepart.getLivres().indexOf(livre);
        if (indexDepart == -1 || magasinDepart.stockLivre.get(indexDepart) < qte) {
            return false;
        }
        magasinDepart.stockLivre.set(indexDepart, magasinDepart.stockLivre.get(indexDepart) - qte);

        int indexArrivee = magasinArrivee.getLivres().indexOf(livre);
        if (indexArrivee == -1) {
            magasinArrivee.getLivres().add(livre);
            magasinArrivee.stockLivre.add(qte);
        } else {
            magasinArrivee.stockLivre.set(indexArrivee, magasinArrivee.stockLivre.get(indexArrivee) + qte);
        }
        return true;
    }

    public Commande passerCommande(Client client, Magasin magasin, List<Livre> listeLivre, List<Integer> qte) {
        Commande nouvelleCommande = new Commande(generateUniqueCommandeId(), "Date actuelle", 'L', client, magasin);
        for (int i = 0; i < listeLivre.size(); i++) {
            nouvelleCommande.ajouterLivre(listeLivre.get(i), qte.get(i));
        }
        return nouvelleCommande;
    }

    private int generateUniqueCommandeId() {
        int nextCommandeId = 1;
        return nextCommandeId++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Vendeur)) {
            return false;
        }
        Vendeur tmp = (Vendeur) obj;
        return super.getNom().equals(tmp.getNom()) && super.getPrenom().equals(tmp.getPrenom())
                && super.getDateDeNaissance().equals(tmp.getDateDeNaissance())
                && this.idVendeur == tmp.getIdVendeur();
    }

    @Override
    public String toString() {
        return super.toString() + ", fait partie des vendeurs, et possède l'id " + this.idVendeur;
    }
}