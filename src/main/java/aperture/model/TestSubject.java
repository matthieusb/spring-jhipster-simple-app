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

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("rooms")
    private List<Room> rooms = new ArrayList<>();


    public TestSubject() {
        this.id = "";
        this.name = "";
    }

    public TestSubject(String idToSet, String nameToSet,
                       Collection<Room> roomsToSet) {
        this.id = idToSet;
        this.name = nameToSet;
        this.rooms.addAll(roomsToSet);
    }

    public String getId() {
        return id;
    }

    public void setId(String idToSet) {
        this.id = idToSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameToSet) {
        this.name = nameToSet;
    }

    public List<Room> getRooms() {
        return rooms;
    }


    public void setRooms(List<Room> roomsToSet) {
        this.rooms = roomsToSet;
    }

    @Override
    public String toString() {
        return String.format(
            "TestSubject[id=%s, name=%s, rooms=%s]",
            id, name, rooms
        );
    }

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

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = result + (name != null ? name.hashCode() : 0);
        result = result + (rooms != null ? rooms.hashCode() : 0);
        return result;
    }
}
