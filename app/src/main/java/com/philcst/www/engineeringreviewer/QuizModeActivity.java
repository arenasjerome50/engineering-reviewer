package com.philcst.www.engineeringreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.philcst.www.engineeringreviewer.adapter.QuizModeAdapter;
import com.philcst.www.engineeringreviewer.data.QuizMode;
import com.philcst.www.engineeringreviewer.interfaces.OnItemClickListener;

import java.util.Arrays;


public class QuizModeActivity extends AppCompatActivity {

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

        final ModeListFragment modeListFragment = new ModeListFragment();
        modeListFragment.setAdapter(new QuizModeAdapter(Arrays.asList(QuizMode.values()), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(QuizModeActivity.this, TopicListActivity.class);
                Bundle args = new Bundle();
                args.putString("quiz_mode", QuizMode.NORMAL.getName());
                intent.putExtras(args);
                startActivity(intent);
            }
        }));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame, modeListFragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
