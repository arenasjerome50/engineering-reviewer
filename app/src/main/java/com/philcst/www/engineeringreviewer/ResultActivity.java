package com.philcst.www.engineeringreviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private String TAG = ResultActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        // get score
        int score = getIntent().getIntExtra("score", 0);
        double percentage = getIntent().getDoubleExtra("percentage", 0);
        int numOfQuestions = getIntent().getIntExtra("num_of_questions", 0);
        Log.i(TAG, "total score percentage: " + percentage);
        // get text view
        TextView percentageTextView = (TextView) findViewById(R.id.percentage_textview);
        TextView totalNumberTextView = (TextView) findViewById(R.id.num_of_questions);
        TextView scoreTextView = (TextView) findViewById(R.id.score_textview);
        TextView feedbackTextView = (TextView) findViewById(R.id.feedback_textview);

        percentageTextView.setText(Double.toString(percentage) + "%");
        totalNumberTextView.setText("Number of questions: " + Integer.toString(numOfQuestions));
        scoreTextView.setText("Number of correct answer: " + Integer.toString(score));

        if (percentage <= 24.0) {
            feedbackTextView.setText("Failed!");
        } else if (percentage >= 25.0 || percentage <= 36.0) {
            feedbackTextView.setText("Conditional");
        } else if (percentage >= 33.0 || percentage <= 42.0) {
            feedbackTextView.setText("Passer");
        } else if (percentage >= 43.0) {
            feedbackTextView.setText("Topnotcher");
        }
    }
}
