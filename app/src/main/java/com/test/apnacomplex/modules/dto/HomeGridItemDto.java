package com.test.apnacomplex.modules.dto;

/**
 * Created by sc on 5/4/18.
 */

public class HomeGridItemDto {

    private int id;
    private int bgImage;
    private String lable;

    public HomeGridItemDto(int id, int imgId, String label) {
        this.id = id;
        this.bgImage = imgId;
        this.lable = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBgImage() {
        return bgImage;
    }

    public void setBgImage(int bgImage) {
        this.bgImage = bgImage;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
