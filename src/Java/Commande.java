package Java;

import java.util.Map;
import java.util.HashMap;

import Exception.LivreInexistantException;

/**
 * La classe {@code Commande} représente une commande passée par un client dans un magasin.
 * Elle contient des informations comme la date, le mode de réception, les livres commandés,
 * le client concerné et le magasin associé.
 */
public class Commande {

    /** Date de la commande. */
    private String dateDeCommande;

    /** Prix total de la commande. */
    private double prixTotal;

    /** Mode de réception de la commande (par exemple : 'L' pour livraison, 'R' pour retrait). */
    private char modeDeReception;

    /** Identifiant unique de la commande. */
    private int idCommande;

    /** Magasin dans lequel la commande est passée. */
    private Magasin magasin;

    /** Client ayant passé la commande. */
    private Client client;

    /** Livres commandés associés à leur quantité. */
    private Map<Livre, Integer> livresCommande;

    /**
     * Constructeur de la classe {@code Commande}.
     *
     * @param idCommande       identifiant unique de la commande
     * @param dateDeCommande   date de la commande
     * @param modeDeReception  mode de réception ('L', 'R', etc.)
     * @param client           client ayant passé la commande
     * @param magasin          magasin dans lequel la commande est passée
     */
    public Commande(int idCommande, String dateDeCommande, char modeDeReception, Client client, Magasin magasin) {
        this.idCommande = idCommande;
        this.dateDeCommande = dateDeCommande;
        this.modeDeReception = modeDeReception;
        this.client = client;
        this.magasin = magasin;
        this.prixTotal = 0.0;
        this.livresCommande = new HashMap<>();
    }

    /**
     * Retourne le client ayant passé la commande.
     *
     * @return le client
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * Retourne la date de la commande.
     *
     * @return la date de commande
     */
    public String getDateDeCommande() {
        return this.dateDeCommande;
    }

    /**
     * Retourne l'identifiant de la commande.
     *
     * @return l'identifiant de la commande
     */
    public int getIdCommande() {
        return this.idCommande;
    }

    /**
     * Retourne le magasin associé à la commande.
     *
     * @return le magasin
     */
    public Magasin getMagasin() {
        return this.magasin;
    }

    /**
     * Retourne le mode de réception de la commande.
     *
     * @return le mode de réception
     */
    public char getModeDeReception() {
        return this.modeDeReception;
    }

    /**
     * Retourne le prix total de la commande.
     *
     * @return le prix total
     */
    public double getPrixTotal() {
        return this.prixTotal;
    }

    /**
     * Retourne les livres commandés avec leur quantité.
     *
     * @return une map associant les livres à leur quantité
     */
    public Map<Livre, Integer> getLivresCommande() {
        return this.livresCommande;
    }

    /**
     * Ajoute un livre avec une quantité spécifique à la commande.
     * Met également à jour le prix total.
     *
     * @param livre     le livre à ajouter
     * @param quantite  la quantité du livre à ajouter
     */
    public void ajouterLivre(Livre livre, Integer quantite) {
        this.livresCommande.put(livre, quantite);
        calculerPrixTotal();
    }

    /**
     * Calcule le prix total de la commande en fonction des quantités.
     * (Remarque : le calcul ici suppose que chaque livre a un prix unitaire de 1.0).
     */
    public void calculerPrixTotal() {
        this.prixTotal = 0;
        for (Integer quantite : this.livresCommande.values()) {
            double quantiteDouble = quantite;
            this.prixTotal += quantiteDouble;
        }
    }

    /**
     * Modifie le stock du magasin en retirant les livres commandés.
     * Si un livre est inexistant dans le stock, une exception est attrapée et affichée.
     */
    public void modifierStock() {
        try {
            this.magasin.supprimerLivres(this.livresCommande);
        } catch (LivreInexistantException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retourne une représentation textuelle de la commande.
     *
     * @return une chaîne décrivant la commande
     */
    @Override
    public String toString() {
        String res = "la commande " + this.idCommande + " datant du " + this.dateDeCommande + " provenant du magasin "
                + this.magasin.getNom() + ", est crée par l'utilisateur "
                + this.client.getPrenom() + " " + this.client.getNom() + " a un prix total de " + this.prixTotal
                + "€ et le mode de reception est " + this.modeDeReception
                + ".\nLes livres se trouvant dans la commande sont : ";
        for (Map.Entry<Livre, Integer> coupleLivre : this.livresCommande.entrySet()) {
            Livre livre = coupleLivre.getKey();
            int quantite = coupleLivre.getValue();
            res += livre + " commandé " + quantite + "fois\n";
        }
        return res;
    }

    /**
     * Vérifie si cette commande est égale à une autre.
     * Deux commandes sont considérées comme égales si tous leurs attributs principaux sont identiques.
     *
     * @param obj l'objet à comparer
     * @return {@code true} si les commandes sont égales, {@code false} sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Commande)) {
            return false;
        }
        Commande tmp = (Commande) obj;
        return this.dateDeCommande.equals(tmp.dateDeCommande) && this.prixTotal == tmp.prixTotal
                && this.modeDeReception == tmp.modeDeReception && this.idCommande == tmp.idCommande
                && this.magasin.equals(tmp.magasin) && this.client.equals(tmp.client);
    }
}
