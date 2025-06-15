package Java;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La classe {@code Livre} représente un livre avec ses caractéristiques principales,
 * telles que son identifiant, nom, date de publication, prix, nombre de pages,
 * ainsi que ses classifications, éditeurs, auteurs et magasins associés.
 */
public class Livre {

    /** Identifiant unique du livre. */
    private long idLivre;

    /** Nom ou titre du livre. */
    private String nomLivre;

    /** Date de publication du livre. */
    private String dateDePublication;

    /** Prix du livre. */
    private double prix;

    /** Nombre de pages du livre. */
    private int nbPage;

    /** Liste des classifications associées au livre. */
    private List<String> classifications;

    /** Liste des identifiants des éditeurs du livre. */
    private List<Integer> editeur;

    /** Liste des auteurs du livre. */
    private List<String> auteur;

    /** Liste des magasins où le livre est disponible. */
    private List<Magasin> magasins;

    /**
     * Constructeur de la classe {@code Livre}.
     *
     * @param id                  identifiant unique du livre
     * @param nomLivre            nom ou titre du livre
     * @param dateDePublication   date de publication du livre
     * @param prix                prix du livre
     * @param nbPage              nombre de pages du livre
     * @param classifications     liste des classifications du livre
     * @param editeur             liste des identifiants des éditeurs
     * @param auteur              liste des auteurs
     */
    public Livre(long id, String nomLivre, String dateDePublication, double prix, int nbPage,
            List<String> classifications, List<Integer> editeur, List<String> auteur) {
        this.idLivre = id;
        this.nomLivre = nomLivre;
        this.dateDePublication = dateDePublication;
        this.nbPage = nbPage;
        this.classifications = classifications;
        this.editeur = editeur;
        this.auteur = auteur;
        this.prix = prix;
        this.magasins = new ArrayList<>();
    }

    /**
     * Retourne la liste des auteurs du livre.
     *
     * @return liste des auteurs
     */
    public List<String> getAuteur() {
        return this.auteur;
    }

    /**
     * Retourne la liste des classifications du livre.
     *
     * @return liste des classifications
     */
    public List<String> getClassification() {
        return this.classifications;
    }

    /**
     * Retourne la liste des identifiants des éditeurs du livre.
     *
     * @return liste des éditeurs
     */
    public List<Integer> getEditeur() {
        return this.editeur;
    }

    /**
     * Retourne la date de publication du livre.
     *
     * @return date de publication
     */
    public String getDateDePublication() {
        return this.dateDePublication;
    }

    /**
     * Retourne l'identifiant unique du livre.
     *
     * @return identifiant du livre
     */
    public long getIdLivre() {
        return this.idLivre;
    }

    /**
     * Retourne la liste des magasins où le livre est disponible.
     *
     * @return liste des magasins
     */
    public List<Magasin> getMagasins() {
        return this.magasins;
    }

    /**
     * Retourne le nombre de pages du livre.
     *
     * @return nombre de pages
     */
    public int getNbPage() {
        return this.nbPage;
    }

    /**
     * Retourne le nom (titre) du livre.
     *
     * @return nom du livre
     */
    public String getNomLivre() {
        return this.nomLivre;
    }

    /**
     * Retourne le prix du livre.
     *
     * @return prix du livre
     */
    public double getPrix() {
        return this.prix;
    }

    /**
     * Ajoute un magasin à la liste des magasins où le livre est disponible.
     *
     * @param magasin magasin à ajouter
     */
    public void addMagasins(Magasin magasin) {
        this.magasins.add(magasin);
    }

    /**
     * Modifie le prix du livre.
     *
     * @param prix nouveau prix
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }

    /**
     * Vérifie si ce livre est égal à un autre objet.
     * Deux livres sont égaux si leurs identifiants, prix, nombre de pages,
     * nom, date de publication, classifications, éditeurs et auteurs sont égaux.
     *
     * @param obj objet à comparer
     * @return {@code true} si les livres sont égaux, {@code false} sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Livre)) {
            return false;
        }
        Livre tmp = (Livre) obj;

        return this.idLivre == tmp.idLivre
                && Double.compare(this.prix, tmp.prix) == 0
                && this.nbPage == tmp.nbPage
                && Objects.equals(this.nomLivre, tmp.nomLivre)
                && Objects.equals(this.dateDePublication, tmp.dateDePublication)
                && Objects.equals(this.classifications, tmp.classifications)
                && Objects.equals(this.editeur, tmp.editeur)
                && Objects.equals(this.auteur, tmp.auteur);
    }

    /**
     * Retourne une représentation textuelle du livre.
     *
     * @return description du livre
     */
    @Override
    public String toString() {
        return "Le livre " + nomLivre + " (ID: " + idLivre + "), publié le " + dateDePublication +
                ", compte " + nbPage + " pages. Son prix est de " + prix + " euros. ";
    }
}
