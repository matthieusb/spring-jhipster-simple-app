package model;


import java.util.ArrayList;

public class TestSubject {
    private Long testSubjectId;
    private String name;
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public TestSubject(Long testSubjectId, String name, ArrayList<Room> rooms) {
        this.testSubjectId = testSubjectId;
        this.name = name;
        this.rooms.addAll(rooms);
    }

    public Long getTestSubjectId() {
        return testSubjectId;
    }

    public void setTestSubjectId(Long testSubjectId) {
        this.testSubjectId = testSubjectId;
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
