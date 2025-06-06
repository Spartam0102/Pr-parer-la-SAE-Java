package TestApp;
import BD.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import App.*; 

public class TestPersonne {

    private Personne personne;

    @BeforeEach
    void setUp() {
        personne = new Personne("NomPersonne", "PrenomPersonne", "1990-01-01") {
        }; // Anonymous class for testing abstract class
    }

    @Test
    void testGetNom() {
        assertEquals("NomPersonne", personne.getNom());
    }

    @Test
    void testGetPrenom() {
        assertEquals("PrenomPersonne", personne.getPrenom());
    }

    @Test
    void testGetDateDeNaissance() {
        assertEquals("1990-01-01", personne.getDateDeNaissance());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(personne.equals(personne));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(personne.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(personne.equals(new Object()));
    }

    @Test
    void testEqualsSameAttributes() {
        Personne autrePersonne = new Personne("NomPersonne", "PrenomPersonne", "1990-01-01") {
        };
        assertTrue(personne.equals(autrePersonne));
    }

    @Test
    void testEqualsDifferentAttributes() {
        Personne autrePersonne = new Personne("AutreNom", "AutrePrenom", "2000-01-01") {
        };
        assertFalse(personne.equals(autrePersonne));
    }

    @Test
    void testToStringFormat() {
        assertEquals("La personne nommée PrenomPersonne NomPersonne est née le 1990-01-01", personne.toString());
    }
}