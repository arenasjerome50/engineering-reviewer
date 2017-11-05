package com.philcst.www.engineeringreviewer.data;


import java.util.ArrayList;

public class Topic {
    private int imageId;
    private String title;
    private String desc;
    private ArrayList<Topic> subTopics;
    private String content;

    public Topic(int imageId, String title, String desc, ArrayList<Topic> subTopics){
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
        this.subTopics = subTopics;
        this.content = null;
    }

    public Topic(int imageId, String title, String desc, String content){
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
        this.subTopics = null;
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() { return desc; }

    public boolean hasSubTopics() {
        return subTopics != null;
    }

    public Topic getSubTopic(int position) {
        return subTopics.get(position);
    }

    public ArrayList<Topic> getSubTopicList() {
        return subTopics;
    }

    public String getContent() {
        return content;
    }
}
