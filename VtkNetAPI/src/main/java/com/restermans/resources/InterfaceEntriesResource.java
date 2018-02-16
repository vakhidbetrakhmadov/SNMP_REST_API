package com.restermans.resources;

import com.restermans.model.InterfaceEntry;
import com.restermans.services.InterfaceEntriesService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class InterfaceEntriesResource {

    @GET
    public List<InterfaceEntry> getAllInterfaceEntries(@PathParam("deviceIp") String deviceIp) throws Exception {
        return InterfaceEntriesService.getAllInterfaceEntries(deviceIp);
    }

    @GET
    @Path("/{interfaceIndex}")
    public InterfaceEntry getInterfaceEntry(@PathParam("deviceIp") String deviceIp,
                                            @PathParam("interfaceIndex") int interfaceIndex) throws Exception {
        return InterfaceEntriesService.getInterfaceEntry(deviceIp, interfaceIndex);
    }

    @GET
    @Path("/indexes")
    public Set<Integer> getAllInterfaceIndexes(@PathParam("deviceIp") String deviceIp) throws IOException {
        return InterfaceEntriesService.getAllInterfaceIndexes(deviceIp);
    }
}
