package com.philcst.www.engineeringreviewer;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.philcst.www.engineeringreviewer.adapter.TopicAdapter;
import com.philcst.www.engineeringreviewer.data.Topic;
import com.philcst.www.engineeringreviewer.data.TopicData;

import java.util.ArrayList;

public class TopicListActivity extends AppCompatActivity implements TopicAdapter.OnItemClickListener {
    // TODO: Fix the state reset problem, apply the concept of using bundles for saving states like
    // what fragment is currently attached before rotating the screen layout?

    private ArrayList<Topic> topicItems;
    private Resources res;

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

        res = getResources();

        //load Data
        loadData();

        // setting up the initial fragment
        TopicListFragment firstTopicList = new TopicListFragment();

        // Sets the Adapter,its dataset, and implements the callback required if the topic is selected.
        firstTopicList.setTopicAdapter(new TopicAdapter(topicItems, this));

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
        // ready the fragment
        TopicListFragment subTopicList = new TopicListFragment();
        subTopicList.setTopicAdapter(new TopicAdapter(mainTopic.getSubTopicList(),
                new TopicAdapter.OnItemClickListener() {
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

    // function for assigning the data to these containers members, mTopicItems, mGeasSubTopicItems
    // and mEnggMathSubTopicItems
    // TODO: we need to rethink how the data is access that is not hard coded to a class instead you
    // can query data to the database.
    private void loadData() {
        topicItems = new ArrayList<>();

        String[] titles = res.getStringArray(R.array.main_topic_names);
        String[] desc = res.getStringArray(R.array.main_topic_desc);

        for (int x = 0; x < titles.length; x++) {

            ArrayList<Topic> subTopics = new ArrayList<>();

            String[][] subTopicTitles = {
                    res.getStringArray(R.array.geas_topic_names),
                    res.getStringArray(R.array.engg_math_topic_names)
            };

            String[][] subTopicDesc = {
                    res.getStringArray(R.array.geas_topic_desc),
                    res.getStringArray(R.array.engg_math_topic_desc)
            };

            for (int y = 0; y < subTopicTitles[x].length; y++) {
                subTopics.add(y, new Topic(TopicData.subTopicImages[x][y],
                        subTopicTitles[x][y],
                        subTopicDesc[x][y],
                        TopicData.subTopicContents[x][y]
                ));
            }


            topicItems.add(x, new Topic(TopicData.images[x], titles[x], desc[x], subTopics));
        }
    }
}
