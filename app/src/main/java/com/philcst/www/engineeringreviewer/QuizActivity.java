package com.philcst.www.engineeringreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.philcst.www.engineeringreviewer.data.DatabaseAccess;
import com.philcst.www.engineeringreviewer.data.Question;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Question> questionArrayList;
    int score = 0;
    int questionId = 0;
    Question currentQuestion;
    TextView questionTextView;
    TextView scoreTextView;
    TextView questionNumberTextView;
    Button choiceAButton, choiceBButton, choiceCButton, choiceDButton /*,nextButton*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        questionArrayList = databaseAccess.getAllQuestions();
        Collections.shuffle(questionArrayList);

        currentQuestion = questionArrayList.get(questionId);
        questionTextView = (TextView) findViewById(R.id.question_textview);
        questionNumberTextView = (TextView) findViewById(R.id.question_number);
        scoreTextView = (TextView) findViewById(R.id.score_number);
        choiceAButton = (Button) findViewById(R.id.choice_a_button);
        choiceBButton = (Button) findViewById(R.id.choice_b_button);
        choiceCButton = (Button) findViewById(R.id.choice_c_button);
        choiceDButton = (Button) findViewById(R.id.choice_d_button);
        //nextButton = (Button) findViewById(R.id.next_button);
        setQuestionView();
        //displayDatabaseInfo();
        choiceAButton.setOnClickListener(this);
        choiceBButton.setOnClickListener(this);
        choiceCButton.setOnClickListener(this);
        choiceDButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String answer = currentQuestion.getANSWER();
        TextView t = (TextView) v;
        if (answer.equals(t.getText())) {
            score++;
            scoreTextView.setText("" + score);
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }
        if (questionId < questionArrayList.size()) {
            currentQuestion = questionArrayList.get(questionId);
            setQuestionView();
        } else {
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); //Your score
            intent.putExtras(b); //Put your score to your next Intent
            startActivity(intent);
            finish();
        }
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    /*private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        ReviewerDbHelper mDbHelper = new ReviewerDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //mDbHelper.addQuestions();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        try (Cursor cursor = db.rawQuery("DELETE FROM " + QuestionEntry.TABLE_NAME, null)) {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.question_textview);
            displayView.setText("Number of rows in question database table: " + cursor.getCount());
        }

        // Always close the cursor when you're done reading from it. This releases all its
        // resources and makes it invalid.
    }*/


    private void setQuestionView() {
        questionNumberTextView.setText("Question " + (questionId + 1));
        questionTextView.setText(currentQuestion.getQUESTION());

        ArrayList<String> choices = new ArrayList<>();
        // add choices including the answer to the list
        choices.add(currentQuestion.getOPTA());
        choices.add(currentQuestion.getOPTB());
        choices.add(currentQuestion.getOPTC());
        choices.add(currentQuestion.getANSWER());

        // OK! its Shake time, I hope nobody get the right answer ahahaha
        Collections.shuffle(choices);

        // set the shuffled choices
        choiceAButton.setText(choices.get(0));
        choiceBButton.setText(choices.get(1));
        choiceCButton.setText(choices.get(2));
        choiceDButton.setText(choices.get(3));
        questionId++;
    }
}
