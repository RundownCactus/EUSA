package com.salmanqureshi.eusa;

public class ServiceDetails {
    private String title;
    private String price;
    private String description;
    private String key;
    private String isSelected;

    public ServiceDetails(String title, String price, String description, String key, String isSelected) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.key = key;
        this.isSelected=isSelected;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
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
