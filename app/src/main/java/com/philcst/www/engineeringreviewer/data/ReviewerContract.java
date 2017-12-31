package com.philcst.www.engineeringreviewer.data;

import android.provider.BaseColumns;

/**
 * A class that explicitly specifies the layout of database schema in a systematic and self-documenting way.
 */

public final class ReviewerContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ReviewerContract() {}

    public static abstract class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String _ID = "_id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTA = "opt_a";
        public static final String COLUMN_OPTB = "opt_b";
        public static final String COLUMN_OPTC = "opt_c";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_CATEGORY = "category";

        public static final int _ID_INDEX = 0;
        public static final int COLUMN_QUESTION_INDEX = 1;
        public static final int COLUMN_OPTA_INDEX = 2;
        public static final int COLUMN_OPTB_INDEX = 3;
        public static final int COLUMN_OPTC_INDEX = 4;
        public static final int COLUMN_ANSWER_INDEX = 5;
        public static final int COLUMN_CATEGORY_INDEX = 6;
    }

}

