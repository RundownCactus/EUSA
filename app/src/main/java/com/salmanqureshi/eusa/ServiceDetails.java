package com.salmanqureshi.eusa;

public class ServiceDetails {
    private String title;
    private String price;
    private String description;
    private String key;


    public ServiceDetails(String title, String price, String description, String key) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
