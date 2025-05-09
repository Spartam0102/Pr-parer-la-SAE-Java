

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestEditeur {

    private Editeur editeur;

    @BeforeEach
    void setUp() {
        editeur = new Editeur(1, "NomEditeur");
    }

    @Test
    void testGetIdEdit() {
        assertEquals(1, editeur.getIdEdit());
    }

    @Test
    void testGetNomEdit() {
        assertEquals("NomEditeur", editeur.getNomEdit());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(editeur.equals(editeur));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(editeur.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(editeur.equals(new Object()));
    }

    @Test
    void testEqualsSameAttributes() {
        Editeur autreEditeur = new Editeur(1, "NomEditeur");
        assertTrue(editeur.equals(autreEditeur));
    }

    @Test
    void testEqualsDifferentAttributes() {
        Editeur autreEditeur = new Editeur(2, "AutreNomEditeur");
        assertFalse(editeur.equals(autreEditeur));
    }

    @Test
    void testToStringFormat() {
        assertEquals("L'éditeur NomEditeur possède l'id 1", editeur.toString());
    }
}