package com.philcst.www.engineeringreviewer.data;


public class Topic {
    private int imageId;
    private String title;
    private String desc;

    public Topic(int imageId, String title, String desc){
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() { return desc; }
}
