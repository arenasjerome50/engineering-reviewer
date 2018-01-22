package com.philcst.www.engineeringreviewer.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.javahelps.externalsqliteimporter.ExternalSQLiteOpenHelper;
import com.philcst.www.engineeringreviewer.data.ReviewerContract.QuestionEntry;

import java.util.ArrayList;

public class DatabaseAccess {
    private ExternalSQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     */
    private DatabaseAccess(Context context, String sourceDirectory) {
        if (sourceDirectory == null) {
            this.openHelper = new DatabaseOpenHelper(context);
        } else {
            this.openHelper = new DatabaseOpenHelper(context, sourceDirectory);
        }
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context           the Context
     * @return the instance of DatabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context, null);
        }
        return instance;
    }

    // Open the database connection.
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    // Close the database connection.
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public ArrayList<Question> getAllQuestions() {
        open();

        // Define what you what to select in this case '*'
        String[] columns = {"*"};

        Cursor cursor = database.query(QuestionEntry.TABLE_NAME, columns,
                null, null, null, null, null);

        ArrayList<Question> questionArrayList = new ArrayList<>();
        // Looping through all rows and adding to list
        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setID(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionEntry._ID)));
            question.setQUESTION(cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_QUESTION)));
            question.setANSWER(cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_ANSWER)));
            question.setOPTA(cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_OPTA)));
            question.setOPTB(cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_OPTB)));
            question.setOPTC(cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_OPTC)));
            questionArrayList.add(question);
        }
        cursor.close();
        close();

        return questionArrayList;
    }

    public ArrayList<Question> getQuestions(int numberOfQuestions, String category) {
        open();

        String[] columns = {"*"};
        String selection_category;

        if (category == null) {
            selection_category = null;
        } else {
            selection_category = "category LIKE '" + category + "'";
        }

        // TODO:
        /*// Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };
        */

        Cursor cursor = database.query(QuestionEntry.TABLE_NAME, columns,
                selection_category, null, null,
                null, "random()", "" + numberOfQuestions);

        ArrayList<Question> questionArrayList = new ArrayList<>();
        // Looping through all rows and adding to list
        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setID(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionEntry._ID)));
            question.setQUESTION(cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_QUESTION)));
            question.setANSWER(cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_ANSWER)));
            question.setOPTA(cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_OPTA)));
            question.setOPTB(cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_OPTB)));
            question.setOPTC(cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_OPTC)));
            questionArrayList.add(question);
        }
        cursor.close();
        close();

        return questionArrayList;
    }

    public void addQuestion(Question question) {
        open(); // SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QuestionEntry.COLUMN_QUESTION, question.getQUESTION());
        values.put(QuestionEntry.COLUMN_ANSWER, question.getANSWER());
        values.put(QuestionEntry.COLUMN_OPTA, question.getOPTA());
        values.put(QuestionEntry.COLUMN_OPTB, question.getOPTB());
        values.put(QuestionEntry.COLUMN_OPTC, question.getOPTC());

        database.insert(QuestionEntry.TABLE_NAME, null, values);

        close();
    }
}
