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
    
    public void ajouterLivre(Magasin magasin, Livre livre, int qte) {
    magasin.ajouterLivre(List.of(livre), List.of(qte)); // En supposant que la méthode du Magasin prend des listes
}
    
    public void mettreAJour(){

    }
    
    public boolean disponibiliteLivre(Magasin magasin, Livre livre) {
    int index = magasin.getLivres().indexOf(livre);
    if (index != -1) { // Le livre est dans le magasin
        return magasin.stockLivre.get(index) > 0; // En supposant que stockLivre est accessible ou que vous avez un getter
    }
    return false; // Livre non trouvé dans ce magasin
}
    
    public boolean transfererLivre(Magasin magasinDepart, Magasin magasinArrivee, Livre livre, int qte) {
    // Gestion des erreurs : Vérifier si magasinDepart a suffisamment de stock
    int indexDepart = magasinDepart.getLivres().indexOf(livre);
    if (indexDepart == -1 || magasinDepart.stockLivre.get(indexDepart) < qte) {
        return false; // Pas assez de stock dans le magasin de départ
    }

    // Mettre à jour le stock dans magasinDepart
    magasinDepart.stockLivre.set(indexDepart, magasinDepart.stockLivre.get(indexDepart) - qte);

    // Mettre à jour le stock dans magasinArrivee
    int indexArrivee = magasinArrivee.getLivres().indexOf(livre);
    if (indexArrivee == -1) { // Le livre n'est pas dans le magasin d'arrivée, l'ajouter
        magasinArrivee.getLivres().add(livre);
        magasinArrivee.stockLivre.add(qte);
    } else { // Le livre existe dans le magasin d'arrivée, mettre à jour le stock
        magasinArrivee.stockLivre.set(indexArrivee, magasinArrivee.stockLivre.get(indexArrivee) + qte);
    }
    return true;
}

    public Commande passerCommande(Client client, Magasin magasin, List<Livre> listeLivre, List<Integer> qte){
    // Créer une nouvelle Commande
    Commande nouvelleCommande = new Commande(generateUniqueCommandeId(), "Date actuelle", 'L', client, magasin);  // Vous devrez générer un ID unique et obtenir la date actuelle

    // Ajouter des livres à la commande
    for (int i = 0; i < listeLivre.size(); i++) {
        nouvelleCommande.ajouterLivre(listeLivre.get(i), qte.get(i));
    }

    return nouvelleCommande;
}

private int generateUniqueCommandeId() {
    // Implémentez la logique pour générer un ID unique (par exemple, en utilisant un compteur, UUID)
    // Pour simplifier, voici un compteur de base (non thread-safe) :
    int nextCommandeId = 1;
    return nextCommandeId++;
}

private String getCurrentDate() {
    // Implémentez la logique pour obtenir la date actuelle
    return "2024-06-04";
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