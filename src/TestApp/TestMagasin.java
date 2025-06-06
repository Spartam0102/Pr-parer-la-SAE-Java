package TestApp;
import BD.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import App.*; 

public class TestMagasin {

    private Magasin magasin;
    private Livre livre1;
    private Livre livre2;

    @BeforeEach
    void setUp() {
        magasin = new Magasin("NomMagasin", "VilleMagasin", 101);
        Auteur auteur = new Auteur("NomAuteur", "PrenomAuteur", "1980-01-01", 1);
        Editeur editeur = new Editeur(1, "NomEditeur");
        Classification classification = new Classification(123, "Fiction");
        List<Classification> classifications = new ArrayList<>();
        classifications.add(classification);
        livre1 = new Livre(1, "TitreLivre1", "2023-01-01", 20, 200, classifications, editeur, auteur);
        livre2 = new Livre(2, "TitreLivre2", "2023-02-02", 30, 300, classifications, editeur, auteur);
    }

    @Test
    void testGetNom() {
        assertEquals("NomMagasin", magasin.getNom());
    }

    @Test
    void testGetVille() {
        assertEquals("VilleMagasin", magasin.getVille());
    }

    @Test
    void testGetIdMagasin() {
        assertEquals(101, magasin.getIdMagasin());
    }

    @Test
    void testGetLivresEmpty() {
        assertTrue(magasin.getLivres().isEmpty());
    }

    @Test
    void testAjouterLivre() {
        magasin.ajouterLivre(List.of(livre1), List.of(10));
        assertFalse(magasin.getLivres().isEmpty());
        assertEquals(1, magasin.getLivres().size());
        assertEquals(1, magasin.stockLivre.size());
        assertEquals(livre1, magasin.getLivres().get(