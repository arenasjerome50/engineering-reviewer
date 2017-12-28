package com.philcst.www.engineeringreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.philcst.www.engineeringreviewer.adapter.TopicAdapter;
import com.philcst.www.engineeringreviewer.data.Topic;
import com.philcst.www.engineeringreviewer.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class TopicListActivity extends AppCompatActivity implements OnItemClickListener {
    // TODO: Fix the state reset problem, apply the concept of using bundles for saving states like
    // what fragment is currently attached before rotating the screen layout?

    private ArrayList<Topic> topicItems;

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

        //load Data
        topicItems = Topic.loadTopicData(getResources());

        // setting up the initial fragment
        TopicListFragment firstTopicList = new TopicListFragment();

        // Sets the Adapter,its dataset, and implements the callback required if the topic is selected.
        firstTopicList.setAdapter(new TopicAdapter(topicItems, this));

        // Add the initial fragment to the Frame.
        getSupportFragmentManager().beginTransaction().add(R.id.frame, firstTopicList).commit();
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


    // Implements the callback which listening at the first added fragment
    @Override
    public void onItemClick(int position) {
        final Topic mainTopic = topicItems.get(position);

        // if it is from QuizModeActivity with quiz_mode data, execute this
        if (getIntent().getExtras() != null) {
            Intent intent = new Intent(TopicListActivity.this, QuizActivity.class);
            Bundle bundle = new Bundle();
            bundle.putAll(getIntent().getExtras());
            bundle.putString("topic", mainTopic.getTitle());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else { // ohhh it's just a reading intent
            // ready the fragment
            TopicListFragment subTopicList = new TopicListFragment();
            subTopicList.setAdapter(new TopicAdapter(mainTopic.getSubTopicList(),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(TopicListActivity.this, ReadingActivity.class);
                            Bundle args = new Bundle();
                            args.putString("title", mainTopic.getSubTopic(position).getTitle());
                            args.putString("content", mainTopic.getSubTopic(position).getContent());
                            intent.putExtras(args);
                            startActivity(intent);
                        }
                    }));

            // swap the fragments
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.frame, subTopicList)
                    .addToBackStack(null).commit();
        }

    }

}
