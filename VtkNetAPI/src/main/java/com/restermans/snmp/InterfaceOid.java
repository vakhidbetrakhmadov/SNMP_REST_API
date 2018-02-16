package com.restermans.snmp;

import java.util.ArrayList;
import java.util.List;

public interface InterfaceOid {

    enum Scalar { }

    enum Tabular {

        IF_NAME("1.3.6.1.2.1.31.1.1.1.1"),
        IF_ALIAS("1.3.6.1.2.1.31.1.1.1.18"),
        IF_TYPE("1.3.6.1.2.1.2.2.1.3"),
        IF_ADMIN_STATUS("1.3.6.1.2.1.2.2.1.7"),
        IF_OPER_STATUS("1.3.6.1.2.1.2.2.1.8"),
        IF_HIGH_SPEED("1.3.6.1.2.1.31.1.1.1.15"),
        IF_LAST_CHANGE("1.3.6.1.2.1.2.2.1.9"),
        IF_IN_ERRORS("1.3.6.1.2.1.2.2.1.14"),
        IF_OUT_ERRORS("1.3.6.1.2.1.2.2.1.20");

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
