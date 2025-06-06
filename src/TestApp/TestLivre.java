package TestApp;
import BD.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import App.*; 

public class TestLivre {

    private Livre livre;
    private Auteur auteur;
    private Editeur editeur;
    private Classification classification;

    @BeforeEach
    void setUp() {
        auteur = new Auteur("NomAuteur", "PrenomAuteur", "1980-05-05", 1);
        editeur = new Editeur(1, "NomEditeur");
        classification = new Classification(123, "Fiction");
        List<Classification> classifications = new ArrayList<>();
        classifications.add(classification);
        livre = new Livre(1, "TitreLivre", "2023-01-15", 25, 300, classifications, editeur, auteur);
    }

    @Test
    void testGetIdLivre() {
        assertEquals(1, livre.getIdLivre());
    }

    @Test
    void testGetNomLivre() {
        assertEquals("TitreLivre", livre.getNomLivre());
    }

    @Test
    void testGetDateDePublication() {
        assertEquals("2023-01-15", livre.getDateDePublication());
    }

    @Test
    void testGetPrix() {
        assertEquals(25, livre.getPrix());
    }

    @Test
    void testGetNbPage() {
        assertEquals(300, livre.getNbPage());
    }

    @Test
    void testGetAuteur() {
        assertEquals(auteur, livre.getAuteur());
    }

    @Test
    void testGetEditeur() {
        assertEquals(editeur, livre.getEditeur());
    }

    @Test
    void testGetClassification() {
        assertEquals(List.of(classification), livre.getClassification());
    }

    @Test
    void testGetMagasinsEmpty() {
        assertTrue(livre.getMagasins().isEmpty());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(livre.equals(livre));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(livre.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(livre.equals(new Object()));
    }

    @Test
    void testEqualsSameAttributes() {
        Livre autreLivre = new Livre(1, "TitreLivre", "2023-01-15", 25, 300, List.of(classification), editeur, auteur);
        assertTrue(livre.equals(autreLivre));
    }

    @Test
    void testEqualsDifferentAttributes() {
        Livre autreLivre = new Livre(2, "AutreTitre", "2024-01-01", 30, 400, new ArrayList<>(), new Editeur(2, "AutreEditeur"), new Auteur("AutreNom", "AutrePrenom", "1990-01-01", 2));
        assertFalse(livre.equals(autreLivre));
    }

    @Test
    void testToStringFormat() {
        String expected = "Le livre TitreLivre d'id 1, écrit par PrenomAuteur NomAuteur, a été publié le 2023-01-15 par NomEditeur. Il coûte 25€ et contient 300 pages. Il a comme classification Fiction. Ce livre est disponnible dans aucun magasin";
        assertEquals(expected, livre.toString());
    }
}