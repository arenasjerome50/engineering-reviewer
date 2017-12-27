package com.philcst.www.engineeringreviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.philcst.www.engineeringreviewer.adapter.TopicAdapter;
import com.philcst.www.engineeringreviewer.data.Topic;

import java.util.ArrayList;


public class QuizModeActivity extends AppCompatActivity {

    ArrayList<Topic> modeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up the Up button at the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadModes();

        final TopicListFragment modeList = new TopicListFragment();

        modeList.setTopicAdapter(new TopicAdapter(modeItems, new TopicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(QuizModeActivity.this, "Selected Mode: " + modeItems.get(position), Toast.LENGTH_SHORT).show();
            }
        }));

        getSupportFragmentManager().beginTransaction().add(R.id.frame, modeList).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadModes() {
        modeItems = new ArrayList<>();

        String[] modes = getResources().getStringArray(R.array.quiz_modes);
        String[] mode_desc = getResources().getStringArray(R.array.quiz_modes_desc);

        int[] images = {
                R.drawable.ic_normal_quiz,
                R.drawable.ic_timed_quiz,
                R.drawable.ic_vitali_quiz
        };

        String[] mode_codes = { "normal", "timed", "vitali_3" };

        for (int x = 0; x < modes.length; x++) {
            modeItems.add(x, new Topic(images[x], modes[x], mode_desc[x], mode_codes[x]));
        }
    }
}
