package com.salmanqureshi.eusa;

import android.graphics.Bitmap;

public class ServiceProvider {
    private Bitmap image;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private String cnic;
    private String city;
    private String address;

    private String worktype;
    private String experience;
    private String hourlyrate;

    public ServiceProvider(Bitmap image, String fname, String lname, String phone, String worktype,String address) {
        this.image=image;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.worktype = worktype;
        this.address=address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getHourlyrate() {
        return hourlyrate;
    }

    public void setHourlyrate(String hourlyrate) {
        this.hourlyrate = hourlyrate;
    }



    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
