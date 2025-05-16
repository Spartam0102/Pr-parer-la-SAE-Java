import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Magasin {
    private String nom;
    private String ville;
    private int idMagasin;
    Map<Livre, Integer> stockLivre;
    List<Vendeur> lesVendeurs;

    public Magasin(String nom, String ville, int idMagasin) {
        this.nom = nom;
        this.ville = ville;
        this.idMagasin = idMagasin;
        this.stockLivre = new HashMap<>();
        this.lesVendeurs = new ArrayList<>();
    }

    public int getIdMagasin() {
        return this.idMagasin;
    }

    public Map<Livre, Integer> getStockLivre() {
        return stockLivre;
    }

    public String getNom() {
        return this.nom;
    }

    public String getVille() {
        return this.ville;
    }

    public List<Vendeur> getLesVendeurs() {
        return lesVendeurs;
    }

    public void ajouterLivres(Map<Livre, Integer> livresAAjouter) {
        for (Map.Entry<Livre, Integer> coupleLivre : livresAAjouter.entrySet()){
            Livre livre = coupleLivre.getKey();
            int quantite = coupleLivre.getValue();
            if (this.stockLivre.containsKey(livre)){
                int nouvelleQuantite = this.stockLivre.get(livre) + quantite;
                this.stockLivre.put(livre, nouvelleQuantite);
            } 
            else{
                this.stockLivre.put(livre, quantite);
            }
        }
    }

    public void supprimerLivres(Map<Livre, Integer> livresASupprimer) {
        for (Map.Entry<Livre, Integer> coupleLivre : livresASupprimer.entrySet()){
            Livre livre = coupleLivre.getKey();
            int quantite = coupleLivre.getValue();
            if (!this.stockLivre.containsKey(livre)){
                System.out.println("Le livre existe pas");   /*faire exception */
            } 
            else{
                int nouvelleQuantite = this.stockLivre.get(livre) - quantite;
                this.stockLivre.put(livre, nouvelleQuantite);
            }
        }
    }

    public void ajouterVendeur(Vendeur vendeur) {
        if (!(this.lesVendeurs.contains(vendeur))) {
            this.lesVendeurs.add(vendeur);
        }
    }

    @Override
    public String toString() {
        return "Le magasin " + this.nom + " est situé " + this.ville + " et possède l'ID " + this.idMagasin;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Magasin)) {
            return false;
        }
        Magasin tmp = (Magasin) obj;
        return this.nom.equals(tmp.nom) && this.idMagasin == tmp.idMagasin && this.ville.equals(tmp.ville);
    }
}