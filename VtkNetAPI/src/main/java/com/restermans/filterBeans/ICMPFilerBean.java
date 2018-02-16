package com.restermans.filterBeans;

import javax.ws.rs.QueryParam;

public class ICMPFilerBean {

    private @QueryParam("repeats") int repeats;

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }
}
