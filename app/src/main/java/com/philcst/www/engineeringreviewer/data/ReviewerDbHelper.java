package com.philcst.www.engineeringreviewer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.philcst.www.engineeringreviewer.data.ReviewerContract.QuestionEntry;


public class ReviewerDbHelper extends SQLiteOpenHelper {
    // if you change the database schema, you must increment the databse version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "reviewer.db";

    public ReviewerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        //addQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QuestionEntry.TABLE_NAME + " (" +
                    QuestionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    QuestionEntry.COLUMN_QUESTION + " TEXT," +
                    QuestionEntry.COLUMN_OPTA + " TEXT," +
                    QuestionEntry.COLUMN_OPTB + " TEXT," +
                    QuestionEntry.COLUMN_OPTC + " TEXT," +
                    QuestionEntry.COLUMN_ANSWER + " TEXT," +
                    QuestionEntry.COLUMN_CATEGORY + " TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + QuestionEntry.TABLE_NAME;

    // Not sure if I can use this function for better flow
    public void addQuestion(Question quest) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuestionEntry.COLUMN_QUESTION, quest.getQUESTION());
        values.put(QuestionEntry.COLUMN_ANSWER, quest.getANSWER());
        values.put(QuestionEntry.COLUMN_OPTA, quest.getOPTA());
        values.put(QuestionEntry.COLUMN_OPTB, quest.getOPTB());
        values.put(QuestionEntry.COLUMN_OPTC, quest.getOPTC());
        // Inserting Row
        this.getWritableDatabase().insert(QuestionEntry.TABLE_NAME, null, values);
    }

    /**
     * Temporary function for adding temporary data
     */
    public void addQuestions() {
        Question q1 = new Question("What is JP?","Jalur Pesawat", "Jack sParrow", "Jerome Programmer", "Jasa Programmer");
        this.addQuestion(q1);
        Question q2 = new Question("where the JP place?", "Monas, Jakarta", "Gelondong, Bangun Tapan, bantul", "Phil, Asia, USA", "Gelondong, Bangun Tapan, bantul");
        this.addQuestion(q2);
        Question q3 = new Question("who is CEO of the JP?","Usman and Jack", "Jack and Rully","BB and GG", "Rully and Usman" );
        this.addQuestion(q3);
        Question q4 = new Question("what do you know about JP?", "JP is programmer home", "JP also realigy home", "all answer is false","all answer is true");
        this.addQuestion(q4);
        Question q5 = new Question("what do you learn in JP?","Realigy","Programming","not all are true","all answer is true");
        this.addQuestion(q5);
    }

    /*public  ArrayList<Question> getAllQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define what you what to select in this case '*'
        String[] columns = {"*"};

        Cursor cursor = db.query(QuestionEntry.TABLE_NAME, columns, null, null, null, null, null);

        ArrayList<Question> questionArrayList= new ArrayList<>();
        // Looping through all rows and adding to list
        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setID(cursor.getInt(QuestionEntry._ID_INDEX));
            question.setQUESTION(cursor.getString(QuestionEntry.COLUMN_QUESTION_INDEX));
            question.setANSWER(cursor.getString(QuestionEntry.COLUMN_ANSWER_INDEX));
            question.setOPTA(cursor.getString(QuestionEntry.COLUMN_OPTA_INDEX));
            question.setOPTB(cursor.getString(QuestionEntry.COLUMN_OPTB_INDEX));
            question.setOPTC(cursor.getString(QuestionEntry.COLUMN_OPTC_INDEX));
            questionArrayList.add(question);
        }
        cursor.close();

        return questionArrayList;
    }
    */
}
