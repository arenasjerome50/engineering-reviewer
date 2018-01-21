package com.philcst.www.engineeringreviewer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.philcst.www.engineeringreviewer.adapter.BigListItemAdapter;
import com.philcst.www.engineeringreviewer.data.QuizMode;
import com.philcst.www.engineeringreviewer.interfaces.OnItemClickListener;


public class QuizModeActivity extends AppCompatActivity {

    BroadcastReceiver receiver;

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

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null && action.equals("finish_quiz_mode_activity")) {
                    finish();
                }
            }
        };
        registerReceiver(receiver, new IntentFilter("finish_quiz_mode_activity"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }
}