public class Administrateur extends Personne{
    
    private int idAdmin;

    public Administrateur(String nom, String prenom, String dateDeNaissance, int idAdmin){
        super(nom, prenom, dateDeNaissance);
        this.idAdmin = idAdmin;
    }
    public int getIdAdmin() {
        return this.idAdmin;
    }
    
    public void créerCompteVendeur(Vendeur vendeur){
        
        
    }
    public void créerCompteClient(Client client){
        
        
    }



    public void ajouterLibrairie(Entreprise entreprise, Magasin librairie) {
        // Implémenter la logique pour ajouter une librairie à l'entreprise
        // Cela impliquerait probablement d'ajouter le magasin à une liste de magasins gérée par l'entreprise.
        entreprise.ajouterMagasin(librairie);
    }

    public void gérerStock(Magasin magasin, Livre livre, int nouvelleQte) {
        // Implémenter la logique pour gérer le stock d'un livre dans un magasin
        // Cela pourrait impliquer de trouver le livre dans le magasin et de mettre à jour sa quantité en stock.
        int index = magasin.getLivres().indexOf(livre);
        if (index != -1) {
            magasin.stockLivre.set(index, nouvelleQte);
            System.out.println("Le stock du livre '" + livre.getNom() + "' dans le magasin '" + magasin.getNomMagasin() + "' a été mis à jour à " + nouvelleQte + ".");
        } else {
            System.out.println("Le livre '" + livre.getNom() + "' n'a pas été trouvé dans le magasin '" + magasin.getNomMagasin() + "'.");
        }
    }

    public void consulterStat(){

    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if(!(obj instanceof Administrateur)){
            return false;
        }
        Administrateur tmp = (Administrateur) obj;
        return super.getNom().equals(tmp.getNom()) && super.getPrenom().equals(tmp.getPrenom()) && super.getDateDeNaissance().equals(tmp.getDateDeNaissance()) 
        && this.idAdmin == tmp.getIdAdmin();
    }
    @Override
    public String toString(){
        return super.toString() + ", fait partie des administrateurs, et possède l'id " + this.idAdmin;
    }
}