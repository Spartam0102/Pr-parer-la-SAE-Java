
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestClassification {

    private Classification classification;

    @BeforeEach
    void setUp() {
        classification = new Classification(123, "Fiction");
    }

    @Test
    void testGetIddewey() {
        assertEquals(123, classification.getIddewey());
    }

    @Test
    void testGetNomclass() {
        assertEquals("Fiction", classification.getNomclass());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(classification.equals(classification));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(classification.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(classification.equals(new Object()));
    }

    @Test
    void testEqualsSameAttributes() {
        Classification autreClassification = new Classification(123, "Fiction");
        assertTrue(classification.equals(autreClassification));
    }

    @Test
    void testEqualsDifferentAttributes() {
        Classification autreClassification = new Classification(456, "Non-Fiction");
        assertFalse(classification.equals(autreClassification));
    }

    @Test
    void testToStringFormat() {
        assertEquals("La classification 123 a comme genre Fiction", classification.toString());
    }
}