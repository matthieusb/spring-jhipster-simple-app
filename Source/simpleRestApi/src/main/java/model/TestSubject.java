package model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Document(collection = "TestSubjects")
public class TestSubject {
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("rooms")
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public TestSubject(String id, String name, ArrayList<Room> rooms) {
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

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
