package com.specialprojectslab.simpleux.message;

import java.util.Date;

public interface MessageLog {

    void onMessage(Date timestamp, String component, String message);
}
