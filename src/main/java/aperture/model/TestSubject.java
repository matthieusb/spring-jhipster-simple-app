package aperture.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The test subject model class.
 * Has a unique id and name, and associated rooms he walks by.
 */
@Document(collection = "TestSubjects")
public class TestSubject {

    /**
     * The test subject identifier.
     */
    @Id
    private String id;

    /**
     * The test subject name.
     */
    @Field("name")
    private String name;

    /**
     * The test subject rooms.
     */
    @Field("rooms")
    private List<Room> rooms = new ArrayList<>();

    /**
     * Test subject empty constructor.
     */
    public TestSubject() {
        this.id = "";
        this.name = "";
    }

    /**
     * Test subject complete constructor.
     *
     * @param idToSet the id to set.
     * @param nameToSet the name to set.
     * @param roomsToSet the rooms to set.
     */
    public TestSubject(String idToSet, String nameToSet,
                       Collection<Room> roomsToSet) {
        this.id = idToSet;
        this.name = nameToSet;
        this.rooms.addAll(roomsToSet);
    }

    /**
     * Gets the test subject id.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param idToSet the id to set.
     */
    public void setId(String idToSet) {
        this.id = idToSet;
    }

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param nameToSet the name to set.
     */
    public void setName(String nameToSet) {
        this.name = nameToSet;
    }

    /**
     * Gets the rooms.
     *
     * @return the rooms, can be empty.
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the rooms.
     *
     * @param roomsToSet should not be null, but can be empty.
     */
    public void setRooms(ArrayList<Room> roomsToSet) {
        this.rooms = roomsToSet;
    }

    /**
     * Visualise the test subject.
     *
     * @return a test subject with this format : TestSubject[id=, name, rooms].
     */
    @Override
    public String toString() {
        return String.format(
            "TestSubject[id=%s, name=%s, rooms=%s]",
            id, name, rooms
        );
    }

    /**
     * Equals method, every attribute should be equals for it to return true.
     *
     * @param objToCompare the object to compare to our TestSubject,
     *                     should be a TestSubject if possible.
     * @return a boolean value, true if all attributes are the same.
     */
    @Override
    public boolean equals(Object objToCompare) {
        if (this == objToCompare) {
            return true;
        }

        if (objToCompare instanceof TestSubject) {
            TestSubject objAsTestSubject = (TestSubject) objToCompare;
            return objAsTestSubject.getId().equals(this.getId())
                && objAsTestSubject.getName().equals(this.getName())
                && objAsTestSubject.getRooms().equals(this.getRooms());
        } else {
            return false;
        }
    }

    /**
     * Hashcode mandatory implementation with equals.
     *
     * @return an unique identifier in the form of an int.
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = result + (name != null ? name.hashCode() : 0);
        result = result + (rooms != null ? rooms.hashCode() : 0);
        return result;
    }
}
