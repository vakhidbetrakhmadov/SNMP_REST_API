package com.restermans.services;

import com.restermans.exceptions.AccessTimeOutException;
import com.restermans.exceptions.DataNotFoundException;
import com.restermans.exceptions.UnexpectedErrorException;
import com.restermans.model.Duration;
import com.restermans.model.InterfaceEntry;
import com.restermans.model.InterfaceStatus;
import com.restermans.model.InterfaceType;
import com.restermans.snmp.DeviceOid;
import com.restermans.snmp.InterfaceOid;
import com.restermans.snmp.SnmpService;
import org.snmp4j.PDU;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.TreeEvent;

import java.io.IOException;
import java.util.*;

public class InterfaceEntriesService {

    // Public class methods ...
    public static Set<Integer> getAllInterfaceIndexes(String deviceIp) throws IOException {

        Set<Integer> interfaceIndexes = new HashSet<>();

        try(SnmpService snmpService = new SnmpService()) {

            List<TreeEvent> treeEvents = snmpService.walk(deviceIp, DeviceOid.Tabular.IF_INDEX.value);
            if (Objects.isNull(treeEvents))
                throw new AccessTimeOutException("Access time out! Device ip: " + "'" + deviceIp + "'");
            if (treeEvents.isEmpty())
                throw new UnexpectedErrorException("IfIndex table was not found. Device ip: " + "'" + deviceIp + "'");

            for(TreeEvent treeEvent: treeEvents) {
                VariableBinding[] variableBindings = treeEvent.getVariableBindings();
                if(Objects.isNull(variableBindings))
                    throw new AccessTimeOutException("Access time out! Device ip: " + "'" + deviceIp + "'");

                for (VariableBinding variableBinding: variableBindings)
                    interfaceIndexes.add(variableBinding.getVariable().toInt());
            }


        }

        return interfaceIndexes;
    }

    public static List<InterfaceEntry> getAllInterfaceEntries(String deviceIp) throws Exception {
        List<InterfaceEntry> interfaceEntries = new ArrayList<>();
        Set<Integer> interfaceIndexes = getAllInterfaceIndexes(deviceIp);
        for(Integer index: interfaceIndexes)
            interfaceEntries.add(__getInterfaceEntry(deviceIp,index));

        return interfaceEntries;
    }

    public static InterfaceEntry getInterfaceEntry(String deviceIp, int interfaceIndex) throws Exception {

        Set<Integer> interfaceIndexes = getAllInterfaceIndexes(deviceIp);
        if(!interfaceIndexes.contains(interfaceIndex))
            throw new DataNotFoundException("Interface was not found. deviceIp = '" + deviceIp +"'" + ", interfaceIndex = '" + interfaceIndex + "'");

        return __getInterfaceEntry(deviceIp,interfaceIndex);
    }

    // Private class methods ...
    private static InterfaceEntry __getInterfaceEntry(String deviceIp, int interfaceIndex) throws Exception {

        InterfaceEntry interfaceEntry = new InterfaceEntry(interfaceIndex);

        try (SnmpService snmpService = new SnmpService()) {

            List<String> oids = InterfaceOid.Tabular.getAll();
            oids.replaceAll( oid -> oid + "." + interfaceIndex);

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
                String oidWithoutIndex = oid.substring(0,oid.lastIndexOf("."));

                InterfaceOid.Tabular oidType = InterfaceOid.Tabular.getByValue(oidWithoutIndex);
                if (Objects.isNull(oidType))
                    throw new UnexpectedErrorException("Could not find proper oid type for oid = " + "'" + oidWithoutIndex + "'");

                Variable variable = binding.getVariable();

                switch (oidType) {
                    case IF_NAME:
                        interfaceEntry.setName(variable.toString()); break;
                    case IF_ALIAS:
                        interfaceEntry.setAlias(variable.toString()); break;
                    case IF_TYPE:
                        interfaceEntry.setType(InterfaceType.getByValue(variable.toInt())); break;
                    case IF_ADMIN_STATUS:
                        interfaceEntry.setAdminStatus(InterfaceStatus.getByValue(variable.toInt())); break;
                    case IF_OPER_STATUS:
                        interfaceEntry.setOperationStatus(InterfaceStatus.getByValue(variable.toInt())); break;
                    case IF_HIGH_SPEED:
                        interfaceEntry.setSpeed(variable.toLong()); break;
                    case IF_LAST_CHANGE:
                        interfaceEntry.setLastChange(Duration.from(variable.toString())); break;
                    case IF_IN_ERRORS:
                        interfaceEntry.setInErrorsCount(variable.toLong()); break;
                    case IF_OUT_ERRORS:
                        interfaceEntry.setOutErrorsCount(variable.toLong()); break;
                }
            });
        }

        return interfaceEntry;
    }
}
