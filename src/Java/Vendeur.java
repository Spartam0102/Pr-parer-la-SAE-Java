package Java;

import java.util.Map;

import Exception.LivreInexistantException;

/**
 * Classe représentant un vendeur travaillant dans un magasin.
 * Hérite de la classe abstraite Personne.
 */
public class Vendeur extends Personne {

    private int idVendeur;
    private Magasin magasin;
    private String motDePasseVendeur;

    /**
     * Constructeur de la classe Vendeur.
     *
     * @param nom            Le nom du vendeur
     * @param prenom         Le prénom du vendeur
     * @param dateDeNaissance La date de naissance du vendeur
     * @param id             L'identifiant unique du vendeur
     * @param magasin        Le magasin dans lequel travaille le vendeur
     * @param motDePasse    Le mot de passe du vendeur
     */
    public Vendeur(String nom, String prenom, String dateDeNaissance, int id, Magasin magasin, String motDePasse) {
        super(nom, prenom, dateDeNaissance);
        this.idVendeur = id;
        this.magasin = magasin;
        this.motDePasseVendeur = motDePasse;
    }

    /**
     * Retourne le magasin associé au vendeur.
     *
     * @return Le magasin du vendeur
     */
    public Magasin getMagasin() {
        return this.magasin;
    }

    /**
     * Retourne l'identifiant du vendeur.
     *
     * @return L'id du vendeur
     */
    public int getIdVendeur() {
        return this.idVendeur;
    }

    public String getMotDePasseVendeur() {
        return this.motDePasseVendeur;
    }

    /**
     * Transfère des livres du magasin du vendeur vers un autre magasin.
     *
     * @param magasinArrivee    Magasin destinataire des livres
     * @param LivresATransferer Map contenant les livres et leurs quantités à transférer
     */
    public void transfererLivre(Magasin magasinArrivee, Map<Livre, Integer> LivresATransferer) {
        try {
            this.magasin.supprimerLivres(LivresATransferer);
        } catch (LivreInexistantException e) {
            System.out.println(e.getMessage());
        }
        magasinArrivee.ajouterLivres(LivresATransferer);
    }

    /**
     * Compare deux objets Vendeur pour vérifier leur égalité.
     *
     * @param obj Objet à comparer
     * @return true si les deux vendeurs sont identiques, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Vendeur)) {
            return false;
        }
        Vendeur tmp = (Vendeur) obj;
        return super.getNom().equals(tmp.getNom()) && super.getPrenom().equals(tmp.getPrenom())
                && super.getDateDeNaissance().equals(tmp.getDateDeNaissance())
                && this.idVendeur == tmp.getIdVendeur()
                && this.magasin.equals(tmp.getMagasin())
                && this.motDePasseVendeur.equals(tmp.getMotDePasseVendeur());
    }

    /**
     * Représentation textuelle d'un vendeur.
     *
     * @return Une chaîne décrivant le vendeur
     */
    @Override
    public String toString() {
        return super.toString() + ", fait partie des vendeurs, il possède l'id " + this.idVendeur  + " avec comme mot de passe " + this.motDePasseVendeur
                + ", et travaille dans " + this.magasin;
    }
}
