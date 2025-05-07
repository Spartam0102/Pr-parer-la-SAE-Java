import java.util.ArrayList;
import java.util.List;

public class Magasin {
    private String nom;
    private String ville;
    private int idMagasin;
    private List<Livre>livres;
    private List<Integer>stockLivre;

    public Magasin(String nom,String ville,int idMagasin) {
        this.nom=nom;
        this.ville=ville;
        this.idMagasin=idMagasin;
        this.livres = new ArrayList<>();
        this.stockLivre = new ArrayList<>();
        
    }
    public int getIdMagasin() {
        return this.idMagasin;
    }
    public List<Livre> getLivres() {
        return this.livres;
    }
    public String getNom() {
        return this.nom;
    }
    public String getVille() {
        return this.ville;
    }
    
    public void ajouterLivre(List <Livre> listeLivre, List<Integer> qte){
        }
    
    @Override
    public String toString(){
        return "Le magasin " + this.nom +" est situé " + this.ville + " et possède l'ID " + this.idMagasin;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if(!(obj instanceof Magasin)){
            return false;
        }
        Magasin tmp = (Magasin) obj;
        return this.nom.equals(tmp.nom) && this.idMagasin == tmp.idMagasin && this.ville.equals(tmp.ville);
    }

    
}
