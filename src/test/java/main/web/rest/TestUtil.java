package main.web.rest;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;

/**
 * Utility class for testing REST controllers.
 */
public class TestUtil {
    /** MediaType for JSON UTF8 */
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
}
