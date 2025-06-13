package Java; 
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Livre {

    private long idLivre;
    private String nomLivre;
    private String dateDePublication;
    private double prix;
    private int nbPage;
    private List<String> classifications;
    private List<Integer> editeur;
    private List<String> auteur;
    private List<Magasin> magasins;

    public Livre(long id, String nomLivre, String dateDePublication, double prix, int nbPage,
            List<String> classifications, List<Integer> editeur, List<String> auteur) {
        this.idLivre = id;
        this.nomLivre = nomLivre;
        this.dateDePublication = dateDePublication;
        this.nbPage = nbPage;
        this.classifications = classifications;
        this.editeur = editeur;
        this.auteur = auteur;
        this.prix = prix;
        this.magasins = new ArrayList<>();
    }
 
    public List<String> getAuteur() {
        return this.auteur;
    }

    public List<String> getClassification() {
        return this.classifications;
    }
    public List<Integer> getEditeur() {
        return this.editeur;
    }


    public String getDateDePublication() {
        return this.dateDePublication;
    }



    public long getIdLivre() {
        return this.idLivre;
    }

    public List<Magasin> getMagasins() {
        return this.magasins;
    }

    public int getNbPage() {
        return this.nbPage;
    }

    public String getNomLivre() {
        return this.nomLivre;
    }

    public double getPrix() {
        return this.prix;
    }

    public void addMagasins(Magasin magasin) {
        this.magasins.add(magasin);
    }

    @Override
public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || !(obj instanceof Livre)) {
        return false;
    }
    Livre tmp = (Livre) obj;

    return this.idLivre == tmp.idLivre
        && Double.compare(this.prix, tmp.prix) == 0
        && this.nbPage == tmp.nbPage
        && Objects.equals(this.nomLivre, tmp.nomLivre)
        && Objects.equals(this.dateDePublication, tmp.dateDePublication)
        && Objects.equals(this.classifications, tmp.classifications)
        && Objects.equals(this.editeur, tmp.editeur)
        && Objects.equals(this.auteur, tmp.auteur);
}
/* 
    @Override
    public String toString() {
        String res = "Le livre " + this.nomLivre + " d'id " + this.idLivre + ", Ã©crit par " + this.auteur.getPrenom()
                + " " + this.auteur.getNom() + ", a Ã©tÃ© publiÃ© le " + this.dateDePublication
                + " par " + this.editeur.getNomEdit() + ". Il coÃ»te " + this.prix + "â‚¬ et contient " + this.nbPage
                + " pages. Il a comme classification";

        if (this.magasins.isEmpty()) {
            res += "dans aucun magasin";
        } else if (this.magasins.size() == 1) {
            res += "dans le magasin " + this.magasins.get(0);
        } else {
            res += "dans les magasins : ";
            for (int i = 0; i < this.magasins.size() - 1; i += 1) {
                res += this.magasins.get(i).getNom() + ", ";
            }
            res += "et " + this.magasins.get(this.magasins.size() - 1).getNom() + ".";
        }
        return res;
    }
*/
}