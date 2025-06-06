package TestApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import BD.*; 
import App.*; 


public class TestAuteur {

    private Auteur auteur;

    @BeforeEach
    void setUp() {
        auteur = new Auteur("NomAuteur", "PrenomAuteur", "1980-05-05", 1);
    }

    @Test
    void testGetNom() {
        assertEquals("NomAuteur", auteur.getNom());
    }

    @Test
    void testGetPrenom() {
        assertEquals("PrenomAuteur", auteur.getPrenom());
    }

    @Test
    void testGetDateDeNaissance() {
        assertEquals("1980-05-05", auteur.getDateDeNaissance());
    }

    @Test
    void testGetIdAuteur() {
        assertEquals(1, auteur.getIdAuteur());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(auteur.equals(auteur));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(auteur.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(auteur.equals(new Object()));
    }

    @Test
    void testEqualsSameAttributes() {
        Auteur autreAuteur = new Auteur("NomAuteur", "PrenomAuteur", "1980-05-05", 1);
        assertTrue(auteur.equals(autreAuteur));
    }

    @Test
    void testEqualsDifferentAttributes() {
        Auteur autreAuteur = new Auteur("AutreNom", "AutrePrenom", "1990-01-01", 2);
        assertFalse(auteur.equals(autreAuteur));
    }

    @Test
    void testToStringFormat() {
        String expectedString = "L'auteur NomAuteur PrenomAuteur est né le 1980-05-05 et possède l'id 1";
        assertEquals(expectedString, auteur.toString());
    }
}