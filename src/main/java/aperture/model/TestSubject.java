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
     *
     */
    @Id
    private String id;

    /**
     *
     */
    @Field("name")
    private String name;

    /**
     *
     */
    @Field("rooms")
    private List<Room> rooms = new ArrayList<>();

    /**
     *
     */
    public TestSubject() {
        this.id = "";
        this.name = "";
    }

    /**
     * @param id
     * @param name
     * @param rooms
     */
    public TestSubject(String id, String name, Collection<Room> rooms) {
        this.id = id;
        this.name = name;
        this.rooms.addAll(rooms);
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     *
     * @param rooms
     */
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format(
            "TestSubject[id=%s, name=%s, rooms=%s]",
            id, name, rooms
        );
    }

    /**
     *
     * @param objToCompare
     * @return
     */
    @Override
    public boolean equals(Object objToCompare) {
        if (this == objToCompare)
            return true;

        if (objToCompare instanceof TestSubject) {
            TestSubject objAsTestSubject = (TestSubject) objToCompare;
            return objAsTestSubject.getId().equals(this.getId())
                && objAsTestSubject.getName().equals(this.getName())
                && objAsTestSubject.getRooms().equals(this.getRooms()); //NOTE : Maybe disable this last option
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = result + (name != null ? name.hashCode() : 0);
        result = result + (rooms != null ? rooms.hashCode() : 0);
        return result;
    }
}
