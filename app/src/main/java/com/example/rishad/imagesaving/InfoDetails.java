package com.example.rishad.imagesaving;

public class InfoDetails {

    private String name,price,shortName;
    private byte[] image;

    public InfoDetails(String name, String price,byte[] image, String shortName) {
        this.name = name;
        this.price = price;
        this.shortName = shortName;
        this.image = image;
    }

    public InfoDetails(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShortName() {
        return shortName;
    }

    public void  setShortName(String shortName) {
        this.shortName = shortName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
