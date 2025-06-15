package Java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Exception.LivreInexistantException;

import java.util.HashMap;

/**
 * Représente un magasin avec un nom, une ville, un identifiant,
 * un stock de livres et une liste de vendeurs associés.
 */
public class Magasin {
    /** Nom du magasin */
    private String nom;

    /** Ville où se situe le magasin */
    private String ville;

    /** Identifiant unique du magasin */
    private int idMagasin;

    /** Stock de livres avec les quantités disponibles */
    Map<Livre, Integer> stockLivre;

    /** Liste des vendeurs travaillant dans le magasin */
    List<Vendeur> lesVendeurs;

    /**
     * Constructeur pour créer un magasin.
     * 
     * @param nom       nom du magasin
     * @param ville     ville où se situe le magasin
     * @param idMagasin identifiant unique du magasin
     */
    public Magasin(String nom, String ville, int idMagasin) {
        this.nom = nom;
        this.ville = ville;
        this.idMagasin = idMagasin;
        this.stockLivre = new HashMap<>();
        this.lesVendeurs = new ArrayList<>();
    }

    /**
     * Retourne l'identifiant du magasin.
     * 
     * @return l'id du magasin
     */
    public int getIdMagasin() {
        return this.idMagasin;
    }

    /**
     * Retourne le stock de livres du magasin.
     * 
     * @return map des livres avec leur quantité
     */
    public Map<Livre, Integer> getStockLivre() {
        return stockLivre;
    }

    /**
     * Retourne le nom du magasin.
     * 
     * @return nom du magasin
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retourne la ville du magasin.
     * 
     * @return ville du magasin
     */
    public String getVille() {
        return this.ville;
    }

    /**
     * Retourne la liste des vendeurs du magasin.
     * 
     * @return liste des vendeurs
     */
    public List<Vendeur> getLesVendeurs() {
        return lesVendeurs;
    }

    /**
     * Modifie l'identifiant du magasin.
     * 
     * @param idMagasin nouvel identifiant
     */
    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }

    /**
     * Modifie la ville du magasin.
     * 
     * @param ville nouvelle ville
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * Modifie le nom du magasin.
     * 
     * @param nom nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Ajoute des livres au stock du magasin.
     * Si un livre existe déjà, augmente la quantité.
     * 
     * @param livresAAjouter map des livres et quantités à ajouter
     */
    public void ajouterLivres(Map<Livre, Integer> livresAAjouter) {
        for (Map.Entry<Livre, Integer> coupleLivre : livresAAjouter.entrySet()) {
            Livre livre = coupleLivre.getKey();
            int quantite = coupleLivre.getValue();
            if (this.stockLivre.containsKey(livre)) {
                int nouvelleQuantite = this.stockLivre.get(livre) + quantite;
                this.stockLivre.put(livre, nouvelleQuantite);
            } else {
                this.stockLivre.put(livre, quantite);
            }
        }
    }

    /**
     * Supprime des livres du stock du magasin.
     * Lance une exception si un livre à supprimer n'existe pas.
     * 
     * @param livresASupprimer map des livres et quantités à retirer
     * @throws LivreInexistantException si un livre n'est pas dans le stock
     */
    public void supprimerLivres(Map<Livre, Integer> livresASupprimer) throws LivreInexistantException {
        for (Map.Entry<Livre, Integer> coupleLivre : livresASupprimer.entrySet()) {
            Livre livre = coupleLivre.getKey();
            int quantite = coupleLivre.getValue();
            if (!this.stockLivre.containsKey(livre)) {
                throw new LivreInexistantException();
            } else {
                int nouvelleQuantite = this.stockLivre.get(livre) - quantite;
                this.stockLivre.put(livre, nouvelleQuantite);
            }
        }
    }

    /**
     * Ajoute un vendeur à la liste des vendeurs du magasin
     * si ce vendeur n'est pas déjà présent.
     * 
     * @param vendeur vendeur à ajouter
     */
    public void ajouterVendeur(Vendeur vendeur) {
        if (!(this.lesVendeurs.contains(vendeur))) {
            this.lesVendeurs.add(vendeur);
        }
    }

    /**
     * Retourne une description textuelle du magasin.
     * 
     * @return chaîne décrivant le magasin
     */
    @Override
    public String toString() {
        return "Le magasin " + this.nom + " est situé " + this.ville + " et possède l'ID " + this.idMagasin;
    }

    /**
     * Compare ce magasin à un autre objet.
     * Deux magasins sont égaux si leur nom, id et ville sont égaux.
     * 
     * @param obj objet à comparer
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
        if (!(obj instanceof Magasin)) {
            return false;
        }
        Magasin tmp = (Magasin) obj;
        return this.nom.equals(tmp.nom) && this.idMagasin == tmp.idMagasin && this.ville.equals(tmp.ville);
    }
}
