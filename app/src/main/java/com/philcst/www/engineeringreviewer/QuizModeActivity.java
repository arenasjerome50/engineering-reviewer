package com.philcst.www.engineeringreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.philcst.www.engineeringreviewer.adapter.BigListItemAdapter;
import com.philcst.www.engineeringreviewer.data.QuizMode;
import com.philcst.www.engineeringreviewer.interfaces.OnItemClickListener;


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

        final ListFragment modeListFragment = new ListFragment();
        modeListFragment.setAdapter(new BigListItemAdapter(QuizMode.getValuesList(),
                new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                QuizMode mode = QuizMode.getValuesList().get(position);
                Intent intent = new Intent(QuizModeActivity.this, TopicListActivity.class);
                intent.putExtra("quiz_mode", (Parcelable) mode);

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
