package App; 


import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Client extends Personne{
    
    private int ideCli;
    private String adresse;
    private Map<Livre, Integer> panier;

    public Client(String nom, String prenom, String dateDeNaissance, int id, String adresse){
        super(nom, prenom, dateDeNaissance);
        this.ideCli = id;
        this.adresse = adresse;
        this.panier = new HashMap<>();
    }

    public int getIdCli() {
        return this.ideCli;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Map<Livre, Integer> getPanier() {
        return this.panier;
    }

    public void ajouterLivrePanier(Livre livre){
        if (this.panier.containsKey(livre)){
            int nouvelleQuantite = this.panier.get(livre) + 1;
            this.panier.put(livre, nouvelleQuantite);
        } 
        else{
            this.panier.put(livre, 1);
        }
    }
    
    public boolean ajouterLivresACommande(Commande commande, List<Livre> livres, List<Integer> qtes) {
        if (!commande.getClient().equals(this)) {
            return false;
        }
        for (int i = 0; i < livres.size(); i++) {
            commande.ajouterLivre(livres.get(i), qtes.get(i));
        }
        return true;
    }

    public boolean choisirModeReception(Commande commande , char modeDeReception){
        if (this.equals(commande.getClient())){
            commande.setModeDeReception(modeDeReception);
            return true;
        }
        else{return false;}
    }

    public Map<Livre, Integer> consulterCatalogue(Magasin magasin){
        return magasin.getStockLivre();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if(!(obj instanceof Client)){
            return false;
        }
        Client tmp = (Client) obj;
        return super.getNom().equals(tmp.getNom()) && super.getPrenom().equals(tmp.getPrenom()) && super.getDateDeNaissance().equals(tmp.getDateDeNaissance()) 
                && this.ideCli == tmp.getIdCli() && this.adresse.equals(tmp.getAdresse());
    }
    @Override
    public String toString(){
        return super.toString() + ", fait partie des clients, et possède l'id " + this.ideCli + ". Elle vit à l'adresse " + this.adresse;
    }
}