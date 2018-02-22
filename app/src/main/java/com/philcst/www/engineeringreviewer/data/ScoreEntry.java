package com.philcst.www.engineeringreviewer.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Entity class of scores table
 */

public class ScoreEntry implements Parcelable{

    private int id;
    private Date date;
    private String topic;
    private int score;
    private double percentage;
    private String remarks;

    public ScoreEntry(int id, Date date, String topic, int score, double percentage, String remarks) {
        this.id = id;
        this.date = date;
        this.topic = topic;
        this.score = score;
        this.percentage = percentage;
        this.remarks = remarks;
    }

    public ScoreEntry(Parcel in) {
        this.id = in.readInt();
        this.date = new Date(in.readLong());
        this.topic = in.readString();
        this.score = in.readInt();
        this.percentage = in.readDouble();
        this.remarks = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(date.getTime());
        dest.writeString(topic);
        dest.writeInt(score);
        dest.writeDouble(percentage);
        dest.writeString(remarks);
    }

    public static final Parcelable.Creator<ScoreEntry> CREATOR = new Parcelable.Creator<ScoreEntry>() {
        @Override
        public ScoreEntry createFromParcel(Parcel source) {
            return new ScoreEntry(source);
        }

        @Override
        public ScoreEntry[] newArray(int size) {
            return new ScoreEntry[size];
        }
    };


    public int getId() {
        return this.id;
    }

    public Date getDate() {
        return this.date;
    }

    public String getTopic() {
        return this.topic;
    }

    public int getScore() {
        return this.score;
    }

    public double getPercentage() {
        return this.percentage;
    }

    public String getRemarks() {
        return this.remarks;
    }
}
