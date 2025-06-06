package TestApp;
import BD.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import App.*; 

public class TestVendeur {

    private Vendeur vendeur;

    @BeforeEach
    void setUp() {
        vendeur = new Vendeur("NomVendeur", "PrenomVendeur", "1990-01-01", 1);
    }

    @Test
    void testGetIdVendeur() {
        assertEquals(1, vendeur.getIdVendeur());
    }

    @Test
    void testGetNom() {
        assertEquals("NomVendeur", vendeur.getNom());
    }

    @Test
    void testGetPrenom() {
        assertEquals("PrenomVendeur", vendeur.getPrenom());
    }

    @Test
    void testGetDateDeNaissance() {
        assertEquals("1990-01-01", vendeur.getDateDeNaissance());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(vendeur.equals(vendeur));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(vendeur.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(vendeur.equals(new Object()));
    }

    @Test
    void testEqualsSameAttributes() {
        Vendeur autreVendeur = new Vendeur("NomVendeur", "PrenomVendeur", "1990-01-01", 1);
        assertTrue(vendeur.equals(autreVendeur));
    }

    @Test
    void testEqualsDifferentAttributes() {
        Vendeur autreVendeur = new Vendeur("AutreNom", "AutrePrenom", "2000-01-01", 2);
        assertFalse(vendeur.equals(autreVendeur));
    }

    @Test
    void testToStringFormat() {
        String expected = "La personne nommée PrenomVendeur NomVendeur est née le 1990-01-01";
        assertEquals(expected, vendeur.toString());
    }
}