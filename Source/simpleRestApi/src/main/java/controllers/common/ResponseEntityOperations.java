package controllers.common;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseEntityOperations {

    public static <E> ResponseEntity<?> getResponseEntityForSingleResponse(E elementToReturn) {
        if (elementToReturn == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(elementToReturn, HttpStatus.OK);
        }
    }

    public static <E> ResponseEntity<?> getResponseEntityForMultipleResponses(List<E> listToReturn) {
        if (listToReturn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listToReturn, HttpStatus.OK);
        }
    }
}
