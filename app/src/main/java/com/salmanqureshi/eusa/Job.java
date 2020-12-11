package com.salmanqureshi.eusa;

public class Job {
    String SPID;
    String UID;
    String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Job(String SPID, String UID, String status) {
        this.SPID = SPID;
        this.UID = UID;
        this.status=status;
    }
}
