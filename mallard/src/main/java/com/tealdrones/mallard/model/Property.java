package com.tealdrones.mallard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a property of a {@link RemoteFile}
 *
 * Properties represent the properties of a {@link RemoteFile} in
 * the XML of a WebDAV PROPFIND response. Properties can be nested.
 * They are identified by a namespace/name combination and have
 * an optional text value.
 */
public class Property {
    private String namespace;
    private String name;
    private String text;
    private List<Property> subProperties;

    protected Property(Builder builder) {
        this.namespace = builder.namespace;
        this.name = builder.name;
        this.text = builder.text;
        this.subProperties = builder.subProperties;
    }

    /**
     * @return The namespace of a property (often `DAV:`)
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @return The name of a property
     */
    public String getName() {
        return name;
    }

    /**
     * @return The value of the property
     */
    public String getText() {
        return text;
    }

    /**
     * @return A list of sub-properties relating to this {@link Property}
     */
    public List<Property> getSubProperties() {
        return subProperties;
    }

    /**
     * Recursively searches for a sub-property specified by namespace and name.
     *
     * @param namespace The namespace of the property (often `DAV:`)
     * @param name The name of the property
     * @return The {@link Property} or null if not found.
     */
    public Property getSubProperty(String namespace, String name) {
        for(Property property : subProperties) {
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
        private String namespace;
        private String name;
        private String text;
        private List<Property> subProperties;

        public Builder() {
            subProperties = new ArrayList<>();
        }

        public Builder setNamespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Builder addSubProperty(Property property) {
            this.subProperties.add(property);
            return this;
        }

        public Builder setSubProperties(List<Property> subProperties) {
            this.subProperties = subProperties;
            return this;
        }

        public Property build() {
            return new Property(this);
        }
    }
}
