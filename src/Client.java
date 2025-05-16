import java.util.List;

public class Client extends Personne{
    
    private int ideCli;
    private String adresse;

    public Client(String nom, String prenom, String dateDeNaissance, int id, String adresse){
        super(nom, prenom, dateDeNaissance);
        this.ideCli = id;
        this.adresse = adresse;
    }

    public int getIdCli() {
        return this.ideCli;
    }

    public String getAdresse() {
        return this.adresse;
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

    public void consulterCatalogue(Magasin magasin){

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