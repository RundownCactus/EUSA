package com.salmanqureshi.eusa;

import android.graphics.Bitmap;
//class to store service providers data
public class ServiceProvider {
    private Bitmap image;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private String cnic;
    private String city;
    private String Uid;
    private String address;
    private String rating;
    private String price;
    private String worktype;
    private String loc;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public ServiceProvider(String Uid,Bitmap image, String fname, String lname, String phone, String worktype, String address, String rating, String pricerat, String loc) {
        this.image=image;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.worktype = worktype;
        this.address=address;
        this.rating = rating;
        this.price = pricerat;
        this.loc=loc;
        this.Uid = Uid;
    }


    public ServiceProvider() {
        this.image=null;
        this.fname = "default";
        this.lname = "default";
        this.phone = "default";
        this.worktype = "default";
        this.address="default";
    }
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
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
