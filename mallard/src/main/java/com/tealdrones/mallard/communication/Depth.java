package com.tealdrones.mallard.communication;

/**
 * Specifies the depth of a Mallard WebDAV request.}
 */
public enum Depth {
    /**
     * The method is applied only to the resource.
     */
    ZERO("0"),

    /**
     * The method is applied to the resource and to its immediate children.
     */
    ONE("1");

    private String value;

    Depth(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
