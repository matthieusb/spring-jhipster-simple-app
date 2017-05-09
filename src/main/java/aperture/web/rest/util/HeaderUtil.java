package aperture.web.rest.util;


import org.springframework.http.HttpHeaders;

public class HeaderUtil {
    public static HttpHeaders getStandardHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return headers;
    }
}
