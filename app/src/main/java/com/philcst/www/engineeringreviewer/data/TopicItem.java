package com.philcst.www.engineeringreviewer.data;


public class TopicItem {
    private int imageId;
    private String title;
    private String desc;

    public TopicItem(int imageId, String title, String desc){
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
