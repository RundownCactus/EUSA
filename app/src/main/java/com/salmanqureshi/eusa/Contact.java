package com.salmanqureshi.eusa;

import android.graphics.Bitmap;

public class Contact {
    //private Bitmap image;
    private String fname;
    private String lname;
    private String phone;
    private String email;


    public Contact(String fname, String lname, String phone, String email) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
    }

    public Contact() {
        this.fname = "default";
        this.lname = "default";
        this.phone = "default";
        this.email = "default";
    }

  /*  public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }*/

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
