package Java;

/**
 * La classe {@code Editeur} représente un éditeur avec un identifiant unique et un nom.
 */
public class Editeur {

    /** Identifiant unique de l'éditeur. */
    private int idEdit;

    /** Nom de l'éditeur. */
    private String nomEdit;

    /**
     * Constructeur de la classe {@code Editeur}.
     *
     * @param idEdit   identifiant unique de l'éditeur
     * @param nomEdit  nom de l'éditeur
     */
    public Editeur(int idEdit, String nomEdit) {
        this.idEdit = idEdit;
        this.nomEdit = nomEdit;
    }

    /**
     * Retourne l'identifiant de l'éditeur.
     *
     * @return l'identifiant de l'éditeur
     */
    public int getIdEdit() {
        return this.idEdit;
    }

    /**
     * Retourne le nom de l'éditeur.
     *
     * @return le nom de l'éditeur
     */
    public String getNomEdit() {
        return this.nomEdit;
    }

    /**
     * Vérifie si cet éditeur est égal à un autre objet.
     * Deux éditeurs sont considérés comme égaux s'ils ont le même identifiant et le même nom.
     *
     * @param obj l'objet à comparer
     * @return {@code true} si les éditeurs sont égaux, {@code false} sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Editeur)) {
            return false;
        }
        Editeur tmp = (Editeur) obj;
        return this.nomEdit.equals(tmp.nomEdit) && this.idEdit == tmp.idEdit;
    }

    /**
     * Retourne une représentation textuelle de l'éditeur.
     *
     * @return une chaîne décrivant l'éditeur
     */
    @Override
    public String toString() {
        return "L'éditeur " + this.nomEdit + " possède l'id " + this.idEdit;
    }
}
