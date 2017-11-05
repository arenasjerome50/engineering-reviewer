package com.philcst.www.engineeringreviewer.data;


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
        this.database = openHelper.getWritableDatabase();

        // Define what you what to select in this case '*'
        String[] columns = {"*"};

        Cursor cursor = database.query(QuestionEntry.TABLE_NAME, columns,
                null, null, null, null, null);

        ArrayList<Question> questionArrayList = new ArrayList<>();
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
}
