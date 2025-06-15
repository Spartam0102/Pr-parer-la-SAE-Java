package Java;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Entreprise} représente une entreprise contenant plusieurs magasins.
 */
public class Entreprise {

    /** Le nom de l'entreprise. */
    private String nom;

    /** L'adresse de l'entreprise. */
    private String adresse;

    /** La liste des magasins associés à l'entreprise. */
    private List<Magasin> listeMagasins;

    /**
     * Constructeur de la classe {@code Entreprise}.
     *
     * @param nom      le nom de l'entreprise
     * @param adresse  l'adresse de l'entreprise
     */
    public Entreprise(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
        this.listeMagasins = new ArrayList<>();
    }

    /**
     * Retourne l'adresse de l'entreprise.
     *
     * @return l'adresse de l'entreprise
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Retourne la liste des magasins de l'entreprise.
     *
     * @return la liste des magasins
     */
    public List<Magasin> getListeMagasins() {
        return listeMagasins;
    }

    /**
     * Retourne le nom de l'entreprise.
     *
     * @return le nom de l'entreprise
     */
    public String getNom() {
        return nom;
    }

    /**
     * Ajoute un magasin à la liste des magasins de l'entreprise.
     *
     * @param magasin le magasin à ajouter
     */
    public void ajouterMagasin(Magasin magasin) {
        this.listeMagasins.add(magasin);
    }

    /**
     * Vérifie si cette entreprise est égale à un autre objet.
     * Deux entreprises sont égales si elles ont le même nom et la même adresse.
     *
     * @param obj l'objet à comparer
     * @return {@code true} si les entreprises sont égales, {@code false} sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Entreprise)) {
            return false;
        }
        Entreprise tmp = (Entreprise) obj;
        return this.nom.equals(tmp.nom) && this.adresse.equals(tmp.adresse);
    }

    /**
     * Retourne une représentation textuelle de l'entreprise.
     *
     * @return une chaîne de caractères décrivant l'entreprise et ses magasins
     */
    @Override
    public String toString() {
        String res = "L'entreprise nommée " + this.nom + " se situe à " + this.adresse + " et a comme magasins : \n";
        for (Magasin magasin : this.listeMagasins) {
            res += magasin.toString() + "\n";
        }
        return res;
    }
}
