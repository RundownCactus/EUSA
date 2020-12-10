package com.salmanqureshi.eusa;

public class Job {
    String SPID;
    String UID;

    public String getSPID() {
        return SPID;
    }

    public void setSPID(String SPID) {
        this.SPID = SPID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public Job(String SPID, String UID) {
        this.SPID = SPID;
        this.UID = UID;
    }
}
