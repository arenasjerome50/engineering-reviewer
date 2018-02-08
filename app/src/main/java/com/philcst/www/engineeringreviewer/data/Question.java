package com.philcst.www.engineeringreviewer.data;

import android.util.SparseIntArray;

import com.philcst.www.engineeringreviewer.R;

/**
 * A class thats encapsulates Question id, Question, options and the correct answer.
 */

public class Question {

    private int ID;
    private String QUESTION;
    private String OPTA;
    private String OPTB;
    private String OPTC;
    private String ANSWER;

    //private HashMap<Integer, Integer> images = new HashMap<>();
    private static SparseIntArray images = new SparseIntArray();

    static {
        images.put(91, R.drawable.ic_img_91);
    }

    public Question() {
        ID=0;
        QUESTION="";
        OPTA="";
        OPTB="";
        OPTC="";
        ANSWER="";
    }

    public Question(String qUESTION, String oPTA, String oPTB, String oPTC, String aNSWER) {
        QUESTION = qUESTION;
        OPTA = oPTA;
        OPTB = oPTB;
        OPTC = oPTC;
        ANSWER = aNSWER;
    }

    /**
     * Getter Methods
     */
    public int getID() {
        return ID;
    }

    public String getQUESTION() {
        return QUESTION;
    }

    public String getOPTA() {
        return OPTA;
    }

    public String getOPTB() {
        return OPTB;
    }

    public String getOPTC() {
        return OPTC;
    }

    public String getANSWER() {
        return ANSWER;
    }
    // End Getter Methods

    public static int getRelatedImage(int question_id) {
        return images.get(question_id);
    }

    /**
     * Setter Methods
     */
    public void setID(int id) {
        ID=id;
    }

    public void setQUESTION(String qUESTION) {
        QUESTION = qUESTION;
    }

    public void setOPTA(String oPTA) {
        OPTA = oPTA;
    }

    public void setOPTB(String oPTB) {
        OPTB = oPTB;
    }

    public void setOPTC(String oPTC) {
        OPTC = oPTC;
    }

    public void setANSWER(String aNSWER) {
        ANSWER = aNSWER;
    }
    // End Setter Methods
}
