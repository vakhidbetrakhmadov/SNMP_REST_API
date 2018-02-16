package com.restermans.snmp;

import java.util.ArrayList;
import java.util.List;

public interface DeviceOid {

    enum Scalar {

        SYS_NAME("1.3.6.1.2.1.1.5.0"),
        SYS_DESCR("1.3.6.1.2.1.1.1.0"),
        SYS_CONTACT("1.3.6.1.2.1.1.4.0"),
        SYS_LOCATION("1.3.6.1.2.1.1.6.0"),
        SYS_UP_TIME_INSTANCE("1.3.6.1.2.1.1.3.0"),
        IF_TABLE_LAST_CHANGE("1.3.6.1.2.1.31.1.5.0"),
        SYS_SERVICES("1.3.6.1.2.1.1.7.0");

        public final String value;

        Scalar(String value) { this.value = value; }

        public static List<String> getAll() {
            List<String> list = new ArrayList<>();
            for(Scalar scalarOid: Scalar.values())
                list.add(scalarOid.value);

            return list;
        }

        public static Scalar getByValue(String value) {
            for (Scalar scalarOidCase : values())
                if (scalarOidCase.value.equals(value))
                    return scalarOidCase;

            return null;
        }
    }

    enum Tabular {

        IF_INDEX("1.3.6.1.2.1.2.2.1.1");

        public final String value;

        Tabular(String value) { this.value = value; }

        public static List<String> getAll() {
            List<String> list = new ArrayList<>();
            for(Tabular tabularOid : Tabular.values())
                list.add(tabularOid.value);

            return list;
        }

        public static Tabular getByValue(String value) {
            for (Tabular tabularOidCase : values())
                if (tabularOidCase.value.equals(value))
                    return tabularOidCase;

            return null;
        }
    }
}
