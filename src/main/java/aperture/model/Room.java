package aperture.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The room model class.
 * A room is defined by its number which is unique.
 * It can have any name.Test subjects have rooms they walk by trying to summon
 * portals and not get killed by turrets.
 */
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


    public Room(String idToSet, Integer numberToSet, String nameToSet) {
        this.id = idToSet;
        this.number = numberToSet;
        this.name = nameToSet;
    }


    public String getId() {
        return id;
    }


    public void setId(String idToSet) {
        this.id = idToSet;
    }


    public Integer getNumber() {
        return number;
    }


    public void setNumber(Integer numberToSet) {
        this.number = numberToSet;
    }


    public String getName() {
        return name;
    }


    public void setName(String nameToSet) {
        this.name = nameToSet;
    }

    @Override
    public String toString() {
        return String.format(
            "Room[id=%s, number=%s, name=%s]",
            id, number, name
        );
    }


    @Override
    public boolean equals(Object objToCompare) {
        if (this == objToCompare) {
            return true;
        }

        if (objToCompare instanceof Room) {
            Room objAsRoom = (Room) objToCompare;
            return objAsRoom.getId().equals(this.getId())
                && objAsRoom.getNumber().equals(this.getNumber())
                && objAsRoom.getName().equals(this.getName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = result + number.hashCode();
        result = result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
