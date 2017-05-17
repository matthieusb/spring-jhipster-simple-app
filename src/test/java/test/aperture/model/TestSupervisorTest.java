package test.aperture.model;

import aperture.model.TestSubject;
import aperture.model.TestSupervisor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests on the TestSupervisor model.
 */
public class TestSupervisorTest {
    private TestSupervisor emptyTestSupervisor, testSupervisor1, testSupervisor2;

    @Before
    public void setup() {
        emptyTestSupervisor = new TestSupervisor();
        testSupervisor1 = new TestSupervisor("idOne", "loginOne", "passOne");
        testSupervisor2 = new TestSupervisor("idTwo", "loginTwo", "passTwo");
    }

    @Test
    public void shouldReturnEmptyValuesEmptyConstructor() {
        assertNotNull(emptyTestSupervisor);
        assertEquals("", emptyTestSupervisor.getId());
        assertEquals("", emptyTestSupervisor.getLogin());
        assertEquals("", emptyTestSupervisor.getPass());
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void shouldReturnTrueEqualsWithSameRoom() {
        assertTrue(this.testSupervisor1.equals(this.testSupervisor1));
    }

    @Test
    public void shouldReturnTrueEqualsWithSameRoomCloned() {
        TestSupervisor testSupervisor1Clone = new TestSupervisor("idOne", "loginOne", "passOne");
        assertTrue(this.testSupervisor1.equals(testSupervisor1Clone));
    }

    @Test
    public void shouldReturnFalseEqualsWithDifferentRoom() {
        assertFalse(this.testSupervisor2.equals(this.testSupervisor1));
    }

    @Test
    public void shouldReturnFalseEqualsWithDifferentObjects() {
        Object testSubject = new TestSubject();
        assertFalse(this.testSupervisor1.equals(testSubject));
    }

    @Test
    public void shouldReturnSameHashCodeWithSameRoom() {
        assertEquals(this.testSupervisor1.hashCode(), this.testSupervisor1.hashCode());
    }

    @Test
    public void shouldReturnSamDifferenteHashCodeWithDifferentRoom() {
        assertNotEquals(this.testSupervisor1.hashCode(), this.testSupervisor2.hashCode());
    }
}
