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
        entreprise.ajouterMagasin(librairie);
    }

    public void gérerStock(Magasin magasin, Livre livre, int nouvelleQte) {
        int index = magasin.getLivres().indexOf(livre);
        if (index != -1) {
            magasin.stockLivre.set(index, nouvelleQte);
<<<<<<< HEAD
            System.out.println("Le stock du livre '" + livre.getNomLivre() + "' dans le magasin '" + magasin.getNomMagasin() + "' a été mis à jour à " + nouvelleQte + ".");
        } else {
            System.out.println("Le livre '" + livre.getNomLivre() + "' n'a pas été trouvé dans le magasin '" + magasin.getNomMagasin() + "'.");
=======
            System.out.println("Le stock du livre '" + livre.getNomLivre() + "' dans le magasin '" + magasin.getNom() + "' a été mis à jour à " + nouvelleQte + ".");
        } else {
            System.out.println("Le livre '" + livre.getNomLivre() + "' n'a pas été trouvé dans le magasin '" + magasin.getNom() + "'.");
>>>>>>> 3d19dfdf23abe81cd181156f6a30bc919afe81d7
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