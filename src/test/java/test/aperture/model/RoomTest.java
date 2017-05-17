package test.aperture.model;

import aperture.model.Room;
import aperture.model.TestSupervisor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests on the Room model.
 */
public class RoomTest {
    private Room emptyRoom, room1, room2;

    @Before
    public void setup() {
        emptyRoom = new Room();
        room1 = new Room("idOne", 1, "nameOne");
        room2 = new Room("idTwo", 2, "nameTwo");
    }

    @Test
    public void shouldReturnEmptyValuesEmptyConstructor() {
        assertNotNull(emptyRoom);
        assertEquals("", emptyRoom.getId());
        assertEquals("", emptyRoom.getName());
        assertEquals(-1, emptyRoom.getNumber().intValue());
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void shouldReturnTrueEqualsWithSameRoom() {
        assertTrue(this.room1.equals(this.room1));
    }

    @Test
    public void shouldReturnTrueEqualsWithSameRoomCloned() {
        Room room1Clone = new Room("idOne", 1, "nameOne");
        assertTrue(this.room1.equals(room1Clone));
    }

    @Test
    public void shouldReturnFalseEqualsWithDifferentRoom() {
        assertFalse(this.room2.equals(this.room1));
    }

    @Test
    public void shouldReturnFalseEqualsWithDifferentObjects() {
        Object testSupervisor = new TestSupervisor();
        assertFalse(this.room1.equals(testSupervisor));
    }

    @Test
    public void shouldReturnSameHashCodeWithSameRoom() {
        assertEquals(this.room1.hashCode(), this.room1.hashCode());
    }

    @Test
    public void shouldReturnSamDifferenteHashCodeWithDifferentRoom() {
        assertNotEquals(this.room1.hashCode(), this.room2.hashCode());
    }
}
