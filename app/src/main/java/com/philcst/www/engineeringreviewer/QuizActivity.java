package com.philcst.www.engineeringreviewer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.philcst.www.engineeringreviewer.data.DatabaseAccess;
import com.philcst.www.engineeringreviewer.data.Question;
import com.philcst.www.engineeringreviewer.data.QuizMode;
import com.philcst.www.engineeringreviewer.data.Topic;
import com.philcst.www.engineeringreviewer.view.MathView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class QuizActivity extends AppCompatActivity implements ChoicesFragment.OnFragmentChoiceListener,
        AnswerFragment.OnFragmentInteractionListener{

    private String TAG = QuizActivity.class.getSimpleName();
    private ArrayList<Question> questionArrayList;
    private int score = 0;
    private int questionId = 0;
    private Question currentQuestion;
    private MathView questionMathView;
    private TextView scoreTextView;
    private TextView questionNumberTextView;

    // Saving the data for determining quiz mode and a kind of topic
    private String selectedTopic;
    private QuizMode mode;

    // For Timed Quiz Mode
    private TextView timerView;
    private volatile boolean isTimerViewSet = false;
    private CountDownTimerHandler countDownTimerHandler;

    // For Vitali-3
    private int numOfWrongs = 0;

    // Fragments
    private ChoicesFragment choicesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // set views
        questionMathView = (MathView) findViewById(R.id.question_view);
        questionNumberTextView = (TextView) findViewById(R.id.question_number);
        scoreTextView = (TextView) findViewById(R.id.score_number);


        //get the data from preceding activity
        mode = getIntent().getParcelableExtra("quiz_mode");
        Topic topic = getIntent().getParcelableExtra("topic");

        String[] mainTopics = getResources().getStringArray(R.array.main_topic_names);
        // debug code
        // TODO: improve this process
        if (topic.getName().equals(mainTopics[1])) {
            selectedTopic = "engg_math:%";
        } else if (topic.getName().equals(mainTopics[0])) {
            selectedTopic = "geas";
        } else if (topic.getContent().equals("random")) {
            selectedTopic = null;
        }
        setTitle(mode.getName());
        prepareQuestions();
        setQuestionView();
    }

    private void prepareQuestions() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        questionArrayList = databaseAccess.getQuestions(20, selectedTopic);
        Collections.shuffle(questionArrayList);
        currentQuestion = questionArrayList.get(questionId);
    }

    private void setQuestionView() {
        questionNumberTextView.setText("Question " + (questionId + 1));
        questionMathView.setText(currentQuestion.getQUESTION());

        // is this timed quiz mode??
        if (mode.getName().equals(QuizMode.TIMED.getName())) {
            //ok set the timer textView to visible
            if (!isTimerViewSet){
                timerView = (TextView) findViewById(R.id.timer);
                timerView.setVisibility(View.VISIBLE);
                isTimerViewSet = false;
            }
            // set the countdown timer
            countDownTimerHandler = new CountDownTimerHandler(120000, 1000);
            countDownTimerHandler.start();
        }

        setChoices();
        questionId++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /*
        Overriding back mechanisms
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            new ExitQuizDialogFragment().show(getFragmentManager(), TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        Overriding back mechanisms
     */
    @Override
    public void onBackPressed() {
        new ExitQuizDialogFragment().show(getFragmentManager(), TAG);
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

        if (choicesFragment == null) {
            Log.i("New Instance created", TAG);
            choicesFragment = ChoicesFragment.newInstance(choices.get(0), choices.get(1),
                    choices.get(2), choices.get(3));
            getSupportFragmentManager().beginTransaction().replace(R.id.quiz_fragment_placement,
                    choicesFragment).commit();
        } else {
            Log.i("Reseting mathviews", TAG);
            Intent intent = new Intent(ChoicesFragment.ACTION_SET_NEW_CHOICES);
            Bundle bundle = new Bundle();
            bundle.putString(ChoicesFragment.ARG_CHOICE_A, choices.get(0));
            bundle.putString(ChoicesFragment.ARG_CHOICE_B, choices.get(1));
            bundle.putString(ChoicesFragment.ARG_CHOICE_C, choices.get(2));
            bundle.putString(ChoicesFragment.ARG_CHOICE_D, choices.get(3));
            intent.putExtras(bundle);
            sendBroadcast(intent);
        }
    }

    /**
     * This method is attached to the cardview choices of ChoicesFragment.
     * It is implemented here on how to process the selected choice if it is wrong or correct
     * It also handles if it is end of the quiz it will go to score activity.
     * @param v View the is being listening.
     */
    @Override
    public void onFragmentChoiceSelect(View v) {
        String answer = currentQuestion.getANSWER();
        CardView parentCard = (CardView) v;
        MathView choice = (MathView) parentCard.getChildAt(0);
        Log.i(TAG, "onClick: text=" + choice.getText());

        // check if this is set before. "only in timed quiz mode"
        if (countDownTimerHandler != null) {
            // stop! stop! he/she already answered
            countDownTimerHandler.cancel();
            countDownTimerHandler = null;
        }

        boolean isEnd = false;
        if (questionId >= questionArrayList.size()) {
            isEnd = true;
        }

        // check your answer now
        if (answer.equals(choice.getText())) {
            // it's correct, plus one
            score++;
            scoreTextView.setText("" + score);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.quiz_fragment_placement, AnswerFragment.newInstance(answer, true, isEnd))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        } else { // if you're wrong
            // check if you are in vitali-3 mode
            if (mode.getName().equals(QuizMode.VITALI_3.getName())) {
                // querying ids to x marks
                int xmarks[] = { R.id.x_1, R.id.x_2, R.id.x_3 };

                AppCompatImageView xMark = (AppCompatImageView) findViewById(xmarks[numOfWrongs++]);
                // show the x mark
                xMark.setVisibility(View.VISIBLE);

            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.quiz_fragment_placement, AnswerFragment.newInstance(answer, false, isEnd))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }
    }

    private void onNextQuestionOrEnd() {
        if (mode.getName().equals(QuizMode.VITALI_3.getName())) {
            // go home now, you are not vital enough to end the quiz without 3 wrongs
            if (numOfWrongs == 3) {
                endQuiz();
            }
        } else {
            if (questionId < questionArrayList.size()) {
                currentQuestion = questionArrayList.get(questionId);
                setQuestionView();
            } else {
                endQuiz();
            }
        }
    }

    private void endQuiz() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        Bundle b = new Bundle();
        b.putInt("score", score); //Your score
        intent.putExtras(b); //Put your score to your next Intent
        startActivity(intent);
        finish();
    }

    @Override
    public void onFragmentNextQuestion() {
        getSupportFragmentManager().popBackStack();
        //procced to the next question or end
        onNextQuestionOrEnd();
    }

    private class CountDownTimerHandler extends CountDownTimer {

        CountDownTimerHandler(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            @SuppressLint("SimpleDateFormat")
            DateFormat formatter = new SimpleDateFormat("mm:ss");
            if (timerView != null){
                timerView.setText(formatter.format(new Date(millisUntilFinished)));
            }
        }

        @Override
        public void onFinish() {
            onNextQuestionOrEnd();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        choicesFragment = null;
        if (countDownTimerHandler != null) {
            countDownTimerHandler.cancel();
            countDownTimerHandler = null;
        }
    }

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
}
