package com.salmanqureshi.eusa;

//job class to store job related data
public class Job {
    String SPID;
    String UID;
    String status;
    String jobBookTime;
    String userLatLng;
    String spLatLng;
    String spLatlngStart;
    String jobAcceptTime;
    String jobRejectTime;
    String userLocationReachedTime;
    String actualWorkStartTime;
    String jobCompletionTime;
    String jobCancelTime;
    String jobUserRating;
    String jobSPRating;
    String travelPrice;
    String workPrice;
    String totalPrice;

    public Job(String SPID, String UID, String status, String jobBookTime, String userLatLng,
               String jobAcceptTime, String userLocationReachedTime, String actualWorkStartTime,
               String jobCompletionTime, String jobCancelTime, String jobUserRating,
               String jobSPRating, String travelPrice, String workPrice, String totalPrice,
               String jobRejectTime,String spLatLng,String spLatlngStart) {
        this.SPID = SPID;
        this.UID = UID;
        this.status = status;
        this.jobBookTime = jobBookTime;
        this.userLatLng = userLatLng;
        this.jobAcceptTime = jobAcceptTime;
        this.userLocationReachedTime = userLocationReachedTime;
        this.actualWorkStartTime = actualWorkStartTime;
        this.jobCompletionTime = jobCompletionTime;
        this.jobCancelTime = jobCancelTime;
        this.jobUserRating = jobUserRating;
        this.jobSPRating = jobSPRating;
        this.travelPrice = travelPrice;
        this.workPrice = workPrice;
        this.totalPrice = totalPrice;
        this.jobRejectTime=jobRejectTime;
        this.spLatLng=spLatLng;
        this.spLatlngStart=spLatlngStart;
    }

    public String getSpLatlngStart() {
        return spLatlngStart;
    }

    public void setSpLatlngStart(String spLatlngStart) {
        this.spLatlngStart = spLatlngStart;
    }

    public String getSpLatLng() {
        return spLatLng;
    }

    public void setSpLatLng(String spLatLng) {
        this.spLatLng = spLatLng;
    }

    public String getJobRejectTime() {
        return jobRejectTime;
    }

    public void setJobRejectTime(String jobRejectTime) {
        this.jobRejectTime = jobRejectTime;
    }

    public String getJobAcceptTime() {
        return jobAcceptTime;
    }

    public void setJobAcceptTime(String jobAcceptTime) {
        this.jobAcceptTime = jobAcceptTime;
    }

    public String getUserLocationReachedTime() {
        return userLocationReachedTime;
    }

    public void setUserLocationReachedTime(String userLocationReachedTime) {
        this.userLocationReachedTime = userLocationReachedTime;
    }

    public String getActualWorkStartTime() {
        return actualWorkStartTime;
    }

    public void setActualWorkStartTime(String actualWorkStartTime) {
        this.actualWorkStartTime = actualWorkStartTime;
    }

    public String getJobCompletionTime() {
        return jobCompletionTime;
    }

    public void setJobCompletionTime(String jobCompletionTime) {
        this.jobCompletionTime = jobCompletionTime;
    }

    public String getJobCancelTime() {
        return jobCancelTime;
    }

    public void setJobCancelTime(String jobCancelTime) {
        this.jobCancelTime = jobCancelTime;
    }

    public String getJobUserRating() {
        return jobUserRating;
    }

    public void setJobUserRating(String jobUserRating) {
        this.jobUserRating = jobUserRating;
    }

    public String getJobSPRating() {
        return jobSPRating;
    }

    public void setJobSPRating(String jobSPRating) {
        this.jobSPRating = jobSPRating;
    }

    public String getTravelPrice() {
        return travelPrice;
    }

    public void setTravelPrice(String travelPrice) {
        this.travelPrice = travelPrice;
    }

    public String getWorkPrice() {
        return workPrice;
    }

    public void setWorkPrice(String workPrice) {
        this.workPrice = workPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }



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

    public String getJobBookTime() {
        return jobBookTime;
    }

    public void setJobBookTime(String jobBookTime) {
        this.jobBookTime = jobBookTime;
    }


    public String getUserLatLng() {
        return userLatLng;
    }

    public void setUserLatLng(String userLatLng) {
        this.userLatLng = userLatLng;
    }

}
