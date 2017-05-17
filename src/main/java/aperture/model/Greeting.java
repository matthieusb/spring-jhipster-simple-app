package aperture.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Sample greeintg spring-boot class.
 */
public class Greeting {

    private final long id;

    private final String content;


    public Greeting(long idToSet, String contentToSet) {
        this.id = idToSet;
        this.content = contentToSet;
    }


    public long getId() {
        return this.id;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The greetings content", required = true)
    public String getContent() {
        return this.content;
    }
}
