package com.media.ui.ServerJobs;

/**
 * Created by prabeer.kochar on 18-07-2017.
 */

public class pollRequest {
    private String IM;
    private String st;
    private String loc;
    private String mcc;
    private String cel;

    public pollRequest(String IM, String st, String loc, String mcc, String cel){
        this.IM = IM;
        this.st = st;
        this.loc = loc;
        this.mcc = mcc;
        this.cel = cel;
    }
    public void setIM(String IM){
        this.IM = IM;
    }
    public void setst(String st){
        this.st = st;
    }
    public void setloc(String loc){
        this.loc = loc;
    }
    public void setmcc(String mcc){
        this.mcc = mcc;
    }
    public void setcel(String cel){
        this.cel = cel;
    }

    public String getIM(){
        return IM;
    }
    public String getst(){
        return st;
    }
    public String getloc(){
        return loc;
    }
    public String getmcc(){
        return mcc;
    }
    public String getcel(){
        return cel;
    }

}
