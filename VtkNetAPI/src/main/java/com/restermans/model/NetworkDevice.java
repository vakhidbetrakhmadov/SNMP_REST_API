package com.restermans.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class NetworkDevice {

    // Private instance properties ...
    private String ipAddress;
    private String name;
    private String description;
    private String vendorContact;
    private String vendorLocation;
    private Duration upTime;
    private Duration interfaceTableLastChange;
    private List<NetworkDeviceSystemServiceLevel> serviceLevel;
    private List<InterfaceEntry> interfaceTable;

    // Constructors ...
    public NetworkDevice() { this(""); }

    public NetworkDevice(String ipAddress) {
        this.ipAddress = ipAddress;
        this.name = "";
        this.description = "";
        this.vendorContact = "";
        this.vendorLocation = "";
        this.upTime = new Duration();
        this.interfaceTableLastChange = new Duration();
        this.serviceLevel = new ArrayList<>();
        this.interfaceTable = new ArrayList<>();
    }

    public NetworkDevice(NetworkDevice other) {

        ipAddress = other.ipAddress;
        name = other.name;
        description = other.description;
        vendorContact = other.vendorContact;
        vendorLocation = other.vendorLocation;
        upTime = other.getUpTime();
        interfaceTableLastChange = other.getInterfaceTableLastChange();
        serviceLevel = other.getServiceLevel();
        interfaceTable = other.getInterfaceTable();
    }

    // Getters ...
    final public String getIpAddress() { return ipAddress; }

    final public String getName() {
        return name;
    }

    final public String getDescription() {
        return description;
    }

    final public String getVendorContact() { return vendorContact; }

    final public String getVendorLocation() {
        return vendorLocation;
    }

    final public Duration getUpTime() {
        return upTime.clone();
    }

    final public Duration getInterfaceTableLastChange() { return interfaceTableLastChange.clone(); }

    final public List<NetworkDeviceSystemServiceLevel> getServiceLevel() { return new ArrayList<>(serviceLevel); }

    final public List<InterfaceEntry> getInterfaceTable() {
        List<InterfaceEntry> interfaceTable = new ArrayList<>(this.interfaceTable.size());
        this.interfaceTable.forEach(interfaceEntry -> interfaceTable.add(interfaceEntry.clone()));
        return interfaceTable;
    }

    // Setters ...
    final public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    final public void setName(String name) {
        this.name = name;
    }

    final public void setDescription(String description) {
        this.description = description;
    }

    final public void setVendorContact(String vendorContact) {
        this.vendorContact = vendorContact;
    }

    final public void setVendorLocation(String vendorLocation) {
        this.vendorLocation = vendorLocation;
    }

    final public void setUpTime(Duration upTime) {
        this.upTime = upTime;
    }

    final public void setInterfaceTableLastChange(Duration interfaceTableLastChange) { this.interfaceTableLastChange = interfaceTableLastChange; }

    final public void setServiceLevel(int level) {
        for(NetworkDeviceSystemServiceLevel next: NetworkDeviceSystemServiceLevel.values())
            if ((level & next.value) != 0)
                this.serviceLevel.add(next);
    }

    final public void setInterfaceTable(List<InterfaceEntry> interfaceTable) {
        this.interfaceTable = interfaceTable;
    }
}
