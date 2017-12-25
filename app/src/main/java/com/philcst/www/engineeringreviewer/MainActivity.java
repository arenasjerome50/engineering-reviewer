package com.philcst.www.engineeringreviewer;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = MainActivity.class.getSimpleName();
    //private long lastPress; // for handling exit confirmation toast
    //private Toast backpressToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // reference those buttons from the layout
        Button viewLectureButton = (Button) findViewById(R.id.btn_view_lecture);
        Button startQuizButton = (Button) findViewById(R.id.btn_start_quiz);
        Button quitButton = (Button) findViewById(R.id.btn_quit);

        // attach listener
        viewLectureButton.setOnClickListener(this);
        startQuizButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);
    }

    /**
     * Function for listening button taps
     * @param v View that is clicked
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_view_lecture:
                // start TopicListActivity
                startActivity(new Intent(MainActivity.this, TopicListActivity.class));
                break;
            case R.id.btn_start_quiz:
                // start QuizActivity
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
                break;
            case R.id.btn_quit:
                // Exits the app
                onBackPressed();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /*
        Modified for confirmation exit mechanism
     */
    /*@Override
    public void onBackPressed() {
        // get the current time
        long currentTime = System.currentTimeMillis();

        // wait for 5 sec, show a toast and save the current time you pressed the button
        if(currentTime - lastPress > 5000) {
            backpressToast = Toast.makeText(getBaseContext(), "press again to exit", Toast.LENGTH_LONG);
            backpressToast.show();
            lastPress = currentTime;
        } else {
            // go exit the app
            if (backpressToast != null) backpressToast.cancel();
            super.onBackPressed();
        }
    }*/

    @Override
    public void onBackPressed() {
        DialogFragment exitDialog = new ExitDialogFragment();
        exitDialog.show(getFragmentManager(), TAG);
    }
}
