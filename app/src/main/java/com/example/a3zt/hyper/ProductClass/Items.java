package com.example.a3zt.hyper.ProductClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("productDescription")
    @Expose
    private String productDescription;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("price")
    @Expose
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
