import java.util.ArrayList;
import java.util.List;

public class Livre {

    private int idLivre;
    private String nomLivre;
    private String dateDePublication;
    private int prix;
    private int nbPage;
    private List<Classification> classifications;
    private Editeur editeur;
    private Auteur auteur;
    private List<Magasin> magasins;

    public Livre(int id, String nomLivre, String dateDePublication, int prix, int nbPage, List<Classification> classifications,Editeur editeur, Auteur auteur){
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
    public Auteur getAuteur() {
        return this.auteur;
    }
    public List<Classification> getClassification() {
        return this.classifications;
    }
    public String getDateDePublication() {
        return this.dateDePublication;
    }
    public Editeur getEditeur() {
        return this.editeur;
    }
    public int getIdLivre() {
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
    public int getPrix() {
        return this.prix;
    }
    public void addMagasins(Magasin magasin){
        this.magasins.add(magasin);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if(!(obj instanceof Livre)){
            return false;
        }
        Livre tmp = (Livre) obj;
        return this.idLivre == tmp.idLivre && this.prix == tmp.prix && this.nbPage == tmp.nbPage && this.nomLivre.equals(tmp.nomLivre) && this.dateDePublication.equals(tmp.dateDePublication) 
        && this.classifications.equals(tmp.classifications) && this.editeur.equals(tmp.editeur) && this.auteur.equals(tmp.auteur);
    }
    @Override
    public String toString(){
        String res = "Le livre " + this.nomLivre + " d'id " + this.idLivre + ", écrit par " + this.auteur.getPrenom() + " " + this.auteur.getNom() + ", a été publié le " + this.dateDePublication
         + " par " + this.editeur.getNomEdit() + ". Il coûte " + this.prix + "€ et contient " + this.nbPage + " pages. Il a comme classification";
        for (Classification classi : this.classifications){
            res += " " + classi.getNomclass();
        }
        res += ". Ce livre est disponnible ";
        if (this.magasins.isEmpty()){
            res += "dans aucun magasin";
        }
        else if (this.magasins.size() == 1){
            res += "dans le magasin " + this.magasins.get(0);
        }
        else{
            res += "dans les magasins : ";
            for (int i = 0 ; i < this.magasins.size() - 1 ; i += 1){
                res += this.magasins.get(i).getNom() + ", ";
            }
            res += "et " + this.magasins.get(this.magasins.size() - 1).getNom() + ".";
        }
        return res;
    }
}