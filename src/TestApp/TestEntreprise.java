package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class TestEntreprise {

    private Entreprise entreprise;
    private Magasin magasin1;
    private Magasin magasin2;

    @BeforeEach
    void setUp() {
        entreprise = new Entreprise("NomEntreprise", "AdresseEntreprise");
        magasin1 = new Magasin("NomMagasin1", "VilleMagasin1", 1);
        magasin2 = new Magasin("NomMagasin2", "VilleMagasin2", 2);
    }

    @Test
    void testGetNom() {
        assertEquals("NomEntreprise", entreprise.getNom());
    }

    @Test
    void testGetAdresse() {
        assertEquals("AdresseEntreprise", entreprise.getAdresse());
    }

    @Test
    void testGetListeMagasinsEmpty() {
        assertTrue(entreprise.getListeMagasins().isEmpty());
    }

    @Test
    void testAjouterMagasin() {
        entreprise.ajouterMagasin(magasin1);
        assertFalse(entreprise.getListeMagasins().isEmpty());
        assertEquals(1, entreprise.getListeMagasins().size());
        assertTrue(entreprise.getListeMagasins().contains(magasin1));
    }

    @Test
    void testGetListeMagasinsMultiple() {
        entreprise.ajouterMagasin(magasin1);
        entreprise.ajouterMagasin(magasin2);
        assertEquals(2, entreprise.getListeMagasins().size());
        assertTrue(entreprise.getListeMagasins().contains(magasin1));
        assertTrue(entreprise.getListeMagasins().contains(magasin2));
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(entreprise.equals(entreprise));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(entreprise.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(entreprise.equals(new Object()));
    }

    @Test
    void testEqualsSameAttributes() {
        Entreprise autreEntreprise = new Entreprise("NomEntreprise", "AdresseEntreprise");
        assertTrue(entreprise.equals(autreEntreprise));
    }

    @Test
    void testEqualsDifferentAttributes() {
        Entreprise autreEntreprise = new Entreprise("AutreNom", "AutreAdresse");
        assertFalse(entreprise.equals(autreEntreprise));
    }

    @Test
    void testToStringFormatEmpty() {
        String expected = "L'entreprise nommée NomEntreprise se situe à AdresseEntreprise et a comme magasins : \n";
        assertEquals(expected, entreprise.toString());
    }

    @Test
    void testToStringFormatWithMagasins() {
        entreprise.ajouterMagasin(magasin1);
        entreprise.ajouterMagasin(magasin2);
        String expected = "L'entreprise nommée NomEntreprise se situe à AdresseEntreprise et a comme magasins : \nLe magasin NomMagasin1 est situé VilleMagasin1 et possède l'ID 1\nLe magasin NomMagasin2 est situé VilleMagasin2 et possède l'ID 2\n";
        assertEquals(expected, entreprise.toString());
    }
}