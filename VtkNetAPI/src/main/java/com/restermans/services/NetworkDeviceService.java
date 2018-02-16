package com.restermans.services;

import com.restermans.exceptions.AccessTimeOutException;
import com.restermans.exceptions.UnexpectedErrorException;
import com.restermans.model.*;
import com.restermans.snmp.DeviceOid;
import com.restermans.snmp.SnmpService;
import org.icmp4j.IcmpPingRequest;
import org.icmp4j.IcmpPingResponse;
import org.icmp4j.IcmpPingUtil;
import org.snmp4j.PDU;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import java.util.*;

public class NetworkDeviceService {

    // Public class methods ...
    public static NetworkDevice getNetworkDeviceDescription(String deviceIp) throws Exception {

        NetworkDevice networkDevice = new NetworkDevice(deviceIp);

        try (SnmpService snmpService = new SnmpService()) {

            List<String> oids = DeviceOid.Scalar.getAll();

            ResponseEvent responseEvent = snmpService.get(deviceIp, oids);
            if (Objects.isNull(responseEvent))
                throw new AccessTimeOutException("Access time out! Device ip: " + "'" + deviceIp + "'");
            if (Objects.nonNull(responseEvent.getError()))
                throw responseEvent.getError();

            PDU pdu = responseEvent.getResponse();
            if(Objects.isNull(pdu))
                throw new AccessTimeOutException("Access time out! Device ip: " + "'" + deviceIp + "'");

            Vector<? extends VariableBinding> variableBindings = pdu.getVariableBindings();

            // Refactor out this shit
            variableBindings.forEach(binding -> {

                String oid = binding.getOid().toString();
                DeviceOid.Scalar oidType = DeviceOid.Scalar.getByValue(oid);
                if(Objects.isNull(oidType))
                    throw new UnexpectedErrorException("Could not find proper oid type for oid = " + "'" + oid + "'");

                Variable variable = binding.getVariable();

                switch (oidType) {
                    case SYS_NAME:
                        networkDevice.setName(variable.toString()); break;
                    case SYS_DESCR:
                        networkDevice.setDescription(variable.toString()); break;
                    case SYS_CONTACT:
                        networkDevice.setVendorContact(variable.toString()); break;
                    case SYS_LOCATION:
                        networkDevice.setVendorLocation(variable.toString()); break;
                    case SYS_UP_TIME_INSTANCE:
                        networkDevice.setUpTime(Duration.from(variable.toString())); break;
                    case IF_TABLE_LAST_CHANGE:
                        networkDevice.setInterfaceTableLastChange(Duration.from(variable.toString())); break;
                    case SYS_SERVICES:
                        networkDevice.setServiceLevel(variable.toInt()); break;
                }
            });

        }

        return networkDevice;
    }

    public static List<ICMPResponse> ping(String deviceIp, int repeats) {

        List<ICMPResponse> icmpResponses = new ArrayList<>();

        final IcmpPingRequest request = IcmpPingUtil.createIcmpPingRequest();
        request.setHost(deviceIp);

        for (int i = 0; i < repeats; ++i) {

            final IcmpPingResponse response = IcmpPingUtil.executePingRequest(request);
            final String formattedResponse = IcmpPingUtil.formatResponse(response);

            ICMPResponse icmpResponse = new ICMPResponse(response.getSuccessFlag(),formattedResponse);
            icmpResponses.add(icmpResponse);
        }

        return icmpResponses;
    }
}
