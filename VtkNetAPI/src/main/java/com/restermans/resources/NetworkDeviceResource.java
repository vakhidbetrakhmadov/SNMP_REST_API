package com.restermans.resources;

import com.restermans.filterBeans.ICMPFilerBean;
import com.restermans.model.ICMPResponse;
import com.restermans.model.InterfaceEntry;
import com.restermans.model.NetworkDevice;
import com.restermans.services.InterfaceEntriesService;
import com.restermans.services.NetworkDeviceService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/networkDevice")
@Produces(MediaType.APPLICATION_JSON)
public class NetworkDeviceResource {

    @GET
    @Path("/{deviceIp}")
    public NetworkDevice getNetworkDeviceAll(@PathParam("deviceIp") String deviceIp) throws Exception {
        NetworkDevice networkDevice = NetworkDeviceService.getNetworkDeviceDescription(deviceIp);
        List<InterfaceEntry> interfaceEntries = InterfaceEntriesService.getAllInterfaceEntries(deviceIp);
        networkDevice.setInterfaceTable(interfaceEntries);

        return networkDevice;
    }

    @GET
    @Path("/{deviceIp}/ping")
    public List<ICMPResponse> ping(@PathParam("deviceIp") String deviceIp,
                                   @BeanParam ICMPFilerBean icmpFilerBean) {
        if (icmpFilerBean.getRepeats() == 0)
            return NetworkDeviceService.ping(deviceIp,1);

        return NetworkDeviceService.ping(deviceIp,icmpFilerBean.getRepeats());
    }

    @GET
    @Path("/{deviceIp}/description")
    public NetworkDevice getNetworkDeviceDescription(@PathParam("deviceIp") String deviceIp) throws Exception {
        return NetworkDeviceService.getNetworkDeviceDescription(deviceIp);
    }

    @Path("/{deviceIp}/interfaces")
    public InterfaceEntriesResource getInterfaceEntriesResource() {
        return new InterfaceEntriesResource();
    }
}
