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
import com.philcst.www.engineeringreviewer.data.ScoreEntry;
import com.philcst.www.engineeringreviewer.data.Topic;
import com.philcst.www.engineeringreviewer.view.MathView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class QuizActivity extends AppCompatActivity implements ChoicesFragment.OnFragmentChoiceListener,
        AnswerFragment.OnFragmentInteractionListener {

    private String TAG = QuizActivity.class.getSimpleName();
    // array list to holds questions
    private ArrayList<Question> questionArrayList;

    // to hold score
    private int score = 0;

    // to hold the current question Id
    private int questionId = 0;
    // the current question object
    private Question currentQuestion;

    // for referencing views
    private MathView questionMathView;
    private TextView scoreTextView;
    private TextView questionNumberTextView;

    // Saving the data for determining quiz mode and a kind of topic
    private String selectedTopic;
    private QuizMode mode;
    private Topic topic;
    private boolean isEnd;
    private String correctAnswer;

    // For Timed Quiz Mode
    private TextView timerView;
    private boolean isTimerViewSet = false;
    private CountDownTimerHandler countDownTimerHandler;

    // For Vitali-3
    private int numOfWrongs = 0;

    // Fragments
    private ChoicesFragment choicesFragment;

    // number of questions
    private int numberOfQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ActionBar actionBar = getSupportActionBar();

        // set the up button at the action bar
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // refer the views
        questionMathView = (MathView) findViewById(R.id.question_view);
        questionNumberTextView = (TextView) findViewById(R.id.question_number);
        scoreTextView = (TextView) findViewById(R.id.score_number);


        //get the data from preceding activity
        mode = getIntent().getParcelableExtra("quiz_mode");
        topic = getIntent().getParcelableExtra("topic");
        numberOfQuestions = getIntent().getIntExtra("number_of_questions", 20);

        String[] mainTopics = getResources().getStringArray(R.array.main_topic_names);
        // debug code
        // TODO: improve this process
        // if topic is not null
        if (topic != null) {
            // compare if the topic's name is equal to "Engineering Mathematics"
            if (topic.getName().equals(mainTopics[1])) {
                selectedTopic = "engg_math:%";

                // compare if the topic's name is equal to "General Engineering and Applied Science"
            } else if (topic.getName().equals(mainTopics[0])) {
                selectedTopic = "geas";
            } /*else if (topic.getContent().equals("random")) {
                selectedTopic = null;
            }*/
        } else {    // if null, which means it is random topic!!
            selectedTopic = null;
        }

        // setting views for appropriate modes.
        if (mode == QuizMode.TIMED && !isTimerViewSet) {
            //ok set the timer textView to visible
            // reference of that TextView is set here.
            timerView = (TextView) findViewById(R.id.timer);

            // show that view because it is timed mode
            timerView.setVisibility(View.VISIBLE);

            // only execute this block once.
            isTimerViewSet = true;  // false;
        }

        setTitle(mode.getName());
        prepareQuestions();
        setQuestionView();
    }

    /**
     * function for preparing question data from the local database.
     */
    private void prepareQuestions() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        // save the questions in the array list (memory)
        questionArrayList = databaseAccess.getQuestions(numberOfQuestions, selectedTopic);

        // shuffle the questions
        Collections.shuffle(questionArrayList);

        // set the current question (in the first run, the questionId variable is equal to 0
        // which means the first question in the list will be shown.
        currentQuestion = questionArrayList.get(questionId);
    }

    /**
     * function for setting the view's data, showing the Questions and the Choices.
     */
    private void setQuestionView() {
        // in order to show the questions, set the data into the views
        questionNumberTextView.setText("Question " + (questionId + 1));
        // show the question in that view
        questionMathView.setText(currentQuestion.getQUESTION());

        // is this timed quiz mode??
        if (mode == QuizMode.TIMED) {
            // set the countdown timer to 2 mins
            countDownTimerHandler = new CountDownTimerHandler(120000, 1000);
            countDownTimerHandler.start();
        }

        setChoices();
        correctAnswer = currentQuestion.getANSWER();

        questionId++;


        // flag for determining if it is end
        isEnd = false;
        // if it is the last question or in vitali-3, if you have 3 wrongs already.
        if (questionId >= questionArrayList.size()) {
            isEnd = true;
        }
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
            // ask the user if he/she is sure to exit the current quiz.
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

    /**
     * function to set the choices into the choices fragment. shuffling the choice makes the quiz
     * very unpredictable.
     * this function is called in setQuestionView()
     */
    private void setChoices() {
        ArrayList<String> choices = new ArrayList<>();
        // add choices including the answer to the list
        choices.add(currentQuestion.getOPTA());
        choices.add(currentQuestion.getOPTB());
        choices.add(currentQuestion.getOPTC());
        choices.add(currentQuestion.getANSWER());

        // OK! its Shake time, I hope nobody get the right answer ha ha ha
        Collections.shuffle(choices);

        // if the fragment in not been instantiated
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
            // send the data through broadcast
            sendBroadcast(intent);
        }
    }

    /**
     * This method is attached to the cardview choices of ChoicesFragment.
     * It is implemented here on how to process the selected choice if it is wrong or correct
     * It also handles if it is end of the quiz it will go to score activity.
     *
     * @param v View the is being listening.
     */
    @Override
    public void onFragmentChoiceSelect(View v) {

        CardView parentCard = (CardView) v;
        MathView choice = (MathView) parentCard.getChildAt(0);
        Log.i(TAG, "onClick: text=" + choice.getText());

        // check if this is set before. "only in timed quiz mode"
        if (countDownTimerHandler != null) {
            // stop! stop! he/she already answered
            countDownTimerHandler.cancel();
            countDownTimerHandler = null;
        }

        // check your answer now
        if (correctAnswer.equals(choice.getText())) {
            // it's correct, plus one
            score++;
            scoreTextView.setText("" + score);

            // swap the fragment to show to correct answer and feedback the user if he/she got it.
            onChangeToAnswerFragment(correctAnswer, true, isEnd);
        } else { // if you're wrong
            // check if you are in vitali-3 mode
            if (mode == QuizMode.VITALI_3) {
                // querying ids to x marks
                int xmarks[] = {R.id.x_1, R.id.x_2, R.id.x_3};

                AppCompatImageView xMark = (AppCompatImageView) findViewById(xmarks[numOfWrongs++]);
                // show the x mark
                xMark.setVisibility(View.VISIBLE);

                // check if the user has 3 wrongs
                if (numOfWrongs == 3) {
                    isEnd = true;
                }
            }
            // swap the fragment to show to correct answer and feedback the user if he/she got it.
            onChangeToAnswerFragment(correctAnswer, false, isEnd);
        }
    }

    private void onNextQuestionOrEnd() {
        if (!isEnd) {
            // the quiz will go on, the questionId is less than the total quiz.
            currentQuestion = questionArrayList.get(questionId);
            setQuestionView();
        } else {
            // ohhh its really the end
            endQuiz();
        }
    }

    private void endQuiz() {
        String quizTopic;
        // check the topic name again
        if (topic == null) {
            quizTopic = "Random Topic";
        } else {
            quizTopic = topic.getName();
        }

        double percentage = ((double) score / (double) numberOfQuestions) * 100;
        Log.i(TAG, "total score percentage: " + percentage);

        // create an ScoreEntry object and store it in the databse.
        ScoreEntry entry = new ScoreEntry(
                0,
                new Date(Calendar.getInstance().getTimeInMillis()),
                quizTopic,
                score,
                percentage,
                percentage >= 70.0 ? "passed" : "failed"
        );

        // storing the data in the database.
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.recordScore(entry, mode);

        // going to the score activity.
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score); //Put your score to your next Intent
        intent.putExtra("percentage", percentage);
        intent.putExtra("num_of_questions", numberOfQuestions);
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

        CountDownTimerHandler(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            @SuppressLint("SimpleDateFormat")
            DateFormat formatter = new SimpleDateFormat("mm:ss");
            if (timerView != null) {
                timerView.setText(formatter.format(new Date(millisUntilFinished)));
            }
        }

        @Override
        public void onFinish() {
            // swap the fragment to show to correct answer and feedback the user if he/she got it.
            onChangeToAnswerFragment(correctAnswer, false, isEnd);
            //onNextQuestionOrEnd();
        }
    }

    private void onChangeToAnswerFragment(String answer, boolean isCorrect, boolean isEnd) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.quiz_fragment_placement, AnswerFragment.newInstance(answer, isCorrect, isEnd))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        choicesFragment = null;
        if (countDownTimerHandler != null) {
            countDownTimerHandler.cancel();
            countDownTimerHandler = null;
        }
    }*/

    @Override
    protected void onDestroy() {
        // trying to freeing up resources.
        choicesFragment = null;
        if (countDownTimerHandler != null) {
            countDownTimerHandler.cancel();
            countDownTimerHandler = null;
        }
        super.onDestroy();
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
