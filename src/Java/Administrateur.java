package Java;

/**
 * La classe {@code Administrateur} représente un administrateur hérité de la
 * classe {@code Personne}.
 * Elle ajoute un identifiant spécifique à l'administrateur.
 * 
 * @see Personne
 */
public class Administrateur extends Personne {

    /** Identifiant unique de l'administrateur. */
    private int idAdmin;

    /**
     * Constructeur de la classe {@code Administrateur}.
     * 
     * @param nom             le nom de l'administrateur
     * @param prenom          le prénom de l'administrateur
     * @param dateDeNaissance la date de naissance de l'administrateur au format
     *                        chaîne
     * @param idAdmin         l'identifiant unique de l'administrateur
     */
    public Administrateur(String nom, String prenom, String dateDeNaissance, int idAdmin) {
        super(nom, prenom, dateDeNaissance);
        this.idAdmin = idAdmin;
    }

    /**
     * Retourne l'identifiant de l'administrateur.
     * 
     * @return l'identifiant unique de l'administrateur
     */
    public int getIdAdmin() {
        return this.idAdmin;
    }

    /**
     * Vérifie si cet objet est égal à un autre objet.
     * Deux administrateurs sont considérés comme égaux s'ils ont le même nom,
     * prénom, date de naissance et identifiant.
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
        if (!(obj instanceof Administrateur)) {
            return false;
        }
        Administrateur tmp = (Administrateur) obj;
        return super.getNom().equals(tmp.getNom()) && super.getPrenom().equals(tmp.getPrenom())
                && super.getDateDeNaissance().equals(tmp.getDateDeNaissance())
                && this.idAdmin == tmp.getIdAdmin();
    }

    /**
     * Retourne une représentation textuelle de l'administrateur.
     * 
     * @return une chaîne représentant l'administrateur
     */
    @Override
    public String toString() {
        return super.toString() + ", fait partie des administrateurs, et possède l'id " + this.idAdmin;
    }
}
