package com.springboot.blog.dto;

import java.util.Date;

public class ErrorDetails {
    private Date timestamps;
    private int status;
    private String message;
    private String details;

    public ErrorDetails(Date timestamps, int status, String message, String details) {
        this.timestamps = timestamps;
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamps() {
        return timestamps;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
