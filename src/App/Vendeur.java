package App; 

import java.util.Map;

public class Vendeur extends Personne {

    private int idVendeur;
    private Magasin magasin;

    public Vendeur(String nom, String prenom, String dateDeNaissance, int id, Magasin magasin) {
    super(nom, prenom, dateDeNaissance);
    this.idVendeur = id;
    this.magasin = magasin;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public int getIdVendeur() {
        return this.idVendeur;
    }

    public void ajouterLivres(Map<Livre, Integer> livresAAjouter) {
        this.magasin.ajouterLivres(livresAAjouter);
    }

    public void mettreAJour() {

    }

    public boolean disponibiliteLivre(Magasin magasin, Livre livre) {
        if (!this.magasin.getStockLivre().containsKey(livre)){
            return false;
        }
        else{
            return true;
        }
    }

    public void transfererLivre(Magasin magasinArrivee, Map<Livre, Integer> LivresATransferer) {
        this.magasin.supprimerLivres(LivresATransferer);
        magasinArrivee.ajouterLivres(LivresATransferer);
    }

    public Commande passerCommande(Client client, Magasin magasin, Map<Livre, Integer> LivresACommander) {
        Commande nouvelleCommande = new Commande(generateUniqueCommandeId(), "Date actuelle", 'L', client, magasin);
        for (Map.Entry<Livre, Integer> coupleLivre : LivresACommander.entrySet()){
            Livre livre = coupleLivre.getKey();
            int quantite = coupleLivre.getValue();
            nouvelleCommande.ajouterLivre(livre, quantite);
        }
        nouvelleCommande.modifierStock();
        return nouvelleCommande;
    }

    private int generateUniqueCommandeId() {
        int nextCommandeId = 1;
        return nextCommandeId++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Vendeur)) {
            return false;
        }
        Vendeur tmp = (Vendeur) obj;
        return super.getNom().equals(tmp.getNom()) && super.getPrenom().equals(tmp.getPrenom())
                && super.getDateDeNaissance().equals(tmp.getDateDeNaissance())
                && this.idVendeur == tmp.getIdVendeur()
                && this.magasin.equals(tmp.getMagasin());
    }

    @Override
    public String toString() {
        return super.toString() + ", fait partie des vendeurs, il possède l'id " + this.idVendeur + ", et travaille dans " + this.magasin;
    }
}