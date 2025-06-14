package Java;

/**
 * Classe abstraite représentant une personne avec un nom, un prénom et une date de naissance.
 */
public abstract class Personne {

    /** Nom de la personne */
    private String nom;

    /** Prénom de la personne */
    private String prenom;

    /** Date de naissance au format String */
    private String dateDeNaissance;

    /**
     * Constructeur de la classe Personne.
     *
     * @param nom            le nom de la personne
     * @param prenom         le prénom de la personne
     * @param dateDeNaissance la date de naissance de la personne
     */
    public Personne(String nom, String prenom, String dateDeNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
    }

    /**
     * Retourne la date de naissance de la personne.
     *
     * @return la date de naissance sous forme de chaîne
     */
    public String getDateDeNaissance() {
        return this.dateDeNaissance;
    }

    /**
     * Retourne le nom de la personne.
     *
     * @return le nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retourne le prénom de la personne.
     *
     * @return le prénom
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * Compare cette personne avec un autre objet.
     * Deux personnes sont égales si elles ont le même nom, prénom et date de naissance.
     *
     * @param obj l'objet à comparer
     * @return true si égal, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Personne)) {
            return false;
        }
        Personne tmp = (Personne) obj;
        return this.nom.equals(tmp.nom) && this.prenom.equals(tmp.prenom)
                && this.dateDeNaissance.equals(tmp.dateDeNaissance);
    }

    /**
     * Retourne une description textuelle de la personne.
     *
     * @return une chaîne décrivant la personne
     */
    @Override
    public String toString() {
        return "La personne nommée " + this.prenom + " " + this.nom + " est née le " + this.dateDeNaissance;
    }
}
