package com.philcst.www.engineeringreviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        // get rating bar object
        RatingBar bar = (RatingBar) findViewById(R.id.rating_bar);
        bar.setNumStars(5);
        bar.setStepSize(0.25f);
        // get score
        int score = getIntent().getIntExtra("score", 0);
        // display score
        bar.setRating(score);

        // get text view
        TextView feedbackTextView = (TextView) findViewById(R.id.feedback_textview);
        TextView scoreTextView = (TextView) findViewById(R.id.score_textview);

        scoreTextView.setText(Integer.toString(score));

        double scoreRatings = score/20;

        if (scoreRatings <= 0.24) {
            feedbackTextView.setText("Failed!");
        } else if (scoreRatings >= 0.25 || scoreRatings <= 0.36) {
            feedbackTextView.setText("Conditional");
        } else if (scoreRatings >= 0.33 || scoreRatings <= 0.42) {
            feedbackTextView.setText("Passer");
        } else if (scoreRatings >= 0.43) {
            feedbackTextView.setText("Topnotcher");
        }
    }
}
