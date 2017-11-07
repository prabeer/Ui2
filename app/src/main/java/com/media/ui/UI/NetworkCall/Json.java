package com.media.ui.UI.NetworkCall;

import java.util.Map;

/**
 * Created by prabeer.kochar on 06-11-2017.
 */

public class Json{
 private  String app_catagory;
    private Map<String,app_details> App_details;

    public Map<String, app_details> getApp_details() {
        return App_details;
    }

    public void setApp_details(Map<String, app_details> app_details) {
        App_details = app_details;
    }

    public String getApp_catagory() {
        return app_catagory;
    }

    public void setApp_catagory(String app_catagory) {
        this.app_catagory = app_catagory;
    }

}
