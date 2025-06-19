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
    private Auteur auteur;

    /** Liste des magasins où le livre est disponible. */
    private List<Magasin> magasins;

    /**
     * Constructeur complet de la classe {@code Livre}.
     *
     * @param id                  identifiant unique du livre
     * @param nomLivre            nom ou titre du livre
     * @param dateDePublication   date de publication du livre
     * @param prix                prix du livre
     * @param nbPage              nombre de pages du livre
     * @param classifications     liste des classifications du livre
     * @param editeur             liste des identifiants des éditeurs
     * @param auteur              auteur du livre
     */
    public Livre(long id, String nomLivre, String dateDePublication, double prix, int nbPage,
            List<String> classifications, List<Integer> editeur, Auteur auteur) {
        this.idLivre = id;
        this.nomLivre = nomLivre;
        this.dateDePublication = dateDePublication;
        this.prix = prix;
        this.nbPage = nbPage;
        this.classifications = classifications != null ? classifications : new ArrayList<>();
        this.editeur = editeur != null ? editeur : new ArrayList<>();
        this.auteur = auteur;
        this.magasins = new ArrayList<>();
    }

    public Livre(String nomLivre, Auteur auteur, int nbPage, double prix) {
        this.idLivre = 0; 
        this.nomLivre = nomLivre;
        this.auteur = auteur;
        this.nbPage = nbPage;
        this.prix = prix;
        
    }

    /**
     * Constructeur simplifié pour la création rapide d'un livre.
     * Utilisé principalement pour l'ajout de nouveaux livres via l'interface.
     *
     * @param titre    titre du livre
     * @param auteur   auteur du livre
     * @param isbn     ISBN du livre (utilisé comme identifiant)
     * @param pages    nombre de pages
     * @param prix     prix du livre
     */
    public Livre(String titre, Auteur auteur, long isbn, int pages, double prix) {
        this.nomLivre = titre;
        this.auteur = auteur;
        this.idLivre = isbn;
        this.nbPage = pages;
        this.prix = prix;
        this.dateDePublication = null; // À définir ultérieurement si nécessaire
        this.classifications = new ArrayList<>();
        this.editeur = new ArrayList<>();
        this.magasins = new ArrayList<>();
    }

    public Livre(String titre, Auteur auteur, int pages, double prix, String dateDePublication) {
    this.nomLivre = titre;
    this.auteur = auteur;
    this.nbPage = pages;
    this.prix = prix;
    this.dateDePublication = dateDePublication;
    this.classifications = new ArrayList<>();
    this.editeur = new ArrayList<>();
    this.magasins = new ArrayList<>();
    this.idLivre = 0; 
}


    /**
     * Constructeur alternatif acceptant un ISBN sous forme de chaîne.
     * Convertit automatiquement l'ISBN en long.
     *
     * @param titre     titre du livre
     * @param auteur    auteur du livre
     * @param isbnStr   ISBN du livre sous forme de chaîne
     * @param pages     nombre de pages
     * @param prix      prix du livre
     * @throws NumberFormatException si l'ISBN n'est pas un nombre valide
     */
    public Livre(String titre, Auteur auteur, String isbnStr, int pages, double prix) {
        this(titre, auteur, Long.parseLong(isbnStr), pages, prix);
    }

    // === GETTERS ===

    /**
     * Retourne l'auteur du livre.
     *
     * @return l'auteur du livre
     */
    public Auteur getAuteur() {
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

    // === SETTERS ===

    /**
     * Modifie le prix du livre.
     *
     * @param prix nouveau prix
     * @throws IllegalArgumentException si le prix est négatif
     */
    public void setPrix(double prix) {
        if (prix < 0) {
            throw new IllegalArgumentException("Le prix ne peut pas être négatif");
        }
        this.prix = prix;
    }

    /**
     * Modifie la date de publication du livre.
     *
     * @param dateDePublication nouvelle date de publication
     */
    public void setDateDePublication(String dateDePublication) {
        this.dateDePublication = dateDePublication;
    }

    /**
     * Modifie l'auteur du livre.
     *
     * @param auteur nouvel auteur
     */
    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public void setIsbn(long isbn) {
        this.idLivre = isbn;
    }

    // === MÉTHODES UTILITAIRES ===

    /**
     * Ajoute un magasin à la liste des magasins où le livre est disponible.
     *
     * @param magasin magasin à ajouter
     */
    public void addMagasins(Magasin magasin) {
        if (magasin != null && !this.magasins.contains(magasin)) {
            this.magasins.add(magasin);
        }
    }

    /**
     * Ajoute une classification au livre.
     *
     * @param classification classification à ajouter
     */
    public void addClassification(String classification) {
        if (classification != null && !classification.trim().isEmpty() 
            && !this.classifications.contains(classification)) {
            this.classifications.add(classification);
        }
    }

    /**
     * Ajoute un éditeur au livre.
     *
     * @param idEditeur identifiant de l'éditeur à ajouter
     */
    public void addEditeur(Integer idEditeur) {
        if (idEditeur != null && !this.editeur.contains(idEditeur)) {
            this.editeur.add(idEditeur);
        }
    }

    /**
     * Vérifie si le livre a une date de publication définie.
     *
     * @return true si la date de publication est définie, false sinon
     */
    public boolean hasDateDePublication() {
        return this.dateDePublication != null && !this.dateDePublication.trim().isEmpty();
    }

    // === MÉTHODES OVERRIDÉES ===

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
     * Génère un code de hachage pour ce livre.
     *
     * @return code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(idLivre, nomLivre, dateDePublication, prix, nbPage, 
                           classifications, editeur, auteur);
    }

    /**
     * Retourne une représentation textuelle du livre.
     *
     * @return description du livre
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Le livre \"").append(nomLivre).append("\" (ID: ").append(idLivre).append(")");
        
        if (hasDateDePublication()) {
            sb.append(", publié le ").append(dateDePublication);
        }
        
        sb.append(", compte ").append(nbPage).append(" pages");
        sb.append(". Son prix est de ").append(String.format("%.2f", prix)).append(" euros.");
        
        if (auteur != null) {
            sb.append(" Auteur: ").append(auteur.toString());
        }
        
        return sb.toString();
    }
}