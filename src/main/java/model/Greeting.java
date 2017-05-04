package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
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
