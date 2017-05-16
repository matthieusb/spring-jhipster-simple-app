package aperture.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Sample greeintg spring-boot class.
 */
public class Greeting {
    /**
     * Greeting identifier.
     */
    private final long id;

    /**
     * The content to display with the greeting.
     */
    private final String content;

    /**
     * Greeing constructor.
     *
     * @param idToSet      greeting identifier.
     * @param contentToSet the content to display with the greeting.
     */
    public Greeting(long idToSet, String contentToSet) {
        this.id = idToSet;
        this.content = contentToSet;
    }

    /**
     * gets the greet id.
     *
     * @return a long value.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Gets the greeting content.
     *
     * @return the greetings string contant, can be null or empty
     */
    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The greetings content", required = true)
    public String getContent() {
        return this.content;
    }
}
