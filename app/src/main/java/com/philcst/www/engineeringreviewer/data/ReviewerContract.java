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
    }

    public static abstract class ScoresEntry implements BaseColumns {
        public static final String TABLE_NAME = "scores";
        public static final String _ID = "_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TOPIC = "topic";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_PERCENTAGE = "percentage";
        public static final String COLUMN_REMARKS = "remarks";
        public static final String COLUMN_MODE = "mode";
    }

}

