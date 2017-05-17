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

    /**
     * Room id.
     */
    @Id
    private String id;

    /**
     * Room number, unique.
     */
    @Field("number")
    private Integer number;

    /**
     * Room name, not unique.
     */
    @Field("name")
    private String name;

    /**
     * Room Empty constructor.
     */
    public Room() {
        this.id = "";
        this.number = -1;
        this.name = "";
    }

    /**
     * Room constructor with all attributes.
     *
     * @param idToSet     the room id, set to null
     *                    to create a new room in the db.
     * @param numberToSet the room number, should not exist if new room.
     * @param nameToSet   the room name, not particular constraint.
     */
    public Room(String idToSet, Integer numberToSet, String nameToSet) {
        this.id = idToSet;
        this.number = numberToSet;
        this.name = nameToSet;
    }

    /**
     * Gets the room id.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the room id.
     *
     * @param idToSet the id to set.
     */
    public void setId(String idToSet) {
        this.id = idToSet;
    }

    /**
     * Gets the room number.
     *
     * @return the room number.
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Sets the room number.
     *
     * @param numberToSet the number to set.
     */
    public void setNumber(Integer numberToSet) {
        this.number = numberToSet;
    }

    /**
     * Gets the name.
     *
     * @return the room name.
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

    @Override
    public String toString() {
        return String.format(
            "Room[id=%s, number=%s, name=%s]",
            id, number, name
        );
    }

    /**
     * Equals method overriden for Room object.
     *
     * @param objToCompare object to compare
     * @return true if every row is the same.
     */
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

    /**
     * Generates the room hashcode.
     *
     * @return the room hashcode.
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = result + number.hashCode();
        result = result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
