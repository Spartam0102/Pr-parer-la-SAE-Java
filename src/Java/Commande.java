package Java; 

import java.util.Map;
import java.util.HashMap;

public class Commande {

    private String dateDeCommande;
    private double prixTotal;
    private char modeDeReception;
    private int idCommande;
    private Magasin magasin;
    private Client client;
    private Map<Livre, Integer> livresCommande;

    public Commande(int idCommande, String dateDeCommande, char modeDeReception, Client client, Magasin magasin){
    this.idCommande = idCommande;
    this.dateDeCommande = dateDeCommande;
    this.modeDeReception = modeDeReception;
    this.client = client;
    this.magasin = magasin;
    this.prixTotal = 0.0;
    this.livresCommande = new HashMap<>();
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

    public Magasin getMagasin() {
        return this.magasin;
    }

    public char getModeDeReception() {
        return this.modeDeReception;
    }

    public double getPrixTotal() {
        return this.prixTotal;
    }

    public Map<Livre, Integer> getLivresCommande() {
        return this.livresCommande;
    }

    public void ajouterLivre(Livre livre, Integer quantite) {
        this.livresCommande.put(livre, quantite);
        calculerPrixTotal();
    }

    public void calculerPrixTotal() {
        this.prixTotal = 0;
        for (Integer quantite : this.livresCommande.values()){
            double quantiteDouble = quantite;
            this.prixTotal += quantiteDouble;
        }
    }

    public void editerFacture() {
        
    }

    public void setModeDeReception(char modeDeReception) {
        this.modeDeReception = modeDeReception;
    }

    public void modifierStock() {
        this.magasin.supprimerLivres(this.livresCommande);
}


    @Override
    public String toString() {
        String res = "la commande " + this.idCommande + " datant du " + this.dateDeCommande + " provenant du magasin "
                + this.magasin.getNom() + ", est crée par l'utilisateur "
                + this.client.getPrenom() + " " + this.client.getNom() + " a un prix total de " + this.prixTotal
                + "€ et le mode de reception est " + this.modeDeReception
                + ".\nLes livres se trouvant dans la commande sont : ";
        for (Map.Entry<Livre, Integer> coupleLivre : this.livresCommande.entrySet()){
            Livre livre = coupleLivre.getKey();
            int quantite = coupleLivre.getValue();
            res += livre + " commandé " + quantite + "fois\n";
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Commande)) {
            return false;
        }
        Commande tmp = (Commande) obj;
        return this.dateDeCommande.equals(tmp.dateDeCommande) && this.prixTotal == tmp.prixTotal
                && this.modeDeReception == tmp.modeDeReception && this.idCommande == tmp.idCommande
                && this.magasin.equals(tmp.magasin) && this.client.equals(tmp.client);
    }
}