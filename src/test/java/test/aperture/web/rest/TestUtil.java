package test.aperture.web.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import org.mongeez.Mongeez;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Utility class for testing REST controllers.
 */
class TestUtil {
    /** MediaType for JSON UTF8 */
    static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    static byte[] convertObjectToJsonBytes(Object object)
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsBytes(object);
    }

    private static Mongeez getMongeezRunner(String changeLogPath, String dbName, String hostName, int hostPort) {
        Mongeez mongeez = new Mongeez();
        mongeez.setFile(new ClassPathResource(changeLogPath));
        mongeez.setMongo(new MongoClient(hostName, hostPort));
        mongeez.setDbName(dbName);

        return mongeez;
    }

    static void executeAllMongeezScripts() {
        String currentDbName = System.getenv().getOrDefault("spring.data.mongodb.database", "apiApertureTest");
        String currentHost = System.getenv().getOrDefault("spring.data.mongodb.host", "localhost");
        String currentPort = System.getenv().getOrDefault("spring.data.mongodb.port", String.valueOf(27017));

        Mongeez mongeez = getMongeezRunner("db/mongeez.xml", currentDbName, currentHost, Integer.parseInt(currentPort));
        mongeez.process();
    }
}
