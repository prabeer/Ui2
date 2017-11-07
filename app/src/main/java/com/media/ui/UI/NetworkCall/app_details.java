package com.media.ui.UI.NetworkCall;

/**
 * Created by prabeer.kochar on 06-11-2017.
 */

public class app_details {
    private String appName;
    private String imageURL;
    private String  APKlocation;
    private String APPPackage;

    public void setAPKlocation(String APKlocation) {
        this.APKlocation = APKlocation;
    }

    public String getAPKlocation() {
        return APKlocation;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAPPPackage(String APPPackage) {
        this.APPPackage = APPPackage;
    }

    public String getAPPPackage() {
        return APPPackage;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }


}
