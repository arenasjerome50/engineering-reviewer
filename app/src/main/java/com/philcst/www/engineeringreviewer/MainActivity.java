package com.philcst.www.engineeringreviewer;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;

import com.philcst.www.engineeringreviewer.adapter.MainMenuAdapter;
import com.philcst.www.engineeringreviewer.data.Topic;
import com.philcst.www.engineeringreviewer.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{

    private String TAG = MainActivity.class.getSimpleName();
    private static final int REVIEW_LECTURES = 0;
    private static final int START_QUIZ = 1;
    private static final int SETTINGS = 2;
    private static final int ABOUT = 3;
    //private long lastPress; // for handling exit confirmation toast
    //private Toast backpressToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO: test code
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        setupWindowAnimations();

        ArrayList<Topic> menuItems = Topic.getMainMenu(getResources());
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.main_menu_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
        //        layoutManager.getOrientation());
        //mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        int[] backgrounds = {
                R.drawable.main_bg_1,
                R.drawable.main_bg_2,
                R.drawable.main_bg_3,
                R.drawable.main_bg_4
        };
        mRecyclerView.setAdapter(new MainMenuAdapter(menuItems, backgrounds, this));

        // TODO: test code
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPreferences.getBoolean(SettingsActivity.KEY_PREF_EXAMPLE_SWITCH, false);
    }

    @SuppressLint("RtlHardcoded")
    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slideTransition = new Slide();
            slideTransition.setSlideEdge(Gravity.LEFT);
            slideTransition.setDuration(500);
            getWindow().setReenterTransition(slideTransition);
            getWindow().setExitTransition(slideTransition);
        }
    }

    @Override
    public void onItemClick(int position) {
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        switch (position) {
            case REVIEW_LECTURES:
                // start TopicListActivity
                startActivity(new Intent(this, TopicListActivity.class),
                        transitionActivityOptions.toBundle());
                break;
            case START_QUIZ:
                // start QuizActivity
                startActivity(new Intent(this, QuizModeActivity.class),
                        transitionActivityOptions.toBundle());
                break;
            case SETTINGS:
                // start SettingsActivity
                startActivity(new Intent(this, SettingsActivity.class),
                        transitionActivityOptions.toBundle());
                break;
            case ABOUT:
                break;
        }
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
