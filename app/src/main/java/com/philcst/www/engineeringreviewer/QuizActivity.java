package com.philcst.www.engineeringreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.philcst.www.engineeringreviewer.data.DatabaseAccess;
import com.philcst.www.engineeringreviewer.data.Question;
import com.philcst.www.engineeringreviewer.data.QuizMode;
import com.philcst.www.engineeringreviewer.data.Topic;
import com.philcst.www.engineeringreviewer.view.MathView;

import java.util.ArrayList;
import java.util.Collections;


public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = QuizActivity.class.getSimpleName();
    private ArrayList<Question> questionArrayList;
    private int score = 0;
    private int questionId = 0;
    private Question currentQuestion;
    private MathView questionMathView;
    private TextView scoreTextView;
    private TextView questionNumberTextView;
    private MathView choiceA, choiceB, choiceC, choiceD;

    private String selectedTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //get the data from preceding activity
        QuizMode mode = getIntent().getParcelableExtra("quiz_mode");
        Topic topic = getIntent().getParcelableExtra("topic");

        String[] mainTopics = getResources().getStringArray(R.array.main_topic_names);
        // debug code
        // TODO: improve this process
        if (topic.getTitle().equals(mainTopics[1])) {
            selectedTopic = "engg_math:%";
        } else if (topic.getTitle().equals(mainTopics[0])) {
            selectedTopic = "geas";
        } else if (topic.getContent().equals("random")) {
            selectedTopic = null;
        }

        setTitle(mode.getName());

        prepareQuestions();
        setInitialViews();
    }

    private void setInitialViews() {
        // set views
        currentQuestion = questionArrayList.get(questionId);
        questionMathView = (MathView) findViewById(R.id.question_view);
        questionNumberTextView = (TextView) findViewById(R.id.question_number);
        scoreTextView = (TextView) findViewById(R.id.score_number);

        // set choices views
        choiceA = (MathView) findViewById(R.id.choice_math_view_a);
        choiceB = (MathView) findViewById(R.id.choice_math_view_b);
        choiceC = (MathView) findViewById(R.id.choice_math_view_c);
        choiceD = (MathView) findViewById(R.id.choice_math_view_d);
        CardView cardA = (CardView) findViewById(R.id.choice_card_view_a);
        CardView cardB = (CardView) findViewById(R.id.choice_card_view_b);
        CardView cardC = (CardView) findViewById(R.id.choice_card_view_c);
        CardView cardD = (CardView) findViewById(R.id.choice_card_view_d);
        //nextButton = (Button) findViewById(R.id.next_button);

        cardA.setOnClickListener(this);
        cardB.setOnClickListener(this);
        cardC.setOnClickListener(this);
        cardD.setOnClickListener(this);

        setQuestionView();
        //displayDatabaseInfo();
    }

    private void prepareQuestions() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        questionArrayList = databaseAccess.getQuestions(20, selectedTopic);
        Collections.shuffle(questionArrayList);
    }

    private void setQuestionView() {
        questionNumberTextView.setText("Question " + (questionId + 1));
        questionMathView.setText(currentQuestion.getQUESTION());
        setChoices();
        questionId++;
    }

    private void setChoices() {
        ArrayList<String> choices = new ArrayList<>();
        // add choices including the answer to the list
        choices.add(currentQuestion.getOPTA());
        choices.add(currentQuestion.getOPTB());
        choices.add(currentQuestion.getOPTC());
        choices.add(currentQuestion.getANSWER());

        // OK! its Shake time, I hope nobody get the right answer ha ha ha
        Collections.shuffle(choices);

        choiceA.setText(choices.get(0));
        choiceB.setText(choices.get(1));
        choiceC.setText(choices.get(2));
        choiceD.setText(choices.get(3));
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
        try (Cursor cursor = db.rawQuery("DELETE FROM " + ReviewerContract.QuestionEntry.TABLE_NAME, null)) {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.question_view);
            displayView.setText("Number of rows in question database table: " + cursor.getCount());
        }

        // Always close the cursor when you're done reading from it. This releases all its
        // resources and makes it invalid.
    }*/

    @Override
    public void onClick(View v) {
        //Toast.makeText(QuizActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
        String answer = currentQuestion.getANSWER();
        CardView parentCard = (CardView) v;
        MathView choice = (MathView) parentCard.getChildAt(0);
        Log.i(TAG, "onClick: text=" + choice.getText());

        if (answer.equals(choice.getText())) {
            score++;
            scoreTextView.setText("" + score);
            Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
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
}
