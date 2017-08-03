package com.media.ui.ServerJobs;

/**
 * Created by prabeer.kochar on 03-08-2017.
 */

public class pollResponse {
    private String status;
    private String data;

    public String getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
