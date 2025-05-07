import java.util.List;

public class Vendeur extends Personne{
    
    private int idVendeur;

    public Vendeur(String nom, String prenom, String dateDeNaissance, int id){
        super(nom, prenom, dateDeNaissance);
        this.idVendeur = id;
    }
    public int getIdVendeur() {
        return this.idVendeur;
    }
    
    public void ajouterLivre(Livre livre){

    }
    
    public void mettreAJour(){

    }
    
    public boolean disponibilitéLivre (){
        return true; 
    }
    
    public boolean transférerLivre(Magasin magasinDépart, Magasin magasinArrivé, Livre livre, int qte){
        return true; 
    }

    public Commande passerCommande(Client client, Magasin magasin, List<Livre> listeLivre, List<Integer> qte){
        return null;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if(!(obj instanceof Vendeur)){
            return false;
        }
        Vendeur tmp = (Vendeur) obj;
        return super.getNom().equals(tmp.getNom()) && super.getPrenom().equals(tmp.getPrenom()) && super.getDateDeNaissance().equals(tmp.getDateDeNaissance()) 
        && this.idVendeur == tmp.getIdVendeur();
    }
    @Override
    public String toString(){
        return super.toString() + ", fait partie des vendeurs, et possède l'id " + this.idVendeur;
    }
}