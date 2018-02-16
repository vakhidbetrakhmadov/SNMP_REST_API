package com.restermans.model;

public class ICMPResponse {

    private boolean successful;
    private String responseMessage;

    public ICMPResponse() { this(false,""); }

    public ICMPResponse(boolean successStatus, String responseMessage) {
        this.successful = successStatus;
        this.responseMessage = responseMessage;
    }

    final public boolean isSuccessful() {
        return successful;
    }

    final public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    final public String getResponseMessage() {
        return responseMessage;
    }

    final public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
