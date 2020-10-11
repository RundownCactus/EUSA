package com.salmanqureshi.eusa;

import android.graphics.Bitmap;

public class Contact {
    private Bitmap image;
    private String name;
    private String phone;
    private String email;
    private String address;



    public Contact(Bitmap image, String name, String phone, String email, String address) {
        this.image=image;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
