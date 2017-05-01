package model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public TestSubject(String id, String name, Collection<Room> rooms) {
        this.id = id;
        this.name = name;
        this.rooms.addAll(rooms);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
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
}
