package com.philcst.www.engineeringreviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        bar.setStepSize(0.5f);
        // get text view
        TextView result = (TextView) findViewById(R.id.result_textview);
        // get score
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        // display score
        bar.setRating(score);

        switch (score) {
            case 0:
            case 1:
            case 2:
                result.setText("Opps, try again bro, keep learning");
                break;
            case 3:
            case 4:
                result.setText("Hmmmm.. maybe you have been reading a lot of JasaProgrammer quiz");
                break;
            case 5:
                result.setText("Who are you? A student in JP???");
                break;
        }
    }
}
