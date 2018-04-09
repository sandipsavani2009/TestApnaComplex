package com.test.apnacomplex.io.dto;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by sc on 5/4/18.
 */
@Parcel
public class Repository {

    @SerializedName("cat_id")
    private String categoryId;

    @SerializedName("cat_name")
    private String categoryName;

    @SerializedName("cat_icon")
    private String categoryIcon;

    @SerializedName("cat_background_img")
    private String categryBackgroundImage;

    @SerializedName("num_docs")
    private int numberDoc;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategryBackgroundImage() {
        return categryBackgroundImage;
    }

    public void setCategryBackgroundImage(String categryBackgroundImage) {
        this.categryBackgroundImage = categryBackgroundImage;
    }

    public int getNumberDoc() {
        return numberDoc;
    }

    public void setNumberDoc(int numberDoc) {
        this.numberDoc = numberDoc;
    }
}
