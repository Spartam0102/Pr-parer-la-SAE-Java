package Java;

/**
 * La classe {@code Classification} représente une classification selon un identifiant Dewey
 * et un nom de classe (genre).
 */
public class Classification {

    /** Identifiant Dewey de la classification. */
    private String iddewey;

    /** Nom de la classe ou du genre associé à la classification. */
    private String nomclass;

    /**
     * Constructeur de la classe {@code Classification}.
     * 
     * @param iddewey  l'identifiant Dewey de la classification
     * @param nomClass le nom du genre associé à cette classification
     */
    public Classification(String iddewey, String nomClass) {
        this.iddewey = iddewey;
        this.nomclass = nomClass;
    }

    /**
     * Retourne l'identifiant Dewey de la classification.
     * 
     * @return l'identifiant Dewey
     */
    public String getIddewey() {
        return this.iddewey;
    }

    /**
     * Retourne le nom du genre ou de la classe associé à la classification.
     * 
     * @return le nom de la classe
     */
    public String getNomclass() {
        return this.nomclass;
    }

    /**
     * Vérifie si cet objet est égal à un autre objet.
     * Deux classifications sont considérées comme égales si elles ont le même
     * identifiant Dewey (référence identique) et le même nom de classe.
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
        if (!(obj instanceof Classification)) {
            return false;
        }
        Classification tmp = (Classification) obj;
        return this.iddewey.equals(tmp.iddewey) && this.nomclass.equals(tmp.nomclass);
    }

    /**
     * Retourne une représentation textuelle de la classification.
     * 
     * @return une chaîne décrivant la classification
     */
    @Override
    public String toString() {
        return "La classification " + this.iddewey + " a comme genre " + this.nomclass;
    }
}
