package com.restermans.model;

// Nested classes and enums ...
public enum InterfaceStatus {

    up(1),
    down(2),
    testing(3),
    unknown(4),
    dormant(5),
    notPresent(6),
    lowerLayerDown(7),

    NOT_KNOWN(0);

    public final int value;

    InterfaceStatus(int value) {
        this.value = value;
    }

    public static InterfaceStatus getByValue(int value) {
        for (InterfaceStatus nextCase : values())
            if (nextCase.value == value)
                return nextCase;

        return NOT_KNOWN;
    }
}
