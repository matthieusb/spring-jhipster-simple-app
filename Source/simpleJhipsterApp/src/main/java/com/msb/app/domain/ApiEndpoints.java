package com.msb.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ApiEndpoints.
 */

@Document(collection = "api_endpoints")
public class ApiEndpoints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 1)
    @Field("name")
    private String name;

    @NotNull
    @Size(min = 2)
    @Field("environment")
    private String environment;

    @NotNull
    @Size(min = 1)
    @Field("host")
    private String host;

    @NotNull
    @Size(min = 1)
    @Field("path")
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ApiEndpoints name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvironment() {
        return environment;
    }

    public ApiEndpoints environment(String environment) {
        this.environment = environment;
        return this;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getHost() {
        return host;
    }

    public ApiEndpoints host(String host) {
        this.host = host;
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public ApiEndpoints path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiEndpoints apiEndpoints = (ApiEndpoints) o;
        if (apiEndpoints.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, apiEndpoints.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApiEndpoints{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", environment='" + environment + "'" +
            ", host='" + host + "'" +
            ", path='" + path + "'" +
            '}';
    }
}
