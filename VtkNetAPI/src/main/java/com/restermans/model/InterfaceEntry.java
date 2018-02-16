package com.restermans.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InterfaceEntry implements Cloneable {

    // Private instance properties ...
    private int index;
    private String name;
    private String alias;
    private InterfaceType type;
    private InterfaceStatus adminStatus;
    private InterfaceStatus operationStatus;
    private long speed;
    private Duration lastChange;
    private long inErrorsCount;
    private long outErrorsCount;

    // Constructors ...
    public InterfaceEntry() { this(0); }

    public InterfaceEntry(int index) {
        this.index = index;
        this.name = "";
        this.alias = "";
        this.type = InterfaceType.NOT_KNOWN;
        this.adminStatus = InterfaceStatus.NOT_KNOWN;
        this.operationStatus = InterfaceStatus.NOT_KNOWN;
        this.speed = 0;
        this.lastChange = new Duration();
        this.inErrorsCount = 0;
        this.outErrorsCount = 0;
    }

    // Getters ...
    final public int getIndex() { return index; }

    final public String getName() {
        return name;
    }

    final public String getAlias() {
        return alias;
    }

    final public InterfaceType getType() {
        return type;
    }

    final public InterfaceStatus getAdminStatus() {
        return adminStatus;
    }

    final public InterfaceStatus getOperationStatus() {
        return operationStatus;
    }

    final public long getSpeed() {
        return speed;
    }

    final public Duration getLastChange() { return lastChange.clone(); }

    final public long getInErrorsCount() {
        return inErrorsCount;
    }

    final public long getOutErrorsCount() {
        return outErrorsCount;
    }

    // Setters ...
    final public void setIndex(int index) { this.index = index; }

    final public void setName(String name) { this.name = name; }

    final public void setAlias(String alias) { this.alias = alias; }

    final public void setType(InterfaceType type) {
        this.type = type;
    }

    final public void setAdminStatus(InterfaceStatus adminStatus) {
        this.adminStatus = adminStatus;
    }

    final public void setOperationStatus(InterfaceStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    final public void setSpeed(long speed) {
        this.speed = speed;
    }

    final public void setLastChange(Duration lastChange) { this.lastChange = lastChange; }

    final public void setInErrorsCount(long inErrorsCount) { this.inErrorsCount = inErrorsCount; }

    final public void setOutErrorsCount(long outErrorsCount) {
        this.outErrorsCount = outErrorsCount;
    }

    // Public instance methods

    @Override
    public InterfaceEntry clone() {
        try {
            InterfaceEntry interfaceEntry =  (InterfaceEntry) super.clone();
            interfaceEntry.setLastChange(lastChange);
            return interfaceEntry;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }

}