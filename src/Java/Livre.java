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

    public void setPrix(double prix) {
        this.prix = prix;
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

    @Override
    public String toString() {
        return "Le livre " + nomLivre + " (ID: " + idLivre + "), publié le " + dateDePublication +
            ", compte " + nbPage + " pages. Son prix est de " + prix + " euros. ";
    }

}