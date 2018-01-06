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
                startActivity(new Intent(this, TopicListActivity.class));
                break;
            case R.id.btn_start_quiz:
                // start QuizActivity
                startActivity(new Intent(this, QuizModeActivity.class));
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
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return true;
    }

    /*
        Modified for confirmation exit mechanism
     */
    @Override
    public void onBackPressed() {
        DialogFragment exitDialog = new ExitDialogFragment();
        exitDialog.show(getFragmentManager(), TAG);
    }
}
