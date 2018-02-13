package com.tealdrones.mallard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a file or directory on a WebDAV server.
 *
 * Remote files have a few properties:
 *  * {@link #href}: the path of the resource
 *  * {@link #status}: the http status of the file
 *  * {@link #properties}: the properties of the resource
 *
 * Properties is a {@link List} of {@link Property} that corresponds to the
 * properties in the PROPFIND XML response. {@link Property}s can have
 * sub-properties. A specific property can be accessed by calling
 * {@link #getProperty(String, String)} and specifying a namespace and name.
 * This will recursively search for the property and return it once it is found.
 * {@link #getProperties()} can be called to return the entire list of properties.
 */
public class RemoteFile {
    private String href;
    private String status;
    private List<Property> properties;

    protected RemoteFile(Builder builder) {
        this.href = builder.href;
        this.status = builder.status;
        this.properties = builder.properties;
    }

    /**
     * @return The path of the resource on the WebDAV server.
     */
    public String getHref() {
        return href;
    }

    /**
     * @return A list of properties of the resource.
     */
    public List<Property> getProperties() {
        return properties;
    }

    /**
     * @return An http status of the resource for the request.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Recursively searches for a property specified by namespace and name.
     *
     * @param namespace The namespace of the property (often `DAV:`)
     * @param name The name of the property
     * @return The {@link Property} or null if not found.
     */
    public Property getProperty(String namespace, String name) {
        for(Property property : properties) {
            if (property.getNamespace().equals(namespace) &&
                    property.getName().equals(name)) {
                return property;
            } else {
                Property subProperty = property.getSubProperty(namespace, name);

                if (subProperty != null) {
                    return subProperty;
                }
            }
        }

        return null;
    }

    public static class Builder {
        private String href;
        private String status;
        private List<Property> properties;

        public Builder() {
            properties = new ArrayList<>();
        }

        public void setHref(String href) {
            this.href = href;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Builder addProperties(List<Property> properties) {
            this.properties.addAll(properties);
            return this;
        }

        public void setProperties(List<Property> properties) {
            this.properties = properties;
        }

        public RemoteFile build() {
            return new RemoteFile(this);
        }
    }
}
