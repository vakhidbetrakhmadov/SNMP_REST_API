package com.restermans.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Error {

    private int code;
    private String message;

    public Error() { this(0,""); }

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    final public int getCode() {
        return code;
    }

    final public void setCode(int code) {
        this.code = code;
    }

    final public String getMessage() {
        return message;
    }

    final public void setMessage(String message) {
        this.message = message;
    }
}
