package com.philcst.www.engineeringreviewer.data;


import android.os.Parcel;
import android.os.Parcelable;

import com.philcst.www.engineeringreviewer.R;
import com.philcst.www.engineeringreviewer.interfaces.ListItemShowable;

import java.util.ArrayList;
import java.util.Arrays;

public enum QuizMode implements Parcelable, ListItemShowable{

    /*
        It defines the Quiz modes of the App with relative description and icon
     */
    NORMAL("Normal Quiz", "Take the quiz with no time limit.", R.drawable.ic_normal_quiz),
    TIMED("Timed Quiz", "Quiz with time limit depends on the difficulty of the question.",
            R.drawable.ic_timed_quiz),
    VITALI_3("Vitali-3", "Prevent getting three(3) wrong answers to survive.",
            R.drawable.ic_vitali_quiz);

    private String name;
    private String desc;
    private int icon;

    QuizMode(String name, String desc, int icon) {
        this.name = name;
        this.desc = desc;
        this.icon = icon;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public static final Parcelable.Creator<QuizMode> CREATOR = new Parcelable.Creator<QuizMode>() {
        @Override
        public QuizMode createFromParcel(Parcel in) {
            String name = in.readString();
            if (name.equals(NORMAL.getName())) {
                return NORMAL;
            } else if (name.equals(TIMED.getName())) {
                return TIMED;
            } else if (name.equals(VITALI_3.getName())) {
                return VITALI_3;
            }

            return null;
        }

        @Override
        public QuizMode[] newArray(int size) {
            return new QuizMode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public int getIcon() {
        return icon;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    public static ArrayList<QuizMode> getValuesList() {
        ArrayList<QuizMode> values = new ArrayList<>();
        values.addAll(Arrays.asList(QuizMode.values()));
        return values;
    }
}
