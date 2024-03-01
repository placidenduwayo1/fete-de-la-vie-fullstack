package com.fdlv.gmd.api.domain.enumeration;

/**
 * The ResponseType enumeration.
 */
public enum ResponseType {
    MULTIPLE("multiple"),
    UNIQUE("unique");

    private final String value;

    ResponseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
