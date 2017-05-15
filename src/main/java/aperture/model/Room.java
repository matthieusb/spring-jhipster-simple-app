package aperture.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
     * @param idToSet     the room id, set to null to create a new room in the db.
     * @param numberToSet the room number, should not exist if new room.
     * @param nameToSet   the room name, not particular constraint.
     */
    public Room(final String idToSet, final Integer numberToSet, final String nameToSet) {
        this.id = idToSet;
        this.number = numberToSet;
        this.name = nameToSet;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer numberToSet) {
        this.number = numberToSet;
    }

    public String getName() {
        return name;
    }

    public void setName(final String nameToSet) {
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
    public boolean equals(final Object objToCompare) {
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
}
