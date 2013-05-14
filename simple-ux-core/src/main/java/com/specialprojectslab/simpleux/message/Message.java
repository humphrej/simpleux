package com.specialprojectslab.simpleux.message;

import java.util.Date;

class Message {

    private final Date timestamp ;
    private final String component;
    private final String message ;

    public Message(Date timestamp, String component, String message) {
        this.timestamp = timestamp;
        this.component = component;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getComponent() {
        return component;
    }

    public String getMessage() {
        return message;
    }
}
