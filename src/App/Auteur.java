package App; 


public class Auteur extends Personne {

    private int idedit;

    public Auteur(String nom, String prenom, String dateDeNaissance, int id) {
        super(nom, prenom, dateDeNaissance);
        this.idedit = id;
    }

    public int getIdedit() {
        return this.idedit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Auteur)) {
            return false;
        }
        Auteur tmp = (Auteur) obj;
        return super.getNom().equals(tmp.getNom()) && super.getPrenom().equals(tmp.getPrenom())
                && super.getDateDeNaissance().equals(tmp.getDateDeNaissance())
                && this.idedit == tmp.getIdedit();
    }

    @Override
    public String toString() {
        return super.toString() + ", fait partie des auteurs, et possède l'id " + this.idedit;
    }
}