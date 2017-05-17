package test.aperture.model;

import aperture.model.Room;
import aperture.model.TestSubject;
import aperture.model.TestSupervisor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests on the TestSubject model.
 */
public class TestSubjectTest {
    private TestSubject emptyTestSubject, emptyTestSubject1, emptyTestSubject2;
    private List<Room> rooms = new ArrayList<>();

    @Before
    public void setup() {
        emptyTestSubject = new TestSubject();
        emptyTestSubject1 = new TestSubject("idOne", "nameOne", this.rooms);
        emptyTestSubject2 = new TestSubject("idTwo", "nameTwo", this.rooms);
    }

    @Test
    public void shouldReturnEmptyValuesEmptyConstructor() {
        assertNotNull(emptyTestSubject);
        assertEquals("", emptyTestSubject.getId());
        assertEquals("", emptyTestSubject.getName());
        assertEquals(0, emptyTestSubject.getRooms().size());
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void shouldReturnTrueEqualsWithSameRoom() {
        assertTrue(this.emptyTestSubject1.equals(this.emptyTestSubject1));
    }

    @Test
    public void shouldReturnTrueEqualsWithSameRoomCloned() {
        TestSubject testSubject1Clone = new TestSubject("idOne", "nameOne", this.rooms);
        assertTrue(this.emptyTestSubject1.equals(testSubject1Clone));
    }

    @Test
    public void shouldReturnFalseEqualsWithDifferentRoom() {
        assertFalse(this.emptyTestSubject2.equals(this.emptyTestSubject1));
    }

    @Test
    public void shouldReturnFalseEqualsWithDifferentObjects() {
        Object testSupervisor = new TestSupervisor();
        assertFalse(this.emptyTestSubject1.equals(testSupervisor));
    }

    @Test
    public void shouldReturnSameHashCodeWithSameRoom() {
        assertEquals(this.emptyTestSubject1.hashCode(), this.emptyTestSubject1.hashCode());
    }

    @Test
    public void shouldReturnSamDifferenteHashCodeWithDifferentRoom() {
        assertNotEquals(this.emptyTestSubject1.hashCode(), this.emptyTestSubject2.hashCode());
    }
}
