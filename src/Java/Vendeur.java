package Java;

import java.util.HashMap;
import java.util.Map;

import Exception.LivreInexistantException;

/**
 * Classe représentant un vendeur travaillant dans un magasin.
 * Hérite de la classe abstraite Personne.
 */
public class Vendeur extends Personne {

    private int idVendeur;
    private Magasin magasin;

    /**
     * Constructeur de la classe Vendeur.
     *
     * @param nom            Le nom du vendeur
     * @param prenom         Le prénom du vendeur
     * @param dateDeNaissance La date de naissance du vendeur
     * @param id             L'identifiant unique du vendeur
     * @param magasin        Le magasin dans lequel travaille le vendeur
     */
    public Vendeur(String nom, String prenom, String dateDeNaissance, int id, Magasin magasin) {
        super(nom, prenom, dateDeNaissance);
        this.idVendeur = id;
        this.magasin = magasin;
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

    /**
     * Ajoute une quantité de livres au stock du magasin.
     *
     * @param livresAAjouter Map contenant les livres et leurs quantités à ajouter
     */
    public void ajouterLivres(Map<Livre, Integer> livresAAjouter) {
        this.magasin.ajouterLivres(livresAAjouter);
    }

    /**
     * Méthode prévue pour une mise à jour (non implémentée).
     */
    public void mettreAJour() {

    }

    /**
     * Vérifie la disponibilité d'un livre dans le magasin du vendeur.
     * Note : le paramètre 'magasin' n'est pas utilisé dans cette méthode.
     *
     * @param magasin Magasin dans lequel vérifier la disponibilité (non utilisé)
     * @param livre  Livre à vérifier
     * @return true si le livre est disponible dans le magasin du vendeur, false sinon
     */
    public boolean disponibiliteLivre(Magasin magasin, Livre livre) {
        if (!this.magasin.getStockLivre().containsKey(livre)) {
            return false;
        } else {
            return true;
        }
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
     * Retire une quantité de livres du panier d'un client et les ajoute au stock du magasin.
     *
     * @param client   Client dont on retire le livre
     * @param livre    Livre à retirer
     * @param quantite Quantité à retirer
     * @return true si l'opération a réussi, false sinon
     */
    public boolean retirerLivrePanierEtAjouterAuMagasin(Client client, Livre livre, int quantite) {
        Map<Livre, Integer> panierClient = client.getPanier();

        if (!panierClient.containsKey(livre) || panierClient.get(livre) < quantite) {
            return false;
        }

        int nouvelleQuantite = panierClient.get(livre) - quantite;
        if (nouvelleQuantite == 0) {
            panierClient.remove(livre);
        } else {
            panierClient.put(livre, nouvelleQuantite);
        }

        Map<Livre, Integer> livresAAjouter = new HashMap<>();
        livresAAjouter.put(livre, quantite);
        magasin.ajouterLivres(livresAAjouter);

        return true;
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
                && this.magasin.equals(tmp.getMagasin());
    }

    /**
     * Représentation textuelle d'un vendeur.
     *
     * @return Une chaîne décrivant le vendeur
     */
    @Override
    public String toString() {
        return super.toString() + ", fait partie des vendeurs, il possède l'id " + this.idVendeur
                + ", et travaille dans " + this.magasin;
    }
}
