package com.salmanqureshi.eusa;

public class JobHistory {
    private String dateTime;
    private String price;
    private String jobId;
    private String status;

    public JobHistory(String dateTime, String price, String jobId, String status) {
        this.dateTime = dateTime;
        this.price = price;
        this.jobId = jobId;
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
