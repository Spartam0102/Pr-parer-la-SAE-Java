package TestApp;
import BD.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import App.*; 

public class TestCommande {

    private Commande commande;
    private Client client;
    private Magasin magasin;
    private Livre livre1;
    private Livre livre2;

    @BeforeEach
    void setUp() {
        client = new Client("NomClient", "PrenomClient", "1990-01-01", 1, "AdresseClient");
        magasin = new Magasin("NomMagasin", "VilleMagasin", 101);
        Auteur auteur = new Auteur("NomAuteur", "PrenomAuteur", "1980-05-05", 1);
        Editeur editeur = new Editeur(1, "NomEditeur");
        Classification classification = new Classification(123, "Fiction");
        List<Classification> classifications = new ArrayList<>();
        classifications.add(classification);
        livre1 = new Livre(1, "TitreLivre1", "2023-01-01", 20, 200, classifications, editeur, auteur);
        livre2 = new Livre(2, "TitreLivre2", "2023-02-02", 30, 300, classifications, editeur, auteur);
        commande = new Commande(1, "2024-03-01", 'L', client, magasin);
    }

    @Test
    void testGetClient() {
        assertEquals(client, commande.getClient());
    }

    @Test
    void testGetDateDeCommande() {
        assertEquals("2024-03-01", commande.getDateDeCommande());
    }

    @Test
    void testGetIdCommande() {
        assertEquals(1, commande.getIdCommande());
    }

    @Test
    void testGetMagasin() {
        assertEquals(magasin, commande.getMagasin());
    }

    @Test
    void testGetModeDeReception() {
        assertEquals('L', commande.getModeDeReception());
    }

    @Test
    void testGetPrixTotalInitial() {
        assertEquals(0.0, commande.getPrixTotal());
    }

    @Test
    void testGetQteInitial() {
        assertTrue(commande.getQte().isEmpty());
    }

    @Test
    void testGetLivresCommanderInitial() {
        assertTrue(commande.getLivresCommander().isEmpty());
    }

    @Test
    void testAjouterLivre() {
        commande.ajouterLivre(livre1, 2);
        assertFalse(commande.getLivresCommander().isEmpty());
        assertFalse(commande.getQte().isEmpty());
        assertEquals(livre1, commande.getLivresCommander().get(0));
        assertEquals(2, commande.getQte().get(0));
    }

    @Test
    void testCalculerPrixTotal() {
        commande.ajouterLivre(livre1, 2);
        commande.ajouterLivre(livre2, 1);
        commande.calculerPrixTotal();
        assertEquals(70.0, commande.getPrixTotal()); // (20 * 2) + (30 * 1)
    }

    @Test
    void testSetModeDeReception() {
        commande.setModeDeReception('P');
        assertEquals('P', commande.getModeDeReception());
    }

    @Test
    void testToStringFormat() {
        commande.ajouterLivre(livre1, 2);
        String expected = "la commande 1 datant du 2024-03-01 provenant du magasin NomMagasin, est crée par l'utilisateur PrenomClient NomClient a un prix total de 40.0€ et le mode de reception est L.\nLes livres se trouvant dans la commande sont : Le livre TitreLivre1 d'id 1, écrit par PrenomAuteur NomAuteur, a été publié le 2023-01-01 par NomEditeur. Il coûte 20€ et contient 200 pages. Il a comme classification Fiction. Ce livre est disponnible dans aucun magasin commandé 2fois\n";
        assertEquals(expected, commande.toString());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(commande.equals(commande));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(commande.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(commande.equals(new Object()));
    }

    @Test
    void testEqualsSameAttributes() {
        Commande autreCommande = new Commande(1, "2024-03-01", 'L', client, magasin);
        assertTrue(commande.equals(autreCommande));
    }

    @Test
    void testEqualsDifferentAttributes() {
        Commande autreCommande = new Commande(2, "2024-04-01", 'P', client, magasin);
        assertFalse(commande.equals(autreCommande));
    }
}