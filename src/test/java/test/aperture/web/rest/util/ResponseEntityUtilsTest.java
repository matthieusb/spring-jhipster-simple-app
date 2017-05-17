package test.aperture.web.rest.util;

import aperture.model.Room;
import aperture.web.rest.util.ResponseEntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests on response entity utils class.
 */
public class ResponseEntityUtilsTest {
    private Room room1, room2;
    private List<Room> rooms;


    @Before
    public void setup() {
        room1 = new Room("idOne", 1, "nameOne");
        room2 = new Room("idTwo", 2, "nameTwo");
        rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);
    }

    @Test
    public void shouldReturnNoContentGetResponseEntityForSingleResponse() {
        ResponseEntity<?> resNoContent = ResponseEntityUtils.getResponseEntityForSingleResponse(null);
        assertEquals(resNoContent.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldReturn200AndContentGetResponseEntityForSingleResponse() {
        ResponseEntity<?> resOkWithContent = ResponseEntityUtils.getResponseEntityForSingleResponse(room1);
        assertEquals(resOkWithContent.getStatusCode(), HttpStatus.OK);
        assertEquals(room1, resOkWithContent.getBody());
    }

    @Test
    public void shouldReturnNoContentGetResponseEntityForMultipleResponses() {
        ResponseEntity<?> resNoContent = ResponseEntityUtils.getResponseEntityForMultipleResponses(new ArrayList<>());
        assertEquals(resNoContent.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldReturn200AndContentGetResponseEntityForMultipleResponses() {
        ResponseEntity<?> resOkWithContent = ResponseEntityUtils.getResponseEntityForMultipleResponses(rooms);
        assertEquals(resOkWithContent.getStatusCode(), HttpStatus.OK);
        assertEquals(rooms, resOkWithContent.getBody());
    }
}
