package model;


public class Room {
    private Long roomId;
    private String number;
    private String name;

    public Room(Long roomId, String number, String name) {
        this.roomId = roomId;
        this.number = number;
        this.name = name;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
