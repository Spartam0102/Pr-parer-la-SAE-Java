package TestApp;
import App.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import BD.*; 


public class TestClient {

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client("NomClient", "PrenomClient", "1990-01-01", 1, "AdresseClient");
    }

    @Test
    void testGetIdCli() {
        assertEquals(1, client.getIdCli());
    }

    @Test
    void testGetAdresse() {
        assertEquals("AdresseClient", client.getAdresse());
    }

    @Test
    void testGetNom() {
        assertEquals("NomClient", client.getNom());
    }

    @Test
    void testGetPrenom() {
        assertEquals("PrenomClient", client.getPrenom());
    }

    @Test
    void testGetDateDeNaissance() {
        assertEquals("1990-01-01", client.getDateDeNaissance());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(client.equals(client));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(client.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(client.equals(new Object()));
    }

    @Test
    void testEqualsSameAttributes() {
        Client autreClient = new Client("NomClient", "PrenomClient", "1990-01-01", 1, "AdresseClient");
        assertTrue(client.equals(autreClient));
    }

    @Test
    void testEqualsDifferentAttributes() {
        Client autreClient = new Client("AutreNom", "AutrePrenom", "2000-01-01", 2, "AutreAdresse");
        assertFalse(client.equals(autreClient));
    }

    @Test
    void testToStringFormat() {
        String expected = "La personne nommée PrenomClient NomClient est née le 1990-01-01, fait partie des clients, et possède l'id 1 et son adresse est AdresseClient";
        assertEquals(expected, client.toString());
    }
}