package com.example.demo.model.response;

import java.util.Date;

public class ErrorResponse {
    private Date timeStamp;
    private String message;

    public ErrorResponse() {

    }

    public ErrorResponse(Date timeStamp, String message) {
        this.timeStamp = timeStamp;
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
