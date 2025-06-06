package App; 

public class Administrateur extends Personne {

    private int idAdmin;

    public Administrateur(String nom, String prenom, String dateDeNaissance, int idAdmin) {
        super(nom, prenom, dateDeNaissance);
        this.idAdmin = idAdmin;
    }

    public int getIdAdmin() {
        return this.idAdmin;
    }

    public Vendeur créerCompteVendeur(String nom, String prenom, String dateDeNaissance, int id, Magasin magasin) {
        return new Vendeur(nom, prenom, dateDeNaissance, id, magasin);
    }

    public Client créerCompteClient(String nom, String prenom, String dateDeNaissance, int id, String adresse) {
        return new Client(nom, prenom, dateDeNaissance, id, adresse);

    }

    public void ajouterLibrairie(Entreprise entreprise, Magasin librairie) {
        entreprise.ajouterMagasin(librairie);
    }

    public void consulterStat() {

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Administrateur)) {
            return false;
        }
        Administrateur tmp = (Administrateur) obj;
        return super.getNom().equals(tmp.getNom()) && super.getPrenom().equals(tmp.getPrenom())
                && super.getDateDeNaissance().equals(tmp.getDateDeNaissance())
                && this.idAdmin == tmp.getIdAdmin();
    }

    @Override
    public String toString() {
        return super.toString() + ", fait partie des administrateurs, et possède l'id " + this.idAdmin;
    }
}
