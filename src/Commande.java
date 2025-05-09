import java.util.ArrayList;
import java.util.List;

public class Commande {
    
    private List<Integer> qte;
    private String dateDeCommande;
    private double prixTotal;
    private char modeDeReception;
    private int idCommande; 
    private Magasin magasin;
    private Client client;
    private List<Livre> livresCommander;

    public Commande(int idCommande, String dateDeCommande, char modeDeReception, Client client, Magasin magasin){
    this.idCommande = idCommande;
    this.dateDeCommande = dateDeCommande;
    this.modeDeReception = modeDeReception;
    this.client = client;
    this.magasin = magasin;
    this.qte = new ArrayList<>();
    this.livresCommander = new ArrayList<>();
    this.prixTotal = 0.0; // Initialiser à 0
}
    
    public Client getClient() {
        return this.client;
    }
    public String getDateDeCommande() {
        return this.dateDeCommande;
    }
    public int getIdCommande() {
        return this.idCommande;
    }
    public List<Livre> getLivresCommander() {
        return this.livresCommander;
    }
    public Magasin getMagasin() {
        return this.magasin;
    }
    public char getModeDeReception() {
        return this.modeDeReception;
    }
    public double getPrixTotal() {
        return this.prixTotal;
    }
    public List<Integer> getQte() {
        return this.qte;
    }
    public void ajouterLivre(Livre livre, int quantite) {
    this.livresCommander.add(livre);
    this.qte.add(quantite);
    calculerPrixTotal(); // Recalculer le prix total après l'ajout
}
    public void calculerPrixTotal() {
    this.prixTotal = 0;
    for (int i = 0; i < this.livresCommander.size(); i++) {
        this.prixTotal += this.livresCommander.get(i).getPrix() * this.qte.get(i);
    }
}
    public void editerFacture(){

    }

    public void setModeDeReception(char modeDeReception) {
        this.modeDeReception = modeDeReception;
    }

    public void modifierStock(double nouvelleValeur){

    }
    
    @Override
    public String toString(){
        String res = "la commande " + this.idCommande +" datant du "+ this.dateDeCommande +" provenant du magasin "+ this.magasin.getNom() + ", est crée par l'utilisateur " 
        + this.client.getPrenom() + " " + this.client.getNom() + " a un prix total de " + this.prixTotal+ "€ et le mode de reception est " + this.modeDeReception 
        + ".\nLes livres se trouvant dans la commande sont : ";
        for (int i = 0 ; i < this.livresCommander.size() ; i += 1){
            res += this.livresCommander.get(i) + " commandé " + this.qte.get(i) + "fois\n";
        }
        return res;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if(!(obj instanceof Commande)){
            return false;
        }
        Commande tmp = (Commande) obj;
        return this.dateDeCommande.equals(tmp.dateDeCommande) && this.prixTotal == tmp.prixTotal && this.modeDeReception == tmp.modeDeReception && this.idCommande == tmp.idCommande && this.magasin.equals(tmp.magasin) && this.client.equals(tmp.client);
    }
}
