package com.restermans.model;

// Nested classes and enums ...
public enum NetworkDeviceSystemServiceLevel {
    physical(0x01),
    dataLink(0x02),
    internet(0x04),
    end_to_end(0x08),
    applications(0x40),

    NOT_KNOWN(0x00);

    public final int value;

    NetworkDeviceSystemServiceLevel(int value) {
        this.value = value;
    }

    public static NetworkDeviceSystemServiceLevel getByValue(int value) {
        for (NetworkDeviceSystemServiceLevel nextCase : values())
            if (nextCase.value == value)
                return nextCase;

        return NOT_KNOWN;
    }
}
