package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Rooms")
public class Room {

    @Id
    private String id;

    @Field("number")
    private Integer number;

    @Field("name")
    private String name;

    public Room() {
        this.id = "";
        this.number = -1;
        this.name = "";
    }

    public Room(String id, Integer number, String name) {
        this.id = id;
        this.number = number;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
            "Room[id=%s, number=%s, name=%s]",
                id, number, name
        );
    }
}
