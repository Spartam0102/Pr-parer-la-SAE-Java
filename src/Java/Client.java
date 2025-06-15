package Java;

import java.util.Map;
import java.util.HashMap;

/**
 * La classe {@code Client} représente un client, héritant de la classe {@code Personne}.
 * Elle ajoute des informations spécifiques telles que l'identifiant client, l'adresse,
 * et un panier de livres.
 *
 * @see Personne
 */
public class Client extends Personne {

    /** Identifiant unique du client. */
    private int ideCli;

    /** Adresse du client. */
    private String adresse;

    /** Panier du client, associant un livre à sa quantité. */
    private Map<Livre, Integer> panier;

    /**
     * Constructeur de la classe {@code Client}.
     *
     * @param nom              le nom du client
     * @param prenom           le prénom du client
     * @param dateDeNaissance  la date de naissance du client
     * @param id               l'identifiant unique du client
     * @param adresse          l'adresse du client
     */
    public Client(String nom, String prenom, String dateDeNaissance, int id, String adresse) {
        super(nom, prenom, dateDeNaissance);
        this.ideCli = id;
        this.adresse = adresse;
        this.panier = new HashMap<>();
    }

    /**
     * Retourne l'identifiant du client.
     *
     * @return l'identifiant du client
     */
    public int getIdCli() {
        return this.ideCli;
    }

    /**
     * Retourne l'adresse du client.
     *
     * @return l'adresse du client
     */
    public String getAdresse() {
        return this.adresse;
    }

    /**
     * Retourne le panier du client.
     *
     * @return une map contenant les livres et leurs quantités
     */
    public Map<Livre, Integer> getPanier() {
        return this.panier;
    }

    /**
     * Modifie le panier du client.
     *
     * @param panier une nouvelle map de livres et leurs quantités
     */
    public void setPanier(Map<Livre, Integer> panier) {
        this.panier = panier;
    }

    /**
     * Réinitialise le panier du client en le vidant.
     */
    public void reunitialiserPanier() {
        this.panier = new HashMap<>();
    }

    /**
     * Ajoute un livre au panier du client.
     * Si le livre est déjà présent, incrémente la quantité.
     *
     * @param livre le livre à ajouter au panier
     */
    public void ajouterLivrePanier(Livre livre) {
        if (this.panier.containsKey(livre)) {
            int nouvelleQuantite = this.panier.get(livre) + 1;
            this.panier.put(livre, nouvelleQuantite);
        } else {
            this.panier.put(livre, 1);
        }
    }

    /**
     * Vérifie si cet objet est égal à un autre objet.
     * Deux clients sont égaux s'ils ont le même nom, prénom, date de naissance,
     * identifiant et adresse.
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
        if (!(obj instanceof Client)) {
            return false;
        }
        Client tmp = (Client) obj;
        return super.getNom().equals(tmp.getNom()) && super.getPrenom().equals(tmp.getPrenom())
                && super.getDateDeNaissance().equals(tmp.getDateDeNaissance())
                && this.ideCli == tmp.getIdCli() && this.adresse.equals(tmp.getAdresse());
    }

    /**
     * Retourne une représentation textuelle du client.
     *
     * @return une chaîne représentant le client
     */
    @Override
    public String toString() {
        return super.toString() + ", fait partie des clients, et possède l'id " + this.ideCli
                + ". Elle vit à l'adresse " + this.adresse;
    }
}
