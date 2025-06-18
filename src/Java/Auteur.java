package Java;

/**
 * La classe {@code Auteur} représente un auteur, qui hérite des attributs de la classe {@code Personne}.
 * Elle ajoute un identifiant d'éditeur spécifique à l'auteur.
 * 
 * @see Personne
 */
public class Auteur extends Personne {

    /** Identifiant d'éditeur associé à l'auteur. */
    private int idedit;

    /**
     * Constructeur de la classe {@code Auteur}.
     * 
     * @param nom             le nom de l'auteur
     * @param prenom          le prénom de l'auteur
     * @param dateDeNaissance la date de naissance de l'auteur au format chaîne
     * @param id              l'identifiant d'éditeur associé à l'auteur
     */
    public Auteur(String nom, String prenom, String dateDeNaissance, int id) {
        super(nom, prenom, dateDeNaissance);
        this.idedit = id;
    }

    /**
     * Retourne l'identifiant d'éditeur de l'auteur.
     * 
     * @return l'identifiant d'éditeur
     */
    public int getIdedit() {
        return this.idedit;
    }

    @Override
    public String getPrenom() {
        return super.getPrenom();
    }

    @Override
    public String getNom() {
        return super.getNom();
    }

    /**
     * Vérifie si cet objet est égal à un autre objet.
     * Deux auteurs sont considérés comme égaux s'ils ont le même nom,
     * prénom, date de naissance et identifiant d'éditeur.
     * 
     * @param obj l'objet à comparer
     * @return {@code true} si les objets sont égaux, {@code false} sinon
     */
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

    /**
     * Retourne une représentation textuelle de l'auteur.
     * 
     * @return une chaîne représentant l'auteur
     */
    @Override
    public String toString() {
        return super.toString() + ", fait partie des auteurs, et possède l'id " + this.idedit;
    }
}
